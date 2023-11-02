package dev.james.lifesync.dao;

import dev.james.lifesync.model.ExerciseData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseDataDAO extends JpaRepository<ExerciseData, Integer> {
    List<ExerciseData> findAllByUserid(int userid);
}
