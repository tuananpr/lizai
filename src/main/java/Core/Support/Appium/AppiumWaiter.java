package Core.Support.Appium;

import Core.BaseEntities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AppiumWaiter extends BaseEntities {
    public static WebDriverWait wait = new WebDriverWait(appiumDriver, Duration.ofSeconds(BaseEntities.getElementTimeOut()));

    public static void waitForElementToBeDisplay(WebElement mobileElement) {
        wait.until(ExpectedConditions.visibilityOf(mobileElement));
    }

    public static void waitForElementToBeClickable(WebElement mobileElement) {
        wait.until(ExpectedConditions.elementToBeClickable(mobileElement));
    }

    public static void wait(int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
