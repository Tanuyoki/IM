package com.yoki.im.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.widget.ImageView;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BitmapUtils {
    public static int[] getImageSize(Drawable drawable) {
        return getImageSize(DrawableUtils.drawableToBitmap(drawable));
    }

    public static int[] getImageSize(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] byteTmp = baos.toByteArray();
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(byteTmp, 0, byteTmp.length, bitmapOptions);
        return calculateBitmapImSize(bitmapOptions.outWidth, bitmapOptions.outHeight);
    }

    public static int[] calculateBitmapSize(int maxWidth, int maxHeight, int outWidth, int outHeight) {
        int[] imageSize = new int[2];
        if (outWidth / maxWidth >= outHeight / maxHeight) {
            if (outWidth >= maxWidth) {
                imageSize[0] = maxWidth;
                imageSize[1] = (outHeight * maxWidth) / outWidth;
            } else {
                imageSize[0] = outWidth;
                imageSize[1] = outHeight;
            }
            if (outHeight < 150) {
                imageSize[1] = 150;
                int width = (outWidth * 150) / outHeight;
                if (width <= maxWidth) {
                    maxWidth = width;
                }
                imageSize[0] = maxWidth;
            }
        } else {
            if (outHeight >= maxHeight) {
                imageSize[1] = maxHeight;
                imageSize[0] = (outWidth * maxHeight) / outHeight;
            } else {
                imageSize[1] = outHeight;
                imageSize[0] = outWidth;
            }
            if (outWidth < 150) {
                imageSize[0] = 150;
                int height = (outHeight * 150) / outWidth;
                if (height <= maxHeight) {
                    maxHeight = height;
                }
                imageSize[1] = maxHeight;
            }
        }
        return imageSize;
    }

    public static int[] calculateBitmapImSize(int outWidth, int outHeight) {
        return calculateBitmapSize(CommonData.ScreenWidth / 2, CommonData.ScreenWidth / 2, outWidth, outHeight);
    }

    public static Bitmap getBitmapByHtml(String url) {
        try {
            return getBitmapByHtml(new URL(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap getBitmapByHtml(URL url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                return BitmapFactory.decodeStream(conn.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while (halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static int calculateInSampleSizeNew(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;
        if (height <= reqHeight && width <= reqWidth) {
            return 1;
        }
        int suitedValue = reqHeight > reqWidth ? reqHeight : reqWidth;
        int heightRatio = Math.round(((float) height) / ((float) suitedValue));
        int widthRatio = Math.round(((float) width) / ((float) suitedValue));
        return heightRatio > widthRatio ? heightRatio : widthRatio;
    }

    public static void printBitmapInfo(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        if (drawable == null) {
            drawable = imageView.getBackground();
        }
        if (drawable != null) {
            printBitmapInfo(((BitmapDrawable) drawable).getBitmap());
        } else {
            LogUtils.d("drawable is null");
        }
    }

    @SuppressLint({"LongLogTag"})
    public static void printBitmapInfo(Bitmap bitmap) {
        LogUtils.d("printBitmapInfo bitmap width = " + bitmap.getWidth() + "  height = " + bitmap.getHeight());
        if (Build.VERSION.SDK_INT >= 19) {
            LogUtils.d("printBitmapInfo memory usage = " + FileUtils.getSizeDescription((long) bitmap.getAllocationByteCount()));
        } else {
            LogUtils.d("printBitmapInfo memory usage = " + FileUtils.getSizeDescription((long) bitmap.getByteCount()));
        }
    }

    public static int byteSizeOf(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= 19) {
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= 12) {
            return bitmap.getByteCount();
        }
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    public static Bitmap readBitmapById(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        return BitmapFactory.decodeStream(context.getResources().openRawResource(resId), null, opt);
    }

    public static Bitmap scaleImage(byte[] buffer, float size) {
        int newHeight;
        int newWidth;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPurgeable = true;
        options.inInputShareable = true;
        float reSize = ((float) options.outWidth) / size;
        if (options.outWidth < options.outHeight) {
            reSize = ((float) options.outHeight) / size;
        }
        if (reSize <= 1.0f) {
            if (options.outWidth > options.outHeight) {
                newWidth = (int) size;
                newHeight = (options.outHeight * ((int) size)) / options.outWidth;
            } else {
                newHeight = (int) size;
                newWidth = (options.outWidth * ((int) size)) / options.outHeight;
            }
            Bitmap bm = scaleImage(BitmapFactory.decodeByteArray(buffer, 0, buffer.length), newWidth, newHeight);
            if (bm != null) {
                return bm;
            }
            LogUtils.e("convertToThumb, decode fail:" + ((Object) null));
            return null;
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = (int) reSize;
        Bitmap bm2 = BitmapFactory.decodeByteArray(buffer, 0, buffer.length, options);
        if (bm2 != null) {
            return bm2;
        }
        LogUtils.e("convertToThumb, decode fail:" + ((Object) null));
        return null;
    }

    public static Bitmap scaleImage(Bitmap bm, int newWidth, int newHeight) {
        return scaleImage(bm, newWidth, newHeight, true);
    }

    public static Bitmap scaleImage(Bitmap bm, int newWidth, int newHeight, boolean isRecycle) {
        if (bm == null) {
            return null;
        }
        if (newWidth == 0 || newHeight == 0) {
            return bm;
        }
        int width = bm.getWidth();
        int height = bm.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(((float) newWidth) / ((float) width), ((float) newHeight) / ((float) height));
        Bitmap createBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        if (!isRecycle || bm == null || bm.isRecycled()) {
            return createBitmap;
        }
        bm.recycle();
        return createBitmap;
    }

    public static byte[] readBitmapFromBuffer(byte[] buffer, float size) {
        return readBitmap(convertToThumb(buffer, size));
    }

    private static byte[] readBitmap(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 60, baos);
        try {
            baos.flush();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    public static Bitmap convertToThumb(byte[] buffer, float size) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPurgeable = true;
        options.inInputShareable = true;
        Bitmap bm = BitmapFactory.decodeByteArray(buffer, 0, buffer.length, options);
        float reSize = ((float) options.outWidth) / size;
        if (options.outWidth > options.outHeight) {
            reSize = ((float) options.outHeight) / size;
        }
        if (reSize <= 0.0f) {
            reSize = 1.0f;
        }
        LogUtils.d("convertToThumb, reSize:" + reSize);
        options.inJustDecodeBounds = false;
        options.inSampleSize = (int) reSize;
        if (bm != null && !bm.isRecycled()) {
            bm.recycle();
            LogUtils.e("convertToThumb, recyle");
        }
        Bitmap bm2 = BitmapFactory.decodeByteArray(buffer, 0, buffer.length, options);
        if (bm2 != null) {
            return bm2;
        }
        LogUtils.e("convertToThumb, decode fail:" + ((Object) null));
        return null;
    }

    public static Bitmap decodeStream(Context context, Intent data, float size) {
        try {
            Uri dataUri = data.getData();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inPurgeable = true;
            options.inInputShareable = true;
            BitmapFactory.decodeStream(context.getContentResolver().openInputStream(dataUri), null, options);
            float reSize = (float) ((int) (((float) options.outWidth) / size));
            if (reSize <= 0.0f) {
                reSize = 1.0f;
            }
            LogUtils.d("old-w:" + options.outWidth + ", llyt-w:" + size + ", resize:" + reSize);
            options.inJustDecodeBounds = false;
            options.inSampleSize = (int) reSize;
            return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(dataUri), null, options);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap fitBitmap(Bitmap image, int newWidth) {
        int width = image.getWidth();
        int height = image.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) newWidth) / ((float) width);
        int i = (int) (((float) height) * scaleWidth);
        matrix.postScale(scaleWidth, scaleWidth);
        Bitmap bmp = Bitmap.createBitmap(image, 0, 0, width, height, matrix, true);
        if (image != null && !image.equals(bmp) && !image.isRecycled()) {
            image.recycle();
        }
        return bmp;
    }

    public static Bitmap createRepeater(Bitmap image, int width) {
        int count = ((image.getWidth() + width) - 1) / image.getWidth();
        Bitmap bitmap = Bitmap.createBitmap(width, image.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        for (int idx = 0; idx < count; idx++) {
            canvas.drawBitmap(image, (float) (image.getWidth() * idx), 0.0f, (Paint) null);
        }
        return bitmap;
    }

    public static Bitmap compressionBitmap(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 1024) {
            baos.reset();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        }
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        int be = 1;
        if (w > h && ((float) w) > 720.0f) {
            be = (int) (((float) newOpts.outWidth) / 720.0f);
        } else if (w < h && ((float) h) > 1280.0f) {
            be = (int) (((float) newOpts.outHeight) / 1280.0f);
        }
        if (be <= 0) {
            be = 1;
        }
        newOpts.inSampleSize = be;
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        if (isBm != null) {
            try {
                isBm.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (image != null && !image.isRecycled()) {
            image.recycle();
        }
        return qualityCompress(bitmap);
    }

    public static Bitmap qualityCompress(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        while (baos.toByteArray().length / 1024 > 1024) {
            baos.reset();
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 10;
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        if (baos != null) {
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (isBm != null) {
            try {
                isBm.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        if (image != null && !image.isRecycled()) {
            image.recycle();
        }
        return bitmap;
    }

    public static File compressImageToFile(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        while (baos.toByteArray().length / 1024 > 1024) {
            baos.reset();
            options -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            long length = (long) baos.toByteArray().length;
        }
        File file = new File(PathUtils.getStoragePicturesPathAndTime()[1]);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
        recycleBitmap(bitmap);
        return file;
    }

    public static void recycleBitmap(Bitmap... bitmaps) {
        if (bitmaps != null) {
            for (Bitmap bm : bitmaps) {
                if (bm != null && !bm.isRecycled()) {
                    bm.recycle();
                }
            }
        }
    }

    public static Bitmap getBitmapById(Context context, int drawableId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inInputShareable = true;
        options.inPurgeable = true;
        return BitmapFactory.decodeStream(context.getResources().openRawResource(drawableId), null, options);
    }

    public static Bitmap getImBitmapByPath(String path) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        BitmapFactory.decodeFile(path, opts);
        int[] size = calculateBitmapImSize(opts.outWidth, opts.outHeight);
        opts.inSampleSize = calculateInSampleSize(opts, size[0], size[1]);
        opts.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, opts);
    }

    public static int[] getImPictureSize(String path) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        BitmapFactory.decodeFile(path, opts);
        return calculateBitmapImSize(opts.outWidth, opts.outHeight);
    }
}
