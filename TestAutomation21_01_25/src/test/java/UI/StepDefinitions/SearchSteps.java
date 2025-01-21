package UI.StepDefinitions;

import UI.UIObject.SearchObject;
import UI.UITest.BaseTest;
import io.appium.java_client.AppiumDriver;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class SearchSteps extends BaseTest {

    SearchObject searchObject = new SearchObject(appiumDriver);

    @Given("I open the app and continue without login")
    public void iOpenTheAppAndContinueWithoutLogin() {
        searchObject.continueWithoutLogin();
    }

    @When("I search for {string}")
    public void iSearchFor(String productName) {
        searchObject.searchForProduct(productName);
    }

    @And("I select the product {string}")
    public void iSelectTheProduct(String productName) {
        searchObject.selectProduct(productName);
    }

    @Then("the product title should contain {string}")
    public void theProductTitleShouldContain(String keyword) {
        boolean isTitleCorrect = searchObject.verifyProductTitleContains(keyword);
        Assert.assertTrue(isTitleCorrect, "Product title does not contain: " + keyword);
    }

    @When("I add the product to the cart")
    public void iAddTheProductToTheCart() {
        searchObject.addToCart();
    }

    @And("I go to the cart")
    public void iGoToTheCart() {
        searchObject.goToCart();
    }

    @Then("the cart should contain {string}")
    public void theCartShouldContain(String productName) {
        boolean isProductInCart = searchObject.verifyProductInCart(productName);
        Assert.assertTrue(isProductInCart, "Product not found in cart: " + productName);
    }
}
