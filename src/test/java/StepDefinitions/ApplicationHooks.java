package StepDefinitions;

import Utils.Browsers;
import Utils.ConfigReader;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Then;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.Duration;
import java.util.Properties;

public class ApplicationHooks {

    public Browsers browsers = new Browsers();
    private static WebDriver driver;
    private static DevTools devTools;
    private ConfigReader configReader;
    Properties prop;
    String browserName = null;

    @Before(order = 0)
    public void getProperty() {
        configReader = new ConfigReader();
        prop = configReader.initProp();
    }

    @Before(order = 1)
    public void launchBrowser() {
        browserName = prop.getProperty("browser");

        System.out.println("Launching browser: " + browserName);

        if (Browsers.tlDriver.get() == null) {
            driver = Browsers.init_driver(browserName);
            Browsers.tlDriver.set(driver);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            System.out.println("Driver launched successfully.");
        } else {
            driver = Browsers.tlDriver.get();
        }
    }

//    @After(order = 0)
//    public void captureScreenshotOnFailure(Scenario scenario) {
//        WebDriver driver = Browsers.getDriver();
//
//        if (driver == null) {
//            System.err.println("Screenshot skipped: WebDriver is null.");
//            return;
//        }
//
//        if (scenario.isFailed()) {
//            try {
//                // Attach to Cucumber report
//                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
//                scenario.attach(screenshot, "image/png", "Failure Screenshot");
//
//                // Save locally (optional)
//                Path screenshotsDir = Paths.get("screenshots");
//                Files.createDirectories(screenshotsDir);
//                String safeScenarioName = scenario.getName().replaceAll("[^a-zA-Z0-9\\-]", "_");
//                String filename = "FAILED_" + safeScenarioName + ".png";
//
//                File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//                Files.copy(screenshotFile.toPath(), screenshotsDir.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
//
//                System.out.println("Screenshot saved to: " + screenshotsDir.resolve(filename));
//
//            } catch (Exception e) {
//                System.err.println("Could not capture screenshot: " + e.getMessage());
//            }
//        }
//    }

    @After(order = 1)
    public void takeScreenshotIfFailed(Scenario scenario) {
        if (scenario.isFailed() && driver != null) {
            try {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                String screenshotDir = "target/screenshots/";
                String screenshotPath = screenshotDir + scenario.getName().replaceAll(" ", "_") + ".png";
                FileUtils.copyFile(screenshot, new File(screenshotPath));
                System.out.println("📸 Screenshot captured: " + screenshotPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (driver == null) {
            System.out.println("Screenshot skipped: WebDriver is null.");
        }
    }
    // By commenting this driver is not quitted after each scenario



//    @After(order = 1)
//    public void turnOff() {
//        if (Browsers.tlDriver.get() != null) {
//            Browsers.tlDriver.get().quit();
//            Browsers.tlDriver.remove();
//            System.out.println("Driver quit and ThreadLocal cleared.");
//        }
//    }

    @Then("close session")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public DevTools getDevTools() {
        return devTools;
    }
}
