package Utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Browsers {
    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    public static ChromeDriver devDriver;
    public static DevTools devTools;

    public static WebDriver init_driver(String browser) {
        System.out.println("browser value is: " + browser);

        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--disable-popup-blocking");

            // Add unique user data dir to avoid profile conflicts
            String userDataDir = System.getProperty("java.io.tmpdir") + "/chrome_profile_" + System.currentTimeMillis();
            options.addArguments("--user-data-dir=" + userDataDir);

            // Optional: Uncomment if running in Linux/CI environments
            // options.addArguments("--headless=new");
            // options.addArguments("--no-sandbox");
            // options.addArguments("--disable-dev-shm-usage");

            WebDriverManager.chromedriver().setup();
            devDriver = new ChromeDriver(options);
            devTools = devDriver.getDevTools();
            tlDriver.set(devDriver);

        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            tlDriver.set(new FirefoxDriver());

        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            tlDriver.set(new EdgeDriver());

        } else {
            System.out.println("Please pass the correct browser value: " + browser);
            return null;
        }

        WebDriver driver = tlDriver.get();
        if (driver != null) {
            driver.manage().deleteAllCookies();
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static DevTools getDevTools() {
        return devTools;
    }
}
