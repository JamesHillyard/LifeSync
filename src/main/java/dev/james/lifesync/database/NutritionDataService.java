package dev.james.lifesync.database;

import dev.james.lifesync.entity.NutritionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NutritionDataService {

    private final NutritionDataRepository nutritionDataRepository;

    @Autowired
    public NutritionDataService(NutritionDataRepository nutritionDataRepository) {
        this.nutritionDataRepository = nutritionDataRepository;
    }

    public List<NutritionData> getUserNutritionData(int userId) {
        // TODO: Eventually the timescale should be controlled by the user
        // TODO: If there is a datapoint missing, an empty NutritionData object should be created
        return nutritionDataRepository.findAllByUserid(userId);
    }

    public NutritionData saveNutritionData(NutritionData newNutritionData) {
        // Save the NutritionData to generate an ID
        return nutritionDataRepository.save(newNutritionData);
    }
}
