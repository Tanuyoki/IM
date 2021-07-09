package com.yoki.im.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.yoki.im.tools.WindowUtils;

public class FillTitleBar extends RelativeLayout {
    public FillTitleBar(Context context) {
        this(context, null);
    }

    public FillTitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FillTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setMinimumHeight(WindowUtils.getStatusBarHeight());
    }
}
