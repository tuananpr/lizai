package Core.Selenium;

import org.openqa.selenium.By;

import java.util.logging.Logger;

public class CheckBox extends BaseElement {
    private final static Logger LOGGER = Logger.getLogger(CheckBox.class.getCanonicalName());

    private static String type = "Check Box";

    public CheckBox(By by, String name) {
        super(by,type,name);
    }

    public boolean isChecked() {
        try {
            String text = getElement().getAttribute("checked");
            if (text != null) {
                return true;
            }
        } catch (Exception e) {
            e.getMessage();
            return false;
        }
        return false;
    }
}
