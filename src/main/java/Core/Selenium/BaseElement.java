package Core.Selenium;

import Core.BaseEntities;
import Core.Support.Selenium.Waiter;
import Core.Utils.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.util.logging.Logger;

public abstract class BaseElement extends BaseEntities {
    private final static Logger LOGGER = Logger.getLogger(BaseElement.class.getCanonicalName());

    private int attempts = 0;
    private int time = 2;
    private By by;
    private String elementName;
    private String elementType;

    public BaseElement(By by, String type, String name) {
        this.by = by;
        this.elementType = type;
        this.elementName = name;
    }

    protected WebElement getElement() {
        WebElement webElement = null;
        while (attempts < time) {
            try {
                webElement = driver.findElement(by);
                break;
            } catch (StaleElementReferenceException e) {
                e.getMessage();
            }
            attempts++;
        }
        return webElement;
    }

    public Boolean isElementDisplay() {
        boolean flag = false;
        while (attempts < time) {
            try {
                flag = driver.findElement(by).isDisplayed();
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

    public Boolean isElementPresent() {
        boolean flag = false;
        while (attempts < time) {
            try {
                flag = driver.findElements(by).size() != 0;
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

    public Boolean isElementDisappear() {
        try {
            WebElement element = driver.findElement(by);
            return !element.isDisplayed();
        } catch (NoSuchElementException ex) {
            return true;
        }
    }

    public void waitForElementToBeDisplay() {
        while (attempts < time) {
            try {
                Waiter.waitForElementToBeDisplay(driver.findElement(by));
                break;
            } catch (StaleElementReferenceException | NoSuchElementException e) {
                System.out.printf("--> [WARNING] [%s] is not displayed ...%n", getElementName());
            }
            attempts++;
        }
    }

    public void waitForElementToBeEnabled() {
        Waiter.waitForElementToBeEnable(driver.findElement(by));
    }

    public void waitForElementToBePresent() {

        Waiter.waitForElementToBePresent(by);
    }

    public void waitForElementToBeDisappear() {
        try {
            Waiter.waitForElementToBeDisappear(driver.findElement(by));
        } catch (NoSuchElementException e) {
            System.out.println("--> " + getElementName() + " is not displayed ...");
        }
    }

    public void click() {
        while (attempts < time) {
            try {
                driver.findElement(by).click();
                break;
            } catch (StaleElementReferenceException e) {
                e.getMessage();
            }
            attempts++;
        }
    }

    public void clickViaJS() {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", driver.findElement(by));
    }

    public void hoverMouse() {
        Actions action = new Actions(driver);
        while (attempts < time) {
            try {
                action.moveToElement(driver.findElement(by)).perform();
                Waiter.wait(1);
                break;
            } catch (StaleElementReferenceException e) {
                e.getMessage();
            }
            attempts++;
        }
    }

    public void scrollToElement() {
        while (attempts < time) {
            try {
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(by));
                Waiter.wait(1);
                break;
            } catch (StaleElementReferenceException e) {
                e.getMessage();
            }
            attempts++;
        }
    }

    public String getText() {
        String returnText = "";
        while (attempts < time) {
            try {
                returnText = driver.findElement(by).getText();
                break;
            } catch (StaleElementReferenceException e) {
                e.getMessage();
            }
            attempts++;
        }
        return returnText;
    }

    public String getInnerHtmlText() {
        return getElement().getAttribute("innerHTML");
    }

    public String getAttributeOfElement(String attribute) {
        String returnText = "";
        while (attempts < time) {
            try {
                returnText = driver.findElement(by).getAttribute(attribute);
                break;
            } catch (StaleElementReferenceException e) {
                e.getMessage();
            }
            attempts++;
        }
        return returnText;
    }

    public void setAttribute(String attribute, String value) {
        while (attempts < time) {
            try {
                JavascriptExecutor jse = (JavascriptExecutor) driver;
                jse.executeScript(String.format("arguments[0].setAttribute('%s', '%s');", attribute, value), driver.findElement(by));
                break;
            } catch (StaleElementReferenceException e) {
                e.getMessage();
            }
            attempts++;
        }
    }

    public WebElement getWebElement() {
        WebElement webElement = null;
        while (attempts < time) {
            try {
                webElement = driver.findElement(by);
                break;
            } catch (StaleElementReferenceException e) {
                e.getMessage();
            }
            attempts++;
        }
        return webElement;
    }

    public void dragAndDrop(BaseElement destination) {
        Actions actions = new Actions(driver);
        actions.dragAndDrop(getWebElement(), destination.getElement()).build().perform();
        Waiter.wait(1);
    }

    public void dragAndDropViaJs(BaseElement destination) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("function createEvent(typeOfEvent) {\n" + "var event =document.createEvent(\"CustomEvent\");\n"
                + "event.initCustomEvent(typeOfEvent,true, true, null);\n" + "event.dataTransfer = {\n" + "data: {},\n"
                + "setData: function (key, value) {\n" + "this.data[key] = value;\n" + "},\n"
                + "getData: function (key) {\n" + "return this.data[key];\n" + "}\n" + "};\n" + "return event;\n"
                + "}\n" + "\n" + "function dispatchEvent(element, event,transferData) {\n"
                + "if (transferData !== undefined) {\n" + "event.dataTransfer = transferData;\n" + "}\n"
                + "if (element.dispatchEvent) {\n" + "element.dispatchEvent(event);\n"
                + "} else if (element.fireEvent) {\n" + "element.fireEvent(\"on\" + event.type, event);\n" + "}\n"
                + "}\n" + "\n" + "function simulateHTML5DragAndDrop(element, destination) {\n"
                + "var dragStartEvent =createEvent('dragstart');\n" + "dispatchEvent(element, dragStartEvent);\n"
                + "var dropEvent = createEvent('drop');\n"
                + "dispatchEvent(destination, dropEvent,dragStartEvent.dataTransfer);\n"
                + "var dragEndEvent = createEvent('dragend');\n"
                + "dispatchEvent(element, dragEndEvent,dropEvent.dataTransfer);\n" + "}\n" + "\n"
                + "var source = arguments[0];\n" + "var destination = arguments[1];\n"
                + "simulateHTML5DragAndDrop(source,destination);", getElement(), destination.getElement());
    }

    public void takeScreenShot(String fileOutput) {
        while (attempts < time) {
            try {
                File file = driver.findElement(by).getScreenshotAs(OutputType.FILE);
                FileUtils.copy(file, fileOutput);
                break;
            } catch (StaleElementReferenceException e) {
                e.getMessage();
            }
            attempts++;
        }
    }

    protected String getElementName() {
        return this.elementName;
    }

    protected String getElementType() {
        return this.elementType;
    }

    protected By getBy() {
        return this.by;
    }


}
