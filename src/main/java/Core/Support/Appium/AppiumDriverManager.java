package Core.Support.Appium;

import Core.BaseEntities;
import io.appium.java_client.android.AndroidDriver;

public class AppiumDriverManager extends BaseEntities {
    private static boolean isAppiumStarted = false;
    private static boolean isAppUpdated = false;

    public static boolean isAppInstalled(String appPackage) {
        if (isIOS()) {
            System.out.println("chua lam ...");
            return false;
        } else {
            AndroidDriver androidDriver = (AndroidDriver) appiumDriver;
            return androidDriver.isAppInstalled(appPackage);
        }
    }

    public static boolean isAppUpdated() {
        return isAppUpdated;
    }

//    public static void downloadAppFromPlayStore(String appName) {
//        String activityName = ".AssetBrowserActivity";
//        String appPackage = "com.android.vending";
//        MobileDriverFactory.initAndroidDriverWithPackage(appPackage, activityName, false);
//        AppiumDriverManager.removeApp(PropertyBuilder.getEnvProperty().getProperty("android.app.package"));
//        PlayStoreHomeScreen playStoreHomeScreen = new PlayStoreHomeScreen();
//        playStoreHomeScreen.searchForApp(appName);
//        playStoreHomeScreen.openAndDownload("WECHEER Staging");
//        PlayStoreAppDetailScreen playStoreAppDetailScreen = new PlayStoreAppDetailScreen();
//        playStoreAppDetailScreen.clickOnInstall();
//        playStoreAppDetailScreen.waitForInstallCompleted();
//        isAppUpdated = true;
//        appiumDriver.quit();
//    }

    public static void removeApp(String appPackage) {
        if (isIOS()) {
            System.out.println("chua lam ...");
        } else {
            System.out.println("--> Remove app: " + appPackage);
            AndroidDriver androidDriver = (AndroidDriver) appiumDriver;
            androidDriver.removeApp(appPackage);
        }
    }

    public static void startAppiumServer() {
        if (!isAppiumStarted) {
            AppiumServer appiumServer = new AppiumServer();
            Thread thread = new Thread(appiumServer);
            thread.start();
            try {
                System.out.println("--> [INFO] wait for appium server to started completely ...");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            isAppiumStarted = true;
        } else {
            System.out.println("--> [INFO] Appium server is started already ...");
        }
    }
}
