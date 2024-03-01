package dev.james.lifesync.cookie;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ThemeCookieServletTest {

    @Test
    public void testDoGetWithExistingCookie() throws Exception {
        // Create a request and response
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addHeader("referer", "/");

        // Add an existing theme cookie
        Cookie existingCookie = new Cookie("theme", "light");
        request.setCookies(existingCookie);

        // Call the servlet's doGet method
        new ThemeCookieServlet().doGet(request, response);

        // Get the updated cookies
        Cookie[] cookies = response.getCookies();

        // Verify the theme cookie was updated
        for (Cookie cookie : cookies) {
            if ("theme".equals(cookie.getName())) {
                assertEquals("dark", cookie.getValue());
            }
        }

        assertEquals(302, response.getStatus());
    }

    @Test
    public void testDoGetWithoutExistingCookie() throws Exception {
        // Create a request and response
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addHeader("referer", "/");
        request.addParameter("light", "light");

        // Add a decoy cookie
        Cookie existingCookie = new Cookie("notTheme", "notAValidValue");
        request.setCookies(existingCookie);

        // Call the servlet's doGet method
        new ThemeCookieServlet().doGet(request, response);

        // Get the updated cookies
        Cookie[] cookies = response.getCookies();

        // Verify the theme cookie was created
        for (Cookie cookie : cookies) {
            if ("theme".equals(cookie.getName())) {
                assertEquals("light", cookie.getValue());
            }
        }

        assertEquals(302, response.getStatus());
    }

}
