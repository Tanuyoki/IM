package com.yoki.im.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class TextViewSingleRow extends AppCompatTextView {
    private StringBuffer mBuffer;
    private int mLineY;
    private String mSpace;
    private String mText;
    private int mViewWidth;

    public TextViewSingleRow(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /* access modifiers changed from: protected */
    @Override // android.support.v7.widget.AppCompatTextView
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        this.mSpace = " ";
        this.mLineY = 0;
        TextPaint paint = getPaint();
        paint.setColor(getCurrentTextColor());
        paint.drawableState = getDrawableState();
        this.mViewWidth = getMeasuredWidth();
        this.mText = String.valueOf(getText());
        this.mLineY = (int) (((float) this.mLineY) + getTextSize());
        if (this.mText.contains(this.mSpace)) {
            this.mText = this.mText.replace(this.mSpace, "");
        }
        float mContentWidth = StaticLayout.getDesiredWidth(this.mText, getPaint());
        float mSingleRowWidth = StaticLayout.getDesiredWidth(this.mSpace, getPaint()) * 3.0f;
        int mSpaceNum = 0;
        while (mContentWidth + mSingleRowWidth <= ((float) this.mViewWidth)) {
            mContentWidth += mSingleRowWidth;
            mSpaceNum++;
        }
        for (int i = 1; i < mSpaceNum; i++) {
            this.mSpace += " ";
        }
        this.mBuffer = new StringBuffer(this.mText);
        int index = 4;
        int addNum = 1;
        for (int i2 = 0; i2 < 4; i2++) {
            this.mBuffer.insert(index, this.mSpace);
            index = ((addNum + 1) * 4) + (mSpaceNum * addNum);
            addNum++;
        }
        this.mText = String.valueOf(this.mBuffer);
        canvas.drawText(this.mText, 0.0f, (float) this.mLineY, paint);
    }
}
