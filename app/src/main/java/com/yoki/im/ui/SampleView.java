package com.yoki.im.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

public class SampleView extends View {
    private float circleX;
    private float circleY;
    private int height;
    private final int[] mCircleColors = {-14967715, -6697898, -135112, -282563, -291028, -959963, -1172443};
    private Matrix mMatrix = new Matrix();
    private Paint mPaint;
    private float mRotate;
    private Shader mShader;
    private int width;

    public SampleView(Context context) {
        super(context);
        init();
    }

    public SampleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SampleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.mPaint = new Paint(1);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(30.0f);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.width = getMeasuredWidth();
        this.height = getMeasuredHeight();
        this.circleX = (float) (this.width / 2);
        this.circleY = (float) (this.height / 2);
        this.mShader = new SweepGradient(this.circleX, this.circleY, this.mCircleColors, (float[]) null);
        this.mPaint.setShader(this.mShader);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        canvas.drawColor(-1);
        this.mMatrix.setRotate(this.mRotate, this.circleX, this.circleY);
        this.mShader.setLocalMatrix(this.mMatrix);
        this.mRotate += 3.0f;
        if (this.mRotate >= 360.0f) {
            this.mRotate = 0.0f;
        }
        invalidate();
        getArc(canvas, this.circleX, this.circleY, 80.0f, 110.0f, 430.0f, this.mPaint);
    }

    public void getArc(Canvas canvas, float x, float y, float r, float startangel, float endangel, Paint paint) {
        RectF rect = new RectF(x - r, y - r, x + r, y + r);
        Path path = new Path();
        path.moveTo(x, y);
        path.lineTo((float) (((double) x) + (((double) r) * Math.cos((((double) startangel) * 3.141592653589793d) / 180.0d))), (float) (((double) y) + (((double) r) * Math.sin((((double) startangel) * 3.141592653589793d) / 180.0d))));
        path.lineTo((float) (((double) x) + (((double) r) * Math.cos((((double) endangel) * 3.141592653589793d) / 180.0d))), (float) (((double) y) + (((double) r) * Math.sin((((double) endangel) * 3.141592653589793d) / 180.0d))));
        path.addArc(rect, startangel, endangel - startangel);
        canvas.clipPath(path);
        canvas.drawCircle(x, y, r, paint);
    }
}
