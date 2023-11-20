package Core.Selenium;

import org.openqa.selenium.By;

public class Label extends BaseElement {
    private static String type = "Label";

    public Label(By by, String name) {
        super(by,type,name);
    }

}
