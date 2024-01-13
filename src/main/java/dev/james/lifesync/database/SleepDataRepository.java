package dev.james.lifesync.database;

import dev.james.lifesync.entity.SleepData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface SleepDataRepository extends JpaRepository<SleepData, Integer> {
    List<SleepData> findAllByUserid(int userid);

    List<SleepData> findAllByUseridAndEndtimeBetween(int userid, Timestamp finalDataPointTime, Timestamp now);

}
