package Core.Support.Selenium;

import Core.BaseEntities;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Set;

public class BrowserManager extends BaseEntities {
    private static BrowserManager instance;
    private static final int trying = 5;

    private BrowserManager() {
    }

    public static BrowserManager getInstance() {
        if (instance == null) {
            instance = new BrowserManager();
        }
        return instance;
    }

    public void navigateToUrl(String url) {
        if (driver == null) {
            System.out.println("Driver Null ...");
        } else {
            driver.get(url);
        }
    }

    public void switchToIFrame(int frameIndex) {
        driver.switchTo().frame(frameIndex);
    }

    public void switchToIFrame(By by) {
        int i = 0;
        while (i < trying) {
            try {
                driver.switchTo().frame(driver.findElement(by));
                break;
            } catch (NoSuchFrameException e) {
                BrowserManager.getInstance().refreshPage();
            }
            i++;
        }
    }

    public void switchToIFrame(String frameId) {
        driver.switchTo().frame(frameId);
    }

    public void backToParentFrame() {
        driver.switchTo().parentFrame();
    }

    public void switchToTab(int tabIndex) {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabIndex));
    }

    public void openUrlInNewTab(String url) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript(String.format("window.open('%s','_blank');", url));
    }

    public void switchToParentTab() {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0));
    }

    public void switchBackToDefaultWindowHandle() {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0));
    }

    public void switchToWindow(int no) {
        String parentWindow = driver.getWindowHandle();
        Set<String> windowsHandles = driver.getWindowHandles();
        ArrayList<String> windowsIds = new ArrayList<>(windowsHandles);
        Assert.assertTrue(windowsIds.size() > 1, "There are only one browser windows at a time.");
        String id = windowsIds.get(no);
        if (!id.equals(parentWindow)) {
            driver.switchTo().window(id);
        } else {
            System.out.println("Wrong window id, child window id == parent window id");
        }
    }

    public void closeWindow(){
        String handleWindow = driver.getWindowHandle();
        driver.switchTo().window(handleWindow).quit();
    }

    public int getTotalNumberOfWindowHandles() {
        return driver.getWindowHandles().size();
    }

    public void refreshPage() {
        System.out.println("--> [INFO] refreshing browser ...");
        driver.navigate().refresh();
    }

    public void backToPreviousPage() {
        driver.navigate().back();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void closeTab() {
        driver.close();
    }

    public void maximize() {
        driver.manage().window().maximize();
    }

    public void setSize(int width, int height) {
        Dimension dimension = new Dimension(width, height);
        driver.manage().window().setSize(dimension);
    }

    public void clearLocalStorage() {
        try {
            if (getSeleniumBrowser().equalsIgnoreCase("chrome")) {
                ((ChromeDriver) driver).getLocalStorage().clear();
            } else {
                ((FirefoxDriver) driver).getLocalStorage().clear();
            }
        } catch (Exception e) {
            System.out.println("clear localStorage fail ...");
        }
    }
}
