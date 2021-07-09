package com.yoki.im.tools;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

public class StringUtils {
    public static final String SDF_YYYYMMDD_HHMMSS = "yyyyMMdd_HHmmss";
    public static final int TEXT_CHECK_TYPE_CH = 11;
    public static final int TEXT_CHECK_TYPE_EN = 12;
    public static final int TEXT_CHECK_TYPE_NUMBER = 10;
    public static final int TEXT_CHECK_TYPE_NUMBER_AND_EN = 13;

    public static String getSimpleDateFormat() {
        return getSimpleDateFormat(new Date());
    }

    @SuppressLint({"SimpleDateFormat"})
    public static String getSimpleDateFormat(Object date) {
        return new SimpleDateFormat(SDF_YYYYMMDD_HHMMSS).format(date);
    }

    @SuppressLint({"SimpleDateFormat"})
    public static String getSimpleDateFormat(Date date) {
        return new SimpleDateFormat(SDF_YYYYMMDD_HHMMSS).format(date);
    }

    public static Date stringToDate(String strDate) {
        if (strDate == null || strDate.isEmpty()) {
            return null;
        }
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getNewString(String origin, String contains) {
        int position = origin.indexOf(contains);
        return origin.substring(0, position) + origin.substring(position + contains.length(), origin.length());
    }

    public static String getBankCardTailNumber(String number) {
        return (number == null || number.isEmpty()) ? number : number.substring(number.length() - 4, number.length());
    }

    public static int getRandomNumber(int minNum, int maxNum) {
        return (new Random().nextInt(maxNum) % ((maxNum - minNum) + 1)) + minNum;
    }

    public static boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static boolean isContainsType(Object str, int checkType) {
        String text = String.valueOf(str + "");
        Pattern pattern = null;
        switch (checkType) {
            case 10:
                pattern = Pattern.compile("[0-9]*");
                break;
            case 11:
                pattern = Pattern.compile("[一-龥]");
                break;
            case 12:
                pattern = Pattern.compile("[a-zA-Z]");
                break;
            case 13:
                pattern = Pattern.compile("[a-zA-Z0-9]");
                break;
        }
        if (pattern == null || !pattern.matcher(text).matches()) {
            return false;
        }
        return true;
    }

    public static boolean isValidPhone(String phone) {
        return Pattern.compile("^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$").matcher(phone).matches();
    }
}
