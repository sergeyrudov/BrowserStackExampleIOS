package screens;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.MobilePlatform;

import java.util.logging.Logger;

public class ScreenFactory {
    private static final Logger logger = Logger.getLogger(ScreenFactory.class.getName());

    @SuppressWarnings("unchecked")
    public static <T extends TestScreen> T getScreenByClass(IOSDriver<IOSElement> driver, Class<T> classType) {
        try {
            Class<?> classDefinition = Class.forName(classType.getName());
            T screen = (T) classDefinition.getDeclaredConstructor().newInstance();
            screen.initWhenVisible(driver);
            return screen;
        } catch (Exception ex) {
            String screenName = classType.getSimpleName();
            logger.info(String.format("Unable to get %s by class. %s", screenName, ex));
            throw new UnsupportedOperationException("Class " + classType.getSimpleName() + " could not be instantiated for " + MobilePlatform.IOS);
        }
    }
}
