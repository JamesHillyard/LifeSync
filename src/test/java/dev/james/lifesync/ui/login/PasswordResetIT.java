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

public class PasswordResetIT extends UITestBase {

    BrowserContext context;
    Page page;

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(getScreenWidth(),getScreenHeight()));
        page = context.newPage();

        page.navigate(BASE_URL+"/passwordreset");
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @Test
    public void testDefaultState() {
        // Verify all elements are enabled as expected
        assertThat(page.getByLabel("Email address")).isEditable();
        assertThat(page.getByLabel("New Password")).isEditable();
        assertThat(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Reset Password"))).isEnabled();
        page.getByText("Password Reset Successfully").isHidden();
        page.getByText("User doesn't exist.").isHidden();
    }

    @Test
    public void testUpdatePassword() {
        // Verify all elements are enabled as expected
        assertThat(page.getByLabel("Email address")).isEditable();
        assertThat(page.getByLabel("New Password")).isEditable();
        assertThat(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Reset Password"))).isEnabled();

        // Update a users password
        page.getByLabel("Email address").click();
        page.getByLabel("Email address").fill("james.hillyard@payara.fish");
        page.getByLabel("New Password").click();
        page.getByLabel("New Password").fill("lemons");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Reset Password")).click();

        // Verify the success message is shown
        page.getByText("Password Reset Successfully").isVisible();
    }

    @Test
    public void testUpdatePasswordNonExistentUser() {
        // Verify all elements are enabled as expected
        assertThat(page.getByLabel("Email address")).isEditable();
        assertThat(page.getByLabel("New Password")).isEditable();
        assertThat(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Reset Password"))).isEnabled();

        // Update a users password
        page.getByLabel("Email address").click();
        page.getByLabel("Email address").fill("IdOnTExiSt");
        page.getByLabel("New Password").click();
        page.getByLabel("New Password").fill("changeit");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Reset Password")).click();

        // Verify the form is NOT disabled as with pass condition
        assertThat(page.getByLabel("Email address")).isEditable();
        assertThat(page.getByLabel("New Password")).isEditable();
        assertThat(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Reset Password"))).isEnabled();

        // Verify the error message is shown and success message is hidden
        page.getByText("User doesn't exist.").isHidden();
        page.getByText("Password Reset Successfully").isHidden();

        // Verify no redirection occurred
        assertEquals(BASE_URL+"/passwordreset", page.url());
    }

    @Test
    public void testBackButton() {
        assertEquals(BASE_URL+"/passwordreset", page.url());
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Back")).click();
        assertEquals(BASE_URL+"/login", page.url());
    }


}
