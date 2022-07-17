package screens;

import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.asserts.SoftAssert;
import utils.AppLocator;

import java.time.Duration;

public class ProductsScreen extends TestScreen {

    SoftAssert softAssert = new SoftAssert();

    protected AppLocator productsHeader = AppLocator.buildIOS(
            MobileBy.name("PRODUCTS")
    );

    protected AppLocator toggle = AppLocator.buildIOS(
            MobileBy.name("test-Toggle")
    );

    protected AppLocator productsList = AppLocator.buildIOS(
            MobileBy.name("test-PRODUCTS")
    );

    protected AppLocator addToCartList = AppLocator.buildIOS(
            By.xpath("//XCUIElementTypeOther[@name='test-ADD TO CART']")
    );

    protected AppLocator cart = AppLocator.buildIOS(
            MobileBy.name("test-Cart")
    );

    protected AppLocator checkout = AppLocator.buildIOS(
            MobileBy.name("test-CHECKOUT")
    );

    public void tapOnCheckout() {
        findElement(checkout).click();
    }

    public void tapOnCart() {
        findElement(cart).click();
    }

    public void putAllViewableProductsToCart() {
        findElements(addToCartList).forEach(RemoteWebElement::click);
    }

    public int getListOfProductsInCart() {
        return Integer.parseInt(findElement(cart).getAttribute("label"));
    }






    public void screenComponentSoftAssert() {
        softAssert.assertTrue(isElementExists(productsHeader), "is absent");
        softAssert.assertTrue(isElementExists(toggle), "is absent");
        softAssert.assertAll();
    }

    @Override
    protected void waitUntilVisible() {
        waitForElementVisibility(Duration.ofSeconds(TIMEOUT_WAIT_SCREEN), productsList);
    }
}
