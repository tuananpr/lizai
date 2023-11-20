package Core.Support.Selenium;

import Core.BaseEntities;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class Waiter extends BaseEntities {
    public static WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(BaseEntities.getElementTimeOut()));

    public static void waitForElementToBeDisplay(WebElement webElement) {
        wait.until(ExpectedConditions.visibilityOf(webElement));
    }

    public static void waitForElementToBeEnable(WebElement webElement) {
        wait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    public static void waitForElementToBePresent(By by) {
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public static void waitForElementToBeClickable(WebElement webElement) {
        wait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    public static void waitForTextPresent(WebElement webElement, String text) {
        wait.until(ExpectedConditions.textToBePresentInElement(webElement, text));
    }

    public static void waitForPresenceOfElement(By by) {
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public static void waitForElementToBeDisappear(WebElement webElement) {
        try{
            wait.until(ExpectedConditions.invisibilityOf(webElement));
        } catch (NoSuchElementException e) {
            e.getMessage();
        }
    }

    public static void waitForJSToComplete() {
        new WebDriverWait(driver, Duration.ofSeconds(BaseEntities.getPageLoadTimeOut())).until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }

    public static void wait(int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
