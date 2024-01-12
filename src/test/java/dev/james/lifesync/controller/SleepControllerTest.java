package dev.james.lifesync.controller;

import dev.james.lifesync.entity.LifeSyncUser;
import dev.james.lifesync.entity.SleepData;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class SleepControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SleepController sleepController;

    @Container
    public static MySQLContainer<?> mysqlContainer =
            new MySQLContainer<>("mysql:8.0.28")
                    .withInitScript("lifesync_database_test.sql")
                    .withDatabaseName("lifesync_database")
                    .withUsername("lifesync")
                    .withPassword("changeit");

    @DynamicPropertySource
    static void registerMySqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
    }

    @BeforeAll
    public static void setUpDatabase() {
        // Testcontainers doesn't support multiple init scripts. This is a workaround to run multiple https://github.com/testcontainers/testcontainers-java/issues/2232
        ScriptUtils.runInitScript(new JdbcDatabaseDelegate(mysqlContainer, ""), "dev/james/lifesync/controller/SleepControllerTest.sql");
        mysqlContainer.start();
    }

    @AfterAll
    public static void stopContainer() {
        mysqlContainer.stop();
    }

    @NotNull
    private static LifeSyncUser getLifeSyncUser() {
        return new LifeSyncUser(1, "James", "Hillyard", "jhillyard", "test");
    }

    @Test
    void testGetSleepPage() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", getLifeSyncUser());

        mockMvc.perform(get("/hlsp/sleep").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("hlsp/sleep"))
                .andExpect(model().attributeExists("sleepData"))
                .andExpect(model().attributeExists("percentageDaysSleepOverRecommended"))
                .andExpect(model().attributeExists("averageSleepDuration"))
                .andExpect(model().attributeExists("articles"));
    }

    @Test
    void testSaveSleepData() throws Exception {
        String starttimeParam = "2023-10-26T22:00:00";
        String endtimeParam = "2023-10-27T08:00:00";

        mockMvc.perform(post("/hlsp/sleep")
                        .param("starttime", starttimeParam)
                        .param("endtime", endtimeParam)
                        .sessionAttr("user", getLifeSyncUser()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/hlsp/sleep"));
    }

    @Test
    void testGetPercentageOfDaysSleepOverRecommended() throws Exception {
        List<SleepData> sleepDataList = Arrays.asList(
                new SleepData(1, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now().plusHours(6))),
                new SleepData(1, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now().plusHours(8))),
                new SleepData(1, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now().plusHours(7)))
        );

        int result = invokeGetPercentageOfDaysSleepOverRecommended(sleepController, sleepDataList);

        assertEquals(33, result); // Recommended is 8 hours so 1/3 or 33% meet this condition
    }

    @Test
    void testGetAverageSleepDurationInMinutesNoData() throws Exception {
        List<SleepData> sleepDataList = List.of();

        long result = invokeGetAverageSleepDurationInMinutes(sleepController, sleepDataList);

        assertEquals(0, result); // Assuming the average sleep duration is 7 hours (420 minutes)
    }

    @Test
    void testGetAverageSleepDurationInMinutes() throws Exception {
        List<SleepData> sleepDataList = Arrays.asList(
                new SleepData(1, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now().plusHours(6))),
                new SleepData(1, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now().plusHours(8))),
                new SleepData(1, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now().plusHours(7)))
        );

        long result = invokeGetAverageSleepDurationInMinutes(sleepController, sleepDataList);

        assertEquals(420, result); // Assuming the average sleep duration is 7 hours (420 minutes)
    }

    private int invokeGetPercentageOfDaysSleepOverRecommended(SleepController sleepController, List<SleepData> sleepDataList) throws Exception {
        Method method = SleepController.class.getDeclaredMethod("getPercentageOfDaysSleepOverRecommended", List.class);
        method.setAccessible(true);
        return (int) method.invoke(sleepController, sleepDataList);
    }

    private long invokeGetAverageSleepDurationInMinutes(SleepController sleepController, List<SleepData> sleepDataList) throws Exception {
        Method method = SleepController.class.getDeclaredMethod("getAverageSleepDurationInMinutes", List.class);
        method.setAccessible(true);
        return (long) method.invoke(sleepController, sleepDataList);
    }


}