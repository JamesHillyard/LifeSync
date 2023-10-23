package dev.james.lifesync.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Testcontainers
public class PasswordResetServletTest {

    @LocalServerPort
    private int springPort;

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
        ScriptUtils.runInitScript(new JdbcDatabaseDelegate(mysqlContainer, ""), "dev/james/lifesync/controller/LoginServletTest.sql");
        mysqlContainer.start();
    }

    @AfterAll
    public static void stopContainer() {
        mysqlContainer.stop();
    }

    @Test
    public void testLoginServletRedirectToLoginPage() throws IOException {
        URL url = new URL(String.format("http://%s:%s/passwordreset",
                "localhost",
                springPort));

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        assertEquals(200, responseCode);
    }

    @Test
    public void testPasswordResetWithValidCredentials() throws IOException {
        URL url = new URL(String.format("http://%s:%s/passwordreset",
                "localhost",
                springPort));

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        String parameters = "username=jhillyard&newPassword=test";
        connection.getOutputStream().write(parameters.getBytes(StandardCharsets.UTF_8));

        int responseCode = connection.getResponseCode();

        assertEquals(200, responseCode);
    }

    @Test
    public void testLoginServletWithIncorrectUsernameCorrectPassword() throws IOException {
        URL url = new URL(String.format("http://%s:%s/passwordreset",
                "localhost",
                springPort));

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        String parameters = "username=ahillyard&password=test";
        connection.getOutputStream().write(parameters.getBytes(StandardCharsets.UTF_8));

        int responseCode = connection.getResponseCode();
        assertEquals(401, responseCode);
    }
}
