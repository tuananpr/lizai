package Core;

import Core.Support.Selenium.BrowserManager;
import Core.Support.Selenium.Waiter;
import Core.Utils.FileUtils;
import org.openqa.selenium.*;
import org.testng.Assert;

import java.io.File;

public abstract class BasePage extends BaseEntities {
    private final String pageName;
    private final By pageLocator;

    public BasePage(By pageLocator, String pageName, boolean assertPageOpen) {
        this.pageLocator = pageLocator;
        this.pageName = pageName;
        if (assertPageOpen) {
            waitForJSToComplete();
            waitForPageLoadComplete();
            Assert.assertTrue(isOnPage());
        }
    }

    public boolean isOnPage() {
        try {
            return driver.findElement(pageLocator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void scrollToTopOfPage() {
//        Label search = new Label(By.xpath("//div[@class='c-page-header']"), "Header");
//        search.scrollToElement();
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, 0)");
    }

    public void scrollToBottomOfPage() {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public void scrollToElement(String xpath_element){
        WebElement element = driver.findElement(By.xpath(xpath_element));
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView();", element);
    }


    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void waitForJSToComplete() {
        Waiter.waitForJSToComplete();
    }

    public void waitForPageLoadComplete() {
        int counter = 0;
        int attempt = 2;
        while (counter < attempt) {
            try {
                Waiter.waitForElementToBeDisplay(driver.findElement(pageLocator));
                break;
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                System.out.println("--> Something wrong while waiting for page to loaded completely, trying again ...");
                e.printStackTrace();
                BrowserManager.getInstance().refreshPage();
                counter++;
            }
        }
    }

    public void takeScreenShot(String des) {
        File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copy(screenShot, des);
    }

    public void refresh() {
        BrowserManager.getInstance().refreshPage();
    }

    public String getPageName() {
        return this.pageName;
    }

    public By getPageLocator() {
        return this.pageLocator;
    }
}
