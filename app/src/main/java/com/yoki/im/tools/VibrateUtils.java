package com.yoki.im.tools;

import android.app.Activity;
import android.os.Vibrator;

public class VibrateUtils {
    public static void vibrate(Activity activity) {
        ((Vibrator) activity.getSystemService("vibrator")).vibrate(150);
    }

    public static void vibrate(Activity activity, long milliseconds) {
        ((Vibrator) activity.getSystemService("vibrator")).vibrate(milliseconds);
    }

    public static void vibrate(Activity activity, long[] pattern, int repeat) {
        ((Vibrator) activity.getSystemService("vibrator")).vibrate(pattern, repeat);
    }

    public static void virateCancle(Activity activity) {
        ((Vibrator) activity.getSystemService("vibrator")).cancel();
    }
}
