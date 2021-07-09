package com.yoki.im.ui.popup;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.yoki.im.R;

public class PopupBubbleLayout extends RelativeLayout {
    public static float CORNER_RADIUS = 8.0f;
    public static int LEG_HALF_BASE = 40;
    public static int PADDING = 20;
    public static float MIN_LEG_DISTANCE = ((float) (PADDING + LEG_HALF_BASE));
    public static int SHADOW_COLOR = Color.argb(0, 0, 0, 0);
    public static float STROKE_WIDTH = 1.0f;
    private float mBubbleLegOffset;
    private final Path mBubbleLegPrototype;
    private BubbleLegOrientation mBubbleOrientation;
    private Paint mFillPaint;
    private final Paint mPaint;
    private Paint mPaintRoundedCorners;
    private final Path mPath;
    private final Path mPathRoundedCorners;
    private int mRoundedCornersSize;
    private float[] radiusArray;

    public enum BubbleLegOrientation {
        TOP,
        LEFT,
        RIGHT,
        BOTTOM,
        NONE
    }

    public PopupBubbleLayout(Context context) {
        this(context, null);
    }

    public PopupBubbleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopupBubbleLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mRoundedCornersSize = 10;
        this.mFillPaint = null;
        this.mPath = new Path();
        this.mPathRoundedCorners = new Path();
        this.mBubbleLegPrototype = new Path();
        this.mPaint = new Paint(4);
        this.mPaintRoundedCorners = new Paint(4);
        this.mBubbleLegOffset = 0.65f;
        this.radiusArray = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
        this.mBubbleOrientation = BubbleLegOrientation.LEFT;
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        if (attrs != null) {
//            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PopupBubble);
//            try {
//                PADDING = a.getDimensionPixelSize(2, PADDING);
//                SHADOW_COLOR = a.getInt(3, SHADOW_COLOR);
//                LEG_HALF_BASE = a.getDimensionPixelSize(1, LEG_HALF_BASE);
//                MIN_LEG_DISTANCE = (float) (PADDING + LEG_HALF_BASE);
//                STROKE_WIDTH = a.getFloat(4, STROKE_WIDTH);
//                CORNER_RADIUS = a.getFloat(0, CORNER_RADIUS);
//            } finally {
//                if (a != null) {
//                    a.recycle();
//                }
//            }
        }
        this.mPaint.setColor(SHADOW_COLOR);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setStrokeCap(Paint.Cap.BUTT);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStrokeWidth(STROKE_WIDTH);
        this.mPaint.setStrokeJoin(Paint.Join.MITER);
        this.mPaintRoundedCorners = new Paint(this.mPaint);
        this.mPaint.setPathEffect(new CornerPathEffect(CORNER_RADIUS));
//        this.mPaintRoundedCorners.setColor(ContextCompat.getColor(context, com.acarbond.car.R.color.bg_bubble));
//        if (Build.VERSION.SDK_INT >= 11) {
//            setLayerType(1, this.mPaint);
//        }
//        this.mFillPaint = new Paint(this.mPaint);
//        this.mFillPaint.setColor(ContextCompat.getColor(context, com.acarbond.car.R.color.bg_bubble));
//        setLayerType(1, this.mFillPaint);
        renderBubbleLegPrototype();
        setPadding(PADDING, PADDING, PADDING, PADDING);
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void renderBubbleLegPrototype() {
        this.mBubbleLegPrototype.moveTo(0.0f, 0.0f);
        this.mBubbleLegPrototype.lineTo(((float) PADDING) * 1.5f, ((float) (-PADDING)) / 1.0f);
        this.mBubbleLegPrototype.lineTo(((float) PADDING) * 1.5f, ((float) PADDING) / 1.0f);
        this.mBubbleLegPrototype.close();
    }

    public void setBubbleParams(BubbleLegOrientation bubbleOrientation, float bubbleOffset) {
        this.mBubbleLegOffset = bubbleOffset;
        this.mBubbleOrientation = bubbleOrientation;
    }

    private Matrix renderBubbleLegMatrix(float width, float height) {
        float offset = Math.max(this.mBubbleLegOffset, MIN_LEG_DISTANCE);
        float dstX = 0.0f;
        float dstY = Math.min(offset, height - MIN_LEG_DISTANCE);
        Matrix matrix = new Matrix();
        switch (this.mBubbleOrientation) {
            case TOP:
                dstX = Math.min(offset, width - MIN_LEG_DISTANCE);
                dstY = 0.0f;
                matrix.postRotate(90.0f);
                break;
            case RIGHT:
                dstX = width;
                dstY = Math.min(offset, height - MIN_LEG_DISTANCE);
                matrix.postRotate(180.0f);
                break;
            case BOTTOM:
                dstX = Math.min(offset, width - MIN_LEG_DISTANCE);
                dstY = height;
                matrix.postRotate(270.0f);
                break;
        }
        matrix.postTranslate(dstX, dstY);
        return matrix;
    }

    public void setRadius(float leftTop, float rightTop, float rightBottom, float leftBottom) {
        this.radiusArray[0] = leftTop;
        this.radiusArray[1] = leftTop;
        this.radiusArray[2] = rightTop;
        this.radiusArray[3] = rightTop;
        this.radiusArray[4] = rightBottom;
        this.radiusArray[5] = rightBottom;
        this.radiusArray[6] = leftBottom;
        this.radiusArray[7] = leftBottom;
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        float width = (float) canvas.getWidth();
        float height = (float) canvas.getHeight();
        this.mPath.rewind();
        this.mPath.addRoundRect(new RectF((float) PADDING, (float) PADDING, width - ((float) PADDING), height - ((float) PADDING)), this.radiusArray, Path.Direction.CW);
        canvas.drawPath(this.mPath, this.mPaint);
        this.mPathRoundedCorners.rewind();
        this.mPathRoundedCorners.addPath(this.mBubbleLegPrototype, renderBubbleLegMatrix(width, height));
        canvas.drawPath(this.mPathRoundedCorners, this.mPaintRoundedCorners);
        canvas.scale((width - STROKE_WIDTH) / width, (height - STROKE_WIDTH) / height, width / 2.0f, height / 2.0f);
        canvas.drawPath(this.mPath, this.mFillPaint);
    }
}
