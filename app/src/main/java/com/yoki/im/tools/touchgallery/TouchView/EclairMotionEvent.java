package com.yoki.im.tools.touchgallery.TouchView;

import android.view.MotionEvent;

public class EclairMotionEvent extends WrapMotionEvent {
    protected EclairMotionEvent(MotionEvent event) {
        super(event);
    }

    @Override // com.yoki.im.tools.touchgallery.TouchView.WrapMotionEvent
    public float getX(int pointerIndex) {
        return this.event.getX(pointerIndex);
    }

    @Override // com.yoki.im.tools.touchgallery.TouchView.WrapMotionEvent
    public float getY(int pointerIndex) {
        return this.event.getY(pointerIndex);
    }

    @Override // com.yoki.im.tools.touchgallery.TouchView.WrapMotionEvent
    public int getPointerCount() {
        return this.event.getPointerCount();
    }

    @Override // com.yoki.im.tools.touchgallery.TouchView.WrapMotionEvent
    public int getPointerId(int pointerIndex) {
        return this.event.getPointerId(pointerIndex);
    }
}
