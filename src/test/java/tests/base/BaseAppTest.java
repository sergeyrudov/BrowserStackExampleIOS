package tests.base;

import driver.DriverManager;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import screens.LoginScreen;
import screens.ScreenFactory;

public abstract class BaseAppTest {
    protected static IOSDriver<IOSElement> driver;


    @BeforeSuite
    public void prepareMobileApplication() {
        driver = DriverManager.initializeDriver();
        ScreenFactory.getScreenByClass(driver, LoginScreen.class);
    }

    @AfterSuite
    public void closeMobileApplication() {
        driver.quit();
    }
}
