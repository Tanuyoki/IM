package com.yoki.im.tools;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

public class KeyboardChangeUtils {
    public static final int KEYBOARD_STATE_CODE_HIDE = 11;
    public static final int KEYBOARD_STATE_CODE_HIDEING = 21;
    public static final int KEYBOARD_STATE_CODE_SHOW = 10;
    public static final int KEYBOARD_STATE_CODE_SHOWING = 20;
    private static int code = 11;
    private boolean isVisiableForLast = false;

    public interface KeyboardChangeListener {
        void onSoftKeyBoardVisible(boolean z, int i, int i2);
    }

    public void addOnSoftKeyBoardVisibleListener(final Activity activity, final KeyboardChangeListener listener) {
        final View decorView = activity.getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            /* class com.yoki.im.tools.KeyboardChangeUtils.AnonymousClass1 */

            public void onGlobalLayout() {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                int displayHeight = rect.bottom - rect.top;
                int screenHeight = decorView.getHeight();
                int keyboardHeight = screenHeight - rect.bottom;
                if (ScreenUtils.checkDeviceHasNavigationBar(activity)) {
                    keyboardHeight -= ScreenUtils.getVirtualBarHeight(activity);
                }
                boolean visible = ((double) displayHeight) / ((double) screenHeight) < 0.8d;
                if (visible != KeyboardChangeUtils.this.isVisiableForLast) {
                    if (visible) {
                        int unused = KeyboardChangeUtils.code = 10;
                    } else {
                        int unused2 = KeyboardChangeUtils.code = 11;
                    }
                }
                KeyboardChangeUtils.this.isVisiableForLast = visible;
                listener.onSoftKeyBoardVisible(visible, keyboardHeight, KeyboardChangeUtils.code);
                if (KeyboardChangeUtils.code == 10) {
                    int unused3 = KeyboardChangeUtils.code = 20;
                } else if (KeyboardChangeUtils.code == 11) {
                    int unused4 = KeyboardChangeUtils.code = 21;
                }
            }
        });
    }
}
