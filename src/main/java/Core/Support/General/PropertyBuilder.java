package Core.Support.General;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PropertyBuilder {
    private static Properties properties;

    public static Properties getEnvProperty() {
        if (properties == null) {
            buildProperty();
        }
        return properties;
    }

    public static void buildProperty() {
        properties = new Properties();
        try {
            BufferedReader ip = new BufferedReader(new InputStreamReader(new FileInputStream(PathFinder.getPathToMainConfiguration()), StandardCharsets.UTF_8));
            properties.load(ip);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static Properties getTimeOutConfig() {
        Properties timeoutProperties = new Properties();
        try {
            BufferedReader ip = new BufferedReader(new InputStreamReader(new FileInputStream(PathFinder.getPathToTimeOutConfiguration()), StandardCharsets.UTF_8));
            timeoutProperties.load(ip);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return timeoutProperties;
    }

    public static Properties getGmailConfig() {
        Properties timeoutProperties = new Properties();
        try {
            BufferedReader ip = new BufferedReader(new InputStreamReader(new FileInputStream(PathFinder.getPathToGmailConfiguration()), StandardCharsets.UTF_8));
            timeoutProperties.load(ip);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return timeoutProperties;
    }

    public static Properties getAppiumConfig() {
        Properties timeoutProperties = new Properties();
        try {
            BufferedReader ip = new BufferedReader(new InputStreamReader(new FileInputStream(PathFinder.getPathToAppiumConfiguration()), StandardCharsets.UTF_8));
            timeoutProperties.load(ip);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return timeoutProperties;
    }

    public static Properties getTeamcityConfig() {
        Properties timeoutProperties = new Properties();
        try {
            BufferedReader ip = new BufferedReader(new InputStreamReader(new FileInputStream(PathFinder.getPathToTeamcityConfig()), StandardCharsets.UTF_8));
            timeoutProperties.load(ip);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return timeoutProperties;
    }

    public static String getApiHost() {
        return properties.getProperty("api.host");
    }

    public static String getVeryfiHost() {
        return properties.getProperty("veryfi.host");
    }

    public static String getVeryfiApiHost() {
        return properties.getProperty("veryfi.api.host");
    }
}
