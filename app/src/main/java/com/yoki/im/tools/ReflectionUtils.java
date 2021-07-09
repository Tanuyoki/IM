package com.yoki.im.tools;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionUtils {
    public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes, Object[] parameters) {
        Method method = getDeclaredMethod(object, methodName, parameterTypes);
        method.setAccessible(true);
        if (method != null) {
            try {
                return method.invoke(object, parameters);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            } catch (InvocationTargetException e3) {
                e3.printStackTrace();
            }
        }
        return null;
    }

    public static Method getDeclaredMethod(Object object, String methodName, Class<?>... parameterTypes) {
        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                return clazz.getDeclaredMethod(methodName, parameterTypes);
            } catch (Exception e) {
            }
        }
        return null;
    }

    public static void setFieldValue(Object object, String fieldName, Object value) {
        Field field = getDeclaredField(object, fieldName);
        field.setAccessible(true);
        try {
            field.set(object, value);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
    }

    public static Field getDeclaredField(Object object, String fieldName) {
        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (Exception e) {
            }
        }
        return null;
    }

    public static Object getFieldValue(Object object, String fieldName) {
        Field field = getDeclaredField(object, fieldName);
        if (field != null) {
            field.setAccessible(true);
            try {
                return field.get(object);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
