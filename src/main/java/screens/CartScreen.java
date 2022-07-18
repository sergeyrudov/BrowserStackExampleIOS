package screens;

import io.appium.java_client.MobileBy;
import org.testng.asserts.SoftAssert;
import utils.AppLocator;
import java.time.Duration;

public class CartScreen extends TestScreen {

    SoftAssert softAssert = new SoftAssert();

    protected AppLocator checkoutInfoHeader = AppLocator.buildIOS(
            MobileBy.name("CHECKOUT: INFORMATION")
    );

    protected AppLocator firstNameField = AppLocator.buildIOS(
            MobileBy.name("test-First Name")
    );

    protected AppLocator lastNameField = AppLocator.buildIOS(
            MobileBy.name("test-Last Name")
    );

    protected AppLocator zipCodeField = AppLocator.buildIOS(
            MobileBy.name("test-Zip/Postal Code")
    );

    protected AppLocator continueButton = AppLocator.buildIOS(
            MobileBy.name("test-CONTINUE")
    );

    protected AppLocator finishButton = AppLocator.buildIOS(
            MobileBy.name("test-FINISH")
    );

    protected AppLocator checkoutCompleteHeader = AppLocator.buildIOS(
            MobileBy.name("CHECKOUT: COMPLETE!")
    );

    protected AppLocator orderSuccessMessage = AppLocator.buildIOS(
            MobileBy.xpath("//XCUIElementTypeStaticText[@name='THANK YOU FOR YOU ORDER']")
    );

    public String getOrderSuccessMessage() {
        return findElement(orderSuccessMessage).getAttribute("label");
    }

    public String getOrderSuccessHeader() {
        return findElement(checkoutCompleteHeader).getAttribute("label");
    }

    public void tapFinishButton() {
        findElement(finishButton).click();
    }

    public void fillInCheckoutInfo(String firstName, String lastName, String zipCode) {
        findElement(firstNameField).sendKeys(firstName);
        findElement(lastNameField).sendKeys(lastName);
        findElement(zipCodeField).sendKeys(zipCode);
        driver.hideKeyboard();
        findElement(continueButton).click();
    }

    public void screenComponentSoftAssert() {
        softAssert.assertTrue(isElementExists(firstNameField), "is absent");
        softAssert.assertTrue(isElementExists(lastNameField), "is absent");
        softAssert.assertTrue(isElementExists(zipCodeField), "is absent");
        softAssert.assertTrue(isElementExists(continueButton), "is absent");
        softAssert.assertAll();
    }

    @Override
    protected void waitUntilVisible() {
        waitForElementVisibility(Duration.ofSeconds(TIMEOUT_WAIT_SCREEN), checkoutInfoHeader);
    }
}
