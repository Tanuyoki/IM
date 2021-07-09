package com.yoki.im.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

public class ViewCustom extends View {
    private static final int TOUCH_SLOP = 20;
    private MotionEvent event;
    private boolean isClicked;
    private boolean isMoved;
    private boolean isReleased;
    private OnLongClickListener listener;
    private int mLastMotionX;
    private int mLastMotionY;
    private Runnable mLongPressRunnable;

    public interface OnLongClickListener {
        void onLongClick(View view, MotionEvent motionEvent);
    }

    public void setOnLongClickListener(OnLongClickListener listener2) {
        this.listener = listener2;
    }

    public ViewCustom(Context context) {
        super(context);
        init();
    }

    public ViewCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ViewCustom(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        this.mLongPressRunnable = new Runnable() {
            /* class com.yoki.im.ui.ViewCustom.AnonymousClass1 */

            public void run() {
                if (!ViewCustom.this.isReleased && !ViewCustom.this.isMoved && ViewCustom.this.listener != null) {
                    ViewCustom.this.listener.onLongClick(ViewCustom.this, ViewCustom.this.event);
                }
            }
        };
    }

    public boolean dispatchTouchEvent(MotionEvent event2) {
        int x = (int) event2.getX();
        int y = (int) event2.getY();
        switch (event2.getAction()) {
            case 0:
                this.mLastMotionX = x;
                this.mLastMotionY = y;
                this.isReleased = false;
                this.isMoved = false;
                this.event = event2;
                postDelayed(this.mLongPressRunnable, (long) ViewConfiguration.getLongPressTimeout());
                break;
            case 1:
                this.isReleased = true;
                removeCallbacks(this.mLongPressRunnable);
                if (this.isClicked) {
                    performClick();
                    break;
                }
                break;
            case 2:
                if (!this.isMoved && (Math.abs(this.mLastMotionX - x) > 20 || Math.abs(this.mLastMotionY - y) > 20)) {
                    this.isMoved = true;
                    removeCallbacks(this.mLongPressRunnable);
                    break;
                }
        }
        return true;
    }
}
