package com.example.steps;

import com.example.pages.LoginPage;
import io.cucumber.java.*;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.edge.*;
import java.net.URL;
import java.util.Objects;
import static org.testng.Assert.assertEquals;

public class LoginSteps {
    private WebDriver driver;
    private LoginPage page;

    @Before
    public void setUp() {
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "true"));
        String browser = System.getProperty("browser", "chrome");
        switch (browser.toLowerCase(java.util.Locale.ROOT)) {
            case "chrome" -> {
                ChromeOptions options = new ChromeOptions();
                if (headless) options.addArguments("--headless=new");
                options.addArguments("--window-size=1280,800");
                driver = new ChromeDriver(options);
            }
            case "edge" -> {
                EdgeOptions options = new EdgeOptions();
                if (headless) options.addArguments("--headless=new");
                options.addArguments("--window-size=1280,800");
                driver = new EdgeDriver(options);
            }
            default -> throw new IllegalArgumentException("Use -Dbrowser=chrome or edge");
        }
        page = new LoginPage(driver);
    }
    @Given("I am on the login page")
    public void openLoginPage() {
        URL demo = Objects.requireNonNull(getClass().getResource("/demo/login.html"),
                "Missing demo/login.html resource");
        page.open(System.getProperty("baseUrl", demo.toExternalForm()));
    }

    @When("I log in with username {string} and password {string}")
    public void logIn(String username, String password) {
        page.login(username, password);
    }

    @Then("I should see the dashboard")
    public void verifyDashboard() {
        assertEquals(page.dashboardText(),"Welcome, demo!");
    }

    @Then("I should see the error {string}")
    public void verifyError(String expected) {
        assertEquals(page.errorText(),expected);
    }
    @Then("I should see the login result {string}")
    public void verifyLoginResult(String result) {
        switch (result) {
            case "success" ->
                assertEquals(page.dashboardText(),"Welcome, demo!");

            case "failure" ->
                assertEquals(
                    "Invalid username or password",
                    page.errorText()
                );

            default ->
                throw new IllegalArgumentException(
                    "Unknown login result: " + result
                );
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        if (driver == null) return;
        try {
            if (scenario.isFailed()) {
                try {
                    scenario.attach(((TakesScreenshot) driver).getScreenshotAs(
                            OutputType.BYTES), "image/png", "Failure screenshot");
                } catch (WebDriverException error) {
                    scenario.log("Screenshot unavailable: " + error.getMessage());
                }
            }
        } finally {
            driver.quit();
        }
    }
}

