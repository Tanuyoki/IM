package com.yoki.im.base;

import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.PopupWindow;

import com.yoki.im.tools.WindowUtils;

public abstract class BasePopupWindow extends PopupWindow {
    private static final String TAG = "BasePopupWindow";
    private boolean mIsBright = true;
    private ViewGroup[] mLayout;
    private Window mWindow;

    public abstract void show();

    public BasePopupWindow() {
        init();
        setWidth(-1);
        setHeight(-2);
    }

    public BasePopupWindow(int width, int height) {
        super(width, height);
        init();
    }

    public BasePopupWindow(View contentView, int width, int height) {
        super(contentView, width, height);
        init();
    }

    public BasePopupWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
        init();
    }

    private void init() {
        setFocusable(true);
        setOutsideTouchable(true);
        setOnDismissListener(new OnDismissListener() {
            /* class com.yoki.im.base.BasePopupWindow.AnonymousClass1 */

            public void onDismiss() {
            }
        });
    }

    public BasePopupWindow followBackgroundAnimation(ViewGroup... layout) {
        this.mLayout = layout;
        return this;
    }

    private BasePopupWindow dimAllLayout(boolean isBright) {
        if (this.mWindow != null) {
            ViewGroup[] viewGroupArr = this.mLayout;
            for (ViewGroup layout : viewGroupArr) {
                if (layout == null) {
                    break;
                }
                WindowUtils.dimLayout(this.mWindow.getContext(), layout, isBright);
            }
        }
        return this;
    }

    public BasePopupWindow bindBackgroundAnimation(Window window) {
        this.mWindow = window;
        return this;
    }

    public BasePopupWindow unbindBackgroundAnimation() {
        this.mWindow = null;
        return this;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private BasePopupWindow dimChange() {
        if (this.mWindow != null) {
            WindowUtils.dimBackground(this.mWindow, this.mIsBright);
            if (this.mLayout != null) {
                dimAllLayout(this.mIsBright);
            }
            this.mIsBright = !this.mIsBright;
        }
        return this;
    }

    public void showAnimation() {
        if (!isShowing()) {
            dimChange();
            show();
        }
    }

    public void setOnDismissListener(final OnDismissListener onDismissListener) {
        super.setOnDismissListener(new OnDismissListener() {
            /* class com.yoki.im.base.BasePopupWindow.AnonymousClass2 */

            public void onDismiss() {
                BasePopupWindow.this.dimChange();
                onDismissListener.onDismiss();
            }
        });
    }
}
