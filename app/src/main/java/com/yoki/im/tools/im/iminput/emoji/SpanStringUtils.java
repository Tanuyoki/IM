package com.yoki.im.tools.im.iminput.emoji;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.widget.TextView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpanStringUtils {
    public static SpannableString getEmotionContent(Context context, TextView tv, String source, int emotionType) {
        return getEmotionContent(context, tv, source, emotionType, -1);
    }

    public static SpannableString getEmotionContent(Context context, TextView tv, String source, int emotionType, int size) {
        int imageSize;
        SpannableString spannableString = new SpannableString(source);
        Resources res = context.getResources();
        Matcher matcherEmotion = Pattern.compile("\\[([一-龥\\w])+\\]").matcher(spannableString);
        while (matcherEmotion.find()) {
            String key = matcherEmotion.group();
            int start = matcherEmotion.start();
            Integer imgRes = Integer.valueOf(EmotionUtils.getImgByName(emotionType, key));
            if (imgRes.intValue() != -1) {
                if (size == -1) {
                    imageSize = (int) ((tv.getTextSize() * 13.0f) / 10.0f);
                } else {
                    imageSize = size;
                }
                Bitmap bitmap = BitmapFactory.decodeResource(res, imgRes.intValue());
                if (bitmap != null) {
                    spannableString.setSpan(new ImageSpan(context, Bitmap.createScaledBitmap(bitmap, imageSize, imageSize, true)), start, key.length() + start, 33);
                }
            }
        }
        return spannableString;
    }
}
