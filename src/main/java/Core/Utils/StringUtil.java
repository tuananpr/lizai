package Core.Utils;

import org.jsoup.Jsoup;

public class StringUtil {
    public static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    public static String removeLineBreaks(String input) {
        return input.replace("\n", "").replace("\r", "");
    }

    public static String removeHtmlTag(String input) {
        return Jsoup.parse(input).text();
    }
}
