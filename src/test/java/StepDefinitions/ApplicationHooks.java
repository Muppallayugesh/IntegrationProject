package StepDefinitions;

import Utils.ConfigReader;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import Utils.Browsers;
import Utils.ConfigReader;

import java.time.Duration;
import java.util.Properties;
import java.util.logging.Logger;

public class ApplicationHooks {
    public Browsers browsers = new Browsers();
    private static WebDriver driver;
    private static DevTools devTools;
    private ConfigReader configReader;
    Properties prop;
    String browserName = null;
    boolean session = false;

    @Before(order = 0)
    public void getProperty() {
        configReader = new ConfigReader();
        prop = configReader.initProp();
    }

    @Before(order = 1)
    public void launchBrowser() throws InterruptedException {
        browserName = prop.getProperty("browser");
//        if (tlDriver.get() == null) {
//            browsers = new Browsers();
//            driver = browsers.init_driver(browserName);
//            /**
//            driver = (WebDriver) Browsers.init_driver(browserName).toArray()[0];
//            devTools = (DevTools) Browsers.init_driver(browserName).toArray()[1]; */
//            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        }
        if (Browsers.tlDriver.get() == null) { // Use ThreadLocal driver
            driver = Browsers.init_driver(browserName);
            Browsers.tlDriver.set(driver); // Store it in ThreadLocal

            session = true;
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        } else {
            driver = Browsers.tlDriver.get(); // Use the existing driver for this thread
        }
    }


//        After section for visual testing
//    @After(order = 0)
//    public void af(Scenario scenario1) throws InterruptedException, IllegalMonitorStateException, IOException {
//        scenario1.log("After Hook");
//
//        ArrayList<String> tabs2 = new ArrayList<>(driver.getWindowHandles());
//        if (!(driver == null)) {
//            if (scenario1.isFailed()) {
//                if (tabs2.size() > 1) {
//                    driver.switchTo().window(tabs2.get(1));
//                }
//                Allure.addAttachment("Error", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
//                String screenshotFile = Utilities.screenshot(driver, "Screenshot error on step"); /** screenshot path */
//                System.out.println("Screenshot at " + screenshotFile);
//                Thread.sleep(1000);
//                //Utilities.refreshPage();
//            }
//            //String tb = Keys.chord(Keys.CONTROL, Keys.chord("w"), Keys.ENTER);
//            //driver.findElement(By.xpath("//*[text()='Company']")).sendKeys(tb);
//            while (tabs2.size() > 1) {
//                driver.switchTo().window(tabs2.get(1));
//                tabs2.remove(1);
//                driver.close();
//                driver.switchTo().window(tabs2.get(0));
//            }
//        }
//    }

    @After(order = 1)
    public void turnOff() {
        if (Browsers.tlDriver.get() != null) {
            Browsers.tlDriver.get().quit(); // Quit the ThreadLocal driver
            Browsers.tlDriver.remove(); // Clear the ThreadLocal storage
        }
//        if (driver != null) {
//            driver.quit();
//            driver = null; // Reset driver for the next test
//        }
    }

    @Then("close session")
    public void tearDown() {
        driver.quit();
    }

    public DevTools getDevTools() {

        return devTools;
    }

}