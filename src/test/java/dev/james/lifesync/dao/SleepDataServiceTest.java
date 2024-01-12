package dev.james.lifesync.dao;

import dev.james.lifesync.entity.SleepData;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.parallel.ExecutionMode.SAME_THREAD;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@Execution(SAME_THREAD)
public class SleepDataServiceTest {

    @Autowired
    SleepDataService sleepDataService;

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
        ScriptUtils.runInitScript(new JdbcDatabaseDelegate(mysqlContainer, ""), "dev/james/lifesync/dao/SleepDataServletTest.sql");
        mysqlContainer.start();
    }

    @AfterAll
    public static void stopContainer() {
        mysqlContainer.stop();
    }

    @Test
    public void testGetUserSleepData() {
        List<SleepData> userNutritionData = sleepDataService.getUserSleepData(1);

        assertEquals(2, userNutritionData.size());
    }

    @Test
    public void testSaveUserSleepData() {
        List<SleepData> user1SleepData = sleepDataService.getUserSleepData(1);
        assertEquals(2, user1SleepData.size());

        List<SleepData> user2SleepData = sleepDataService.getUserSleepData(2);
        assertEquals(1, user2SleepData.size());

        SleepData sleepData = new SleepData(1, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now().plusSeconds(1)));
        sleepDataService.saveUserSleepData(sleepData);

        user1SleepData = sleepDataService.getUserSleepData(1);
        assertEquals(3, user1SleepData.size());

        user2SleepData = sleepDataService.getUserSleepData(2);
        assertEquals(1, user2SleepData.size());
    }

}