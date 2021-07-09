package com.yoki.im.tools;

import android.content.Context;
import android.text.SpannableStringBuilder;

import androidx.core.content.ContextCompat;

import com.yoki.im.R;

public class HtmlUtils {
    public static CharSequence formatFontColor(String text, int color) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append((CharSequence) ("<font color='" + color + "'>"));
        ssb.append((CharSequence) text);
        ssb.append((CharSequence) "</font>");
        return ssb;
    }

    public static CharSequence formatFontColorBlue(Context context, String text) {
        return formatFontColor(text, ContextCompat.getColor(context, R.color.txt_blue));
    }

    public static CharSequence formatFontColorRed(Context context, String text) {
        return formatFontColor(text, ContextCompat.getColor(context, R.color.txt_red));
    }
}
