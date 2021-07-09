package com.yoki.im.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.drawerlayout.widget.DrawerLayout;

public class DrawerLayoutCustom extends DrawerLayout {
    private float lastX;
    private float lastY;

    public DrawerLayoutCustom(Context context) {
        this(context, null);
    }

    public DrawerLayoutCustom(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawerLayoutCustom(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setScrimColor(805306368);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case 0:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case 2:
                if (Math.abs(ev.getRawX() - this.lastX) > Math.abs(ev.getRawY() - this.lastY)) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                    break;
                }
                break;
        }
        this.lastX = ev.getRawX();
        this.lastY = ev.getRawY();
        return super.dispatchTouchEvent(ev);
    }

    /* access modifiers changed from: protected */
    @Override // android.support.v4.widget.DrawerLayout
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(widthMeasureSpec), 1073741824), View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(heightMeasureSpec), 1073741824));
    }
}
