package com.yoki.im.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.yoki.im.R;

public class TextViewDrawable extends AppCompatTextView {
    public static final int POSITION_BOTTOM = 3;
    public static final int POSITION_LEFT = 0;
    public static final int POSITION_RIGHT = 2;
    public static final int POSITION_TOP = 1;
    private Drawable bottom;
    private int bottomDrawableHeight;
    private int bottomDrawableWidth;
    private Drawable left;
    private int leftDrawableHeight;
    private int leftDrawableWidth;
    private Rect mBound;
    private int mDefaultTextColor;
    private Paint mPaint;
    private Paint mPaint2;
    private int mSelectedTextColor;
    private Drawable right;
    private int rightDrawableHeight;
    private int rightDrawableWidth;
    private Drawable top;
    private int topDrawableHeight;
    private int topDrawableWidth;

    public TextViewDrawable(Context context) {
        this(context, null);
    }

    public TextViewDrawable(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextViewDrawable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.leftDrawableWidth = -1;
        this.leftDrawableHeight = -1;
        this.topDrawableWidth = -1;
        this.topDrawableHeight = -1;
        this.rightDrawableWidth = -1;
        this.rightDrawableHeight = -1;
        this.bottomDrawableWidth = -1;
        this.bottomDrawableHeight = -1;
//        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TextViewDrawable, defStyleAttr, 0);
//        for (int i = 0; i < a.getIndexCount(); i++) {
//            int attr = a.getIndex(i);
//            switch (attr) {
//                case 0:
//                    this.bottomDrawableHeight = a.getDimensionPixelSize(attr, -1);
//                    break;
//                case 1:
//                    this.leftDrawableHeight = a.getDimensionPixelSize(attr, -1);
//                    break;
//                case 2:
//                    this.rightDrawableHeight = a.getDimensionPixelSize(attr, -1);
//                    break;
//                case 3:
//                    this.topDrawableHeight = a.getDimensionPixelSize(attr, -1);
//                    break;
//                case 4:
//                    this.bottomDrawableWidth = a.getDimensionPixelSize(attr, -1);
//                    break;
//                case 5:
//                    this.leftDrawableWidth = a.getDimensionPixelSize(attr, -1);
//                    break;
//                case 6:
//                    this.rightDrawableWidth = a.getDimensionPixelSize(attr, -1);
//                    break;
//                case 7:
//                    this.topDrawableWidth = a.getDimensionPixelSize(attr, -1);
//                    break;
//                case 8:
//                    this.mSelectedTextColor = a.getColor(attr, 0);
//                    if (this.mSelectedTextColor != 0) {
//                        this.mDefaultTextColor = getCurrentTextColor();
//                        break;
//                    } else {
//                        break;
//                    }
//            }
//        }
//        a.recycle();
//        setCompoundDrawablesWithIntrinsicBounds(this.left, this.top, this.right, this.bottom);
    }

    @Override // android.widget.TextView
    public void setCompoundDrawablesWithIntrinsicBounds(@Nullable Drawable left2, @Nullable Drawable top2, @Nullable Drawable right2, @Nullable Drawable bottom2) {
        this.left = left2;
        this.top = top2;
        this.right = right2;
        this.bottom = bottom2;
        if (left2 != null) {
            left2.setBounds(0, 0, this.leftDrawableWidth == -1 ? left2.getIntrinsicWidth() : this.leftDrawableWidth, this.leftDrawableHeight == -1 ? left2.getIntrinsicHeight() : this.leftDrawableHeight);
        }
        if (right2 != null) {
            right2.setBounds(0, 0, this.rightDrawableWidth == -1 ? right2.getIntrinsicWidth() : this.rightDrawableWidth, this.rightDrawableHeight == -1 ? right2.getIntrinsicHeight() : this.rightDrawableHeight);
        }
        if (top2 != null) {
            top2.setBounds(0, 0, this.topDrawableWidth == -1 ? top2.getIntrinsicWidth() : this.topDrawableWidth, this.topDrawableHeight == -1 ? top2.getIntrinsicHeight() : this.topDrawableHeight);
        }
        if (bottom2 != null) {
            bottom2.setBounds(0, 0, this.bottomDrawableWidth == -1 ? bottom2.getIntrinsicWidth() : this.bottomDrawableWidth, this.bottomDrawableHeight == -1 ? bottom2.getIntrinsicWidth() : this.bottomDrawableHeight);
        }
        setCompoundDrawables(left2, top2, right2, bottom2);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void setDrawableSize(int width, int height, int position) {
        if (position == 0) {
            this.leftDrawableWidth = width;
            this.leftDrawableHeight = height;
        }
        if (position == 1) {
            this.topDrawableWidth = width;
            this.topDrawableHeight = height;
        }
        if (position == 2) {
            this.rightDrawableWidth = width;
            this.rightDrawableHeight = height;
        }
        if (position == 3) {
            this.bottomDrawableWidth = width;
            this.bottomDrawableHeight = height;
        }
        setCompoundDrawablesWithIntrinsicBounds(this.left, this.top, this.right, this.bottom);
    }

    public void setTextSelectedChange(int selectedChange) {
        setTextSelectedChange(getCurrentTextColor(), selectedChange);
    }

    public void setTextSelectedChange(int defaultColor, int selectedColor) {
        this.mDefaultTextColor = defaultColor;
        this.mSelectedTextColor = selectedColor;
    }

    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (this.mSelectedTextColor != 0) {
            setTextColor(selected ? this.mSelectedTextColor : this.mDefaultTextColor);
        }
    }
}
