package dev.james.lifesync.controller;

import dev.james.lifesync.entity.LifeSyncUser;
import dev.james.lifesync.entity.NutritionData;
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
class NutritionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NutritionController nutritionController;

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
        ScriptUtils.runInitScript(new JdbcDatabaseDelegate(mysqlContainer, ""), "dev/james/lifesync/controller/NutritionControllerTest.sql");
        mysqlContainer.start();
    }

    @AfterAll
    public static void stopContainer() {
        mysqlContainer.stop();
    }

    @NotNull
    private static LifeSyncUser getLifeSyncUser() {
        return new LifeSyncUser(1, "James", "Hillyard", "james.hillyard@payara.fish", "test");
    }

    @Test
    public void testGetNutritionPage() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", getLifeSyncUser());

        mockMvc.perform(get("/hlsp/nutrition").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("hlsp/nutrition"))
                .andExpect(model().attributeExists("nutritionData"))
                .andExpect(model().attributeExists("nutritionDataGrouped"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attributeExists("averageCalorieIntake"))
                .andExpect(model().attributeExists("averageSugarIntake"));
    }

    @Test
    public void testSaveNutritionData() throws Exception {
        // Create a user and set up a session with the user attribute
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", getLifeSyncUser());

        // Define the parameters for the nutrition data
        String dateParam = "2024-01-01";
        String nutritionDetailsParam = "2 Apples";

        // Perform a POST request to save nutrition data
        mockMvc.perform(post("/hlsp/nutrition").session(session)
                        .param("date", dateParam)
                        .param("nutritionDetails", nutritionDetailsParam))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/hlsp/nutrition"));
    }

    @Test
    public void testCalculateAverageCaloriesIntake() {
        List<NutritionData> nutritionDataList = List.of(
                new NutritionData(java.sql.Date.valueOf(LocalDate.now()), 10, 20, 30),
                new NutritionData(java.sql.Date.valueOf(LocalDate.now().plusDays(1)), 20, 30, 40)
        );

        assertEquals(15, nutritionController.calculateAverageCaloriesIntake(nutritionDataList));
    }

    @Test
    public void testCalculateAverageSugarIntake() {
        List<NutritionData> nutritionDataList = List.of(
                new NutritionData(java.sql.Date.valueOf(LocalDate.now()), 10, 20, 30),
                new NutritionData(java.sql.Date.valueOf(LocalDate.now().plusDays(1)), 20, 30, 40)
        );

        assertEquals(35, nutritionController.calculateAverageSugarIntake(nutritionDataList));
    }

    @Test
    public void testGroupNutritionData() {
        List<NutritionData> nutritionDataList = List.of(
                new NutritionData(java.sql.Date.valueOf(LocalDate.now()), 10, 20, 30),
                new NutritionData(java.sql.Date.valueOf(LocalDate.now().plusDays(1)), 20, 30, 40),
                new NutritionData(java.sql.Date.valueOf(LocalDate.now()), 25, 35, 45)
        );

        assertEquals(3, nutritionDataList.size());
        assertEquals(2, nutritionController.groupNutritionData(nutritionDataList).size());
    }

}