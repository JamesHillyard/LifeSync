package dev.james.lifesync.cookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ThemeUtilTest {

    @Test
    public void testGetThemeDark() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("theme", "dark");
        when(request.getCookies()).thenReturn(cookies);

        String theme = ThemeUtil.getTheme(request);

        assertEquals("dark", theme);
    }

    @Test
    public void testGetThemeLight() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Cookie[] cookies = new Cookie[2];
        cookies[0] = new Cookie("theme", "light");
        cookies[1] = new Cookie("decoyCookie", "decoyValue");
        when(request.getCookies()).thenReturn(cookies);

        String theme = ThemeUtil.getTheme(request);

        assertEquals("light", theme);
    }

    @Test
    public void testGetThemeDefault() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getCookies()).thenReturn(null);

        String theme = ThemeUtil.getTheme(request);

        assertEquals("light", theme);
    }
}
