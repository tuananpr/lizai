package Core.Selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;

import java.util.logging.Logger;

public class FileUploader extends BaseElement {
    private final static Logger LOGGER = Logger.getLogger(FileUploader.class.getCanonicalName());

    private static String type = "File Uploader";
    private int attempts = 0;
    private int time = 2;

    public FileUploader(By by, String name) {
        super(by, type, name);
    }

    public void upload(String filePath) {
        while (attempts < time) {
            try {
                super.getElement().sendKeys(filePath);
                break;
            } catch (StaleElementReferenceException e) {
                e.getMessage();
            }
            attempts++;
        }
    }
}
