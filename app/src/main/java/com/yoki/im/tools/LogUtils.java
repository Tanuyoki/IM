package com.yoki.im.tools;

import android.util.Log;
import com.yoki.im.BuildConfig;

public class LogUtils {
    private static final boolean DEBUG = BuildConfig.DEBUG;

    public static String getTag(int tagStackPosition) {
        String tag = "";
        StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        for (int i = 0; i < ste.length; i++) {
            if (ste[i].getMethodName().equals("getTag")) {
                String methodName = ste[i + tagStackPosition].getMethodName();
                String fileName = ste[i + tagStackPosition].getFileName();
                tag = fileName.substring(0, fileName.lastIndexOf(".java")) + "." + methodName + "(" + fileName + ":" + ste[i + tagStackPosition].getLineNumber() + ")";
            }
        }
        return tag;
    }

    public static String getTag() {
        return getTag(2);
    }

    public static void d(String msg) {
        if (DEBUG) {
            Log.d(getTag(), msg);
        }
    }

    public static void i(String msg) {
        if (DEBUG) {
            Log.i(getTag(), msg);
        }
    }

    public static void e(String msg) {
        if (DEBUG) {
            Log.e(getTag(), msg);
        }
    }

    public static void v(String msg) {
        if (DEBUG) {
            Log.v(getTag(), msg);
        }
    }

    public static void w(String msg) {
        if (DEBUG) {
            Log.w(getTag(), msg);
        }
    }

    public static void d(String msg, int tagStackPosition) {
        if (DEBUG) {
            Log.d(getTag(tagStackPosition), msg);
        }
    }

    public static void i(String msg, int tagStackPosition) {
        if (DEBUG) {
            Log.i(getTag(tagStackPosition), msg);
        }
    }

    public static void e(String msg, int tagStackPosition) {
        if (DEBUG) {
            Log.e(getTag(tagStackPosition), msg);
        }
    }

    public static void v(String msg, int tagStackPosition) {
        if (DEBUG) {
            Log.v(getTag(tagStackPosition), msg);
        }
    }

    public static void w(String msg, int tagStackPosition) {
        if (DEBUG) {
            Log.w(getTag(tagStackPosition), msg);
        }
    }
}
