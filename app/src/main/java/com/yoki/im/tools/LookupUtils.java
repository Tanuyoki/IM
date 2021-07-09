package com.yoki.im.tools;

import android.content.Context;
import android.widget.ImageView;

public class LookupUtils {
    public static void setDrawableWithName(Context context, ImageView imageView, String imageName) {
        imageView.setImageResource(context.getResources().getIdentifier(imageName, "drawable", context.getPackageName()));
    }

    public static int getResourceId(Context context, String name) {
        return context.getResources().getIdentifier(name, "id", context.getPackageName());
    }
}
