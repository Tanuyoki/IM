package com.yoki.im.tools;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import java.lang.reflect.Field;

public final class ToastCompat extends Toast {
    @NonNull
    private final Toast toast;

    private ToastCompat(Context context, @NonNull Toast base) {
        super(context);
        this.toast = base;
    }

    public static ToastCompat makeText(Context context, CharSequence text, int duration) {
        Toast toast2 = Toast.makeText(context, text, duration);
        setContextCompat(toast2.getView(), new SafeToastContext(context, toast2));
        return new ToastCompat(context, toast2);
    }

    public static Toast makeText(Context context, @StringRes int resId, int duration) throws Resources.NotFoundException {
        return makeText(context, context.getResources().getText(resId), duration);
    }

    @NonNull
    public ToastCompat setBadTokenListener(@NonNull BadTokenListener listener) {
        Context context = getView().getContext();
        if (context instanceof SafeToastContext) {
            ((SafeToastContext) context).setBadTokenListener(listener);
        }
        return this;
    }

    public void show() {
        this.toast.show();
    }

    public void setDuration(int duration) {
        this.toast.setDuration(duration);
    }

    public void setGravity(int gravity, int xOffset, int yOffset) {
        this.toast.setGravity(gravity, xOffset, yOffset);
    }

    public void setMargin(float horizontalMargin, float verticalMargin) {
        this.toast.setMargin(horizontalMargin, verticalMargin);
    }

    @Override // android.widget.Toast
    public void setText(int resId) {
        this.toast.setText(resId);
    }

    @Override // android.widget.Toast
    public void setText(CharSequence s) {
        this.toast.setText(s);
    }

    public void setView(View view) {
        this.toast.setView(view);
        setContextCompat(view, new SafeToastContext(view.getContext(), this));
    }

    public float getHorizontalMargin() {
        return this.toast.getHorizontalMargin();
    }

    public float getVerticalMargin() {
        return this.toast.getVerticalMargin();
    }

    public int getDuration() {
        return this.toast.getDuration();
    }

    public int getGravity() {
        return this.toast.getGravity();
    }

    public int getXOffset() {
        return this.toast.getXOffset();
    }

    public int getYOffset() {
        return this.toast.getYOffset();
    }

    public View getView() {
        return this.toast.getView();
    }

    @NonNull
    public Toast getBaseToast() {
        return this.toast;
    }

    private static void setContextCompat(@NonNull View view, @NonNull Context context) {
        if (Build.VERSION.SDK_INT == 25) {
            try {
                Field field = View.class.getDeclaredField("mContext");
                field.setAccessible(true);
                field.set(view, context);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }
}
