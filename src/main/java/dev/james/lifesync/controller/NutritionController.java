package dev.james.lifesync.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.james.lifesync.article.ArticleRecommender;
import dev.james.lifesync.database.NutritionDataService;
import dev.james.lifesync.entity.LifeSyncUser;
import dev.james.lifesync.entity.NutritionData;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/hlsp/nutrition")
public class NutritionController {

    private final NutritionDataService nutritionDataService;
    private final ArticleRecommender articleRecommender;

    @Autowired
    public NutritionController(NutritionDataService nutritionDataService, ArticleRecommender articleRecommender) {
        this.nutritionDataService = nutritionDataService;
        this.articleRecommender = articleRecommender;
    }

    @PostMapping
    public String saveNutritionData(@RequestParam("date") String dateString,
                                    @RequestParam("nutritionDetails") String nutritionDetails,
                                    @SessionAttribute("user") LifeSyncUser user) {
        Date date = Date.valueOf(dateString);
        int userId = user.getId();

        List<NutritionData> allInputNutritionData = processUserInput(userId, date, nutritionDetails);
        allInputNutritionData.forEach(nutritionDataService::saveNutritionData);

        return "redirect:/hlsp/nutrition";
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

    @GetMapping
    public String getNutritionPage(Model model, @SessionAttribute("user") LifeSyncUser user) {
        loadUserNutritionData(user);

        model.addAttribute("nutritionData", user.getNutritionData());
        model.addAttribute("nutritionDataGrouped", groupNutritionData(user.getNutritionData()));
        model.addAttribute("articles", articleRecommender.getNutritionArticles());
        model.addAttribute("averageCalorieIntake", calculateAverageCaloriesIntake(user.getNutritionData()));
        model.addAttribute("averageSugarIntake", calculateAverageSugarIntake(user.getNutritionData()));

        return "hlsp/nutrition";
    }

    private void loadUserNutritionData(LifeSyncUser user) {
        user.setNutritionData(nutritionDataService.getUserNutritionData(user.getId()));
    }

    protected float calculateAverageCaloriesIntake(List<NutritionData> userNutritionData) {
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

    protected float calculateAverageSugarIntake(List<NutritionData> userNutritionData) {
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
    protected List<NutritionData> groupNutritionData(List<NutritionData> userNutritionData) {
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
