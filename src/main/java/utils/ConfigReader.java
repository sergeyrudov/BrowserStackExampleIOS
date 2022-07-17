package utils;


import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

/**
 * ConfigReader class is used to read the configuration file test.properties.
 */
public class ConfigReader {

    private static FileBasedConfiguration getConfig() {
        String configFileName = "test.properties";
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties()
                                .setFileName(configFileName));
        try {
            return builder.getConfiguration();
        } catch (ConfigurationException e) {
            // loading of the configuration file failed
            String errorMessage = "Can not read property file '" + configFileName + "'.";
            throw new RuntimeException(errorMessage + e);
        }
    }

    public String getDevice() {
        return getConfig().getString("device");
    }

    public String getIOSVersion() {
        return getConfig().getString("iosVersion");
    }

    public boolean useLocalApp() {
        return getConfig().getBoolean("useLocalApp");
    }

    public String getApp() {
        return getConfig().getString("iosApp");
    }

    public String getFarmURL() {
        return getConfig().getString("browserstackURL");
    }

    public String getBrowserStackHub() {
        return getConfig().getString("browserstackHUB");
    }

    public String getUserName() {
        return getConfig().getString("userName");
    }

    public String getAccessKey() {
        return getConfig().getString("accessKey");
    }
}
