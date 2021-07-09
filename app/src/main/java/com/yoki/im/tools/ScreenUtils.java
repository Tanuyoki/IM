package com.yoki.im.tools;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;

public final class ScreenUtils {

    private static Context context;

    private ScreenUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService("window");
        if (wm == null) {
            return context.getResources().getDisplayMetrics().widthPixels;
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= 17) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService("window");
        if (wm == null) {
            return context.getResources().getDisplayMetrics().heightPixels;
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= 17) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    public static float getScreenDensity() {
        return context.getResources().getDisplayMetrics().density;
    }

    public static int getScreenDensityDpi() {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    public static void setFullScreen(@NonNull Activity activity) {
        activity.getWindow().addFlags(1536);
    }

    public static void setNonFullScreen(@NonNull Activity activity) {
        activity.getWindow().clearFlags(1536);
    }

    public static void toggleFullScreen(@NonNull Activity activity) {
        Window window = activity.getWindow();
        if ((window.getAttributes().flags & 1024) == 1024) {
            window.clearFlags(1536);
        } else {
            window.addFlags(1536);
        }
    }

    public static boolean isFullScreen(@NonNull Activity activity) {
        return (activity.getWindow().getAttributes().flags & 1024) == 1024;
    }

    public static void setLandscape(@NonNull Activity activity) {
//        activity.setRequestedOrientation(0);
    }

    public static void setPortrait(@NonNull Activity activity) {
//        activity.setRequestedOrientation(1);
    }

    public static boolean isLandscape() {
        return context.getResources().getConfiguration().orientation == 2;
    }

    public static boolean isPortrait() {
        return context.getResources().getConfiguration().orientation == 1;
    }

    public static int getScreenRotation(@NonNull Activity activity) {
        switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
            case 0:
            default:
                return 0;
            case 1:
                return 90;
            case 2:
                return 180;
            case 3:
                return 270;
        }
    }

    public static Bitmap screenShot(@NonNull Activity activity) {
        return screenShot(activity, false);
    }

    public static Bitmap screenShot(@NonNull Activity activity, boolean isDeleteStatusBar) {
        Bitmap ret;
        View decorView = activity.getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        decorView.setWillNotCacheDrawing(false);
        Bitmap bmp = decorView.getDrawingCache();
        if (bmp == null) {
            return null;
        }
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        if (isDeleteStatusBar) {
            Resources resources = activity.getResources();
            int statusBarHeight = resources.getDimensionPixelSize(resources.getIdentifier("status_bar_height", "dimen", "android"));
            ret = Bitmap.createBitmap(bmp, 0, statusBarHeight, dm.widthPixels, dm.heightPixels - statusBarHeight);
        } else {
            ret = Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels);
        }
        decorView.destroyDrawingCache();
        return ret;
    }

    public static boolean isScreenLock() {
        KeyguardManager km = (KeyguardManager) context.getSystemService("keyguard");
        return km != null && km.inKeyguardRestrictedInputMode();
    }

    @RequiresPermission("android.permission.WRITE_SETTINGS")
    public static void setSleepDuration(int duration) {
        Settings.System.putInt(context.getContentResolver(), "screen_off_timeout", duration);
    }

    public static int getSleepDuration() {
        try {
            return Settings.System.getInt(context.getContentResolver(), "screen_off_timeout");
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return -123;
        }
    }

    public static boolean isTablet() {
        return (context.getResources().getConfiguration().screenLayout & 15) >= 3;
    }

    public static void adapterScreen(Activity activity) {
        adapterScreen(activity, false);
    }

    public static void adapterScreen(Activity activity, boolean isVerticalSlide) {
        if (isVerticalSlide) {
            adaptScreen4VerticalSlide(activity, 720);
        } else {
            adaptScreen4HorizontalSlide(activity, 1280);
        }
    }

    public static void adaptScreen4VerticalSlide(Activity activity, int designWidthInPx) {
        adaptScreen(activity, designWidthInPx, true);
    }

    public static void adaptScreen4HorizontalSlide(Activity activity, int designHeightInPx) {
        adaptScreen(activity, designHeightInPx, false);
    }

    private static void adaptScreen(Activity activity, int sizeInPx, boolean isVerticalSlide) {
        if (activity != null) {
            DisplayMetrics systemDm = Resources.getSystem().getDisplayMetrics();
            DisplayMetrics appDm = activity.getResources().getDisplayMetrics();
            DisplayMetrics activityDm = activity.getResources().getDisplayMetrics();
            if (isVerticalSlide) {
                activityDm.density = ((float) activityDm.widthPixels) / ((float) sizeInPx);
            } else {
                activityDm.density = ((float) activityDm.heightPixels) / ((float) sizeInPx);
            }
            activityDm.scaledDensity = activityDm.density * (systemDm.scaledDensity / systemDm.density);
            activityDm.densityDpi = (int) (160.0f * activityDm.density);
            appDm.density = activityDm.density;
            appDm.scaledDensity = activityDm.scaledDensity;
            appDm.densityDpi = activityDm.densityDpi;
            CommonData.ScreenDensity = activityDm.density;
            CommonData.ScreenScaledDensity = activityDm.scaledDensity;
        }
    }

    public static void cancelAdaptScreen(Activity activity) {
        if (activity != null) {
            DisplayMetrics systemDm = Resources.getSystem().getDisplayMetrics();
            DisplayMetrics appDm = context.getResources().getDisplayMetrics();
            DisplayMetrics activityDm = activity.getResources().getDisplayMetrics();
            activityDm.density = systemDm.density;
            activityDm.scaledDensity = systemDm.scaledDensity;
            activityDm.densityDpi = systemDm.densityDpi;
            appDm.density = systemDm.density;
            appDm.scaledDensity = systemDm.scaledDensity;
            appDm.densityDpi = systemDm.densityDpi;
            CommonData.ScreenDensity = activityDm.density;
            CommonData.ScreenScaledDensity = activityDm.scaledDensity;
        }
    }

    public static boolean isAdaptScreen() {
        return Resources.getSystem().getDisplayMetrics().density != context.getResources().getDisplayMetrics().density;
    }

    @Deprecated
    public static int getScreenResolution(Context context) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels * outMetrics.heightPixels;
    }

    public static int getStatusHeight(Context context) {
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            return context.getResources().getDimensionPixelSize(Integer.parseInt(clazz.getField("status_bar_height").get(clazz.newInstance()).toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            String navBarOverride = (String) systemPropertiesClass.getMethod("get", String.class).invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                return false;
            }
            if ("0".equals(navBarOverride)) {
                return true;
            }
            return hasNavigationBar;
        } catch (Exception e) {
            return hasNavigationBar;
        }
    }

    public static int getVirtualBarHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            Class.forName("android.view.Display").getMethod("getRealMetrics", DisplayMetrics.class).invoke(display, dm);
            return dm.heightPixels - windowManager.getDefaultDisplay().getHeight();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
