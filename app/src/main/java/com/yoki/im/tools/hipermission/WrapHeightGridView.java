package com.yoki.im.tools.hipermission;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

public class WrapHeightGridView extends GridView {
    public boolean isOnMeasure;

    public WrapHeightGridView(Context context) {
        super(context);
    }

    public WrapHeightGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapHeightGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.isOnMeasure = true;
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(536870911, Integer.MIN_VALUE));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        this.isOnMeasure = false;
        super.onLayout(changed, l, t, r, b);
    }
}
