package dev.james.lifesync.cookie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;

@WebServlet(name = "TimeRangeCookieServlet", urlPatterns = "/hlsp/cookie/timeRange")
public class TimeRangeCookieServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie timeRangeCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("timeRange"))
                .findFirst()
                .orElse(null);

        if (timeRangeCookie != null) { // If the timeRange cookie does exist, update it
            timeRangeCookie.setValue(request.getParameter("days"));
        } else { // If the time range cookie doesn't exist create it
            timeRangeCookie = new Cookie("timeRange", request.getParameter("days"));
        }

        timeRangeCookie.setPath("/hlsp/");
        timeRangeCookie.setMaxAge(86400);

        // Send the updated cookie back to the client
        response.addCookie(timeRangeCookie);

        // Send the user back to where they came from
        response.sendRedirect(request.getHeader("referer"));
    }
}
