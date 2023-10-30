package dev.james.lifesync.cookie;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TimeRangeCookieServletTest {

    private TimeRangeCookieServlet servlet;

    @BeforeEach
    public void setup() {
        servlet = new TimeRangeCookieServlet();
    }

    @Test
    public void testDoGetWithExistingCookie() throws Exception {
        // Create a request and response
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addHeader("referer", "/");

        // Add an existing timeRange cookie
        Cookie existingCookie = new Cookie("timeRange", "5");
        request.setCookies(existingCookie);

        // Set the request parameter
        request.addParameter("days", "7");

        // Call the servlet's doGet method
        servlet.doGet(request, response);

        // Get the updated cookies
        Cookie[] cookies = response.getCookies();

        // Verify the timeRange cookie was updated
        for (Cookie cookie : cookies) {
            if ("timeRange".equals(cookie.getName())) {
                assertEquals("7", cookie.getValue());
            }
        }

        assertEquals(302, response.getStatus());
    }

    @Test
    public void testDoGetWithExistingCookieSameValue() throws Exception {
        // Create a request and response
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addHeader("referer", "/");

        // Add an existing timeRange cookie
        Cookie existingCookie = new Cookie("timeRange", "7");
        request.setCookies(existingCookie);

        // Set the request parameter
        request.addParameter("days", "7");

        // Call the servlet's doGet method
        servlet.doGet(request, response);

        // Get the updated cookies
        Cookie[] cookies = response.getCookies();

        // Verify the timeRange cookie was updated
        for (Cookie cookie : cookies) {
            if ("timeRange".equals(cookie.getName())) {
                assertEquals("7", cookie.getValue());
            }
        }

        assertEquals(302, response.getStatus());
    }

    @Test
    public void testDoGetWithNoTimeRangeCookie() throws Exception {
        // Create a request and response
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addHeader("referer", "/");

        // Add an existing timeRange cookie
        Cookie existingCookie = new Cookie("notTimeRangeCookie", "Unexpected Cookie");
        request.setCookies(existingCookie);

        // Set the request parameter
        request.addParameter("days", "14");

        // Call the servlet's doGet method
        servlet.doGet(request, response);

        // Get the updated cookies
        Cookie[] cookies = response.getCookies();

        // Verify the timeRange cookie was updated
        for (Cookie cookie : cookies) {
            if ("timeRange".equals(cookie.getName())) {
                assertEquals("14", cookie.getValue());
            }
        }

        assertEquals(302, response.getStatus());
    }
}
