package com.yoki.im.tools;

import android.os.Environment;
import java.io.File;

public class PathUtils {
    private static final String APK_NAME = "acarbang_temp.apk";
    private static final String CACHE_PICTURES_DIRECTORY = "photo";
    private static final String CACHE_ROOT_PATH = getCacheRootDirectory();
    private static final String CACHE_TEMP_DIRECTORY = "temp";
    private static final String CACHE_VOICE_DIRECTORY = "voice";
    private static final String STORAGE_DIRECTORY = "Acarbang";

    public static String getCacheRootDirectory() {
        try {
//            return App.getContext().getExternalFilesDir(null).getPath();
            return "";
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getCachePicturesDirectory() {
        String directory = CACHE_ROOT_PATH + File.separator + CACHE_PICTURES_DIRECTORY + File.separator;
        createDirectory(directory);
        return directory;
    }

    public static String getCachePicturesPath(String fileName) {
        return getCachePicturesDirectory() + fileName + ".jpg";
    }

    public static String getCachePicturesPathAndTime() {
        return getCachePicturesPath("IMG_" + StringUtils.getSimpleDateFormat());
    }

    public static String getStoragePicturesDirectory() {
        String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "Acarbang" + File.separator;
        createDirectory(directory);
        return directory;
    }

    public static String getStoragePicturesPath(String fileName) {
        return getStoragePicturesDirectory() + fileName + ".jpg";
    }

    public static String[] getStoragePicturesPathAndTime() {
        String[] path = new String[2];
        path[0] = "IMG_" + StringUtils.getSimpleDateFormat();
        path[1] = getStoragePicturesPath(path[0]);
        return path;
    }

    public static String getVoicePath() {
        String directory = CACHE_ROOT_PATH + File.separator + CACHE_VOICE_DIRECTORY + File.separator;
        createDirectory(directory);
        return directory + System.currentTimeMillis() + ".amr";
    }

    public static String getApkPath() {
        String directory = CACHE_ROOT_PATH + File.separator + CACHE_TEMP_DIRECTORY + File.separator;
        createDirectory(directory);
        return directory + APK_NAME;
    }

    private static void createDirectory(String path) {
        File dirFirstFolder = new File(path);
        if (!dirFirstFolder.exists()) {
            dirFirstFolder.mkdirs();
        }
    }
}
