package StepDefinitions;

import Utils.ConfigReader;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import Utils.Browsers;

import java.time.Duration;
import java.util.Properties;

public class ApplicationHooks {
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
        if (Browsers.tlDriver.get() == null) {
            driver = Browsers.init_driver(browserName);
            devTools = Browsers.getDevTools();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        } else {
            driver = Browsers.tlDriver.get();
        }
    }

    @After(order = 1)
    public void turnOff() {
        if (Browsers.tlDriver.get() != null) {
            Browsers.tlDriver.get().quit();
            Browsers.tlDriver.remove();
        }
    }

    @Then("close session")
    public void tearDown() {
        driver.quit();
    }

    public DevTools getDevTools() {
        return devTools;
    }
}