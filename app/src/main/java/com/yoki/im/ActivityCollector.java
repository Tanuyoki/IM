package com.yoki.im;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import com.yoki.im.tools.LogUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ActivityCollector {
    public static HashMap<Class<?>, Activity> activities = new LinkedHashMap();
    private static Activity mCurrentActivity;

    public static void addActivity(Activity activity, Class<?> clz) {
        activities.put(clz, activity);
    }

    @TargetApi(17)
    public static <T extends Activity> boolean isActivityExist(Class<T> clz) {
        Activity activity = getActivity(clz);
        return activity != null && !activity.isFinishing() && !activity.isDestroyed();
    }

    public static <T extends Activity> T getActivity(Class<T> clazz) {
        return (T) activities.get(clazz);
    }

    public static void removeActivity(Activity activity) {
        if (activities.containsValue(activity)) {
            activities.remove(activity.getClass());
        }
    }

    public static void removeAllActivity() {
        if (activities != null && activities.size() > 0) {
            for (Map.Entry<Class<?>, Activity> s : activities.entrySet()) {
                if (!s.getValue().isFinishing()) {
                    s.getValue().finish();
                }
            }
            activities.clear();
        }
    }

    public static void logcatActivity() {
        if (activities != null && activities.size() > 0) {
            LogUtils.d("Logcat Activity : " + activities.values());
        }
    }

    public static void removeAllActivity(Class clz) {
        if (activities != null && activities.size() > 0) {
            for (Map.Entry<Class<?>, Activity> s : activities.entrySet()) {
                if (clz != s.getValue().getClass() && !s.getValue().isFinishing()) {
                    s.getValue().finish();
                }
            }
            activities.clear();
        }
    }

    public static boolean isActivityTop(Context context, Class cls) {
        ActivityManager manager = (ActivityManager) context.getSystemService("activity");
        if (manager == null) {
            return false;
        }
        return manager.getRunningTasks(1).get(0).topActivity.getClassName().equals(cls.getName());
    }

    public static void setCurrentActivity(Activity activity) {
        mCurrentActivity = activity;
    }

    public static Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    public static String getRunningActivityName(Activity activity) {
        String contextString = activity.toString();
        return contextString.substring(contextString.lastIndexOf(".") + 1, contextString.indexOf("@"));
    }
}
