package Core.Utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    public static ArrayList<String> getMatchItemList(Pattern pattern, String inputSource) {
        Matcher matcher = pattern.matcher(inputSource);
        ArrayList<String> resultList = new ArrayList<>();
        while (matcher.find()) {
            resultList.add(matcher.group(1));
        }
        return resultList;
    }
}
