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
        assertThat(page.getByLabel("First Name")).isVisible();
        assertThat(page.getByLabel("Last Name")).isVisible();
        assertThat(page.getByLabel("Email address")).isVisible();
        assertThat(page.getByLabel("Password")).isVisible();
        assertThat(page.getByText("I agree to the Terms of Use and the LifeSync Privacy Policy")).isVisible();
        assertThat(page.getByLabel("I agree to the Terms of Use and the LifeSync Privacy Policy")).isVisible();
        assertThat(page.getByLabel("I agree to the Terms of Use and the LifeSync Privacy Policy")).isEnabled();

        // Verify all error messages are hidden
        assertThat(page.getByText("Please enter your firstname")).isHidden();
        assertThat(page.getByText("Please enter your lastname")).isHidden();
        assertThat(page.getByText("Please enter a email")).isHidden();
        assertThat(page.getByText("Please enter a password")).isHidden();
        assertThat(page.getByText("You must agree to the terms and conditions")).isHidden();
    }

    @Test
    public void testSuccessfulSignUp() {
        page.getByLabel("First Name").click();
        page.getByLabel("First Name").fill("James");
        page.getByLabel("Last Name").click();
        page.getByLabel("Last Name").fill("Hillyard");
        page.getByLabel("Email address").click();
        page.getByLabel("Email address").fill("jhillyard10@test.com");
        page.getByLabel("Password").click();
        page.getByLabel("Password").fill("test1234");
        page.getByLabel("I agree to the Terms of Use and the LifeSync Privacy Policy").check();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Signup")).click();

        // Verify success message is shown
        assertThat(page.getByText("Registered Successfully")).isVisible();

        assertEquals(BASE_URL+"/signup", page.url());


    }

    @Test
    public void testSignupWhereUserExists() {
        page.getByLabel("First Name").click();
        page.getByLabel("First Name").fill("James");
        page.getByLabel("Last Name").click();
        page.getByLabel("Last Name").fill("Hillyard");
        page.getByLabel("Email address").click();
        page.getByLabel("Email address").fill("james.hillyard@payara.fish");
        page.getByLabel("Password").click();
        page.getByLabel("Password").fill("test1234");
        page.getByLabel("I agree to the Terms of Use and the LifeSync Privacy Policy").check();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Signup")).click();

        // Verify error message is shown
        assertThat(page.getByText("Username james.hillyard@payara.fish is already taken")).isVisible();

        // Verify form did not disable
        assertThat(page.getByLabel("First Name")).isEditable();
        assertThat(page.getByLabel("Last Name")).isEditable();
        assertThat(page.getByLabel("Email address")).isEditable();
        assertThat(page.getByLabel("Password")).isEditable();

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
