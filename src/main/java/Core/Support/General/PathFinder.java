package Core.Support.General;

import Core.BaseEntities;

import java.io.File;
import java.util.logging.Logger;

public class PathFinder extends BaseEntities {
    private final static String currentWorkingDirectory = System.getProperty("user.dir");
    private final static Logger LOGGER = Logger.getLogger(PathFinder.class.getCanonicalName());

    // Folder
    private static final String configFolderName = "config";
    private static final String UTILS = "Utils";
    private static final String DATA = "data";
    private static final String AVATARS = "avatars";
    private static final String TMP = "tmp";
    private static final String DOWNLOAD = "download";
    private static final String SIKULI = "sikuli_image";
    private static final String CORE = "Core";

    public static String getPathToMainConfiguration() throws Exception {

        String mainConfigurationFileName;
        String env = BaseEntities.getEnv();
        if (env.equals("")) {
            env = "demo";
        }

        switch (env.toUpperCase()) {
            case "DEMO":
                mainConfigurationFileName = "demo.properties";
                break;
            case "PRODUCTION":
                mainConfigurationFileName = "production.properties";
                break;
            default:
                throw new Exception("Invalid 'env' ... ");
        }
        return currentWorkingDirectory + File.separator + configFolderName + File.separator + mainConfigurationFileName;
    }

    public static String getPathToTimeOutConfiguration() {
        return currentWorkingDirectory + File.separator + configFolderName + File.separator + "timeout.properties";
    }

    public static String getPathToTeamcityConfig() {
        return currentWorkingDirectory + File.separator + configFolderName + File.separator + "teamcity.properties";
    }

    public static String getPathToGmailConfiguration() {
        return currentWorkingDirectory + File.separator + configFolderName + File.separator + "gmail.properties";
    }

    public static String getSecretKeyPath() {
        return currentWorkingDirectory + File.separator + "key.txt";
    }

    public static String getAvatarsPath() {
        return currentWorkingDirectory + File.separator + DATA + File.separator + AVATARS + File.separator;
    }

    public static String getDataFolderPath() {
        return currentWorkingDirectory + File.separator + DATA + File.separator;
    }

    public static String getJsonDataFolderPath() {
        return getDataFolderPath() + "payload" + File.separator;
    }

    public static String getReceiptDataFolderPath() {
        return getDataFolderPath() + "receipt" + File.separator;
    }

    public static String getBottleCapsFolderPath() {
        return getDataFolderPath() + "bottle_caps" + File.separator;
    }

    public static String getSikuliImageStorage() {
        return currentWorkingDirectory + File.separator + DATA + File.separator + SIKULI + File.separator;
    }

    public static String getDownloadFolderPath() {
        return getDataFolderPath() + DOWNLOAD;
    }

    public static String getTmpFolderPath() {
        return currentWorkingDirectory + File.separator + TMP + File.separator;
    }

    public static String getPathToAppiumConfiguration() {
        return currentWorkingDirectory + File.separator + configFolderName + File.separator + "appium.properties";
    }
}
