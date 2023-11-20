package Core.Support.Selenium;

import Core.BaseEntities;
import Core.Support.General.PathFinder;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class BrowserFactory extends BaseEntities {
    static final String CHROME = "CHROME";
    static final String FIREFOX = "FIREFOX";

    public static void initBrowser(String... additionalInfo) {
        String browser = "";
        try {
            browser = BaseEntities.getSeleniumBrowser();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (browser.equalsIgnoreCase(CHROME)) {
//            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(initChromeOptions(additionalInfo));
        } else if (browser.equalsIgnoreCase(FIREFOX)) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver(initFirefoxOptions());
        } else {
            Assert.fail(browser + " is not supported yet. Please contact Tung Do.");
        }
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(BaseEntities.getPageLoadTimeOut()));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(BaseEntities.getImplicitTimeOut()));
    }

    public static void initBrowserInMobileMode() {

    }

    public static void killBrowser() {
        driver.quit();
    }

    private static ChromeOptions initChromeOptions(String... additionalInfo) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("enable-automation");
        if (BaseEntities.isSeleniumHeadless()) {
            options.addArguments("--headless=new");
            options.addArguments("--profile-directory=Default");
            options.addArguments("--ignore-certificate-errors");
        }
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-extensions");
        options.addArguments("--dns-prefetch-disable");
        options.addArguments("--disable-gpu");
        options.addArguments("--use-fake-device-for-media-stream");
        options.addArguments("--use-fake-ui-for-media-stream");
        options.addArguments("user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.102 Safari/537.36");
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        // mobile mode
        if (additionalInfo.length > 0) {
            Map<String, String> mobileEmulation = new HashMap<>();
            mobileEmulation.put("deviceName", "iPhone 12 Pro");
            options.setExperimentalOption("mobileEmulation", mobileEmulation);
        }

        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_setting_values.media_stream_mic", 1);
        prefs.put("profile.default_content_setting_values.media_stream_camera", 1);
        prefs.put("profile.default_content_setting_values.geolocation", 1);
        prefs.put("profile.default_content_setting_values.notifications", 1);
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("download.default_directory", PathFinder.getDownloadFolderPath());
        options.setExperimentalOption("prefs", prefs);
        return options;
    }

    private static FirefoxOptions initFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("enable-automation");
        if (BaseEntities.isSeleniumHeadless()) {
            options.addArguments("--headless");
        }
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-extensions");
        options.addArguments("--dns-prefetch-disable");
        options.addArguments("--disable-gpu");
        options.addArguments("--use-fake-device-for-media-stream");
        options.addArguments("--use-fake-ui-for-media-stream");
        options.addArguments("user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.102 Safari/537.36");
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        return options;
    }

}
