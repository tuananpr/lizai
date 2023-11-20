package Core.Appium;

import Core.Support.Selenium.Waiter;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TextBox extends MobileBaseElement {
    private final static Logger LOGGER = Logger.getLogger(TextBox.class.getCanonicalName());

    public TextBox(By by, String name) {
        super(by, name);
    }

    public void sendClearText(String text) {
        int counter = 0;
        int attempt = 2;
        while (counter < attempt) {
            try {
                getElement().clear();
                getElement().clear();
                getElement().sendKeys(text);
                break;
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, String.format("Fail to locate element [%s], trying again ...", getName()));
            }
            counter++;
        }
    }

    public void sendClearTextViaAction(String input) {
        Actions actions = new Actions(appiumDriver);
        actions.sendKeys(input).perform();
        Waiter.wait(1);
    }
}
