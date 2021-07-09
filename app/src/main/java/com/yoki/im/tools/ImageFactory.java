package com.yoki.im.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageFactory {
    public static Bitmap getBitmap(String imgPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = false;
        newOpts.inPurgeable = true;
        newOpts.inInputShareable = true;
        newOpts.inSampleSize = 1;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(imgPath, newOpts);
    }

    public static void storeImage(Bitmap bitmap, String outPath) throws FileNotFoundException {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(outPath));
    }

    public static Bitmap ratio(String imgPath, float pixelW, float pixelH) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        BitmapFactory.decodeFile(imgPath, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        int be = 1;
        if (w > h && ((float) w) > pixelW) {
            be = (int) (((float) newOpts.outWidth) / pixelW);
        } else if (w < h && ((float) h) > pixelH) {
            be = (int) (((float) newOpts.outHeight) / pixelH);
        }
        if (be <= 0) {
            be = 1;
        }
        newOpts.inSampleSize = be;
        return BitmapFactory.decodeFile(imgPath, newOpts);
    }

    public static Bitmap ratio(Bitmap image, float pixelW, float pixelH) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, os);
        if (os.toByteArray().length / 1024 > 1024) {
            os.reset();
            image.compress(Bitmap.CompressFormat.JPEG, 50, os);
        }
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        BitmapFactory.decodeStream(is, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        int be = 1;
        if (w > h && ((float) w) > pixelW) {
            be = (int) (((float) newOpts.outWidth) / pixelW);
        } else if (w < h && ((float) h) > pixelH) {
            be = (int) (((float) newOpts.outHeight) / pixelH);
        }
        if (be <= 0) {
            be = 1;
        }
        newOpts.inSampleSize = be;
        return BitmapFactory.decodeStream(new ByteArrayInputStream(os.toByteArray()), null, newOpts);
    }

    public static void compressAndGenImage(Bitmap image, String outPath, int maxSize) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int options = 100;
        image.compress(Bitmap.CompressFormat.JPEG, 100, os);
        while (os.toByteArray().length / 1024 > maxSize) {
            os.reset();
            options -= 10;
            image.compress(Bitmap.CompressFormat.JPEG, options, os);
        }
        FileOutputStream fos = new FileOutputStream(outPath);
        fos.write(os.toByteArray());
        fos.flush();
        fos.close();
    }

    public static void compressAndGenImage(String imgPath, String outPath, int maxSize, boolean needsDelete) throws IOException {
        compressAndGenImage(getBitmap(imgPath), outPath, maxSize);
        if (needsDelete) {
            File file = new File(imgPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public static void ratioAndGenThumb(Bitmap image, String outPath, float pixelW, float pixelH) throws FileNotFoundException {
        storeImage(ratio(image, pixelW, pixelH), outPath);
    }

    public static void ratioAndGenThumb(String imgPath, String outPath, float pixelW, float pixelH, boolean needsDelete) throws FileNotFoundException {
        storeImage(ratio(imgPath, pixelW, pixelH), outPath);
        if (needsDelete) {
            File file = new File(imgPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
