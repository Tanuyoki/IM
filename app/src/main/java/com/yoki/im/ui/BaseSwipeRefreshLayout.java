package com.yoki.im.ui;

import android.content.Context;
import android.util.AttributeSet;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yoki.im.R;

public class BaseSwipeRefreshLayout extends SwipeRefreshLayout {
    public BaseSwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public BaseSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
//        setColorSchemeResources(17170451, 17170459, 17170452, 17170456, 17170454, R.color.colorBlue);
        setSize(0);
    }

    public void resetState() {
        if (isRefreshing()) {
            setRefreshing(false);
        }
    }
}
