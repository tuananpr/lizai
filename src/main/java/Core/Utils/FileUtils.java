package Core.Utils;

import Core.Support.General.PathFinder;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

public class FileUtils {
    public static ArrayList<String> getFilesName(String folderPath) {
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> filesName = new ArrayList<>();

        for (int i = 0; i < Objects.requireNonNull(listOfFiles).length; i++) {
            if (listOfFiles[i].isFile()) {
                filesName.add(listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
        return filesName;
    }

    public static void clearDataInDownload() {
        String downloadFolderPath = PathFinder.getDownloadFolderPath();
        if (isFolderExisted(downloadFolderPath)) {
            try {
                org.apache.commons.io.FileUtils.cleanDirectory(new File(downloadFolderPath));
                System.out.println("Download folder is cleared");
            } catch (IOException e) {
                System.out.println("Something wrong when trying to clean Download Folder");
            }
        } else {
            System.out.println("Download Folder is not Exist ... do nothing");
        }
    }

    public static void clearDataInTmp() {
        String downloadFolderPath = PathFinder.getTmpFolderPath();
        if (isFolderExisted(downloadFolderPath)) {
            try {
                org.apache.commons.io.FileUtils.cleanDirectory(new File(downloadFolderPath));
                System.out.println("Download folder is cleared");
            } catch (IOException e) {
                System.out.println("Something wrong when trying to clean Download Folder");
            }
        } else {
            System.out.println("Download Folder is not Exist ... do nothing");
        }
    }

    public static boolean isFolderExisted(String folderPath) {
        Path path = Paths.get(folderPath);
        return java.nio.file.Files.exists(path);
    }

    public static void createFolder(String folderPath) {
        try {
            Path path = Paths.get(folderPath);
            //java.nio.file.Files;
            java.nio.file.Files.createDirectories(path);
            System.out.println("Directory is created!");
        } catch (IOException e) {
            System.err.println("Failed to create directory!" + e.getMessage());
        }
    }

    public static String getFileExtension(String filePath) {
        return Files.getFileExtension(filePath);
    }

    public static void copy(String originalPath, String copiedPath) {
        File original = new File(originalPath);
        File copied = new File(copiedPath);
        try {
            org.apache.commons.io.FileUtils.copyFile(original, copied);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copy(File file, String des) {
        try {
            org.apache.commons.io.FileUtils.copyFile(file, new File(des));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
