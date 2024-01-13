package dev.james.lifesync.database;

import dev.james.lifesync.entity.SleepData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SleepDataService {

    private final SleepDataRepository sleepDataRepository;

    @Autowired
    public SleepDataService(SleepDataRepository sleepDataRepository) {
        this.sleepDataRepository = sleepDataRepository;
    }

    public List<SleepData> getUserSleepData(int userId) {
        // TODO: Eventually the timescale should be controlled by the user
        // TODO: If there is a datapoint missing, an empty SleepData object should be created
//        return sleepDataDAO.findAllByUseridAndEndtimeBetween(userId,
//                Timestamp.from(Instant.now().minus(7, ChronoUnit.DAYS)),
//                Timestamp.from(Instant.now()));
        return sleepDataRepository.findAllByUserid(userId);
    }

    public void saveUserSleepData(SleepData newUserData) {
        sleepDataRepository.save(newUserData);
    }
}
