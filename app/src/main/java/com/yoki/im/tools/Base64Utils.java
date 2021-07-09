package com.yoki.im.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import java.io.ByteArrayOutputStream;

public class Base64Utils {
    public static String bitmapToBase64(Bitmap bit) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        return Base64.encodeToString(bos.toByteArray(), 0).replaceAll("\r|\n", "");
    }

    public static Bitmap base64ToBitmap(String base64) {
        try {
            byte[] bitmapArray = Base64.decode(base64, 0);
            return BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
