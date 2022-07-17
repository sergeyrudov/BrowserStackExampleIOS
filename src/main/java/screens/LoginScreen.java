package screens;

import io.appium.java_client.MobileBy;
import lombok.ToString;
import org.testng.asserts.SoftAssert;
import utils.AppLocator;

import java.time.Duration;


public class LoginScreen extends TestScreen {
    @ToString
    public enum User {
        STANDARD("test-standard_user");
        User(String displayName) {
            this.displayName = displayName;
        }
        private final String displayName;
    }
    SoftAssert softAssert = new SoftAssert();

    protected AppLocator loginInputField = AppLocator.buildIOS(
            MobileBy.name("test-Username")
    );

    protected AppLocator passwordInputField = AppLocator.buildIOS(
            MobileBy.name("test-Password")
    );

    protected AppLocator loginButton = AppLocator.buildIOS(
            MobileBy.name("test-LOGIN")
    );

    public void signInAs(User user) {
        findElement(AppLocator.buildIOS(MobileBy.name(user.displayName))).click();
        findElement(loginButton).click();
    }

    public void screenComponentSoftAssert() {
        softAssert.assertTrue(isElementExists(loginInputField), "is absent");
        softAssert.assertTrue(isElementExists(passwordInputField), "is absent");
        softAssert.assertTrue(isElementExists(loginButton), "is absent");
        softAssert.assertAll();
    }

    @Override
    protected void waitUntilVisible() {
        waitForElementVisibility(Duration.ofSeconds(TIMEOUT_WAIT_SCREEN), loginInputField);
    }
}
