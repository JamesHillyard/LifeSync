package dev.james.lifesync.controller;

import dev.james.lifesync.article.ArticleRecommender;
import dev.james.lifesync.database.SleepDataService;
import dev.james.lifesync.entity.LifeSyncUser;
import dev.james.lifesync.entity.SleepData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/hlsp/sleep")
public class SleepController {

    private final double RECOMMENDED_SLEEP_DURATION = 8.0;
    // TODO: This should be determined by the timeRange cookie. But for now it can only be 7
    private final int DATAPOINTS = 7;

    private final SleepDataService sleepDataService;
    private final ArticleRecommender articleRecommender;

    @Autowired
    public SleepController(SleepDataService sleepDataService, ArticleRecommender articleRecommender) {
        this.sleepDataService = sleepDataService;
        this.articleRecommender = articleRecommender;
    }

    Logger LOGGER = Logger.getLogger(SleepController.class.getName());

    private void loadUserSleepData(LifeSyncUser user) {
        user.setSleepData(sleepDataService.getUserSleepData(user.getId()));
    }

    @GetMapping
    public String getSleepPage(Model model, @SessionAttribute("user") LifeSyncUser user) {
        loadUserSleepData(user);

        model.addAttribute("sleepData", user.getSleepData());
        model.addAttribute("percentageDaysSleepOverRecommended", getPercentageOfDaysSleepOverRecommended(user.getSleepData()));
        model.addAttribute("averageSleepDuration", getAverageSleepDurationInMinutes(user.getSleepData()));
        model.addAttribute("articles", articleRecommender.getSleepArticles());

        return "hlsp/sleep";
    }

    @PostMapping
    protected String saveSleepData(@RequestParam("starttime") String starttimeParam,
                                   @RequestParam("endtime") String endtimeParam,
                                   @SessionAttribute("user") LifeSyncUser user) {
        Timestamp starttime = Timestamp.valueOf(LocalDateTime.parse(starttimeParam));
        Timestamp endtime = Timestamp.valueOf(LocalDateTime.parse(endtimeParam));

        SleepData newUserData = new SleepData(user.getId(), starttime, endtime);
        sleepDataService.saveUserSleepData(newUserData);

        return "redirect:/hlsp/sleep";
    }

    protected int getPercentageOfDaysSleepOverRecommended(List<SleepData> userSleepData) {
        int total = userSleepData.size();
        int aboveRecommended = (int) userSleepData.stream()
                .filter(sleepData -> sleepData.getDurationInHoursAndMinutes() >= RECOMMENDED_SLEEP_DURATION)
                .count();

        double percentage = (double) aboveRecommended / total * 100;
        return Math.toIntExact(Math.round(percentage));
    }

    protected long getAverageSleepDurationInMinutes(List<SleepData> userSleepData) {
        Duration totalDuration = Duration.ZERO;
        int count = 0;

        if (userSleepData.isEmpty()) {
            return 0;
        }

        for (SleepData sleepData : userSleepData) {
            Duration duration = Duration.between(sleepData.getStarttime().toLocalDateTime(), sleepData.getEndtime().toLocalDateTime());
            totalDuration = totalDuration.plus(duration);
            count++;
        }

        long totalMinutes = totalDuration.toMinutes();

        return totalMinutes / count;
    }
}
