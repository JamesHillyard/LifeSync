package dev.james.lifesync.dao;

import dev.james.lifesync.model.NutritionData;
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
public class NutritionDataServiceTest {

    @Autowired
    NutritionDataService nutritionDataService;

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
        ScriptUtils.runInitScript(new JdbcDatabaseDelegate(mysqlContainer, ""), "dev/james/lifesync/dao/NutritionDataServletTest.sql");
        mysqlContainer.start();
    }

    @AfterAll
    public static void stopContainer() {
        mysqlContainer.stop();
    }

    @Test
    public void testGetUserNutritionData() {
        List<NutritionData> userNutritionData = nutritionDataService.getUserNutritionData(1);

        assertEquals(2, userNutritionData.size());
        for (NutritionData nutritionData : userNutritionData) {
            assertEquals("Apple", nutritionData.getFoodName());
        }
    }

    @Test
    public void testSaveUserNutritionData() {
        List<NutritionData> user1NutritionData = nutritionDataService.getUserNutritionData(1);
        assertEquals(2, user1NutritionData.size());

        List<NutritionData> user2NutritionData = nutritionDataService.getUserNutritionData(2);
        assertEquals(1, user2NutritionData.size());

        NutritionData nutritionData = new NutritionData(1, Date.valueOf(LocalDate.now()), "Lemon", 40, 3, 28);
        nutritionDataService.saveNutritionData(nutritionData);

        user1NutritionData = nutritionDataService.getUserNutritionData(1);
        assertEquals(3, user1NutritionData.size());

        user2NutritionData = nutritionDataService.getUserNutritionData(2);
        assertEquals(1, user2NutritionData.size());
    }



}