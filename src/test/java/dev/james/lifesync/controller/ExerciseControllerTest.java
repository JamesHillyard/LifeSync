package dev.james.lifesync.controller;

import dev.james.lifesync.entity.ExerciseData;
import dev.james.lifesync.entity.LifeSyncUser;
import kong.unirest.Unirest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
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
class ExerciseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ExerciseController exerciseController;

    @MockBean
    private Unirest unirestTemplate;

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
        ScriptUtils.runInitScript(new JdbcDatabaseDelegate(mysqlContainer, ""), "dev/james/lifesync/controller/ExerciseControllerTest.sql");
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
    public void testGetExercisePage() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", getLifeSyncUser());

        mockMvc.perform(get("/hlsp/exercise").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("hlsp/exercise"))
                .andExpect(model().attributeExists("exerciseData"))
                .andExpect(model().attributeExists("exerciseDataGrouped"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attributeExists("averageCaloriesBurnt"))
                .andExpect(model().attributeExists("averageDurationInMinutes"))
                .andExpect(model().attributeExists("averageDurationFormatted"));
    }

    @Test
    public void testSaveExerciseData() throws Exception {
        // Create a user and set up a session with the user attribute
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", getLifeSyncUser());

        // Define the parameters for the exercise data
        String dateParam = "2024-01-01";
        String exerciseDetailsParam = "Running";

        // Perform a POST request to save exercise data
        mockMvc.perform(post("/hlsp/exercise").session(session)
                        .param("date", dateParam)
                        .param("exerciseDetails", exerciseDetailsParam))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/hlsp/exercise"));
    }

    @Test
    public void testGetAverageDurationInMinutesNoData() {
        List<ExerciseData> exerciseDataList = List.of();

        assertEquals(0, exerciseController.getAverageDurationInMinutes(exerciseDataList));
        assertEquals(0, exerciseController.getAverageDurationInMinutes(null));

    }

    @Test
    public void testGetAverageDurationInMinutes() {
        List<ExerciseData> exerciseDataList = List.of(
                new ExerciseData(java.sql.Date.valueOf(LocalDate.now().plusDays(1)), 10, 100),
                new ExerciseData(java.sql.Date.valueOf(LocalDate.now()), 20, 350));

        assertEquals(15, exerciseController.getAverageDurationInMinutes(exerciseDataList));
    }

    @Test
    public void testGetAverageDurationInHoursAndMinutesHumanReadableNoData() {
        assertEquals("0 Hours 0 Minutes", exerciseController.getAverageDurationInHoursAndMinutesHumanReadable(0));
    }

    @Test
    public void testGetAverageDurationInHoursAndMinutesHumanReadable() {
        assertEquals("0 Hours 30 Minutes", exerciseController.getAverageDurationInHoursAndMinutesHumanReadable(30));
        assertEquals("1 Hours 45 Minutes", exerciseController.getAverageDurationInHoursAndMinutesHumanReadable(105));
        assertEquals("23 Hours 1 Minutes", exerciseController.getAverageDurationInHoursAndMinutesHumanReadable(1381));

    }

    @Test
    public void testCalculateAverageCaloriesBurntNoData() {
        List<ExerciseData> exerciseDataList = List.of();

        assertEquals(0, exerciseController.calculateAverageCaloriesBurnt(exerciseDataList));
        assertEquals(0, exerciseController.calculateAverageCaloriesBurnt(null));
    }

    @Test
    public void testCalculateAverageCaloriesBurnt() {
        List<ExerciseData> exerciseDataList = List.of(
                new ExerciseData(java.sql.Date.valueOf(LocalDate.now().plusDays(1)), 10, 100),
                new ExerciseData(java.sql.Date.valueOf(LocalDate.now()), 20, 350));

        assertEquals(225, exerciseController.calculateAverageCaloriesBurnt(exerciseDataList));

    }

    @Test
    public void testGroupExerciseData() {
        List<ExerciseData> exerciseDataList = List.of(
                new ExerciseData(java.sql.Date.valueOf(LocalDate.now()), 10, 100),
                new ExerciseData(java.sql.Date.valueOf(LocalDate.now().plusDays(1)), 10, 100),
                new ExerciseData(java.sql.Date.valueOf(LocalDate.now()), 20, 350));

        assertEquals(3, exerciseDataList.size());
        assertEquals(2, exerciseController.groupExerciseData(exerciseDataList).size());

    }
}