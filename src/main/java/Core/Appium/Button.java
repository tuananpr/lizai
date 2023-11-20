package Core.Appium;

import Core.Support.Appium.AppiumWaiter;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Button extends MobileBaseElement {
    private final static Logger LOGGER = Logger.getLogger(Button.class.getCanonicalName());

    private int attempts = 0;
    private int tryingTime = 2;

    public Button(By by, String name) {
        super(by, name);
    }

    public void waitForClickable() {
        while (attempts < tryingTime) {
            try {
                AppiumWaiter.waitForElementToBeClickable(getElement());
                break;
            } catch (StaleElementReferenceException e) {
                LOGGER.log(Level.WARNING, String.format("Fail to locate element [%s], trying again ...", getName()));
            }
            attempts++;
        }
    }
}
