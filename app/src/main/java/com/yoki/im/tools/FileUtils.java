package com.yoki.im.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.media.session.PlaybackStateCompat;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;

public class FileUtils {
    public static Bitmap getHttpBitmap(String url) {
        Bitmap bitmap = null;
        try {
            InputStream in = new URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return bitmap;
        }
    }

    public static boolean saveBitmapFile(Bitmap bitmap, String fileName) {
        boolean isSaveSuccess = false;
//        if (PermissionUtils.checkPermission("android.permission.WRITE_EXTERNAL_STORAGE")) {
//            File file = new File(PathUtils.getStoragePicturesPath(fileName));
//            if (file.exists()) {
//                file.delete();
//            }
//            try {
//                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
//                isSaveSuccess = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
//                bos.flush();
//                bos.close();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e2) {
//                e2.printStackTrace();
//            }
//            SystemUtils.scanFile(file);
//        } else {
//            PermissionUtils.applyPermission("android.permission.WRITE_EXTERNAL_STORAGE");
//        }
        return isSaveSuccess;
    }

    public static long getFileSize(File f) {
        Exception e;
        if (!f.exists()) {
            return 0;
        }
        try {
            try {
                return (long) new FileInputStream(f).available();
            } catch (Exception e2) {
                e = e2;
                e.printStackTrace();
                return 0;
            }
        } catch (Exception e3) {
            e = e3;
            e.printStackTrace();
            return 0;
        }
    }

    public static String getSizeDescription(long size) {
        StringBuffer bytes = new StringBuffer();
        DecimalFormat format = new DecimalFormat("###.0");
        if (size >= 1073741824) {
            bytes.append(format.format(((double) size) / 1.073741824E9d)).append("GB");
        } else if (size >= PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED) {
            bytes.append(format.format(((double) size) / 1048576.0d)).append("MB");
        } else if (size >= PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            bytes.append(format.format(((double) size) / 1024.0d)).append("KB");
        } else if (size < PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            if (size <= 0) {
                bytes.append("0B");
            } else {
                bytes.append((int) size).append("B");
            }
        }
        return bytes.toString();
    }
}
