package dev.james.lifesync.dao;

import dev.james.lifesync.model.NutritionData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NutritionDataDAO extends JpaRepository<NutritionData, Integer> {
    List<NutritionData> findAllByUserid(int userid);
}
