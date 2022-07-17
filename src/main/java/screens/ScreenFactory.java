package screens;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.MobilePlatform;

import java.util.logging.Logger;

public class ScreenFactory {
    private static final Logger logger = Logger.getLogger(ScreenFactory.class.getName());

    private static boolean isClassExists(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends TestScreen> T getScreenByClass(IOSDriver<IOSElement> driver, Class<T> classType) {
        try {
            final String platformScreenName = MobilePlatform.IOS + classType.getSimpleName();
            final String commonClassName = classType.getName();
            final String customClassName = classType.getPackage().getName() + "." + MobilePlatform.IOS.toLowerCase() + "." + platformScreenName;

            Class classDefinition = isClassExists(customClassName)
                    ? Class.forName(customClassName)
                    : Class.forName(commonClassName);

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
