package com.yoki.im.tools;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;
import com.yoki.im.ActivityCollector;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ToastUtils {
    private static final int TOAST_SHOW_DURATION = 500;
    private static Toast mToast;

    public static void show(final Activity activity, final String text) {
        activity.runOnUiThread(new Runnable() {
            /* class com.yoki.im.tools.ToastUtils.AnonymousClass1 */

            public void run() {
                ToastUtils.show(activity.getApplicationContext(), text);
            }
        });
    }

    public static void show(String text) {

    }

    public static void show(Context context, String text) {
        show(context, text, TOAST_SHOW_DURATION);
    }

    public static void show(Context context, String text, int duration) {
        ScreenUtils.cancelAdaptScreen(ActivityCollector.getCurrentActivity());
        ToastCompat.makeText(context, (CharSequence) text, duration).show();
        ScreenUtils.adapterScreen(ActivityCollector.getCurrentActivity());
    }

//    public static void show(Context context, String text) {
//        show(context, text);
//    }
//
//    public static void show(Context context, String text, int duration) {
//        show(context, text, duration);
//    }

    public Object invoke(String className, String methodName, Object... args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Class aClass = Class.forName(className);
        Class[] parameterTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = args[i].getClass();
        }
        Method method = aClass.getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method.invoke(aClass.newInstance(), args);
    }

    public void testFiled(String className, String fieldName, String val) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, InstantiationException {
        Class cAClass = Class.forName(className);
        Field field = null;
        for (Class clazz2 = cAClass; clazz2 != Object.class; clazz2 = clazz2.getSuperclass()) {
            field = cAClass.getDeclaredField(fieldName);
        }
        Object obj = cAClass.newInstance();
        field.setAccessible(true);
        field.set(obj, val);
    }
}
