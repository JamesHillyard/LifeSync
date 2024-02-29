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

public class NutritionIT extends UITestBase {

    BrowserContext context;
    Page page;

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(getScreenWidth(),getScreenHeight()));
        page = context.newPage();

        loginToLifeSync(page);
        page.navigate(BASE_URL+"/hlsp/nutrition");
        assertEquals(BASE_URL+"/hlsp/nutrition", page.url());
    }

    @AfterEach
    void closeContext() {
        context.close();
    }
    
    @Test
    public void testNutritionPageRendered() {
        // Verify Nutrition Chart is Visible
        assertThat(page.locator("#nutritionChart")).isVisible();

        // Verify Stats are Visible
        assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Average Calorie Intake"))).isVisible();
        assertThat(page.getByText("1950.0 Calories")).isVisible();
        assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Average Sugar Intake"))).isVisible();
        assertThat(page.getByText("20.2 g")).isVisible();

        // Verify Articles Table is Visible
        assertThat(page.locator("div").filter(new Locator.FilterOptions().setHasText("Title Source URL")).nth(2)).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("Title"))).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("Source"))).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("URL"))).isVisible();

        // Verify CRUD Tabs are Visible
        assertThat(page.getByRole(AriaRole.TAB, new Page.GetByRoleOptions().setName("View Nutrition Data"))).isVisible();
        assertThat(page.getByRole(AriaRole.TAB, new Page.GetByRoleOptions().setName("Input Nutrition Data"))).isVisible();
        assertThat(page.getByRole(AriaRole.TAB, new Page.GetByRoleOptions().setName("View Nutrition Data"))).isEnabled();
        assertThat(page.getByRole(AriaRole.TAB, new Page.GetByRoleOptions().setName("Input Nutrition Data"))).isEnabled();
        assertThat(page.locator("li").filter(new Locator.FilterOptions().setHasText("Edit Nutrition Data"))).isVisible();

        // Verify Nutrition Table Headers are Visible
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("Date"))).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("Name"))).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("Calories"))).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("Fat"))).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("Sugar"))).isVisible();

        // Verify Nutrition Table Data is Visible
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("2023-10-30")).first()).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("Chips"))).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("350.0"))).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("5.6 g"))).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("10.4 g"))).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("2023-10-30")).nth(1)).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("Cheese burger"))).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("1600.0"))).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("20.8 g"))).isVisible();
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("9.8 g"))).isVisible();
    }

    @Test
    public void testAddNutritionData() {
        // Click Input Data Tab and Enter Data
        page.getByRole(AriaRole.TAB, new Page.GetByRoleOptions().setName("Input Nutrition Data")).click();
        page.getByLabel("Nutrition Details").click();
        page.getByLabel("Nutrition Details").fill("Three Apples");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit")).click();

        // Verify the Data appears in the table
        assertThat(page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("Apples"))).isVisible();

        // Verify the calorie intake stat changed
        // Specifically check the old value is now hidden incase of Nutritionix API changes
        assertThat(page.getByText("1950.0 Calories")).isHidden();
    }

}
