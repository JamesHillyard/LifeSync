package dev.james.lifesync.dao;

import dev.james.lifesync.model.SleepData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface SleepDataDAO extends JpaRepository<SleepData, Integer> {
    List<SleepData> findAllByUserid(int userid);

    List<SleepData> findAllByUseridAndEndtimeBetween(int userid, Timestamp finalDataPointTime, Timestamp now);

}
