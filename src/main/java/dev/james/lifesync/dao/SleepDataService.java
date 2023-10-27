package dev.james.lifesync.dao;

import dev.james.lifesync.model.LifeSyncUser;
import dev.james.lifesync.model.SleepData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.List;

@Service
public class SleepDataService {

    private final SleepDataDAO sleepDataDAO;

    @Autowired
    public SleepDataService(SleepDataDAO sleepDataDAO) {
        this.sleepDataDAO = sleepDataDAO;
    }

    public List<SleepData> getUserSleepData(int userId) {
        // TODO: Eventually the timescale should be controlled by the user
        // TODO: If there is a datapoint missing, an empty SleepData object should be created
//        return sleepDataDAO.findAllByUseridAndEndtimeBetween(userId,
//                Timestamp.from(Instant.now().minus(7, ChronoUnit.DAYS)),
//                Timestamp.from(Instant.now()));
        return sleepDataDAO.findAllByUserid(userId);
    }

}
