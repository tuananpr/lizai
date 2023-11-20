package Core.Support.General;

import Core.BaseEntities;
import Core.Support.RestAPI.StepsData;
import Core.Support.Selenium.BrowserFactory;
import Core.Support.Selenium.BrowserManager;
import Core.Support.Selenium.Waiter;
import Core.Utils.FileUtils;
import Core.Utils.Vars;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;

import java.util.logging.Logger;

public class Hooks extends BaseEntities {
    private final static Logger LOGGER = Logger.getLogger(Hooks.class.getCanonicalName());

    protected void before() {
        PropertyBuilder.buildProperty();
    }

    protected void after() {
        Vars.clearVarList();
        FileUtils.clearDataInDownload();
        FileUtils.clearDataInTmp();
        StepsData.getInstance().clearCookie();
        driver.quit();
    }

    protected void beforeWeb() {
        try {
            BrowserFactory.initBrowser();
        } catch (WebDriverException | NullPointerException e) {
            System.out.println("[Waring] Driver is not reachable ...");
            Waiter.wait(3);
            BrowserFactory.initBrowser();
        }
        BrowserManager.getInstance().navigateToUrl(PropertyBuilder.getEnvProperty().getProperty("base.url"));
    }

    protected void afterWeb(Scenario scenario) {
        if (scenario.isFailed()) {
            final byte[] screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenShot, "image/png", scenario.getName());
        }
        driver.quit();
    }

    protected void beforeNonHeadless() {
        System.out.println("--> Non-Headless mode detected, switch back to non-headless ...");
        String originalSeleniumEnv = System.getProperty("selenium");
        seleniumEnv = originalSeleniumEnv;
        String[] parts = originalSeleniumEnv.split(",");
        String newSeleniumEnv = String.format("%s,%s,%s", parts[0], parts[1], "false");
        System.setProperty("selenium", newSeleniumEnv);
    }

    protected void afterNonHeadless() {
        System.setProperty("selenium", seleniumEnv);
    }
}
