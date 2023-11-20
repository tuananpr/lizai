package Core.Selenium;

import Core.Support.Selenium.Waiter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;

public class Textbox extends BaseElement {
    private static String type = "TextBox";
    private int attempts = 0;
    private int time = 2;

    public Textbox(By by, String name) {
        super(by, type, name);
    }

    public void sendClearText(String text) {
        while (attempts < time) {
            try {
                super.getElement().clear();
                Thread.sleep(500);
                super.getElement().sendKeys(text);
                break;
            } catch (StaleElementReferenceException | InterruptedException e) {
                e.printStackTrace();
            }
            attempts++;
        }
    }

    public void sendText(String text) {
        while (attempts < time) {
            try {
                Thread.sleep(500);
                super.getElement().sendKeys(text);
                break;
            } catch (StaleElementReferenceException | InterruptedException e) {
                e.printStackTrace();
            }
            attempts++;
        }
    }

    public void sendKey(Keys key) {
        while (attempts < time) {
            try {
                super.getElement().clear();
                Thread.sleep(1000);
                super.getElement().sendKeys(key);
                break;
            } catch (StaleElementReferenceException | InterruptedException e) {
                e.printStackTrace();
            }
            attempts++;
        }
    }

    public void waitForTextToBePresent(String text) {
        Waiter.waitForTextPresent(super.getWebElement(), text);
    }

    public void sendClearTextViaJs(String text) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript(String.format("arguments[0].setAttribute('value','%s');", text), driver.findElement(super.getBy()));
    }

    public void sendTextIntoValueAttribute(String text) {
        while (attempts < time) {
            try {
                super.getElement().clear();
                Thread.sleep(500);
                super.getElement().sendKeys("value", text);
                break;
            } catch (StaleElementReferenceException | InterruptedException e) {
                e.printStackTrace();
            }
            attempts++;
        }
    }
}
