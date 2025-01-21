// Updated BaseTest Class
package UI.UITest;

import UI.UIObject.BaseObject;
import UI.UIObject.SearchObject;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

public class BaseTest {

    protected WebDriver driver;
    protected AppiumDriver appiumDriver;
    protected SearchObject searchObject;

    private static final Logger logger = Logger.getLogger(BaseTest.class.getName());

    @BeforeClass
    public void setup() throws MalformedURLException {
        if (Boolean.parseBoolean(BaseObject.getConfigValue("useAppium"))) {
            setupAppiumDriver();
        } else {
            setupSeleniumDriver();
        }
    }

    private void setupSeleniumDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        searchObject = new SearchObject(driver);

        logger.info("Selenium WebDriver is set up.");
    }

    private void setupAppiumDriver() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName(BaseObject.getConfigValue("platformName"));
        options.setDeviceName(BaseObject.getConfigValue("deviceName"));
        options.setAutomationName(BaseObject.getConfigValue("automationName"));

        String serverURL = BaseObject.getConfigValue("appiumServerURL");
        appiumDriver = new AppiumDriver(new URL(serverURL), options);

        searchObject = new SearchObject(appiumDriver);
        logger.info("Appium Driver is set up.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        if (appiumDriver != null) {
            appiumDriver.quit();
        }
        logger.info("Drivers are closed.");
    }
}
