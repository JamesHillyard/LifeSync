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

public class SignupIT extends UITestBase {

    BrowserContext context;
    Page page;

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(getScreenWidth(),getScreenHeight()));
        page = context.newPage();

        page.navigate(BASE_URL+"/signup");
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @Test
    public void testDefaultState() {
        // Verify all elements are enabled as expected
        assertThat(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("← Back"))).isVisible();
        assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Signup to LifeSync"))).isVisible();
        assertThat(page.getByText("First Name:")).isVisible();
        assertThat(page.getByPlaceholder("Firstname")).isVisible();
        assertThat(page.getByText("Last Name:")).isVisible();
        assertThat(page.getByPlaceholder("Lastname")).isVisible();
        assertThat(page.getByText("Username:")).isVisible();
        assertThat(page.getByPlaceholder("Username")).isVisible();
        assertThat(page.getByText("Password:")).isVisible();
        assertThat(page.getByPlaceholder("Password")).isVisible();
        assertThat(page.getByText("I agree to the Terms of Use and the LifeSync Privacy Policy")).isVisible();
        assertThat(page.getByLabel("I agree to the Terms of Use and the LifeSync Privacy Policy")).isVisible();
        assertThat(page.getByLabel("I agree to the Terms of Use and the LifeSync Privacy Policy")).isEnabled();

        // Verify all error messages are hidden
        assertThat(page.getByText("Please enter your firstname")).isHidden();
        assertThat(page.getByText("Please enter your lastname")).isHidden();
        assertThat(page.getByText("Please enter a username")).isHidden();
        assertThat(page.getByText("Please enter a password")).isHidden();
        assertThat( page.getByText("You must agree to the terms and conditions")).isHidden();
    }

    @Test
    public void testSuccessfulSignUp() {
        page.getByPlaceholder("Firstname").click();
        page.getByPlaceholder("Firstname").fill("James");
        page.getByPlaceholder("Lastname").click();
        page.getByPlaceholder("Lastname").fill("Hillyard");
        page.getByPlaceholder("Username").click();
        page.getByPlaceholder("Username").fill("jhillyard10");
        page.getByPlaceholder("Password").click();
        page.getByPlaceholder("Password").fill("test1234");
        page.getByLabel("I agree to the Terms of Use and the LifeSync Privacy Policy").check();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Signup")).click();

        // Verify success message is shown
        assertThat(page.getByText("Registered Successfully")).isVisible();

        // Verify form disabled correctly
        assertThat(page.getByPlaceholder("Firstname")).isDisabled();
        assertThat(page.getByPlaceholder("Lastname")).isDisabled();
        assertThat(page.getByPlaceholder("Username")).isDisabled();
        assertThat(page.getByPlaceholder("Password")).isDisabled();

        // Click the return to login button and verify the redirection was correct
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Return To Login")).click();
        assertEquals(BASE_URL+"/login", page.url());


    }

    @Test
    public void testSignupWhereUserExists() {
        page.getByPlaceholder("Firstname").click();
        page.getByPlaceholder("Firstname").fill("James");
        page.getByPlaceholder("Lastname").click();
        page.getByPlaceholder("Lastname").fill("Hillyard");
        page.getByPlaceholder("Username").click();
        page.getByPlaceholder("Username").fill("jhillyard");
        page.getByPlaceholder("Password").click();
        page.getByPlaceholder("Password").fill("test1234");
        page.getByLabel("I agree to the Terms of Use and the LifeSync Privacy Policy").check();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Signup")).click();

        // Verify error message is shown
        assertThat(page.getByText("Username jhillyard is already taken")).isVisible();

        // Verify form did not disable
        assertThat(page.getByPlaceholder("Firstname")).isEditable();
        assertThat(page.getByPlaceholder("Lastname")).isEditable();
        assertThat(page.getByPlaceholder("Username")).isEditable();
        assertThat(page.getByPlaceholder("Password")).isEditable();

        // Verify the return to login button was not shown and no redirect happened
        assertThat(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Return To Login"))).isHidden();
        assertEquals(BASE_URL+"/signup", page.url());
    }

    @Test
    public void testBackButton() {
        assertEquals(BASE_URL+"/signup", page.url());
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("← Back")).click();
        assertEquals(BASE_URL+"/login", page.url());
    }

}
