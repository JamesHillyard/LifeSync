package dev.james.lifesync.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.james.lifesync.article.ArticleRecommender;
import dev.james.lifesync.dao.ExerciseDataService;
import dev.james.lifesync.entity.ExerciseData;
import dev.james.lifesync.entity.LifeSyncUser;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.sql.Date;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/hlsp/exercise")
public class ExerciseController {

    private final ExerciseDataService exerciseDataService;
    private final ArticleRecommender articleRecommender;

    @Autowired
    public ExerciseController(ExerciseDataService exerciseDataService, ArticleRecommender articleRecommender) {
        this.exerciseDataService = exerciseDataService;
        this.articleRecommender = articleRecommender;
    }

    private void loadUserExerciseData(LifeSyncUser user) {
        user.setExerciseData(exerciseDataService.getUserExerciseData(user.getId()));
    }

    @PostMapping
    public String saveExerciseData(@RequestParam("date") String dateString,
                                   @RequestParam("exerciseDetails") String exerciseDetails,
                                   @SessionAttribute("user") LifeSyncUser user) {
        Date date = Date.valueOf(dateString);
        int userId = user.getId();

        List<ExerciseData> newExerciseData = processUserInput(userId, date, exerciseDetails);
        newExerciseData.forEach(exerciseDataService::saveUserExerciseData);

        return "redirect:/hlsp/exercise";
    }

    protected List<ExerciseData> processUserInput(int userId, Date date, String exerciseDetails) {
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
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return newExercise;
    }

    /**
     *
     * @param model The MVC model being modified to display the exercise.jsp page correctly
     * @param user A class level session attribute representing the user that signed in from the {@link LoginController}
     * @return The hlsp/exercise view
     */
    @GetMapping
    public String getExercisePage(Model model, @SessionAttribute("user") LifeSyncUser user) {
        loadUserExerciseData(user);

        model.addAttribute("exerciseData", user.getExerciseData());
        model.addAttribute("exerciseDataGrouped", groupExerciseData(user.getExerciseData()));
        model.addAttribute("articles", articleRecommender.getExerciseArticles());
        model.addAttribute("averageCaloriesBurnt", calculateAverageCaloriesBurnt(user.getExerciseData()));
        model.addAttribute("averageDurationInMinutes", getAverageDurationInMinutes(user.getExerciseData()));
        model.addAttribute("averageDurationFormatted", getAverageDurationInHoursAndMinutesHumanReadable(getAverageDurationInMinutes(user.getExerciseData())));

        return "hlsp/exercise";
    }

    protected int getAverageDurationInMinutes(List<ExerciseData> userExerciseData) {
        if (userExerciseData == null || userExerciseData.isEmpty()) {
            return 0;
        }

        int totalDurationInMinutes = userExerciseData.stream()
                .mapToInt(ExerciseData::getDuration)
                .sum();
        int numberOfUniqueDates = userExerciseData.stream()
                .map(ExerciseData::getDate)
                .collect(Collectors.toSet())
                .size();

        return totalDurationInMinutes / numberOfUniqueDates;
    }

    protected String getAverageDurationInHoursAndMinutesHumanReadable(int averageDurationInMinutes) {
        if (averageDurationInMinutes > 0) {
            return String.format("%d Hours %d Minutes",
                    Duration.ofMinutes(averageDurationInMinutes).toHoursPart(),
                    Duration.ofMinutes(averageDurationInMinutes).toMinutesPart());
        } else {
            return "0 Hours 0 Minutes";
        }
    }

    protected int calculateAverageCaloriesBurnt(List<ExerciseData> userExerciseData) {
        if (userExerciseData == null || userExerciseData.isEmpty()) {
            return 0;
        }

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
    protected List<ExerciseData> groupExerciseData(List<ExerciseData> userExerciseData) {
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
