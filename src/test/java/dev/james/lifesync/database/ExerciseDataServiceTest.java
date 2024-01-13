package dev.james.lifesync.database;

import dev.james.lifesync.entity.ExerciseData;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class ExerciseDataServiceTest {

    @Autowired
    ExerciseDataService exerciseDataService;

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

    @BeforeEach // Do before each so the tests don't interfere with eachother
    public void setUpDatabase() {
        // Testcontainers doesn't support multiple init scripts. This is a workaround to run multiple https://github.com/testcontainers/testcontainers-java/issues/2232
        ScriptUtils.runInitScript(new JdbcDatabaseDelegate(mysqlContainer, ""), "dev/james/lifesync/database/ExerciseDataServletTest.sql");
        mysqlContainer.start();
    }

    @AfterAll
    public static void stopContainer() {
        mysqlContainer.stop();
    }

    @Test
    public void testGetUserExerciseData() {
        List<ExerciseData> userExerciseData = exerciseDataService.getUserExerciseData(1);

        assertEquals(2, userExerciseData.size());
        for (ExerciseData exerciseData : userExerciseData) {
            assertEquals("Skiing", exerciseData.getActivityName());
        }
    }

    @Test
    public void testSaveUserExerciseData() {
        List<ExerciseData> user1ExerciseData = exerciseDataService.getUserExerciseData(1);
        assertEquals(2, user1ExerciseData.size());

        List<ExerciseData> user2ExerciseData = exerciseDataService.getUserExerciseData(2);
        assertEquals(1, user2ExerciseData.size());

        ExerciseData exerciseData = new ExerciseData(1, "Golf", Date.valueOf(LocalDate.now()), 90, 158);
        exerciseDataService.saveUserExerciseData(exerciseData);

        user1ExerciseData = exerciseDataService.getUserExerciseData(1);
        assertEquals(3, user1ExerciseData.size());

        user2ExerciseData = exerciseDataService.getUserExerciseData(2);
        assertEquals(1, user2ExerciseData.size());
    }
}