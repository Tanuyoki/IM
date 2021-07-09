package com.yoki.im.base;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.yoki.im.tools.CommonData;

import java.util.List;

public class BaseViewPager extends ViewPager {
    private boolean isOpenSelectedChange = false;
    private ContentPagerAdapter mAdapter;
    private float mBeforeX;
    private View[] mClickView;
    private Context mContext;
    private int mCurrentItem = 0;
    private View mIndicator;
    private int mIndicatorProportion;
    private int mIndicatorWidth;
    private boolean mIsSliding = true;
    private int mLineWidth = 0;
    private ViewPager.OnPageChangeListener mListener;
    private RelativeLayout.LayoutParams mRelativeLayoutParams;
    private int mScreenWidth;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        /* class com.yoki.im.base.BaseViewPager.AnonymousClass1 */

        public void onClick(View v) {
            BaseViewPager.this.setCurrentItem(((Integer) v.getTag()).intValue());
        }
    };
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        /* class com.yoki.im.base.BaseViewPager.AnonymousClass2 */
        float sumPositionAndPositionOffset;

        @Override // android.support.v4.view.ViewPager.OnPageChangeListener
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (BaseViewPager.this.mIndicator != null && BaseViewPager.this.mRelativeLayoutParams != null && (BaseViewPager.this.mIndicator.getLayoutParams() instanceof RelativeLayout.LayoutParams)) {
                BaseViewPager.this.mRelativeLayoutParams = (RelativeLayout.LayoutParams) BaseViewPager.this.mIndicator.getLayoutParams();
                if (!(BaseViewPager.this.mRelativeLayoutParams == null || positionOffset == 0.0f)) {
                    BaseViewPager.this.mRelativeLayoutParams.leftMargin = (int) ((((float) position) + positionOffset) * ((float) BaseViewPager.this.mRelativeLayoutParams.width));
                    BaseViewPager.this.mIndicator.setLayoutParams(BaseViewPager.this.mRelativeLayoutParams);
                }
                if (!(!BaseViewPager.this.isOpenSelectedChange || BaseViewPager.this.mClickView == null || positionOffset == 0.0f)) {
                    TextView tvCurr = (TextView) BaseViewPager.this.mClickView[position];
                    float scaleEnlarge = 1.0f + (positionOffset / 2.0f);
                    float scaleShrink = 1.0f + (0.5f - (positionOffset / 2.0f));
                    if (position + 1 < BaseViewPager.this.mClickView.length) {
                        TextView tvLast = (TextView) BaseViewPager.this.mClickView[position + 1];
                        tvLast.setScaleX(scaleEnlarge);
                        tvLast.setScaleY(scaleEnlarge);
                    }
                    tvCurr.setScaleX(scaleShrink);
                    tvCurr.setScaleY(scaleShrink);
                }
                this.sumPositionAndPositionOffset = ((float) position) + positionOffset;
            }
        }

        @Override // android.support.v4.view.ViewPager.OnPageChangeListener
        public void onPageSelected(int position) {
            if (BaseViewPager.this.isOpenSelectedChange) {
                ((TextView) BaseViewPager.this.mClickView[BaseViewPager.this.mCurrentItem]).setTypeface(Typeface.defaultFromStyle(0));
                ((TextView) BaseViewPager.this.mClickView[position]).setTypeface(Typeface.defaultFromStyle(1));
            }
            if (BaseViewPager.this.mListener != null) {
                BaseViewPager.this.mListener.onPageSelected(position);
            }
            BaseViewPager.this.mCurrentItem = position;
        }

        @Override // android.support.v4.view.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int state) {
        }
    };

    public BaseViewPager(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    private void init() {
        addOnPageChangeListener(this.onPageChangeListener);
    }

    public BaseViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!this.mIsSliding) {
            switch (ev.getAction()) {
                case 0:
                    this.mBeforeX = ev.getX();
                    break;
                case 2:
                    float motionValue = ev.getX() - this.mBeforeX;
                    if (motionValue >= 0.0f && motionValue <= 0.0f) {
                        this.mBeforeX = ev.getX();
                    } else {
                        return true;
                    }
                    break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public BaseViewPager addLineIndicator(View view, RelativeLayout.LayoutParams layoutParams) {
        addLineIndicator(view, layoutParams, 0);
        return this;
    }

    public BaseViewPager addLineIndicator(View view, RelativeLayout.LayoutParams layoutParams, int proportion) {
        addLineIndicator(view, layoutParams, proportion, 0);
        return this;
    }

    public BaseViewPager addLineIndicator(View view, RelativeLayout.LayoutParams layoutParams, int proportion, int width) {
        this.mIndicator = view;
        this.mRelativeLayoutParams = layoutParams;
        this.mIndicatorProportion = proportion;
        this.mLineWidth = width;
        return this;
    }

    public BaseViewPager setFragment(List<Fragment> list, FragmentManager fm) {
        this.mAdapter = new ContentPagerAdapter(list, fm);
        setAdapter(this.mAdapter);
        if (this.mIndicatorProportion == 0) {
            this.mIndicatorProportion = list.size();
        }
        this.mScreenWidth = CommonData.ScreenWidth;
        if (this.mLineWidth != 0) {
            this.mIndicatorWidth = this.mLineWidth;
        } else {
            this.mIndicatorWidth = this.mScreenWidth / this.mIndicatorProportion;
        }
        this.mRelativeLayoutParams.width = this.mIndicatorWidth;
        this.mRelativeLayoutParams.height = this.mScreenWidth / 180;
        this.mIndicator.setLayoutParams(this.mRelativeLayoutParams);
        return this;
    }

    public BaseViewPager addOnClickListener(boolean isOpenSelectedChange2, View... view) {
        if (!(view == null || view.length == 0)) {
            this.isOpenSelectedChange = isOpenSelectedChange2;
            this.mClickView = view;
            for (int i = 0; i < this.mClickView.length; i++) {
                this.mClickView[i].setTag(Integer.valueOf(i));
                this.mClickView[i].setOnClickListener(this.onClickListener);
                if (isOpenSelectedChange2 && this.mClickView[0] != null) {
                    this.mClickView[0].setScaleX(1.5f);
                    this.mClickView[0].setScaleY(1.5f);
                    if (this.mClickView[0] instanceof TextView) {
                        ((TextView) view[0]).setTypeface(Typeface.defaultFromStyle(1));
                    }
                }
            }
        }
        return this;
    }

    public BaseViewPager setSliding(boolean sliding) {
        this.mIsSliding = sliding;
        return this;
    }

    class ViewPagerAdapter extends PagerAdapter {
        private List<View> views;

        public ViewPagerAdapter(List<View> views2) {
            this.views = views2;
        }

        @Override // android.support.v4.view.PagerAdapter
        public int getCount() {
            return this.views.size();
        }

        @Override // android.support.v4.view.PagerAdapter
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(this.views.get(position));
            return this.views.get(position);
        }

        @Override // android.support.v4.view.PagerAdapter
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(this.views.get(position));
        }

        @Override // android.support.v4.view.PagerAdapter
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override // android.support.v4.view.PagerAdapter
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }

    class ContentPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public ContentPagerAdapter(List<Fragment> fragments2, FragmentManager fm) {
            super(fm);
            this.fragments = fragments2;
        }

        @Override // android.support.v4.app.FragmentPagerAdapter
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }

        @Override // android.support.v4.view.PagerAdapter
        public int getCount() {
            return this.fragments.size();
        }
    }
}
