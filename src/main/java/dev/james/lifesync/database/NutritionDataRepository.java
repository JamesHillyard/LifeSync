package dev.james.lifesync.database;

import dev.james.lifesync.entity.NutritionData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NutritionDataRepository extends JpaRepository<NutritionData, Integer> {
    List<NutritionData> findAllByUserid(int userid);
}
