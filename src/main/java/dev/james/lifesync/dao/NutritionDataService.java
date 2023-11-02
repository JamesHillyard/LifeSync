package dev.james.lifesync.dao;

import dev.james.lifesync.model.NutritionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NutritionDataService {

    private final NutritionDataDAO nutritionDataDAO;

    @Autowired
    public NutritionDataService(NutritionDataDAO nutritionDataDAO) {
        this.nutritionDataDAO = nutritionDataDAO;
    }

    public List<NutritionData> getUserNutritionData(int userId) {
        // TODO: Eventually the timescale should be controlled by the user
        // TODO: If there is a datapoint missing, an empty NutritionData object should be created
        return nutritionDataDAO.findAllByUserid(userId);
    }

    public NutritionData saveNutritionData(NutritionData newNutritionData) {
        // Save the NutritionData to generate an ID
        return nutritionDataDAO.save(newNutritionData);
    }
}
