package com.yoki.im.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.drawerlayout.widget.DrawerLayout;

import com.yoki.im.fragment.OnFilterClickListener;
import com.yoki.im.tools.EditTextUtils;
import com.yoki.im.R;

public class DrawerFilter extends DrawerLayoutCustom {
    private float latestX;
    private float latestY;
    private View mBottomView;
    private Button mBtnConfirm;
    private Button mBtnReset;
    private Context mContext;
    private FrameLayout mFrameLayout;
    private OnFilterClickListener mListener;

    public DrawerFilter(Context context) {
        this(context, null);
    }

    public DrawerFilter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawerFilter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        init();
    }

    private void init() {
        addFrameLayout();
        addDrawerListener(new DrawerLayout.DrawerListener() {
            /* class com.yoki.im.ui.DrawerFilter.AnonymousClass1 */

            @Override // android.support.v4.widget.DrawerLayout.DrawerListener
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override // android.support.v4.widget.DrawerLayout.DrawerListener
            public void onDrawerOpened(View drawerView) {
            }

            @Override // android.support.v4.widget.DrawerLayout.DrawerListener
            public void onDrawerClosed(View drawerView) {
//                EditTextUtils.hideSoftKeyboard(drawerView);
            }

            @Override // android.support.v4.widget.DrawerLayout.DrawerListener
            public void onDrawerStateChanged(int newState) {
            }
        });
    }

    private void addFrameLayout() {
//        this.mBottomView = View.inflate(this.mContext, R.layout.include_filter_confirm1, null);
//        this.mBottomView.setId(R.id.filter_button);
//        this.mBtnReset = (Button) this.mBottomView.findViewById(R.id.include_filter_confirm_btn_reset);
//        this.mBtnConfirm = (Button) this.mBottomView.findViewById(R.id.include_filter_confirm_btn_confirm);
        this.mBtnReset.setOnClickListener(new View.OnClickListener() {
            /* class com.yoki.im.ui.DrawerFilter.AnonymousClass2 */

            public void onClick(View v) {
                if (DrawerFilter.this.mListener != null) {
                    DrawerFilter.this.mListener.onReset();
                }
            }
        });
        this.mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            /* class com.yoki.im.ui.DrawerFilter.AnonymousClass3 */

            public void onClick(View v) {
                if (DrawerFilter.this.mListener != null) {
                    DrawerFilter.this.mListener.onConfirm();
                }
            }
        });
    }

    public void setOnFilterClickListener(OnFilterClickListener listener) {
        this.mListener = listener;
    }

//    public void addFilterFragment(FragmentManager fm, Fragment fragment, int containerViewId) {
//        addFilterFragment(fm, fragment, containerViewId, null);
//    }
//
//    public void addFilterFragment(FragmentManager fm, Fragment fragment, int containerViewId, String tag) {
//        FragmentTransaction fTransaction = fm.beginTransaction();
//        fTransaction.add(containerViewId, fragment, tag);
//        fTransaction.commit();
//    }
}
