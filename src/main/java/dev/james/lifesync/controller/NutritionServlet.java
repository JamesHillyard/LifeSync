package dev.james.lifesync.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.james.lifesync.article.ArticleRecommender;
import dev.james.lifesync.dao.NutritionDataService;
import dev.james.lifesync.model.LifeSyncUser;
import dev.james.lifesync.model.NutritionData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "NutritionServlet", urlPatterns = "/hlsp/nutrition")
public class NutritionServlet extends HttpServlet {

    private final NutritionDataService nutritionDataService;
    private final ArticleRecommender articleRecommender;

    @Autowired
    public NutritionServlet(NutritionDataService nutritionDataService, ArticleRecommender articleRecommender) {
        this.nutritionDataService = nutritionDataService;
        this.articleRecommender = articleRecommender;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Date date = Date.valueOf((request.getParameter("date")));
        String nutritionDetails = request.getParameter("nutritionDetails");
        int userId = ((LifeSyncUser) request.getSession().getAttribute("user")).getId();

        List<NutritionData> allInputNutritionData = processUserInput(userId, date, nutritionDetails);
        allInputNutritionData.forEach(nutritionDataService::saveNutritionData);

        response.sendRedirect(request.getContextPath() + "/hlsp/nutrition");
    }

    private List<NutritionData> processUserInput(int userId, Date date, String nutritionDetails) {
        HttpResponse<String> nutritionixResponse = Unirest.post("https://trackapi.nutritionix.com/v2/natural/nutrients")
                .header("x-app-id", "75d270c9") //TODO: Store these as secrets
                .header("x-app-key", "27500d0d891f227514f6f2a9aabc698a") //TODO: Store these as secrets
                .header("x-remote-user-id", "0")
                .header("Content-Type", "application/json")
                .body("{\"query\":\""+nutritionDetails+"\"}")
                .asString();

        ObjectMapper objectMapper = new ObjectMapper();
        List<NutritionData> newNutritionData = new ArrayList<>();
        try {
            JsonNode rootNode = objectMapper.readTree(nutritionixResponse.getBody());
            JsonNode foodsNode = rootNode.get("foods");

            if (foodsNode != null && foodsNode.isArray()) {
                for (JsonNode exerciseNode : foodsNode) {
                    String name = exerciseNode.get("food_name").asText();
                    float nfCalories = (float) exerciseNode.get("nf_calories").asDouble();
                    float nfTotalFat = (float) exerciseNode.get("nf_total_fat").asDouble();
                    float nfSugar = (float) exerciseNode.get("nf_sugars").asDouble();
                    NutritionData nutritionData = new NutritionData(userId, date, name, nfCalories, nfTotalFat, nfSugar);

                    newNutritionData.add(nutritionData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newNutritionData;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LifeSyncUser user = loadUserNutritionData((LifeSyncUser) request.getSession().getAttribute("user"));

        request.getSession().setAttribute("nutritionData", user.getNutritionData());
        request.getSession().setAttribute("nutritionDataGrouped", groupNutritionData(user.getNutritionData()));
        request.getSession().setAttribute("articles", articleRecommender.getNutritionArticles());
        request.getSession().setAttribute("averageCalorieIntake", calculateAverageCaloriesIntake(user.getNutritionData()));
        request.getSession().setAttribute("averageSugarIntake", calculateAverageSugarIntake(user.getNutritionData()));

        response.sendRedirect(request.getContextPath() + "nutrition.jsp");
    }

    private LifeSyncUser loadUserNutritionData(LifeSyncUser user) {
        user.setNutritionData(nutritionDataService.getUserNutritionData(user.getId()));
        return user;
    }

    private float calculateAverageCaloriesIntake(List<NutritionData> userNutritionData) {
        float totalCalorieIntake = 0;
        for (NutritionData nutritionData : userNutritionData) {
            totalCalorieIntake += nutritionData.getCalories();
        }

        int numberOfUniqueDates = userNutritionData.stream()
                .map(NutritionData::getDate)
                .collect(Collectors.toSet())
                .size();

        return totalCalorieIntake / numberOfUniqueDates;
    }

    private float calculateAverageSugarIntake(List<NutritionData> userNutritionData) {
        float totalCalorieIntake = 0;
        for (NutritionData nutritionData : userNutritionData) {
            totalCalorieIntake += nutritionData.getSugar();
        }

        int numberOfUniqueDates = userNutritionData.stream()
                .map(NutritionData::getDate)
                .collect(Collectors.toSet())
                .size();

        return totalCalorieIntake / numberOfUniqueDates;
    }

    // To display the chart correctly, the nutrition data needs grouping into days and amalgamating into one NutritionData object
    // This is a transient state and should never be pushed to the database.
    private List<NutritionData> groupNutritionData(List<NutritionData> userNutritionData) {
        Map<Date, float[]> dateMetricsMap = new HashMap<>();
        List<NutritionData> groupNutritionData = new ArrayList<>();

        // Iterate through the list and accumulate calories, fat and sugar for each date
        for (NutritionData nutritionData : userNutritionData) {

            Date date = nutritionData.getDate();
            float calories = nutritionData.getCalories();
            float fat = nutritionData.getFat();
            float sugar = nutritionData.getSugar();

            // Check if the date is already in the map, and if not, initialize it with [0, 0, 0]
            dateMetricsMap.putIfAbsent(date, new float[3]);

            // Update the accumulated calories, fat and sugar for the date
            float[] metrics = dateMetricsMap.get(date);
            metrics[0] += calories;
            metrics[1] += fat;
            metrics[2] += sugar;
        }

        // Now, dateMetricsMap contains the total calories, fat and sugar for each date
        for (Map.Entry<Date, float[]> entry : dateMetricsMap.entrySet()) {
            Date date = entry.getKey();
            float[] metrics = entry.getValue();
            float totalCalories = metrics[0];
            float totalFat = metrics[1];
            float totalSugar = metrics[2];

            groupNutritionData.add(new NutritionData(date, totalCalories, totalFat, totalSugar));
        }
        return groupNutritionData;
    }

}
