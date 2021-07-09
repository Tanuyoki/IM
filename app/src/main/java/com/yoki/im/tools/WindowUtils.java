package com.yoki.im.tools;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import com.yoki.im.R;

public class WindowUtils {
    public static void dimBackground(Window window, boolean isDim) {
        float f;
        float f2 = 0.75f;
        if (isDim) {
            f = 1.0f;
        } else {
            f = 0.75f;
        }
        if (!isDim) {
            f2 = 1.0f;
        }
        dimBackground(window, f, f2);
    }

    public static void dimBackground(final Window window, float from, float to) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);
        valueAnimator.setDuration(300L);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class com.yoki.im.tools.WindowUtils.AnonymousClass1 */

            public void onAnimationUpdate(ValueAnimator animation) {
                WindowManager.LayoutParams params = window.getAttributes();
                params.alpha = ((Float) animation.getAnimatedValue()).floatValue();
                window.addFlags(2);
                window.setAttributes(params);
            }
        });
        valueAnimator.start();
    }

    public static void dimLayout(Context context, final ViewGroup layout, final boolean isDimLayout) {
        if (Build.VERSION.SDK_INT >= 23) {
//            layout.setForeground(DrawableUtils.getDrawable(context, R.drawable.all_dim));
            layout.getForeground().setAlpha(0);
        }
        AnimUtils animUtils = new AnimUtils();
        animUtils.setValueAnimator(64.0f, 0.0f, 300);
        animUtils.addUpdateListener(new AnimUtils.UpdateListener() {
            /* class com.yoki.im.tools.WindowUtils.AnonymousClass2 */

            @Override // com.yoki.im.tools.AnimUtils.UpdateListener
            public void progress(float progress) {
                int layoutAlpha = isDimLayout ? (int) (64.0f - progress) : (int) progress;
                if (Build.VERSION.SDK_INT >= 23) {
                    layout.getForeground().setAlpha(layoutAlpha);
                }
            }
        });
        animUtils.startAnimator();
    }

    public static void setTransparentStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= 21) {
            activity.getWindow().getDecorView().setSystemUiVisibility(1280);
            activity.getWindow().setStatusBarColor(0);
        } else if (Build.VERSION.SDK_INT >= 19) {
            activity.getWindow().addFlags(67108864);
        }
    }

    public static void setStatusBarColor(Activity activity, int colorResId) {
        if (Build.VERSION.SDK_INT >= 21) {
            activity.getWindow().setStatusBarColor(activity.getResources().getColor(colorResId));
            View decorView = activity.getWindow().getDecorView();
            if (colorResId == R.color.white) {
                decorView.setSystemUiVisibility(9216);
            } else {
                activity.getWindow().getDecorView().setSystemUiVisibility(1280);
            }
        } else if (Build.VERSION.SDK_INT >= 19) {
            activity.getWindow().addFlags(67108864);
        }
    }

    public static Activity getActivity(Context ctx, View view) {
        if (Build.VERSION.SDK_INT >= 23) {
            return (Activity) ctx;
        }
        return getActivityFromView(view);
    }

    public static Activity getActivityFromView(View view) {
        for (Context context = view.getContext(); context instanceof ContextWrapper; context = ((ContextWrapper) context).getBaseContext()) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
        }
        return null;
    }

    public static int getStatusBarHeight() {
        int resId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            return Resources.getSystem().getDimensionPixelSize(resId);
        }
        return 0;
    }

    public static boolean isOpenAutoRotate() {
//        return Settings.System.getInt(App.getContext().getContentResolver(), "accelerometer_rotation", 0) == 1;
        return false;
    }
}
