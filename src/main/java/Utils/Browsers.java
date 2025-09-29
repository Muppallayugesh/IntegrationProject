package Utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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

            // Create a unique temp directory for user-data-dir to avoid conflicts
            try {
                Path tempDir = Files.createTempDirectory("chrome-profile-");
                tempDir.toFile().deleteOnExit(); // Deletes on JVM exit
                options.addArguments("--user-data-dir=" + tempDir.toAbsolutePath().toString());
            } catch (IOException e) {
                System.err.println("Failed to create temp directory for Chrome user data");
                e.printStackTrace();
            }

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
