package Core.Utils;

import java.util.concurrent.ThreadLocalRandom;

public class IntegerUtil {
    public static int randomIntegerInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalStateException("MIN should be < MAX");
        } else {
            return ThreadLocalRandom.current().nextInt(min, max + 1);
        }
    }

    public static int convertToInt(String input) {
        return Integer.parseInt(input);
    }
}
