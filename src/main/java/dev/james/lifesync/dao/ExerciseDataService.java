package dev.james.lifesync.dao;

import dev.james.lifesync.entity.ExerciseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseDataService {

    private final ExerciseDataDAO exerciseDataDAO;

    @Autowired
    public ExerciseDataService(ExerciseDataDAO exerciseDataDAO) {
        this.exerciseDataDAO = exerciseDataDAO;
    }

    public List<ExerciseData> getUserExerciseData(int userId) {
        // TODO: Eventually the timescale should be controlled by the user
        // TODO: If there is a datapoint missing, an empty ExerciseData object should be created
        return exerciseDataDAO.findAllByUserid(userId);
    }

    public void saveUserExerciseData(ExerciseData newExerciseData) {
        exerciseDataDAO.save(newExerciseData);
    }
}
