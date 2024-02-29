package dev.james.lifesync.ui.generic;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.Cookie;
import dev.james.lifesync.ui.UITestBase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FooterIT extends UITestBase {

    BrowserContext context;
    Page page;

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(getScreenWidth(), getScreenHeight()));
        page = context.newPage();

        loginToLifeSync(page);
    }

    @AfterEach
    void closeContext() {
        // Clear the cookies to make sure any light/dark theme tests clean up after themselves
        context.clearCookies();

        context.close();
    }

    @Test
    public void testLoginCorrectRendering() {
        page.navigate(BASE_URL+"/login");
        assertEquals(BASE_URL+"/login", page.url());

        assertFalse(page.getByRole(AriaRole.CONTENTINFO).isVisible());
        assertFalse(page.getByRole(AriaRole.CONTENTINFO).locator("div").filter(new Locator.FilterOptions().setHasText("© 2024 LifeSync")).isVisible());
        assertFalse(page.getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName("Logo").setExact(true)).isVisible());
        assertFalse(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("")).isVisible());
    }

    @Test
    public void testSignupCorrectRendering() {
        page.navigate(BASE_URL+"/signup");
        assertEquals(BASE_URL+"/signup", page.url());

        assertFalse(page.getByRole(AriaRole.CONTENTINFO).isVisible());
        assertFalse(page.getByRole(AriaRole.CONTENTINFO).locator("div").filter(new Locator.FilterOptions().setHasText("© 2024 LifeSync")).isVisible());
        assertFalse(page.getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName("Logo").setExact(true)).isVisible());
        assertFalse(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("")).isVisible());
    }

    @Test
    public void testPasswordResetCorrectRendering() {
        page.navigate(BASE_URL+"/passwordreset");
        assertEquals(BASE_URL+"/passwordreset", page.url());

        assertFalse(page.getByRole(AriaRole.CONTENTINFO).isVisible());
        assertFalse(page.getByRole(AriaRole.CONTENTINFO).locator("div").filter(new Locator.FilterOptions().setHasText("© 2024 LifeSync")).isVisible());
        assertFalse(page.getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName("Logo").setExact(true)).isVisible());
        assertFalse(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("")).isVisible());
    }

    @Test
    public void testDashboardCorrectRendering() {
        page.navigate(BASE_URL+"/hlsp/dashboard");
        assertEquals(BASE_URL+"/hlsp/dashboard", page.url());

        assertTrue(page.getByRole(AriaRole.CONTENTINFO).isVisible());
        assertTrue(page.getByRole(AriaRole.CONTENTINFO).locator("div").filter(new Locator.FilterOptions().setHasText("© 2024 LifeSync")).isVisible());
        assertTrue(page.getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName("Logo").setExact(true)).isVisible());
        assertTrue(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("")).isVisible());
    }

    @Test
    public void testNutritionCorrectRendering() {
        page.navigate(BASE_URL+"/hlsp/nutrition");
        assertEquals(BASE_URL+"/hlsp/nutrition", page.url());

        assertTrue(page.getByRole(AriaRole.CONTENTINFO).isVisible());
        assertTrue(page.getByRole(AriaRole.CONTENTINFO).locator("div").filter(new Locator.FilterOptions().setHasText("© 2024 LifeSync")).isVisible());
        assertTrue(page.getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName("Logo").setExact(true)).isVisible());
        assertTrue(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("")).isVisible());
    }

    @Test
    public void testExerciseCorrectRendering() {
        page.navigate(BASE_URL+"/hlsp/exercise");
        assertEquals(BASE_URL+"/hlsp/exercise", page.url());

        assertTrue(page.getByRole(AriaRole.CONTENTINFO).isVisible());
        assertTrue(page.getByRole(AriaRole.CONTENTINFO).locator("div").filter(new Locator.FilterOptions().setHasText("© 2024 LifeSync")).isVisible());
        assertTrue(page.getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName("Logo").setExact(true)).isVisible());
        assertTrue(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("")).isVisible());
    }

    @Test
    public void testSleepCorrectRendering() {
        page.navigate(BASE_URL+"/hlsp/sleep");
        assertEquals(BASE_URL+"/hlsp/sleep", page.url());

        assertTrue(page.getByRole(AriaRole.CONTENTINFO).isVisible());
        assertTrue(page.getByRole(AriaRole.CONTENTINFO).locator("div").filter(new Locator.FilterOptions().setHasText("© 2024 LifeSync")).isVisible());
        assertTrue(page.getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName("Logo").setExact(true)).isVisible());
        assertTrue(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("")).isVisible());
    }

    @Test
    public void testDarkThemeCookie() {
        Cookie cookie = new Cookie("theme", "dark");
        cookie.setUrl(BASE_URL+"/hlsp/");
        context.addCookies(List.of(cookie));

        page.navigate(BASE_URL+"/hlsp/sleep");
        assertEquals(BASE_URL+"/hlsp/sleep", page.url());

        String theme = (String) page.evaluate("document.documentElement.getAttribute('data-bs-theme')");
        assertEquals("dark", theme);

        theme = (String) page.evaluate("document.documentElement.getAttribute('data-bs-theme')");
        assertEquals("dark", theme);

        page.navigate(BASE_URL+"/hlsp/nutrition");
        assertEquals(BASE_URL+"/hlsp/nutrition", page.url());

        theme = (String) page.evaluate("document.documentElement.getAttribute('data-bs-theme')");
        assertEquals("dark", theme);
    }

    @Test
    public void testLightThemeCookie() {
        Cookie cookie = new Cookie("theme", "light");
        cookie.setUrl(BASE_URL+"/hlsp/");
        context.addCookies(List.of(cookie));

        page.navigate(BASE_URL+"/hlsp/sleep");
        assertEquals(BASE_URL+"/hlsp/sleep", page.url());

        String theme = (String) page.evaluate("document.documentElement.getAttribute('data-bs-theme')");
        assertEquals("light", theme);

        page.navigate(BASE_URL+"/hlsp/exercise");
        assertEquals(BASE_URL+"/hlsp/exercise", page.url());

        theme = (String) page.evaluate("document.documentElement.getAttribute('data-bs-theme')");
        assertEquals("light", theme);

        page.navigate(BASE_URL+"/hlsp/nutrition");
        assertEquals(BASE_URL+"/hlsp/nutrition", page.url());

        theme = (String) page.evaluate("document.documentElement.getAttribute('data-bs-theme')");
        assertEquals("light", theme);
    }

    @Test
    public void testDefaultThemeNoCookie() {
        page.navigate(BASE_URL+"/hlsp/sleep");
        assertEquals(BASE_URL+"/hlsp/sleep", page.url());

        String theme = (String) page.evaluate("document.documentElement.getAttribute('data-bs-theme')");
        assertEquals("light", theme);
    }
}
