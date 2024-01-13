package dev.james.lifesync.ui.navigation;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import dev.james.lifesync.ui.UITestBase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NavBarIT extends UITestBase {

    BrowserContext context;
    Page page;

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(getScreenWidth(),getScreenHeight()));
        page = context.newPage();

        loginToLifeSync(page);
        assertEquals(BASE_URL+"/hlsp/dashboard", page.url());
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @Test
    public void testNavbarRendered() {
        assertThat(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Your Logo"))).isVisible();
        assertThat(page.locator("#navbarNav")).isVisible();
        assertThat(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("7 Days"))).isVisible();
        assertThat(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Your Profile James"))).isVisible();
    }

    @Test
    public void testNavigation() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Dashboard")).click();
        assertEquals(BASE_URL+"/hlsp/dashboard", page.url());

        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Nutrition")).click();
        assertEquals(BASE_URL+"/hlsp/nutrition", page.url());

        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Exercise")).click();
        assertEquals(BASE_URL+"/hlsp/exercise", page.url());

        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sleep")).click();
        assertEquals(BASE_URL+"/hlsp/sleep", page.url());
    }

    @Test
    public void testLogOut() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Your Profile James")).click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Log Out")).click();
        assertEquals(BASE_URL+"/login", page.url());
    }
}
