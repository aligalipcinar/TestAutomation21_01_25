// Updated BaseObject Class
package UI.UIObject;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class BaseObject {

    protected WebDriver driver;
    protected AppiumDriver appiumDriver;
    protected static final Logger logger = Logger.getLogger(BaseObject.class.getName());
    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(new FileInputStream("src/test/java/config/config.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config properties", e);
        }
    }

    public BaseObject(WebDriver driver) {
        this.driver = driver;
    }

    public BaseObject(AppiumDriver appiumDriver) {
        this.appiumDriver = appiumDriver;
    }

    public static String getConfigValue(String key) {
        return properties.getProperty(key);
    }

    protected WebElement findElement(By locator) {
        if (driver != null) {
            return driver.findElement(locator);
        } else if (appiumDriver != null) {
            return appiumDriver.findElement(locator);
        } else {
            throw new IllegalStateException("No driver instance found");
        }
    }

    protected List<WebElement> findElements(By locator) {
        if (driver != null) {
            return driver.findElements(locator);
        } else if (appiumDriver != null) {
            return appiumDriver.findElements(locator);
        } else {
            throw new IllegalStateException("No driver instance found");
        }
    }

    protected void click(By locator) {
        findElement(locator).click();
    }

    protected void fillElement(By locator, String text) {
        WebElement element = findElement(locator);
        element.clear();
        element.sendKeys(text);
        logger.info(text + " has been written to the element.");
    }

    protected boolean isElementPresent(By locator) {
        if (driver != null) {
            return driver.findElements(locator).size() > 0;
        } else if (appiumDriver != null) {
            return appiumDriver.findElements(locator).size() > 0;
        } else {
            throw new IllegalStateException("No driver instance found");
        }
    }

    protected void switchToTab(int tabIndex) {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabIndex));
        logger.info("Switched to tab index: " + tabIndex);
    }

    protected void waitFor(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            logger.warning("Thread was interrupted: " + e.getMessage());
        }
    }

    protected void clickIfElementPresent(By locator) {
        if (isElementPresent(locator)) {
            click(locator);
            logger.info("Clicked on the element: " + locator);
        } else {
            logger.warning("Element not found: " + locator);
        }
    }

    public boolean isFileDownloaded(String fileName) {
        File dir = new File(System.getProperty("user.home") + "/Downloads");
        File[] dirContents = dir.listFiles();

        for (File file : dirContents) {
            if (file.getName().equals(fileName)) {
                return true;
            }
        }
        return false;
    }
}
