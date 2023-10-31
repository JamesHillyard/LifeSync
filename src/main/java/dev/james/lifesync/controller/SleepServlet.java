package dev.james.lifesync.controller;

import dev.james.lifesync.article.ArticleRecommender;
import dev.james.lifesync.dao.ArticleService;
import dev.james.lifesync.dao.SleepDataService;
import dev.james.lifesync.model.LifeSyncUser;
import dev.james.lifesync.model.SleepData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "SleepServlet", urlPatterns = "/hlsp/sleep")
public class SleepServlet extends HttpServlet {

    private final double RECOMMENDED_SLEEP_DURATION = 8.0;
    // TODO: This should be determined by the timeRange cookie. But for now it can only be 7
    private final int DATAPOINTS = 7;

    private final SleepDataService sleepDataService;
    private final ArticleRecommender articleRecommender;

    @Autowired
    public SleepServlet(SleepDataService sleepDataService, ArticleRecommender articleRecommender) {
        this.sleepDataService = sleepDataService;
        this.articleRecommender = articleRecommender;
    }

    Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());

    private LifeSyncUser loadUserSleepData(LifeSyncUser user) {
        user.setSleepData(sleepDataService.getUserSleepData(user.getId()));
        return user;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LifeSyncUser user = loadUserSleepData((LifeSyncUser) request.getSession().getAttribute("user"));

        request.getSession().setAttribute("sleepData", user.getSleepData());
        request.getSession().setAttribute("percentageDaysSleepOverRecommended", getPercentageOfDaysSleepOverRecommended(user.getSleepData()));
        request.getSession().setAttribute("averageSleepDuration", getAverageSleepDurationInMinutes(user.getSleepData()));
        request.getSession().setAttribute("articles", articleRecommender.getSleepArticles());
        response.sendRedirect(request.getContextPath() + "sleep.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Timestamp starttime = Timestamp.valueOf(LocalDateTime.parse(request.getParameter("starttime")));
        Timestamp endtime = Timestamp.valueOf(LocalDateTime.parse(request.getParameter("endtime")));

        int userId = loadUserSleepData((LifeSyncUser) request.getSession().getAttribute("user")).getId();

        SleepData newUserData = new SleepData(userId, starttime, endtime);
        sleepDataService.saveUserSleepData(newUserData);

        response.sendRedirect(request.getContextPath() + "/hlsp/sleep");
    }

    private int getPercentageOfDaysSleepOverRecommended(List<SleepData> userSleepData) {
        int total = userSleepData.size();
        int aboveRecommended = (int) userSleepData.stream()
                .filter(sleepData -> sleepData.getDurationInHoursAndMinutes() >= RECOMMENDED_SLEEP_DURATION)
                .count();

        double percentage = (double) aboveRecommended / total * 100;
        return Math.toIntExact(Math.round(percentage));
    }

    public long getAverageSleepDurationInMinutes(List<SleepData> userSleepData) {
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
