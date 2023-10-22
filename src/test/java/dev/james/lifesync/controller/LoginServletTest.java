package dev.james.lifesync.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Testcontainers
public class LoginServletTest {

    @LocalServerPort
    private int springPort;

    @Container
    public static MySQLContainer<?> mysqlContainer;

    static {
        mysqlContainer = new MySQLContainer<>("mysql:8.0.28")
                .withDatabaseName("lifesync_database")
                .withUsername("lifesync")
                .withPassword("changeit")
                .withInitScript("dev/james/lifesync/controller/LoginServletTest.sql")
                .withInitScript(String.valueOf("lifesync_database_test.sql"));
    }

    @BeforeAll
    public static void setUpDatabase() {
        mysqlContainer.start();
    }

    @AfterAll
    public static void stopContainer() {
        mysqlContainer.stop();
    }

    @Test
    public void testLoginServletRedirectToLoginPage() throws IOException {
        URL url = new URL(String.format("http://%s:%s/login",
                mysqlContainer.getHost(),
                springPort));

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        assertEquals(200, responseCode);
    }

    @Test
    public void testLoginServletWithValidCredentials() throws IOException {
        URL url = new URL(String.format("http://%s:%s/login",
                mysqlContainer.getHost(),
                springPort));

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        String parameters = "username=jhillyard&password=test";
        connection.getOutputStream().write(parameters.getBytes(StandardCharsets.UTF_8));

        int responseCode = connection.getResponseCode();

        assertEquals(200, responseCode); // 301 returned due to the redirect to /dashboard
    }

    @Test
    public void testLoginServletWithCorrectUsernameIncorrectPassword() throws IOException {
        URL url = new URL(String.format("http://%s:%s/login",
                mysqlContainer.getHost(),
                springPort));

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        String parameters = "username=jhillyard&password=wrongpassword";
        connection.getOutputStream().write(parameters.getBytes(StandardCharsets.UTF_8));

        int responseCode = connection.getResponseCode();
        assertEquals(401, responseCode);
    }
    
    @Test
    public void testLoginServletWithIncorrectUsernameCorrectPassword() throws IOException {
        URL url = new URL(String.format("http://%s:%s/login",
                mysqlContainer.getHost(),
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
