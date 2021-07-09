package com.yoki.im.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.yoki.im.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class DrawableTextView extends AppCompatTextView {
    public static final int BOTTOM = 3;
    public static final int LEFT = 0;
    public static final int RIGHT = 2;
    public static final int TOP = 1;
    private Drawable[] drawables;
    private int[] heights;
    private int[] widths;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DrawGravity {
    }

    public DrawableTextView(Context context) {
        this(context, null);
    }

    public DrawableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 16842884);
    }

    public DrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.drawables = new Drawable[4];
        this.widths = new int[4];
        this.heights = new int[4];
        setGravity(17);
//        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DrawableTextView);
//        this.drawables[0] = array.getDrawable(3);
//        this.drawables[1] = array.getDrawable(9);
//        this.drawables[2] = array.getDrawable(6);
//        this.drawables[3] = array.getDrawable(0);
//        this.widths[0] = array.getDimensionPixelSize(5, 0);
//        this.widths[1] = array.getDimensionPixelSize(11, 0);
//        this.widths[2] = array.getDimensionPixelSize(8, 0);
//        this.widths[3] = array.getDimensionPixelSize(2, 0);
//        this.heights[0] = array.getDimensionPixelSize(4, 0);
//        this.heights[1] = array.getDimensionPixelSize(10, 0);
//        this.heights[2] = array.getDimensionPixelSize(7, 0);
//        this.heights[3] = array.getDimensionPixelSize(1, 0);
//        array.recycle();
    }

    public void setDrawable(int gravity, Drawable drawable, int width, int height) {
        this.drawables[gravity] = drawable;
        this.widths[gravity] = width;
        this.heights[gravity] = height;
        postInvalidate();
    }

    public void setDrawables(Drawable[] drawables2, int[] widths2, int[] heights2) {
        if (drawables2 != null && drawables2.length >= 4 && widths2 != null && widths2.length >= 4 && heights2 != null && heights2.length >= 4) {
            this.drawables = drawables2;
            this.widths = widths2;
            this.heights = heights2;
            postInvalidate();
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        int drawablePadding = getCompoundDrawablePadding();
        translateText(canvas, drawablePadding);
        super.onDraw(canvas);
        float centerX = (float) (((getWidth() + getPaddingLeft()) - getPaddingRight()) / 2);
        float centerY = (float) (((getHeight() + getPaddingTop()) - getPaddingBottom()) / 2);
        float halfTextWidth = getPaint().measureText(getText().toString().isEmpty() ? getHint().toString() : getText().toString()) / 2.0f;
        Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
        float halfTextHeight = (fontMetrics.descent - fontMetrics.ascent) / 2.0f;
        if (this.drawables[0] != null) {
            int left = (int) (((centerX - ((float) drawablePadding)) - halfTextWidth) - ((float) this.widths[0]));
            int top = (int) (centerY - ((float) (this.heights[0] / 2)));
            this.drawables[0].setBounds(left, top, this.widths[0] + left, this.heights[0] + top);
            canvas.save();
            this.drawables[0].draw(canvas);
            canvas.restore();
        }
        if (this.drawables[2] != null) {
            int left2 = (int) (centerX + halfTextWidth + ((float) drawablePadding));
            int top2 = (int) (centerY - ((float) (this.heights[2] / 2)));
            this.drawables[2].setBounds(left2, top2, this.widths[2] + left2, this.heights[2] + top2);
            canvas.save();
            this.drawables[2].draw(canvas);
            canvas.restore();
        }
        if (this.drawables[1] != null) {
            int left3 = (int) (centerX - ((float) (this.widths[1] / 2)));
            int bottom = (int) ((centerY - halfTextHeight) - ((float) drawablePadding));
            this.drawables[1].setBounds(left3, bottom - this.heights[1], this.widths[1] + left3, bottom);
            canvas.save();
            this.drawables[1].draw(canvas);
            canvas.restore();
        }
        if (this.drawables[3] != null) {
            int left4 = (int) (centerX - ((float) (this.widths[3] / 2)));
            int top3 = (int) (centerY + halfTextHeight + ((float) drawablePadding));
            this.drawables[3].setBounds(left4, top3, this.widths[3] + left4, this.heights[3] + top3);
            canvas.save();
            this.drawables[3].draw(canvas);
            canvas.restore();
        }
    }

    private void translateText(Canvas canvas, int drawablePadding) {
        int translateWidth = 0;
        if (this.drawables[0] != null && this.drawables[2] != null) {
            translateWidth = (this.widths[0] - this.widths[2]) / 2;
        } else if (this.drawables[0] != null) {
            translateWidth = (this.widths[0] + drawablePadding) / 2;
        } else if (this.drawables[2] != null) {
            translateWidth = (-(this.widths[2] + drawablePadding)) / 2;
        }
        int translateHeight = 0;
        if (this.drawables[1] != null && this.drawables[3] != null) {
            translateHeight = (this.heights[1] - this.heights[3]) / 2;
        } else if (this.drawables[1] != null) {
            translateHeight = (this.heights[1] + drawablePadding) / 2;
        } else if (this.drawables[3] != null) {
            translateHeight = (-(this.heights[3] - drawablePadding)) / 2;
        }
        canvas.translate((float) translateWidth, (float) translateHeight);
    }
}
