package Core.Support.Appium;

import Core.BaseEntities;
import Core.Support.General.CommandExecutor;
import Core.Support.General.PropertyBuilder;

public class Emulator extends BaseEntities implements Runnable {
    private String emulatorName;

    public Emulator(String emulatorName) {
        this.emulatorName = emulatorName;
    }

    @Override
    public void run() {
        System.out.println("--> Start emulator ...");
        String commandPath = "emulator";
        if (teamcity()) {
            commandPath = PropertyBuilder.getTeamcityConfig().getProperty("path.emulator");
        }
        CommandExecutor.execute(String.format(commandPath + " @%s -no-snapshot-load", emulatorName));
    }
}
