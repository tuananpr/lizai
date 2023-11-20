package Core.Selenium;

import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Select extends BaseElement {
    private final static Logger LOGGER = Logger.getLogger(Select.class.getCanonicalName());

    private static String type = "Select";

    public Select(By by, String name) {
        super(by, type, name);
    }

    public void selectByVisibleText(String text) {
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(super.getElement());
        select.selectByVisibleText(text);
    }

    public ArrayList<String> getOptionsText() {
        ArrayList<String> arr = new ArrayList<>();
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(super.getElement());
        select.getOptions().forEach(x -> arr.add(x.getText()));
        return arr;
    }

    public void selectByContains(String text){
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(super.getElement());
        select.getOptions().contains(text);
    }
}
