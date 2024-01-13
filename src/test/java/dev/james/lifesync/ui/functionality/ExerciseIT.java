package dev.james.lifesync.ui.functionality;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import dev.james.lifesync.ui.UITestBase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExerciseIT extends UITestBase {

    BrowserContext context;
    Page page;

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(getScreenWidth(),getScreenHeight()));
        page = context.newPage();

        loginToLifeSync(page);
        page.navigate(BASE_URL+"/hlsp/exercise");
        assertEquals(BASE_URL+"/hlsp/exercise", page.url());
    }

    @AfterEach
    void closeContext() {
        context.close();
    }
    
    @Test
    public void testExercisePageRendered() {
        // Verify Exercise Chart is Visible
        assertThat(page.locator("#exerciseChart")).isVisible();

        // Verify Stats are Visible
        assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Average Calories Burnt"))).isVisible();
        assertThat(page.getByText("714 Calories")).isVisible();
        assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Average Exercise Duration"))).isVisible();
        assertThat(page.getByText("1 Hours 35 Minutes")).isVisible();

        // Verify Articles Table is Visible
        assertThat(page.locator("div").filter(new Locator.FilterOptions().setHasText("Title Source URL")).nth(2)).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("Title"))).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("Source"))).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("URL"))).isVisible();

        // Verify CRUD Tabs are Visible
        assertThat(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("View Exercise Data"))).isVisible();
        assertThat(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Input Exercise Data"))).isVisible();
        assertThat(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("View Exercise Data"))).isEnabled();
        assertThat(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Input Exercise Data"))).isEnabled();
        assertThat(page.locator("li").filter(new Locator.FilterOptions().setHasText("Edit Exercise Data"))).isVisible();

        // Verify Exercise Table Headers are Visible
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("Date"))).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("Activity"))).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("Calories Burnt"))).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("Duration"))).isVisible();

        // Verify Exercise Table Data is Visible
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("2023-10-30"))).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("Skiing")).first()).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("653")).first()).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("1 Hours 20 Minutes")).first()).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("2023-10-29")).first()).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("Skiing")).nth(1)).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("653")).nth(1)).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("1 Hours 20 Minutes")).nth(1)).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("2023-10-29")).nth(1)).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("Walking"))).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("123"))).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("0 Hours 30 Minutes"))).isVisible();
    }

    @Test
    public void testAddExerciseData() {
        // Click Input Data Tab and Enter Data
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Input Exercise Data")).click();
        page.getByLabel("Date:").fill("2024-01-10");
        page.getByLabel("Exercise Details:").fill("20 Minute Rock Climbing");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit")).click();

        // Verify the Data appears in the table
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("Rock Climbing"))).isVisible();

        // Verify the calorie burnt stat changed
        // Specifically check the old value is now hidden incase of Nutritionix API changes
        assertThat(page.getByText("714 Calories")).isHidden();
    }
}
