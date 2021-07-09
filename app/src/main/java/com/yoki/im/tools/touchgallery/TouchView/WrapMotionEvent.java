package com.yoki.im.tools.touchgallery.TouchView;

import android.view.MotionEvent;

public class WrapMotionEvent {
    protected MotionEvent event;

    protected WrapMotionEvent(MotionEvent event2) {
        this.event = event2;
    }

    public static WrapMotionEvent wrap(MotionEvent event2) {
        try {
            return new EclairMotionEvent(event2);
        } catch (VerifyError e) {
            return new WrapMotionEvent(event2);
        }
    }

    public int getAction() {
        return this.event.getAction();
    }

    public float getX() {
        return this.event.getX();
    }

    public float getX(int pointerIndex) {
        verifyPointerIndex(pointerIndex);
        return getX();
    }

    public float getY() {
        return this.event.getY();
    }

    public float getY(int pointerIndex) {
        verifyPointerIndex(pointerIndex);
        return getY();
    }

    public int getPointerCount() {
        return 1;
    }

    public int getPointerId(int pointerIndex) {
        verifyPointerIndex(pointerIndex);
        return 0;
    }

    private void verifyPointerIndex(int pointerIndex) {
        if (pointerIndex > 0) {
            throw new IllegalArgumentException("Invalid pointer index for Donut/Cupcake");
        }
    }
}
