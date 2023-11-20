package Core.Utils;

import org.testng.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.UUID;

public class CommonUtil {
    public static void verifyArraySize(ArrayList<?> arr, int expectedSize) {
        Assert.assertEquals(arr.size(), expectedSize, String.format("Expect size [%s] but actual is [%s]", expectedSize, arr.size()));
    }

    public static void verifyArraySizeGreaterThan(ArrayList<?> arr, int expectedSize) {
        Assert.assertTrue(arr.size() > expectedSize, String.format("Expect Array Size should be greater than [%s], but actual array size is [%s]", expectedSize, arr.size()));
    }

    public static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    public static String removeLineBreaks(String input) {
        return input.replace("\n", "").replace("\r", "");
    }

    public static void executeShell(String commandOrPath) {
        try {
            ProcessBuilder pb = new ProcessBuilder(commandOrPath);
            Process p = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String generateRandomUUID() {
        return UUID.randomUUID().toString();
    }
}
