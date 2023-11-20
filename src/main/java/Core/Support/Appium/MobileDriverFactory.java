package Core.Support.Appium;

import Core.BaseEntities;
import Core.Support.General.PropertyBuilder;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class MobileDriverFactory extends BaseEntities {
    private final static String IOS = "iOS";
    private final static String ANDROID = "Android";
    private final static String XCUITEST = "XCUITest";
    private final static String USERNEWWDA = "useNewWDA";
    private final static String APPPACKAGE = "appPackage";
    private final static String APPACTIVITY = "appActivity";

    public static void initAppiumDriver() {
        if (isIOS()) {
            System.out.println("--> [INFO] ios detected ...");
            initIOSDriver();
        } else {
            initAndroidDriver();
        }
        System.out.println("--> [INFO] emulator: " + isEmulator());
    }

    private static void initAndroidDriver() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, getDeviceName());
        caps.setCapability(MobileCapabilityType.UDID, getDeviceUdid());
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, ANDROID);
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, getDevicePlatformVersion());
        // caps.setCapability(APPPACKAGE, PropertyBuilder.getEnvProperty().getProperty("android.app.package"));
        // caps.setCapability(APPACTIVITY, PropertyBuilder.getEnvProperty().getProperty("android.app.activity"));
        caps.setCapability(MobileCapabilityType.NO_RESET, false);
        caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 300);
        caps.setCapability("autoGrantPermissions", true);
        //      caps.setCapability("unicodeKeyboard", true);
        try {
            appiumDriver = new AndroidDriver(new URL(PropertyBuilder.getAppiumConfig().getProperty("appium_server_url")), caps);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        appiumDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
//        appiumDriver.context("NATIVE_APP");
        caps.setCapability("autoWebview", true);
    }

    public static void initAndroidDriverWithPackage(String appPackage, String appActivities, boolean isWebView) {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, getDeviceName());
        caps.setCapability(MobileCapabilityType.UDID, getDeviceUdid());
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, ANDROID);
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, getDevicePlatformVersion());
        caps.setCapability(APPPACKAGE, appPackage);
        caps.setCapability(APPACTIVITY, appActivities);
        caps.setCapability(MobileCapabilityType.NO_RESET, false);
        caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 300);
        caps.setCapability("autoGrantPermissions", true);
        try {
            appiumDriver = new AndroidDriver(new URL(PropertyBuilder.getAppiumConfig().getProperty("appium_server_url")), caps);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        appiumDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        caps.setCapability("autoWebview", isWebView);
    }

    private static void initIOSDriver() {
        DesiredCapabilities capabilities;
        capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, getDeviceName());
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, IOS);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, getDevicePlatformVersion());
        capabilities.setCapability(MobileCapabilityType.UDID, getDeviceUdid());
        capabilities.setCapability(MobileCapabilityType.APP, getAppPath());
        capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, XCUITEST);
        capabilities.setCapability(USERNEWWDA, false);
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 300);
        capabilities.setCapability("unicodeKeyboard", true);
        try {
            appiumDriver = new IOSDriver(new URL(PropertyBuilder.getAppiumConfig().getProperty("appium_server_url")), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        appiumDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
}
