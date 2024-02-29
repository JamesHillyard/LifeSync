package dev.james.lifesync.cookie;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;

@WebServlet(name = "ThemeCookieServlet", urlPatterns = "/hlsp/cookie/theme")
public class ThemeCookieServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie themeCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("theme"))
                .findFirst()
                .orElse(null);

        if (themeCookie != null) { // If the themeCookie cookie does exist, update it
            if (themeCookie.getValue().equals("light")) {
                themeCookie.setValue("dark");
            } else {
                themeCookie.setValue("light");
            }
        } else { // If the time range cookie doesn't exist create it
            themeCookie = new Cookie("theme", request.getParameter("light"));
        }

        themeCookie.setPath("/hlsp/");
        themeCookie.setMaxAge(86400);

        // Send the updated cookie back to the client
        response.addCookie(themeCookie);

        // Send the user back to where they came from
        response.sendRedirect(request.getHeader("referer"));
    }
}
