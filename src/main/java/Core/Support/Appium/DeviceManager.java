package Core.Support.Appium;

import Core.BaseEntities;

public class DeviceManager extends BaseEntities {
    private static boolean isEmulatorStarted = false;

    public static void startEmulator(String emulatorName) {
        if (!isEmulatorStarted) {
            Emulator emulator = new Emulator(emulatorName);
            Thread thread = new Thread(emulator);
            thread.start();
            try {
                System.out.println("--> [INFO] wait for emulator started completely ...");
                if (teamcity()) {
                    Thread.sleep(60000);
                } else {
                    Thread.sleep(30000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            isEmulatorStarted = true;
        } else {
            System.out.printf("--> [INFO] Emulator [%s] is started already ...%n", emulatorName);
        }
    }
}
