package Core.Appium;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

public class ListOfElements extends MobileBaseElement {
    private final static Logger LOGGER = Logger.getLogger(ListOfElements.class.getCanonicalName());
    private int attempts = 0;
    private int tryingTime = 2;

    public ListOfElements(By by, String name) {
        super(by, name);
    }

    public List<WebElement> getListOfElement() {
        List<WebElement> returnedList = null;
        while (attempts < tryingTime) {
            try {
                returnedList = appiumDriver.findElements(by);
                break;
            } catch (StaleElementReferenceException e) {
                e.getMessage();
            } catch (NoSuchElementException e) {
                e.printStackTrace();
                break;
            }
            attempts++;
        }
        return returnedList;
    }

    public int getNumberOfElement() {
        return getListOfElement().size();
    }
}
