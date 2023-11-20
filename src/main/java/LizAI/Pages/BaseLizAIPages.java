package LizAI.Pages;

import Core.BasePage;
import Core.Selenium.Label;
import Core.Support.Selenium.Waiter;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

public class BaseLizAIPages extends BasePage {
    //locator
    private final String xpath_circleLoading = "//div[@class='loading-container']";
    private final String xpath_thundraTraceSnackBarError = "//snack-bar-container[contains(.,'Error')]";
    private final String xpath_toolTip = "//div[@class='tooltip BR']";

    //elements
    private final Label loading = new Label(By.xpath(xpath_circleLoading), "Loading");
    private final Label labelThundraSnackBarError = new Label(By.xpath(xpath_thundraTraceSnackBarError), "Thundra Error");
    private final Label labelToolTip = new Label(By.xpath(xpath_toolTip), "ToolTip");
    public static final Label labelToastContainer = new Label(By.id("toast-container"),"labelToastContainer");

    //constructor
    public BaseLizAIPages(By pageLocator, String pageName, boolean assertPageOpen) {super(pageLocator, pageName, assertPageOpen);}

    public void waitForLoadingComplete() {
        if (loading.isElementDisplay()) {
            try {
                loading.waitForElementToBeDisappear();
            } catch (TimeoutException e) {
                Waiter.wait(1);
            }
        }
    }

    public boolean isLoadingDisplays() {
        return loading.isElementDisplay();
    }

    public boolean isThundraErrorDisplay() {
        return labelThundraSnackBarError.isElementDisplay();
    }

    public void closeToolTip() {
        waitForLoadingComplete();
        waitForJSToComplete();
        int counter = 0;
        int attempts = 3;
        while (counter < attempts) {
            if (labelToolTip.isElementDisplay()) {
                labelToolTip.click();
                labelToolTip.waitForElementToBeDisappear();
                counter++;
            } else {
                break;
            }
        }
    }

    public static String getToastMessage() {
        labelToastContainer.getText();
        return labelToastContainer.getText();
    }
}
