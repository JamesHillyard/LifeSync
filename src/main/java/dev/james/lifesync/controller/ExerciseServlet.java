package dev.james.lifesync.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.james.lifesync.article.ArticleRecommender;
import dev.james.lifesync.dao.ExerciseDataService;
import dev.james.lifesync.model.ExerciseData;
import dev.james.lifesync.model.LifeSyncUser;
import dev.james.lifesync.model.SleepData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@WebServlet(name = "ExerciseServlet", urlPatterns = "/hlsp/exercise")
public class ExerciseServlet extends HttpServlet {

    private final ExerciseDataService exerciseDataService;
    private final ArticleRecommender articleRecommender;

    @Autowired
    public ExerciseServlet(ExerciseDataService exerciseDataService, ArticleRecommender articleRecommender) {
        this.exerciseDataService = exerciseDataService;
        this.articleRecommender = articleRecommender;
    }

    Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());

    private LifeSyncUser loadUserExerciseData(LifeSyncUser user) {
        user.setExerciseData(exerciseDataService.getUserExerciseData(user.getId()));
        return user;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Date date = Date.valueOf((request.getParameter("date")));
        String exerciseDetails = request.getParameter("exerciseDetails");
        int userId = ((LifeSyncUser) request.getSession().getAttribute("user")).getId();

        List<ExerciseData> newExerciseData = processUserInput(userId, date, exerciseDetails);
        newExerciseData.forEach(exerciseDataService::saveUserExerciseData);

        response.sendRedirect(request.getContextPath() + "/hlsp/exercise");
    }

    private List<ExerciseData> processUserInput(int userId, Date date, String exerciseDetails) {
        HttpResponse<String> nutritionixResponse = Unirest.post("https://trackapi.nutritionix.com/v2/natural/exercise")
                .header("x-app-id", "75d270c9") //TODO: Store these as secrets
                .header("x-app-key", "27500d0d891f227514f6f2a9aabc698a") //TODO: Store these as secrets
                .header("x-remote-user-id", "0")
                .header("Content-Type", "application/json")
                .body("{\"query\":\""+exerciseDetails+"\"}")
                .asString();

        ObjectMapper objectMapper = new ObjectMapper();
        List<ExerciseData> newExercise = new ArrayList<>();
        try {
            JsonNode rootNode = objectMapper.readTree(nutritionixResponse.getBody());
            JsonNode exercisesNode = rootNode.get("exercises");

            if (exercisesNode != null && exercisesNode.isArray()) {
                for (JsonNode exerciseNode : exercisesNode) {
                    String name = exerciseNode.get("name").asText();
                    int nfCalories = exerciseNode.get("nf_calories").asInt();
                    int duration = exerciseNode.get("duration_min").asInt();

                    newExercise.add(new ExerciseData(userId, name, date, duration, nfCalories));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newExercise;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LifeSyncUser user = loadUserExerciseData((LifeSyncUser) request.getSession().getAttribute("user"));

        request.getSession().setAttribute("exerciseData", user.getExerciseData());
        request.getSession().setAttribute("exerciseDataGrouped", groupExerciseData(user.getExerciseData()));
        request.getSession().setAttribute("articles", articleRecommender.getExerciseArticles());
        request.getSession().setAttribute("averageCaloriesBurnt", calculateAverageCaloriesBurnt(user.getExerciseData()));
        request.getSession().setAttribute("averageDurationInMinutes", getAverageDurationInMinutes(user.getExerciseData()));
        request.getSession().setAttribute("averageDurationFormatted", getAverageDurationInHoursAndMinutesHumanReadable(getAverageDurationInMinutes(user.getExerciseData())));

        response.sendRedirect(request.getContextPath() + "exercise.jsp");
    }

    public int getAverageDurationInMinutes(List<ExerciseData> userExerciseData) {
        int totalDurationInMinutes = userExerciseData.stream()
                .mapToInt(ExerciseData::getDuration)
                .sum();
        int numberOfUniqueDates = userExerciseData.stream()
                .map(ExerciseData::getDate)
                .collect(Collectors.toSet())
                .size();

        return totalDurationInMinutes / numberOfUniqueDates;
    }

    public String getAverageDurationInHoursAndMinutesHumanReadable(int averageDurationInMinutes) {
        if (averageDurationInMinutes > 0) {
            return String.format("%d Hours %d Minutes",
                    Duration.ofMinutes(averageDurationInMinutes).toHoursPart(),
                    Duration.ofMinutes(averageDurationInMinutes).toMinutesPart());
        } else {
            return null;
        }
    }

    private int calculateAverageCaloriesBurnt(List<ExerciseData> userExerciseData) {
        int totalCaloriesBurnt = userExerciseData.stream()
                .mapToInt(ExerciseData::getCaloriesBurnt)
                .sum();
        int numberOfUniqueDates = userExerciseData.stream()
                .map(ExerciseData::getDate)
                .collect(Collectors.toSet())
                .size();

        return totalCaloriesBurnt / numberOfUniqueDates;
    }

    // To display the chart correctly, the exercise data needs grouping into days and amalgamating into one ExerciseData object
    // This is a transient state and should never be pushed to the database.
    private List<ExerciseData> groupExerciseData(List<ExerciseData> userExerciseData) {
        // Create a HashMap to store the accumulated calories and duration for each date
        Map<Date, int[]> dateMetricsMap = new HashMap<>();
        List<ExerciseData> groupExerciseData = new ArrayList<>();

        // Iterate through the list and accumulate calories and duration for each date
        for (ExerciseData exerciseData : userExerciseData) {
            Date date = exerciseData.getDate();
            int calories = exerciseData.getCaloriesBurnt();
            int duration = exerciseData.getDuration();

            // Check if the date is already in the map, and if not, initialize it with [0, 0]
            dateMetricsMap.putIfAbsent(date, new int[2]);

            // Update the accumulated calories and duration for the date
            int[] metrics = dateMetricsMap.get(date);
            metrics[0] += calories;
            metrics[1] += duration;
        }

        // Now, dateMetricsMap contains the total calories and duration for each date
        for (Map.Entry<Date, int[]> entry : dateMetricsMap.entrySet()) {
            Date date = entry.getKey();
            int[] metrics = entry.getValue();
            int totalCalories = metrics[0];
            int totalDuration = metrics[1];
            groupExerciseData.add(new ExerciseData(date, totalDuration, totalCalories));
        }
        return groupExerciseData;
    }

}
