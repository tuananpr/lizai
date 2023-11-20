package Core.Appium;

import Core.BaseEntities;
import Core.Support.Appium.AppiumWaiter;
import Core.Support.Selenium.Waiter;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
//import org.openqa.selenium.interactions.touch.TouchActions;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class MobileBaseElement extends BaseEntities {
    private final static Logger LOGGER = Logger.getLogger(MobileBaseElement.class.getCanonicalName());

    protected By by;
    private String name;
    private int attempts = 0;
    private int tryingTime = 2;

    public MobileBaseElement(By by, String name) {
        this.by = by;
        this.name = name;
    }

    public WebElement getElement() {
        WebElement mobileElement = null;
        while (attempts < tryingTime) {
            try {
                mobileElement = getAppiumDriver().findElement(by);
                break;
            } catch (StaleElementReferenceException e) {
                LOGGER.log(Level.WARNING, "Fail to locate element, trying again ...");
            }
            attempts++;
        }
        return mobileElement;
    }

    public void tap() {
        while (attempts < tryingTime) {
            try {
                getAppiumDriver().findElement(by).click();
                break;
            } catch (StaleElementReferenceException e) {
                LOGGER.log(Level.WARNING, "Fail to locate element, trying again ...");
            }
            attempts++;
        }
    }

    public String getText() {
        while (attempts < tryingTime) {
            try {
                return getAppiumDriver().findElement(by).getText();
            } catch (StaleElementReferenceException e) {
                LOGGER.log(Level.WARNING, "Fail to locate element, trying again ...");
            }
            attempts++;
        }
        return "not found";
    }

    public void waitForElementToBeDisplayed() {
        while (attempts < tryingTime) {
            try {
                AppiumWaiter.waitForElementToBeDisplay(appiumDriver.findElement(by));
                break;
            } catch (StaleElementReferenceException e) {
                e.getMessage();
            }
            attempts++;
        }
    }

    public boolean isEnabled() {
        return getElement().isEnabled();
    }

    public void waitForElementToBeDisappear() {
        try {
            Waiter.waitForElementToBeDisappear(appiumDriver.findElement(by));
        } catch (NoSuchElementException e) {
            e.getMessage();
        }
    }

    public boolean isDisplayed() {
        boolean flag = false;
        while (attempts < tryingTime) {
            try {
                flag = appiumDriver.findElement(by).isDisplayed();
                break;
            } catch (NoSuchElementException ex) {
                return false;
            } catch (StaleElementReferenceException ex) {
                ex.getMessage();
            }
            attempts++;
        }
        return flag;
    }

    public void scrollTo() {
        WebElement mobileElement = appiumDriver.findElement(by);
        int x = mobileElement.getLocation().getX();
        int y = mobileElement.getLocation().getY();

//        TouchActions action = new TouchActions(appiumDriver);
//        action.move(x + 90, y).release().perform();
    }

    public void screenshot(String outputPath) {
        File srcFile = getElement().getScreenshotAs(OutputType.FILE);
        File targetFile = new File(outputPath);
        try {
            FileUtils.copyFile(srcFile, targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void swipe(DIRECTION direction) {
        final int ANIMATION_TIME = 200; // ms
        final int PRESS_TIME = 200; // ms
        int edgeBorder = 0;
        PointOption pointOptionStart, pointOptionEnd;
        Rectangle rect = getElement().getRect();

        switch (direction) {
            case LEFT:
                pointOptionStart = PointOption.point(rect.x + rect.width - edgeBorder,
                        rect.y + rect.height / 2);
                pointOptionEnd = PointOption.point(rect.x + edgeBorder,
                        rect.y + rect.height / 2);
                break;
            case RIGHT:
                pointOptionStart = PointOption.point(rect.x + edgeBorder,
                        rect.y + rect.height / 2);
                pointOptionEnd = PointOption.point(rect.x + rect.width - edgeBorder,
                        rect.y + rect.height / 2);
                break;
            case UP:
                pointOptionStart = PointOption.point(rect.x + rect.width / 2,
                        rect.y + rect.height - edgeBorder);
                pointOptionEnd = PointOption.point(rect.x + rect.width / 2,
                        rect.y + edgeBorder);
                break;
            case DOWN:
                pointOptionStart = PointOption.point(rect.x + rect.width / 2,
                        rect.y + edgeBorder);
                pointOptionEnd = PointOption.point(rect.x + rect.width / 2,
                        rect.y + rect.height - edgeBorder);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }

        try {
            new TouchAction((AndroidDriver) appiumDriver)
                    .press(pointOptionStart)
                    // a bit more reliable when we add small wait
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME)))
                    .moveTo(pointOptionEnd)
                    .release().perform();
        } catch (Exception e) {
            System.err.println("swipeElementAndroid(): TouchAction FAILED\n" + e.getMessage());
            return;
        }

        // always allow swipe action to complete
        try {
            Thread.sleep(ANIMATION_TIME);
        } catch (InterruptedException e) {
            // ignore
        }
    }

    protected String getName() {
        return this.name;
    }

    public enum DIRECTION {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
}
