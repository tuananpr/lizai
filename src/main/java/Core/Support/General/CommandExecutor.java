package Core.Support.General;

import Core.BaseEntities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandExecutor extends BaseEntities {
    public static void execute(String command) {
        System.out.println("--> Execute command: " + command);
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while (true) {
            try {
                if ((line = reader.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(line);
    }

    public static void stopEmulator() {
        String adbPath = "emulator";
        if (teamcity()) {
            adbPath = PropertyBuilder.getTeamcityConfig().getProperty("path.adb");
        }
        execute(adbPath + " emu kill");
    }
}
