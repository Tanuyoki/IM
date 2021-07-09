package com.yoki.im.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class RoundColor extends View {
    private int angle = 190;
    private int barWidth = 8;
    private float circleX;
    private float circleY;
    private int height;
    private float innerRadius;
    private boolean isGizwitLight = false;
    private final int[] mCircleColors = {-14967715, -6697898, -135112, -282563, -291028, -959963, -1172443};
    private float markPointX;
    private float markPointY;
    private float outerRadius;
    private Paint paintCircleRing;
    private Paint paintSelecter;
    private int width;

    public RoundColor(Context context) {
        super(context);
        init();
    }

    public RoundColor(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundColor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.paintCircleRing = new Paint(1);
        this.paintCircleRing.setAntiAlias(true);
        this.paintCircleRing.setStyle(Paint.Style.STROKE);
        this.paintCircleRing.setStrokeWidth(30.0f);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.barWidth = dp2px(getContext(), 10.0f);
        this.width = getMeasuredWidth();
        this.height = getMeasuredHeight();
        int size = this.width > this.height ? this.height : this.width;
        this.circleX = (float) (this.width / 2);
        this.circleY = (float) (this.height / 2);
        if (this.width <= 480) {
            this.paintCircleRing.setStrokeWidth(40.0f);
            this.outerRadius = (float) ((size / 2) - dp2px(getContext(), 40.0f));
            this.innerRadius = this.outerRadius - ((float) this.barWidth);
        } else if (this.width > 480 && this.width <= 720) {
            this.paintCircleRing.setStrokeWidth(45.0f);
            this.outerRadius = (float) ((size / 2) - dp2px(getContext(), 60.0f));
            this.innerRadius = this.outerRadius - ((float) this.barWidth);
        } else if (this.width <= 720 || this.width > 1080) {
            this.paintCircleRing.setStrokeWidth(100.0f);
            this.outerRadius = (float) ((size / 2) - dp2px(getContext(), 70.0f));
            this.innerRadius = this.outerRadius - ((float) this.barWidth);
        } else {
            this.paintCircleRing.setStrokeWidth(80.0f);
            this.outerRadius = (float) ((size / 2) - dp2px(getContext(), 60.0f));
            this.innerRadius = this.outerRadius - ((float) this.barWidth);
        }
        this.paintCircleRing.setShader(new SweepGradient(this.circleX, this.circleY, this.mCircleColors, (float[]) null));
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        canvas.drawCircle(this.circleX, this.circleY, this.outerRadius, this.paintCircleRing);
        setInitMarkToXY(getAngle());
        super.onDraw(canvas);
    }

    private void setInitMarkToXY(int angle2) {
        this.markPointX = (float) (((double) this.circleX) + (((double) this.outerRadius) * Math.sin((((double) angle2) * 3.141592653589793d) / 180.0d)));
        this.markPointY = (float) (((double) this.circleY) - (((double) this.outerRadius) * Math.cos((((double) angle2) * 3.141592653589793d) / 180.0d)));
        invalidate();
    }

    private boolean isMarkPointRange(float x, float y) {
        float range = (float) dp2px(getContext(), 60.0f);
        return x > this.markPointX - range && x < this.markPointX + range && y > this.markPointY - range && y < this.markPointY + range;
    }

    private int interpCircleColor(int[] colors, float degree) {
        float degree2 = degree - 90.0f;
        if (degree2 < 0.0f) {
            degree2 += 360.0f;
        }
        float p = (((float) (colors.length - 1)) * degree2) / 360.0f;
        int i = (int) p;
        float p2 = p - ((float) i);
        int c0 = colors[i];
        int c1 = colors[i + 1];
        return Color.argb(ave(Color.alpha(c0), Color.alpha(c1), p2), ave(Color.red(c0), Color.red(c1), p2), ave(Color.green(c0), Color.green(c1), p2), ave(Color.blue(c0), Color.blue(c1), p2));
    }

    private float fromColor2Degree(int color) {
        float degree;
        int diff = 360 / (this.mCircleColors.length - 1);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        int[] mColor = {b, g, r};
        int min = findMin(b, g, r);
        int max = findMax(b, g, r);
        int temp = (255 << (max * 8)) - 16777216;
        if (max == min) {
            return 90.0f;
        }
        int mid = (3 - max) - min;
        int start = 0;
        int end = 0;
        for (int i = 0; i < this.mCircleColors.length - 2; i++) {
            if (this.mCircleColors[i] - temp == 0) {
                start = i;
            }
            if (this.mCircleColors[i] - temp == (255 << (mid * 8))) {
                end = i;
            }
        }
        int degreeDiff = (int) (((float) diff) * (((float) mColor[mid]) / 255.0f));
        if (start < end) {
            degree = ((float) (start * diff)) + ((float) degreeDiff);
        } else {
            degree = ((float) (start * diff)) - ((float) degreeDiff);
        }
        float degree2 = degree + 90.0f;
        if (degree2 > 360.0f) {
            degree2 -= 360.0f;
        }
        return degree2;
    }

    private int colorFilter(int color) {
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        int[] mColor = {b, g, r};
        int min = findMin(b, g, r);
        int max = findMax(b, g, r);
        if (mColor[min] == 0 && mColor[max] == 255) {
            return Color.argb(255, mColor[2], mColor[1], mColor[0]);
        }
        return 0;
    }

    private int findMin(int one, int two, int three) {
        if (one < two && one < three) {
            return 0;
        }
        if (two < three) {
            return 1;
        }
        return 2;
    }

    private int findMax(int one, int two, int three) {
        if (one > two && one > three) {
            return 0;
        }
        if (two > three) {
            return 1;
        }
        return 2;
    }

    private int ave(int s, int d, float p) {
        return Math.round(((float) (d - s)) * p) + s;
    }

    private static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(1, dpVal, context.getResources().getDisplayMetrics());
    }

    private int getAngle() {
        return this.angle;
    }

    public boolean isGizwitLight() {
        return this.isGizwitLight;
    }

    public void setGizwitLight(boolean gizwitLight) {
        this.isGizwitLight = gizwitLight;
    }
}
