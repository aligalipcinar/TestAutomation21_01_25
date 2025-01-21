package UI.UITest;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.logging.Logger;

public class SearchTest extends BaseTest {

    private static final Logger logger = Logger.getLogger(SearchTest.class.getName());

    @Test(priority = 1, testName = "TC_0001 Search and Verify Test")
    public void searchAndVerifyTest() {
        searchObject.searchForProduct("Laptop");
        searchObject.selectProduct("Laptop");

        boolean isTitleCorrect = searchObject.verifyProductTitleContains("Laptop");
        Assert.assertTrue(isTitleCorrect, "Product title does not contain 'Laptop'.");

        logger.info("Search and verify test completed successfully.");
    }

    @Test(priority = 2, testName = "TC_0002 Add to Cart Test")
    public void addToCartTest() {
        searchObject.searchForProduct("Tablet");
        searchObject.selectProduct("Tablet");

        searchObject.addToCart();
        searchObject.goToCart();

        boolean isProductInCart = searchObject.verifyProductInCart("Tablet");
        Assert.assertTrue(isProductInCart, "Product not found in cart.");

        logger.info("Add to cart test completed successfully.");
    }
}
