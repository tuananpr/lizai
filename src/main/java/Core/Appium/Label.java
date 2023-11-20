package Core.Appium;

import org.openqa.selenium.By;

import java.util.logging.Logger;

public class Label extends MobileBaseElement {
    private final static Logger LOGGER = Logger.getLogger(Label.class.getCanonicalName());

    private int attempts = 0;
    private int tryingTime = 2;

    public Label(By by, String name) {
        super(by, name);
    }
}
