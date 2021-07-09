package com.yoki.im.tools.touchgallery.GalleryWidget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.yoki.im.tools.touchgallery.TouchView.TouchImageView;

public class GalleryViewPager extends ViewPager {
    private static final int CLICK_ACTION_THRESHHOLD = 5;
    PointF last;
    public TouchImageView mCurrentView;
    protected OnItemClickListener mOnItemClickListener;
    private float startX;
    private float startY;

    public interface OnItemClickListener {
        void onItemClicked(View view, int i);
    }

    public GalleryViewPager(Context context) {
        super(context);
    }

    public GalleryViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override // android.support.v4.view.ViewPager
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if ((event.getAction() & 255) == 1) {
            if (!isAClick(this.startX, event.getX(), this.startY, event.getY())) {
                super.onInterceptTouchEvent(event);
            } else if (this.mOnItemClickListener != null) {
                this.mOnItemClickListener.onItemClicked(this.mCurrentView, getCurrentItem());
            }
        }
        if ((event.getAction() & 255) == 0) {
            this.startX = event.getX();
            this.startY = event.getY();
        }
        float[] difference = handleMotionEvent(event);
        if (this.mCurrentView.pagerCanScroll()) {
            return super.onInterceptTouchEvent(event);
        }
        if (difference != null && this.mCurrentView.onRightSide && difference[0] < 0.0f) {
            return super.onInterceptTouchEvent(event);
        }
        if (difference != null && this.mCurrentView.onLeftSide && difference[0] > 0.0f) {
            return super.onInterceptTouchEvent(event);
        }
        if (difference != null) {
            return false;
        }
        if (this.mCurrentView.onLeftSide || this.mCurrentView.onRightSide) {
            return super.onInterceptTouchEvent(event);
        }
        return false;
    }

    @Override // android.support.v4.view.ViewPager
    public boolean onTouchEvent(MotionEvent event) {
        if ((event.getAction() & 255) == 1) {
            if (!isAClick(this.startX, event.getX(), this.startY, event.getY())) {
                super.onTouchEvent(event);
            } else if (this.mOnItemClickListener != null) {
                this.mOnItemClickListener.onItemClicked(this.mCurrentView, getCurrentItem());
            }
        }
        if ((event.getAction() & 255) == 0) {
            this.startX = event.getX();
            this.startY = event.getY();
        }
        float[] difference = handleMotionEvent(event);
        if (this.mCurrentView.pagerCanScroll()) {
            return super.onTouchEvent(event);
        }
        if (difference != null && this.mCurrentView.onRightSide && difference[0] < 0.0f) {
            return super.onTouchEvent(event);
        }
        if (difference != null && this.mCurrentView.onLeftSide && difference[0] > 0.0f) {
            return super.onTouchEvent(event);
        }
        if (difference != null) {
            return false;
        }
        if (this.mCurrentView.onLeftSide || this.mCurrentView.onRightSide) {
            return super.onTouchEvent(event);
        }
        return false;
    }

    private boolean isAClick(float startX2, float endX, float startY2, float endY) {
        float differenceX = Math.abs(startX2 - endX);
        float differenceY = Math.abs(startY2 - endY);
        if (differenceX > 5.0f || differenceY > 5.0f) {
            return false;
        }
        return true;
    }

    @TargetApi(5)
    private float[] handleMotionEvent(MotionEvent event) {
        switch (event.getAction() & 255) {
            case 1:
            case 2:
                PointF curr = new PointF(event.getX(0), event.getY(0));
                return new float[]{curr.x - this.last.x, curr.y - this.last.y};
            case 0:
                this.last = new PointF(event.getX(0), event.getY(0));
                break;
        }
        return null;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
