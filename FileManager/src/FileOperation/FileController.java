package FileOperation;

import java.io.File;

/**
 * @author Ðì´Ï
 * @version 1.0
 * @date 2021-11-19 18:42
 */

public class FileController {
    public static String path = "";
    public static File copyFile = null;
    public static boolean hideFile = false;


    public static void setPath(String path) {
        FileController.path = path;
    }

    public static void setHideFile(boolean hideFile) {
        FileController.hideFile = hideFile;
    }

    public static String getPath() {
        return path;
    }

    public static boolean isHideFile() {
        return hideFile;
    }

    public static File getCopyFile() {
        return copyFile;
    }

    public static void setCopyFile(File copyFile) {
        FileController.copyFile = copyFile;
    }
}
