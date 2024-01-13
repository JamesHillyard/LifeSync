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

public class SleepIT extends UITestBase {

    BrowserContext context;
    Page page;

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(getScreenWidth(),getScreenHeight()));
        page = context.newPage();

        loginToLifeSync(page);
        page.navigate(BASE_URL+"/hlsp/sleep");
        assertEquals(BASE_URL+"/hlsp/sleep", page.url());
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @Test
    public void testNutritionPageRendered() {
        // Verify Nutrition Chart is Visible
        assertThat(page.locator("#sleepChart")).isVisible();

        // Verify Stats are Visible
        assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Recommended Sleep Frequency"))).isVisible();
        assertThat(page.getByText("86%")).isVisible();
        assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Average Sleep Duration"))).isVisible();
        assertThat(page.getByText("9 hours38 minutes")).isVisible();

        // Verify Articles Table is Visible
        assertThat(page.locator("div").filter(new Locator.FilterOptions().setHasText("Title Source URL")).nth(2)).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("Title"))).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("Source"))).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("URL"))).isVisible();
        
        // Verify Article Exists
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("How can I get better quality sleep?"))).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("BBC")).first()).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("Read Here")).first()).isVisible();

        // Verify CRUD Tabs are Visible
        assertThat(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("View Sleep Data"))).isVisible();
        assertThat(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Input Sleep Data"))).isVisible();
        assertThat(page.locator("li").filter(new Locator.FilterOptions().setHasText("Edit Sleep Data"))).isVisible();

        // Verify Nutrition Table Headers are Visible
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("Start Time"))).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("End Time"))).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("Duration"))).isVisible();

        // Verify Nutrition Table Data is Visible
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("2023-10-23 22:00:00.0"))).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("2023-10-24 08:00:00.0"))).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("10 Hours 0 Minutes")).first()).isVisible();
    }

    @Test
    public void testAddNutritionData() {
        // Click Input Data Tab and Enter Data
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Input Sleep Data")).click();
        page.getByLabel("Start Sleep:").fill("2024-01-10T22:30");
        page.getByLabel("End Sleep:").fill("2024-01-11T08:30");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit")).click();

        // Verify the recommended sleep frequency updated
        assertThat(page.getByText("86%")).isHidden();
        assertThat(page.getByText("88%")).isVisible();
    }
}
