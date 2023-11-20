package Core.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Vars {
    private final static Logger LOGGER = Logger.getLogger(Vars.class.getCanonicalName());

    private static List<Map<String, Object>> varList = new ArrayList<>();

    public static final String OPENER_CAMPAIGN = "opener_campaign";
    public static final String STAFF_REFERRAL_CAMPAIGN = "staff_referral_campaign";
    public static final String CAMPAIGN = "campaign";
    public static final String UNIVERSAL_CODE = "universal_code";

    private static boolean isVariableNameExist(String varName) {
        for (Map<String, Object> item : varList) {
            if (item.containsKey(varName)) {
                return true;
            }
        }
        return false;
    }

    private static int getKeyIndexByItsName(String keyName) {
        for (int i = 0; i < varList.size(); i++) {
            if (varList.get(i).containsKey(keyName)) {
                return i;
            }
        }
        return -1;
    }

    public static void clearVarList() {
        LOGGER.log(Level.INFO,">>>>> Vars list is cleared ...");
        varList = new ArrayList<>();
    }

    public static void add(String varName, Object object) {
        if (!isVariableNameExist(varName)) {
            Map<String, Object> itemToAdd = new HashMap<>();
            itemToAdd.put(varName, object);
            varList.add(itemToAdd);
        } else {
            System.out.printf("--> [INFO] Update variable name [%s] with value [%s]\n", varName, object);
            varList.remove(getKeyIndexByItsName(varName));
            Map<String, Object> itemToAdd = new HashMap<>();
            itemToAdd.put(varName, object);
            varList.add(itemToAdd);
        }
    }

    /*
    Have to cast object back to their original type
    Example: Employee employee = ((Employee)Vars.get("employee1"));
    -> Cast the stored Object back to Employee type
     */
    public static Object get(String varName) {
        if (isVariableNameExist(varName)) {
            return varList.get(getKeyIndexByItsName(varName)).get(varName);
        } else {
            LOGGER.log(Level.WARNING,"[WARNING]: Variable with name = " + varName + " is not exist");
            return "";
        }
    }
}