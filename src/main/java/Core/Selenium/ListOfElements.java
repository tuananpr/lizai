package Core.Selenium;

import Core.Support.Selenium.Waiter;
import Core.Utils.IntegerUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ListOfElements extends BaseElement {
    private static String type = "List of Element";
    private int attempts = 0;
    private int time = 2;

    public ListOfElements(By by, String name) {
        super(by, type, name);
    }

    public List<WebElement> getListOfElement() {
        List<WebElement> returnedList = null;
        while (attempts < time) {
            try {
                returnedList = driver.findElements(super.getBy());
                break;
            } catch (StaleElementReferenceException e) {
                e.getMessage();
            }
            attempts++;
        }
        return returnedList;
    }

    public void scrollToElement(int index) {
        while (attempts < time) {
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElements(super.getBy()).get(index));
                Waiter.wait(1);
                break;
            } catch (StaleElementReferenceException e) {
                e.getMessage();
            }
            attempts++;
        }
    }

    public int getNumberOfElement() {
        return getListOfElement().size();
    }

    public void selectAnyItemInList() {
        if (getNumberOfElement() == 1) {
            getListOfElement().get(0).click();
        } else if (getNumberOfElement() > 1) {
            getListOfElement().get(IntegerUtil.randomIntegerInRange(0, getNumberOfElement() - 1)).click();
        } else {
            throw new IndexOutOfBoundsException(">>>>>> Item List Is Empty");
        }
    }

    public WebElement getElement(int index) {
        return getListOfElement().get(index);
    }

    public void selectFirstItemInList() {
        getListOfElement().get(0).click();
    }

    public void scrollToTopOfPage() {
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("scroll(0, 0);");
    }
}
