package dev.james.lifesync.database;

import dev.james.lifesync.entity.ExerciseData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseDataRepository extends JpaRepository<ExerciseData, Integer> {
    List<ExerciseData> findAllByUserid(int userid);
}
