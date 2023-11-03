package dev.james.lifesync.article;

import dev.james.lifesync.model.Article;
import org.junit.jupiter.api.BeforeAll;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class ArticleRecommenderTest {

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
        ScriptUtils.runInitScript(new JdbcDatabaseDelegate(mysqlContainer, ""), "dev/james/lifesync/article/ArticleRecommenderTest.sql");
        mysqlContainer.start();
    }

    @Autowired
    private ArticleRecommender articleRecommender;

    @Test
    public void testRecommendedSleepArticles() {
        List<Article> articleList = articleRecommender.getSleepArticles();
        assertEquals(3, articleList.size());

        for(Article article : articleList) {
            assertEquals("Sleep", article.getSection());
        }
    }

    @Test
    public void testRecommendedExerciseArticles() {
        List<Article> articleList = articleRecommender.getExerciseArticles();
        assertEquals(2, articleList.size());

        for(Article article : articleList) {
            assertEquals("Exercise", article.getSection());
        }
    }

    @Test
    public void testRecommendedNutritionArticles() {
        List<Article> articleList = articleRecommender.getNutritionArticles();
        assertEquals(1, articleList.size());

        for(Article article : articleList) {
            assertEquals("Nutrition", article.getSection());
        }
    }

}