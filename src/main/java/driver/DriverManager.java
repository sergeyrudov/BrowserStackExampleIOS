package driver;

import io.appium.java_client.ios.IOSDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.ConfigReader;
import io.appium.java_client.ios.*;

import static utils.FileUploadService.postFile;

/**
 * IOS driver init
 */

public class DriverManager {
    private static final Logger logger = Logger.getLogger(DriverManager.class.getName());
    private static final ConfigReader reader = new ConfigReader();

    public static IOSDriver<IOSElement> initializeDriver() {
        DesiredCapabilities caps = new DesiredCapabilities();
        File classpathRoot = new File(System.getProperty("user.dir"));
        logger.info("Initializing driver for ios...");
        File app = new File(classpathRoot, "src/main/resources/testedapps/" + reader.getApp());

        caps.setCapability("browserstack.user", reader.getUserName());
        caps.setCapability("browserstack.key", reader.getAccessKey());

        if (reader.useLocalApp()) {
            caps.setCapability("app", app.getAbsolutePath());
        } else {
            caps.setCapability("app", postFile(reader.getFarmURL(), app));
        }

        caps.setCapability("device", reader.getDevice());
        caps.setCapability("os_version", reader.getIOSVersion());
        caps.setCapability("project", "First Java Project");
        caps.setCapability("build", "browserstack-build-1");
        caps.setCapability("name", "task for ios");

        try {
            return new IOSDriver<>(new URL(reader.getBrowserStackHub()), caps);
        } catch (SessionNotCreatedException e) {
            logger.info("Session not created");
            throw e;
        } catch (MalformedURLException s) {
            logger.info("URL is malformed");
            throw new RuntimeException(s);
        }
    }
}
