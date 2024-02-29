package dev.james.lifesync.cookie;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class ThemeUtil {
    public static String getTheme(HttpServletRequest request) {
        String theme = "light"; // default value
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("theme")) {
                    theme = cookie.getValue();
                    break;
                }
            }
        }
        return theme;
    }
}

