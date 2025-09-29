package Utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Browsers {
    /** class that manages the browser element */

    public WebDriver driver;

    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    public static ChromeDriver devDriver;
    public static DevTools devTools;

    public static String browserSize = "";

    public void browsers(WebDriver driver) {
        this.driver = driver;
    }

    public static WebDriver init_driver(String browser) { /** Set if multiple return /*
     /** Method that interprets the select game annotation. It searches and opens the games form
     @param  browser     browser platform name
     @return getDriver     WebDriver browser element */

        //WebDriverManager.chromedriver().setup();
        //driver = new ChromeDriver();
        System.out.println("browser value is: " + browser);
        devTools = null;

        if (browser.equals("chrome")) {
            /**  fix Invalid Status code=403 text=Forbidden */
            ChromeOptions option = new ChromeOptions();

            //option.setExperimentalOption("w3c", false); // Disabilita il protocollo W3C
            option.addArguments("--remote-allow-origins=*");
            option.addArguments("--disable-popup-blocking");
            option.addArguments("use-fake-device-for-media-stream");
            option.addArguments("use-fake-ui-for-media-stream");
            //Add these for CI stability:
            option.addArguments("--headless=new"); // Headless for CI/CD
            option.addArguments("--no-sandbox");
            option.addArguments("--disable-dev-shm-usage");
            option.setAcceptInsecureCerts(true); // Accept self-signed certs

//            // Other useful options
//            option.addArguments("--ignore-certificate-errors");
//            option.addArguments("--allow-insecure-localhost");
//
//            // ✅ Add headless + CI-specific stability flags if running in GitHub Actions
//            if (System.getenv("CI") != null) {
//                option.addArguments("--headless=new");
//                option.addArguments("--no-sandbox");
//                option.addArguments("--disable-dev-shm-usage");
//                try {
//                    Path tempProfile = Files.createTempDirectory("chrome-profile");
//                    option.addArguments("--user-data-dir=" + tempProfile.toAbsolutePath().toString());
//                } catch (IOException e) {
//                    System.err.println("Failed to create temp chrome user data dir: " + e.getMessage());
//                }
//            }
            //option.addArguments("--remote-allow-origins=*", "--auto-open-devtools-for-tabs");
//            System.setProperty("webdriver.chrome.driver", "C:\\Users\\ntt22877\\OneDrive - IGT PLC\\Documents\\PROJECTS\\ndt-local\\src\\main\\resources\\drivers\\chromedriver.exe");
            WebDriverManager.chromedriver().setup();
            devDriver = new ChromeDriver(option);
            devTools = devDriver.getDevTools();
            tlDriver.set(devDriver);
        } else if (browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            tlDriver.set(new FirefoxDriver());
        } else if (browser.equals("edge")) {
            tlDriver.set(new EdgeDriver());
        } else {
            System.out.println("Please pass the correct browser value: " + browser);
        }
        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
        //QUI CERCARE IL CHROMEDRIVER
//        getDriver().manage().window().setSize(new Dimension(1366, 768));
        /**
         Set<Object> res = new HashSet<>(Arrays.asList(getDriver(),
         devTools
         )); */
        return tlDriver.get();
    }

    public static synchronized WebDriver getDriver() {

        return tlDriver.get();
    }

//    public static synchronized DevTools getDevTools(WebDriver driverInput) {
//
//        driverInput.manage();
//        //devTools = driverInput.getDevTools();
//        return devTools;
//    }

}
