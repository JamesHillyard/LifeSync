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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class SignupControllerTest {

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
        ScriptUtils.runInitScript(new JdbcDatabaseDelegate(mysqlContainer, ""), "dev/james/lifesync/controller/SignupServletTest.sql");
        mysqlContainer.start();
    }

    @AfterAll
    public static void stopContainer() {
        mysqlContainer.stop();
    }

    @Test
    public void testSignupServletRedirectToSignupPage() throws IOException {
        URL url = new URL(String.format("http://localhost:%s/signup", springPort));

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        assertEquals(200, responseCode);
    }

    @Test
    public void testSignupServletExistingNameExistingUsername() throws IOException {
        URL url = new URL(String.format("http://localhost:%s/signup", springPort));

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        String parameters = "firstname=james&lastname=hillyard&username=jhillyard&password=test";
        connection.getOutputStream().write(parameters.getBytes(StandardCharsets.UTF_8));

        int responseCode = connection.getResponseCode();

        assertEquals(409, responseCode); // 409 returned as the username exists
    }

    @Test
    public void testSignupServletExistingNameDifferentUsername() throws IOException {
        URL url = new URL(String.format("http://localhost:%s/signup", springPort));

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        String parameters = "firstname=james&lastname=hillyard&username=jhillyard1&password=test";
        connection.getOutputStream().write(parameters.getBytes(StandardCharsets.UTF_8));

        int responseCode = connection.getResponseCode();

        assertEquals(201, responseCode); // 201 Created returned as the user created
    }

    @Test
    public void testSignupServletDifferentNameDifferentUsername() throws IOException {
        URL url = new URL(String.format("http://localhost:%s/signup", springPort));

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        String parameters = "firstname=Sam&lastname=Cole&username=samcole&password=Football3";
        connection.getOutputStream().write(parameters.getBytes(StandardCharsets.UTF_8));

        int responseCode = connection.getResponseCode();

        assertEquals(201, responseCode); // 201 Created returned as the user created
    }

}