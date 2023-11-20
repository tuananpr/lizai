package Core.Support.Appium;

import Core.BaseEntities;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;

import java.time.Duration;

public class Gesture extends BaseEntities {
    private static Gesture instance;

    private Gesture() {
    }

    public static Gesture getInstance() {
        if (instance == null) {
            instance = new Gesture();
        }
        return instance;
    }

    public void pressBackKey() {
        appiumDriver.navigate().back();
    }

    public void reload() {
        final int PRESS_TIME = 500;
        int edgeBorder = 10;
        AndroidDriver androidDriver = (AndroidDriver) appiumDriver;
        PointOption pointOptionStart, pointOptionEnd;
        Dimension dims = appiumDriver.manage().window().getSize();
        pointOptionStart = PointOption.point(dims.width / 2, dims.height / 2);
        pointOptionEnd = PointOption.point(dims.width / 2, dims.height - edgeBorder);
        try {
            AppiumWaiter.wait(1);
            new TouchAction(androidDriver)
                    .press(pointOptionStart)
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME)))
                    .moveTo(pointOptionEnd)
                    .release().perform();
            AppiumWaiter.wait(3);
        } catch (Exception e) {
            System.err.println("TouchAction FAILED\n" + e.getMessage());
        }
    }

    public void performEditorAction(EDITOR_ACTION editor_action) {
        /*
        action: The name or an integer code of the editor action to be executed.
        The following action names are supported: normal, unspecified, none, go, search, send, next, done, previous.
        Read https://developer.android.com/reference/android/view/inputmethod/EditorInfo for more details on this topic.
         */
        String action;
        switch (editor_action) {
            case GO:
                action = "go";
                break;
            case DONE:
                action = "done";
                break;
            case NEXT:
                action = "next";
                break;
            case SEND:
                action = "send";
                break;
            case SEARCH:
                action = "search";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + editor_action);
        }
        appiumDriver.executeScript("mobile: performEditorAction", ImmutableMap.of("action", action));
    }

    public enum EDITOR_ACTION {
        SEND,
        GO,
        SEARCH,
        NEXT,
        DONE
    }
}
