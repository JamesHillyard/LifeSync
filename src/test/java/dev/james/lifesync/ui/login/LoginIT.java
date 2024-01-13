package dev.james.lifesync.ui.login;

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

//TODO: Spring should start before these tests are run
public class LoginIT extends UITestBase {

    BrowserContext context;
    Page page;

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(getScreenWidth(),getScreenHeight()));
        page = context.newPage();

        page.navigate(BASE_URL+"/login");
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @Test
    public void testPrivacyPolicyTermsOfUseModals() {
        assertThat(page.locator("#privacyPolicyModal")).isHidden();
        assertThat(page.locator("#termsOfUseModal")).isHidden();

        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Privacy Policy")).click();
        assertThat(page.locator("#privacyPolicyModal")).isVisible();
        assertThat(page.locator("#termsOfUseModal")).isHidden();

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Close")).click();
        assertThat(page.locator("#privacyPolicyModal")).isHidden();
        assertThat(page.locator("#termsOfUseModal")).isHidden();

        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Terms of Use")).click();
        assertThat(page.locator("#privacyPolicyModal")).isHidden();
        assertThat(page.locator("#termsOfUseModal")).isVisible();

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Close")).click();
        assertThat(page.locator("#privacyPolicyModal")).isHidden();
        assertThat(page.locator("#termsOfUseModal")).isHidden();
    }

    @Test
    public void testInvalidLogin() {
        assertThat(page.getByText("Invalid Username or Password.")).isHidden();
        page.getByPlaceholder("Username").click();
        page.getByPlaceholder("Username").fill("jhillyard");
        page.getByPlaceholder("Password").click();
        page.getByPlaceholder("Password").fill("INCoRReCtP@ssWoRd!");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login")).click();
        assertThat(page.getByText("Invalid Username or Password.")).isVisible();
    }

    @Test
    public void testValidLogin() {
        page.getByPlaceholder("Username").click();
        page.getByPlaceholder("Username").fill("jhillyardSAFE");
        page.getByPlaceholder("Password").click();
        page.getByPlaceholder("Password").fill("test");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login")).click();
    }

    @Test
    public void testSignUpButton() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign Up")).click();
        assertEquals(BASE_URL+"/signup", page.url());
    }

    @Test
    public void testSignUpThenBack() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign Up")).click();
        assertEquals(BASE_URL+"/signup", page.url());

        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("← Back")).click();
        assertEquals(BASE_URL+"/login", page.url());

    }

    @Test
    public void testForgotPasswordButton() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Forgot Password?")).click();
        assertEquals(BASE_URL+"/passwordreset", page.url());
    }

    @Test
    public void testForgotPasswordThenBack() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Forgot Password?")).click();
        assertEquals(BASE_URL+"/passwordreset", page.url());

        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("← Back")).click();
        assertEquals(BASE_URL+"/login", page.url());
    }

    @Test
    public void testUsernamePasswordErrorTest() {
        // Verify both error messages are hidden
        assertThat(page.getByText("Please enter your username")).isHidden();
        assertThat(page.getByText("Please enter your password")).isHidden();

        // Click the username and password box then login
        page.getByPlaceholder("Username").click();
        page.getByPlaceholder("Password").click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login")).click();

        // Verify both error messages are visible
        assertThat(page.getByText("Please enter your username")).isVisible();
        assertThat(page.getByText("Please enter your password")).isVisible();

        // Populate the username box
        page.getByPlaceholder("Username").click();
        page.getByPlaceholder("Username").fill("a");
        page.getByPlaceholder("Password").click(); // This is required due to the speed playwright runs at

        // Verify the username error is hidden and the password error is visible
        assertThat(page.getByText("Please enter your username")).isHidden();
        assertThat(page.getByText("Please enter your password")).isVisible();

        // Populate the password box
        page.getByPlaceholder("Password").click();
        page.getByPlaceholder("Password").fill("b");
        page.getByPlaceholder("Username").click(); // This is required due to the speed playwright runs at

        // Verify both error messages are hidden
        assertThat(page.getByText("Please enter your username")).isHidden();
        assertThat(page.getByText("Please enter your password")).isHidden();
    }

}
