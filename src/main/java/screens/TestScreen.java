package screens;

import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import utils.AppLocator;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public abstract class TestScreen {
    public static final long TIMEOUT_WAIT_SCREEN = 120L;

    public static final long TIMEOUT_WAIT_ELEMENT_VISIBLE = 5L;

    public static final long SCROLL_TIME = 1L;

    protected IOSDriver<IOSElement> driver;

    public void initWhenVisible(IOSDriver<IOSElement> driver) {
        this.driver = driver;
        init(driver);
        waitUntilVisible();
    }

    public IOSDriver<IOSElement> getAppDriver() {
        return driver;
    }

    public IOSElement findElement(AppLocator locator) {
        return waitForElementVisibility(Duration.ofSeconds(TIMEOUT_WAIT_ELEMENT_VISIBLE), locator);
    }

    public boolean isElementExists(AppLocator locator) {
        return !findElements(locator).isEmpty();
    }

    public List<IOSElement> findElements(AppLocator locator) {
        return waitForElementsVisibility(Duration.ofSeconds(TIMEOUT_WAIT_ELEMENT_VISIBLE), locator);
    }

    protected List<IOSElement> waitForElementsVisibility(Duration duration, AppLocator locator) {
        try {
            waitForCondition(duration,
                    ExpectedConditions.visibilityOfAllElementsLocatedBy(locator.getBy()), true);
            return getAppDriver().findElements(locator.getByIOS());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    protected IOSElement waitForElementVisibility(Duration duration, AppLocator locator) {
        waitForCondition(duration,
                ExpectedConditions.visibilityOfElementLocated(locator.getBy()));
        return getAppDriver().findElement(locator.getByIOS());
    }

    protected void waitForCondition(Duration duration, ExpectedCondition expectedCondition, boolean suppressWarning) {
        FluentWait<IOSDriver<IOSElement>> fwait = new FluentWait<>(driver)
                .withTimeout(duration)
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(NoSuchElementException.class)
                .ignoring(TimeoutException.class);
        try {
            fwait.until(expectedCondition);
        } catch (Exception e) {
            if (!suppressWarning) {
                throw (e);
            }
        }
    }

    protected void waitForCondition(Duration duration, ExpectedCondition expectedCondition) {
        waitForCondition(duration, expectedCondition, false);
    }

    private void init(SearchContext context) {
        PageFactory.initElements(new AppiumFieldDecorator(context, Duration.ofSeconds(10)), this);
    }

    protected abstract void waitUntilVisible();

    public void scrollDown() {
        Dimension dim = driver.manage().window().getSize();
        int x = dim.getWidth() / 2;
        int y = dim.getHeight() / 2;
        int endY = 50;
        int startY = 50;
        TouchAction t = new TouchAction(driver);
        t.press(PointOption.point(x, y))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(SCROLL_TIME)))
                .moveTo(PointOption.point(startY, endY))
                .release()
                .perform();
    }
}
