package com.yoki.im.tools.touchgallery.TouchView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

@SuppressLint({"NewApi"})
public class TouchImageView extends ImageView {
    static final int CLICK = 10;
    static final long DOUBLE_PRESS_INTERVAL = 600;
    static final int DRAG = 1;
    static final float FRICTION = 0.9f;
    static final int NONE = 0;
    static final int ZOOM = 2;
    boolean allowInert = false;
    float bmHeight;
    float bmWidth;
    float bottom;
    float height;
    PointF last = new PointF();
    PointF lastDelta = new PointF(0.0f, 0.0f);
    long lastDragTime = 0;
    long lastPressTime = 0;
    float[] m;
    private Timer mClickTimer;
    private Context mContext;
    private OnClickListener mOnClickListener;
    private Object mScaleDetector;
    private Handler mTimerHandler = null;
    Matrix matrix = new Matrix();
    float matrixX;
    float matrixY;
    float maxScale = 3.0f;
    PointF mid = new PointF();
    float minScale = 1.0f;
    int mode = 0;
    float oldDist = 1.0f;
    public boolean onBottomSide = false;
    public boolean onLeftSide = false;
    public boolean onRightSide = false;
    public boolean onTopSide = false;
    float origHeight;
    float origWidth;
    private int positionForTouchImageView = -1;
    float redundantXSpace;
    float redundantYSpace;
    float right;
    float saveScale = 1.0f;
    Matrix savedMatrix = new Matrix();
    PointF start = new PointF();
    float velocity = 0.0f;
    float width;
    private boolean zoomToOriginalSize = false;

    public boolean isZoomToOriginalSize() {
        return this.zoomToOriginalSize;
    }

    public void setZoomToOriginalSize(boolean zoomToOriginalSize2) {
        this.zoomToOriginalSize = zoomToOriginalSize2;
    }

    public TouchImageView(Context context) {
        super(context);
        super.setClickable(true);
        this.mContext = context;
        init();
    }

    public TouchImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setClickable(true);
        this.mContext = context;
        init();
    }

    /* access modifiers changed from: protected */
    public void init() {
        this.mTimerHandler = new TimeHandler(this);
        this.matrix.setTranslate(1.0f, 1.0f);
        this.m = new float[9];
        setImageMatrix(this.matrix);
        setScaleType(ScaleType.MATRIX);
        if (Build.VERSION.SDK_INT >= 8) {
            this.mScaleDetector = new ScaleGestureDetector(this.mContext, new ScaleListener());
        }
        setOnTouchListener(new OnTouchListener() {
            /* class com.yoki.im.tools.touchgallery.TouchView.TouchImageView.AnonymousClass1 */

            public boolean onTouch(View v, MotionEvent rawEvent) {
                WrapMotionEvent event = WrapMotionEvent.wrap(rawEvent);
                if (TouchImageView.this.mScaleDetector != null) {
                    ((ScaleGestureDetector) TouchImageView.this.mScaleDetector).onTouchEvent(rawEvent);
                }
                TouchImageView.this.fillMatrixXY();
                PointF curr = new PointF(event.getX(), event.getY());
                switch (event.getAction() & 255) {
                    case 0:
                        TouchImageView.this.allowInert = false;
                        TouchImageView.this.savedMatrix.set(TouchImageView.this.matrix);
                        TouchImageView.this.last.set(event.getX(), event.getY());
                        TouchImageView.this.start.set(TouchImageView.this.last);
                        TouchImageView.this.mode = 1;
                        break;
                    case 1:
                        TouchImageView.this.allowInert = true;
                        TouchImageView.this.mode = 0;
                        int xDiff = (int) Math.abs(event.getX() - TouchImageView.this.start.x);
                        int yDiff = (int) Math.abs(event.getY() - TouchImageView.this.start.y);
                        if (xDiff < 10 && yDiff < 10) {
                            long pressTime = System.currentTimeMillis();
                            if (pressTime - TouchImageView.this.lastPressTime <= TouchImageView.DOUBLE_PRESS_INTERVAL) {
                                if (TouchImageView.this.mClickTimer != null) {
                                    TouchImageView.this.mClickTimer.cancel();
                                }
                                if (TouchImageView.this.saveScale == 1.0f) {
                                    float targetScale = TouchImageView.this.maxScale / TouchImageView.this.saveScale;
                                    TouchImageView.this.matrix.postScale(targetScale, targetScale, TouchImageView.this.start.x, TouchImageView.this.start.y);
                                    TouchImageView.this.saveScale = TouchImageView.this.maxScale;
                                } else {
                                    TouchImageView.this.matrix.postScale(TouchImageView.this.minScale / TouchImageView.this.saveScale, TouchImageView.this.minScale / TouchImageView.this.saveScale, TouchImageView.this.width / 2.0f, TouchImageView.this.height / 2.0f);
                                    TouchImageView.this.saveScale = TouchImageView.this.minScale;
                                }
                                TouchImageView.this.calcPadding();
                                TouchImageView.this.checkAndSetTranslate(0.0f, 0.0f);
                                TouchImageView.this.lastPressTime = 0;
                            } else {
                                TouchImageView.this.lastPressTime = pressTime;
                                TouchImageView.this.mClickTimer = new Timer();
                                TouchImageView.this.mClickTimer.schedule(new Task(), 300);
                            }
                            if (TouchImageView.this.saveScale == TouchImageView.this.minScale) {
                                TouchImageView.this.scaleMatrixToBounds();
                                break;
                            }
                        }
                        break;
                    case 2:
                        TouchImageView.this.allowInert = false;
                        if (TouchImageView.this.mode != 1) {
                            if (TouchImageView.this.mScaleDetector == null && TouchImageView.this.mode == 2) {
                                float newDist = TouchImageView.this.spacing(event);
                                if (rawEvent.getPointerCount() >= 2 && 10.0f <= Math.abs(TouchImageView.this.oldDist - newDist) && Math.abs(TouchImageView.this.oldDist - newDist) <= 50.0f) {
                                    float mScaleFactor = newDist / TouchImageView.this.oldDist;
                                    TouchImageView.this.oldDist = newDist;
                                    float origScale = TouchImageView.this.saveScale;
                                    TouchImageView.this.saveScale *= mScaleFactor;
                                    if (TouchImageView.this.saveScale > TouchImageView.this.maxScale) {
                                        TouchImageView.this.saveScale = TouchImageView.this.maxScale;
                                        mScaleFactor = TouchImageView.this.maxScale / origScale;
                                    } else if (TouchImageView.this.saveScale < TouchImageView.this.minScale) {
                                        TouchImageView.this.saveScale = TouchImageView.this.minScale;
                                        mScaleFactor = TouchImageView.this.minScale / origScale;
                                    }
                                    TouchImageView.this.calcPadding();
                                    if (TouchImageView.this.origWidth * TouchImageView.this.saveScale <= TouchImageView.this.width || TouchImageView.this.origHeight * TouchImageView.this.saveScale <= TouchImageView.this.height) {
                                        TouchImageView.this.matrix.postScale(mScaleFactor, mScaleFactor, TouchImageView.this.width / 2.0f, TouchImageView.this.height / 2.0f);
                                        if (mScaleFactor < 1.0f) {
                                            TouchImageView.this.fillMatrixXY();
                                            if (mScaleFactor < 1.0f) {
                                                TouchImageView.this.scaleMatrixToBounds();
                                            }
                                        }
                                    } else {
                                        PointF mid = TouchImageView.this.midPointF(event);
                                        TouchImageView.this.matrix.postScale(mScaleFactor, mScaleFactor, mid.x, mid.y);
                                        TouchImageView.this.fillMatrixXY();
                                        if (mScaleFactor < 1.0f) {
                                            if (TouchImageView.this.matrixX < (-TouchImageView.this.right)) {
                                                TouchImageView.this.matrix.postTranslate(-(TouchImageView.this.matrixX + TouchImageView.this.right), 0.0f);
                                            } else if (TouchImageView.this.matrixX > 0.0f) {
                                                TouchImageView.this.matrix.postTranslate(-TouchImageView.this.matrixX, 0.0f);
                                            }
                                            if (TouchImageView.this.matrixY < (-TouchImageView.this.bottom)) {
                                                TouchImageView.this.matrix.postTranslate(0.0f, -(TouchImageView.this.matrixY + TouchImageView.this.bottom));
                                            } else if (TouchImageView.this.matrixY > 0.0f) {
                                                TouchImageView.this.matrix.postTranslate(0.0f, -TouchImageView.this.matrixY);
                                            }
                                        }
                                    }
                                    TouchImageView.this.checkSiding();
                                    break;
                                }
                            }
                        } else {
                            float deltaX = curr.x - TouchImageView.this.last.x;
                            float deltaY = curr.y - TouchImageView.this.last.y;
                            long dragTime = System.currentTimeMillis();
                            TouchImageView.this.velocity = (((float) TouchImageView.this.distanceBetween(curr, TouchImageView.this.last)) / ((float) (dragTime - TouchImageView.this.lastDragTime))) * TouchImageView.FRICTION;
                            TouchImageView.this.lastDragTime = dragTime;
                            TouchImageView.this.checkAndSetTranslate(deltaX, deltaY);
                            TouchImageView.this.lastDelta.set(deltaX, deltaY);
                            TouchImageView.this.last.set(curr.x, curr.y);
                            break;
                        }
                        break;
                    case 5:
                        TouchImageView.this.oldDist = TouchImageView.this.spacing(event);
                        if (TouchImageView.this.oldDist > 10.0f) {
                            TouchImageView.this.savedMatrix.set(TouchImageView.this.matrix);
                            TouchImageView.this.midPoint(TouchImageView.this.mid, event);
                            TouchImageView.this.mode = 2;
                            break;
                        }
                        break;
                    case 6:
                        TouchImageView.this.mode = 0;
                        TouchImageView.this.velocity = 0.0f;
                        TouchImageView.this.savedMatrix.set(TouchImageView.this.matrix);
                        TouchImageView.this.oldDist = TouchImageView.this.spacing(event);
                        break;
                }
                TouchImageView.this.setImageMatrix(TouchImageView.this.matrix);
                TouchImageView.this.invalidate();
                return false;
            }
        });
    }

    public void resetScale() {
        fillMatrixXY();
        this.matrix.postScale(this.minScale / this.saveScale, this.minScale / this.saveScale, this.width / 2.0f, this.height / 2.0f);
        this.saveScale = this.minScale;
        calcPadding();
        checkAndSetTranslate(0.0f, 0.0f);
        scaleMatrixToBounds();
        setImageMatrix(this.matrix);
        invalidate();
    }

    public boolean pagerCanScroll() {
        if (this.mode == 0 && this.saveScale == this.minScale) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.allowInert) {
            float deltaX = this.lastDelta.x * this.velocity;
            float deltaY = this.lastDelta.y * this.velocity;
            if (deltaX <= this.width && deltaY <= this.height) {
                this.velocity *= FRICTION;
                if (((double) Math.abs(deltaX)) >= 0.1d || ((double) Math.abs(deltaY)) >= 0.1d) {
                    checkAndSetTranslate(deltaX, deltaY);
                    setImageMatrix(this.matrix);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void checkAndSetTranslate(float deltaX, float deltaY) {
        float scaleWidth = (float) Math.round(this.origWidth * this.saveScale);
        float scaleHeight = (float) Math.round(this.origHeight * this.saveScale);
        fillMatrixXY();
        if (scaleWidth < this.width) {
            deltaX = 0.0f;
            if (this.matrixY + deltaY > 0.0f) {
                deltaY = -this.matrixY;
            } else if (this.matrixY + deltaY < (-this.bottom)) {
                deltaY = -(this.matrixY + this.bottom);
            }
        } else if (scaleHeight < this.height) {
            deltaY = 0.0f;
            if (this.matrixX + deltaX > 0.0f) {
                deltaX = -this.matrixX;
            } else if (this.matrixX + deltaX < (-this.right)) {
                deltaX = -(this.matrixX + this.right);
            }
        } else {
            if (this.matrixX + deltaX > 0.0f) {
                deltaX = -this.matrixX;
            } else if (this.matrixX + deltaX < (-this.right)) {
                deltaX = -(this.matrixX + this.right);
            }
            if (this.matrixY + deltaY > 0.0f) {
                deltaY = -this.matrixY;
            } else if (this.matrixY + deltaY < (-this.bottom)) {
                deltaY = -(this.matrixY + this.bottom);
            }
        }
        this.matrix.postTranslate(deltaX, deltaY);
        checkSiding();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void checkSiding() {
        fillMatrixXY();
        float scaleWidth = (float) Math.round(this.origWidth * this.saveScale);
        float scaleHeight = (float) Math.round(this.origHeight * this.saveScale);
        this.onBottomSide = false;
        this.onTopSide = false;
        this.onRightSide = false;
        this.onLeftSide = false;
        if ((-this.matrixX) < 10.0f) {
            this.onLeftSide = true;
        }
        if ((scaleWidth >= this.width && (this.matrixX + scaleWidth) - this.width < 10.0f) || (scaleWidth <= this.width && (-this.matrixX) + scaleWidth <= this.width)) {
            this.onRightSide = true;
        }
        if ((-this.matrixY) < 10.0f) {
            this.onTopSide = true;
        }
        if (Math.abs(((-this.matrixY) + this.height) - scaleHeight) < 10.0f) {
            this.onBottomSide = true;
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void calcPadding() {
        this.right = ((this.width * this.saveScale) - this.width) - ((this.redundantXSpace * 2.0f) * this.saveScale);
        this.bottom = ((this.height * this.saveScale) - this.height) - ((this.redundantYSpace * 2.0f) * this.saveScale);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void fillMatrixXY() {
        this.matrix.getValues(this.m);
        this.matrixX = this.m[2];
        this.matrixY = this.m[5];
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void scaleMatrixToBounds() {
        if (Math.abs(this.matrixX + (this.right / 2.0f)) > 0.5f) {
            this.matrix.postTranslate(-(this.matrixX + (this.right / 2.0f)), 0.0f);
        }
        if (Math.abs(this.matrixY + (this.bottom / 2.0f)) > 0.5f) {
            this.matrix.postTranslate(0.0f, -(this.matrixY + (this.bottom / 2.0f)));
        }
    }

    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        this.bmWidth = (float) bm.getWidth();
        this.bmHeight = (float) bm.getHeight();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.width = (float) MeasureSpec.getSize(widthMeasureSpec);
        this.height = (float) MeasureSpec.getSize(heightMeasureSpec);
        float scale = Math.min(this.width / this.bmWidth, this.height / this.bmHeight);
        this.matrix.setScale(scale, scale);
        setImageMatrix(this.matrix);
        this.saveScale = 1.0f;
        this.redundantYSpace = this.height - (this.bmHeight * scale);
        this.redundantXSpace = this.width - (this.bmWidth * scale);
        this.redundantYSpace /= 2.0f;
        this.redundantXSpace /= 2.0f;
        this.matrix.postTranslate(this.redundantXSpace, this.redundantYSpace);
        this.origWidth = this.width - (this.redundantXSpace * 2.0f);
        this.origHeight = this.height - (this.redundantYSpace * 2.0f);
        calcPadding();
        setImageMatrix(this.matrix);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private double distanceBetween(PointF left, PointF right2) {
        return Math.sqrt(Math.pow((double) (left.x - right2.x), 2.0d) + Math.pow((double) (left.y - right2.y), 2.0d));
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private float spacing(WrapMotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt((double) ((x * x) + (y * y)));
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void midPoint(PointF point, WrapMotionEvent event) {
        point.set((event.getX(0) + event.getX(1)) / 2.0f, (event.getY(0) + event.getY(1)) / 2.0f);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private PointF midPointF(WrapMotionEvent event) {
        return new PointF((event.getX(0) + event.getX(1)) / 2.0f, (event.getY(0) + event.getY(1)) / 2.0f);
    }

    public void setOnClickListener(OnClickListener l) {
        this.mOnClickListener = l;
    }

    private class Task extends TimerTask {
        private Task() {
        }

        public void run() {
            TouchImageView.this.mTimerHandler.sendEmptyMessage(0);
        }
    }

    /* access modifiers changed from: private */
    public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private ScaleListener() {
        }

        public boolean onScaleBegin(ScaleGestureDetector detector) {
            TouchImageView.this.mode = 2;
            return true;
        }

        public boolean onScale(ScaleGestureDetector detector) {
            float mScaleFactor = (float) Math.min((double) Math.max(0.95f, detector.getScaleFactor()), 1.05d);
            float origScale = TouchImageView.this.saveScale;
            TouchImageView.this.saveScale *= mScaleFactor;
            if (TouchImageView.this.saveScale > TouchImageView.this.maxScale) {
                TouchImageView.this.saveScale = TouchImageView.this.maxScale;
                mScaleFactor = TouchImageView.this.maxScale / origScale;
            } else if (TouchImageView.this.saveScale < TouchImageView.this.minScale) {
                TouchImageView.this.saveScale = TouchImageView.this.minScale;
                mScaleFactor = TouchImageView.this.minScale / origScale;
            }
            TouchImageView.this.right = ((TouchImageView.this.width * TouchImageView.this.saveScale) - TouchImageView.this.width) - ((TouchImageView.this.redundantXSpace * 2.0f) * TouchImageView.this.saveScale);
            TouchImageView.this.bottom = ((TouchImageView.this.height * TouchImageView.this.saveScale) - TouchImageView.this.height) - ((TouchImageView.this.redundantYSpace * 2.0f) * TouchImageView.this.saveScale);
            if (TouchImageView.this.origWidth * TouchImageView.this.saveScale <= TouchImageView.this.width || TouchImageView.this.origHeight * TouchImageView.this.saveScale <= TouchImageView.this.height) {
                TouchImageView.this.matrix.postScale(mScaleFactor, mScaleFactor, TouchImageView.this.width / 2.0f, TouchImageView.this.height / 2.0f);
                if (mScaleFactor >= 1.0f) {
                    return true;
                }
                TouchImageView.this.matrix.getValues(TouchImageView.this.m);
                float x = TouchImageView.this.m[2];
                float y = TouchImageView.this.m[5];
                if (mScaleFactor >= 1.0f) {
                    return true;
                }
                if (((float) Math.round(TouchImageView.this.origWidth * TouchImageView.this.saveScale)) < TouchImageView.this.width) {
                    if (y < (-TouchImageView.this.bottom)) {
                        TouchImageView.this.matrix.postTranslate(0.0f, -(TouchImageView.this.bottom + y));
                        return true;
                    } else if (y <= 0.0f) {
                        return true;
                    } else {
                        TouchImageView.this.matrix.postTranslate(0.0f, -y);
                        return true;
                    }
                } else if (x < (-TouchImageView.this.right)) {
                    TouchImageView.this.matrix.postTranslate(-(TouchImageView.this.right + x), 0.0f);
                    return true;
                } else if (x <= 0.0f) {
                    return true;
                } else {
                    TouchImageView.this.matrix.postTranslate(-x, 0.0f);
                    return true;
                }
            } else {
                TouchImageView.this.matrix.postScale(mScaleFactor, mScaleFactor, detector.getFocusX(), detector.getFocusY());
                TouchImageView.this.matrix.getValues(TouchImageView.this.m);
                float x2 = TouchImageView.this.m[2];
                float y2 = TouchImageView.this.m[5];
                if (mScaleFactor >= 1.0f) {
                    return true;
                }
                if (x2 < (-TouchImageView.this.right)) {
                    TouchImageView.this.matrix.postTranslate(-(TouchImageView.this.right + x2), 0.0f);
                } else if (x2 > 0.0f) {
                    TouchImageView.this.matrix.postTranslate(-x2, 0.0f);
                }
                if (y2 < (-TouchImageView.this.bottom)) {
                    TouchImageView.this.matrix.postTranslate(0.0f, -(TouchImageView.this.bottom + y2));
                    return true;
                } else if (y2 <= 0.0f) {
                    return true;
                } else {
                    TouchImageView.this.matrix.postTranslate(0.0f, -y2);
                    return true;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public static class TimeHandler extends Handler {
        private final WeakReference<TouchImageView> mService;

        TimeHandler(TouchImageView view) {
            this.mService = new WeakReference<>(view);
        }

        public void handleMessage(Message msg) {
            this.mService.get().performClick();
            if (this.mService.get().mOnClickListener != null) {
                this.mService.get().mOnClickListener.onClick(this.mService.get());
            }
        }
    }
}
