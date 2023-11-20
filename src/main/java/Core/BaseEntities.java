package Core;

import Core.Support.General.PropertyBuilder;
import io.appium.java_client.AppiumDriver;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public abstract class BaseEntities {
    protected static WebDriver driver;
    protected static AppiumDriver appiumDriver;
    protected static String seleniumEnv;
    private final static String[] seleniumValidBrowser = new String[]{"CHROME", "FIREFOX", "SAFARI", "EDGE"};
    private final static String[] appiumValidPlatform = new String[]{"IOS", "ANDROID"};
    private final static String[] validEnv = new String[]{"DEMO", "PRODUCTION"};
    private final static String[] validLanguage = new String[]{"EN", "VN"};
    private static Scenario scenario;

    // selenium = window ,chrome, language, headless
    private static String[] getSeleniumConfig() {
        return System.getProperty("selenium").split(",");
    }

    private static String[] getAppiumConfig() {
        return System.getProperty("appium").split(",");
    }

    protected static boolean isRestAssuredDebugModeOn() {
        return System.getProperty("debug").equals("true");
    }

    protected static boolean isSeleniumHeadless() {
        return getSeleniumConfig()[2] != null && getSeleniumConfig()[2].equals("true");
    }

    protected static String getSeleniumBrowser() throws Exception {
        String browser = getSeleniumConfig()[0];
        if (Arrays.asList(seleniumValidBrowser).contains(browser.toUpperCase())) {
            return browser;
        } else {
            throw new Exception("Invalid Browser, currently we only support: chrome ...");
        }
    }

    public static String getEnv() {
        String env = System.getProperty("env");
        if (Arrays.asList(validEnv).contains(env.toUpperCase())) {
            return env;
        } else {
            throw new IllegalStateException("Invalid Test environment, currently we only support: preview, staging, production ...");
        }
    }

    protected static String getLanguage() throws Exception {
        String language = getSeleniumConfig()[1];
        if (Arrays.asList(validLanguage).contains(language.toUpperCase())) {
            return language;
        } else {
            throw new Exception("Invalid language, currently we only support: en ...");
        }
    }

    public static int getLanguageIndex() {
        int langIndex = 0;
        try {
            switch (getLanguage()) {
                case "en":
                    langIndex = 0;
                    break;
                default:
                    langIndex = 1;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return langIndex;
    }

    protected static int getPageLoadTimeOut() {
        return Integer.parseInt(PropertyBuilder.getTimeOutConfig().getProperty("page.load.time.out"));
    }

    protected static int getImplicitTimeOut() {
        return Integer.parseInt(PropertyBuilder.getTimeOutConfig().getProperty("implicitly.time.out"));
    }

    protected static int getElementTimeOut() {
        return Integer.parseInt(PropertyBuilder.getTimeOutConfig().getProperty("element.time.out"));
    }

    public static Scenario getScenario() {
        return scenario;
    }

    protected static void setScenario(Scenario scenario) {
        BaseEntities.scenario = scenario;
    }

    // appium = android, android9.0, device_name, device_udid, app_path
    protected static AppiumDriver getAppiumDriver() {
        return appiumDriver;
    }

    protected static String getDevicePlatform() throws Exception {
        String platform = getAppiumConfig()[0];
        if (Arrays.asList(appiumValidPlatform).contains(platform.toUpperCase())) {
            return platform;
        } else {
            throw new Exception("Invalid Device platform, currently we only support: IOS or ANDROID");
        }
    }

    protected static String getDevicePlatformVersion() {
        return getAppiumConfig()[2];
    }

    protected static String getDeviceName() {
        return getAppiumConfig()[1];
    }

    protected static String getDeviceUdid() {
        return getAppiumConfig()[3];
    }

    protected static boolean isEmulator() {
        String udid = getAppiumConfig()[3].toLowerCase();
        return udid.contains("emulator");
    }

    protected static boolean updateApp() {
        return Boolean.parseBoolean(System.getProperty("update_app"));
    }

    protected static boolean teamcity() {
        return Boolean.parseBoolean(System.getProperty("teamcity"));
    }

    protected static String getAppPath() {
        return getAppiumConfig()[4];
    }

    protected static boolean isIOS() {
        try {
            return getDevicePlatform().toUpperCase().equals("IOS");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    protected static OS getOS() {
        String os = System.getProperty("os.name").toLowerCase();
        System.out.println("OS detected: " + os);
        if (os.contains("mac")) {
            return OS.MAC;
        } else if (os.startsWith("windows")) {
            return OS.WINDOWS;
        } else {
            return OS.LINUX;
        }
    }

    protected boolean isAppiumRunning() {
        try (Socket ignored = new Socket("localhost", 4723)) {
            return true;
        } catch (IOException ignored) {
            return false;
        }
    }

    protected enum OS {
        WINDOWS,
        MAC,
        LINUX
    }
}
