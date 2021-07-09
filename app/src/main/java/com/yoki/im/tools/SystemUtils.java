package com.yoki.im.tools;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import java.io.File;

public class SystemUtils {
    public static void scanFile(Context context, File file) {
        scanFile(context, file.getAbsolutePath());
    }

    public static void scanFile(Context context, String filePath) {
        context.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.parse("file://" + filePath)));
    }

    public static String getSystemModel() {
        return Build.MODEL;
    }

    public static String getDeviceBrand() {
        return Build.BRAND;
    }
}
