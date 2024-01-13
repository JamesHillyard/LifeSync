package dev.james.lifesync.database;

import dev.james.lifesync.entity.ExerciseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseDataService {

    private final ExerciseDataRepository exerciseDataRepository;

    @Autowired
    public ExerciseDataService(ExerciseDataRepository exerciseDataRepository) {
        this.exerciseDataRepository = exerciseDataRepository;
    }

    public List<ExerciseData> getUserExerciseData(int userId) {
        // TODO: Eventually the timescale should be controlled by the user
        // TODO: If there is a datapoint missing, an empty ExerciseData object should be created
        return exerciseDataRepository.findAllByUserid(userId);
    }

    public void saveUserExerciseData(ExerciseData newExerciseData) {
        exerciseDataRepository.save(newExerciseData);
    }
}
