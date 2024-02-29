package dev.james.lifesync.ui;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.parallel.Execution;

import static org.junit.jupiter.api.parallel.ExecutionMode.SAME_THREAD;

/**
 * The base for all UI tests using playwright. This is common functionality for all tests and allows defaults to be set
 */
@Execution(SAME_THREAD) // This forces all inheritors to execute on the same thread. Required as Playwright isn't multithread safe
public class UITestBase {

    /**
     * The base URL for accessing LifeSync.
     * Do NOT leave a trailing slash
     */
    public static final String BASE_URL = "http://localhost:8080";

    /**
     * Controls headlessMode for all tests extending this class.
     * Can be overridden by setting headlessMode at a method level
     */
    protected static boolean headless = false;

    /**
     * Variables that are configured before and after all tests and don't change class to class
     */
    protected static Playwright playwright;
    protected static Browser browser;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();

        // Set browser options
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(headless)
                .setChannel("chrome");

        browser = playwright.chromium().launch(options);
    }

    @AfterAll
    static void closeBrowser() {
        browser.close();
        playwright.close();
    }

    // Helper method to get the screen width
    public static int getScreenWidth() {
        return (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    }

    // Helper method to get the screen height
    public static int getScreenHeight() {
        return (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    }

    /**
     * Pages at /hlsp/* require a user to log in to work correctly. This is a common method for all UI tests that require
     * a logged in user to login using a verified safe user with test data.
     * <br>
     * Goes to the login page and login with a pre-configured test user whose credentials are NEVER changed from config/database/sample_data.sql
     * <br>
     * @param page The browser context page object managed by each test class.
     */
    protected void loginToLifeSync(Page page) {
        page.navigate(BASE_URL+"/login");
        page.getByLabel("Email address").click();
        page.getByLabel("Email address").fill("james.hillyardSAFE@payara.fish");
        page.getByLabel("Password").click();
        page.getByLabel("Password").fill("test");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login")).click();
    }
}
