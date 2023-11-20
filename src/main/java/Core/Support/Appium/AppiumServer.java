package Core.Support.Appium;

import Core.Support.General.CommandExecutor;

public class AppiumServer implements Runnable {
    @Override
    public void run() {
        System.out.println("--> Start appium server.");
        CommandExecutor.execute("appium");
    }
}
