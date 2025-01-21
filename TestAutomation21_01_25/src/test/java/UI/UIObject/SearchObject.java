// Updated SearchObject Class
package UI.UIObject;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.logging.Logger;

public class SearchObject extends BaseObject {

    private static final Logger logger = Logger.getLogger(SearchObject.class.getName());
    private final By searchField = AppiumBy.id("com.example.app:id/search_field");
    private final By productTitle = AppiumBy.id("com.example.app:id/product_title");
    private final By addToCartButton = AppiumBy.id("com.example.app:id/add_to_cart_button");
    private final By cartButton = AppiumBy.id("com.example.app:id/cart_button");
    private final By productInCart = AppiumBy.xpath("//android.widget.TextView[contains(@text, 'product_name')]");

    public SearchObject(WebDriver driver) {
        super(driver);
    }

    public SearchObject(AppiumDriver appiumDriver) {
        super(appiumDriver);
    }

    public void continueWithoutLogin() {
        By continueWithoutLoginButton = AppiumBy.id("com.example.app:id/continue_without_login");
        if (isElementPresent(continueWithoutLoginButton)) {
            click(continueWithoutLoginButton);
            logger.info("Continued without login.");
        } else {
            logger.warning("Continue without login button not found.");
        }
    }

    public void searchForProduct(String productName) {
        click(searchField);
        fillElement(searchField, productName);
        logger.info("Searched for product: " + productName);
    }

    public void selectProduct(String productName) {
        By product = AppiumBy.xpath(String.format("//android.widget.TextView[contains(@text, '%s')]"+ productName));
        click(product);
        logger.info("Selected product: " + productName);
    }

    public boolean verifyProductTitleContains(String keyword) {
        WebElement titleElement = findElement(productTitle);
        if (titleElement != null) {
            String title = titleElement.getText().toLowerCase();
            logger.info("Product title found: " + title);
            return title.contains(keyword.toLowerCase());
        } else {
            logger.warning("Product title not found.");
            return false;
        }
    }

    public void addToCart() {
        click(addToCartButton);
        logger.info("Product added to cart.");
    }

    public void goToCart() {
        click(cartButton);
        logger.info("Navigated to cart.");
    }

    public boolean verifyProductInCart(String productName) {
        By product = AppiumBy.xpath(String.format("//android.widget.TextView[contains(@text, '%s')]",productName));
        boolean isPresent = isElementPresent(product);
        if (isPresent) {
            logger.info("Product found in cart: " + productName);
        } else {
            logger.warning("Product not found in cart: " + productName);
        }
        return isPresent;
    }
}
