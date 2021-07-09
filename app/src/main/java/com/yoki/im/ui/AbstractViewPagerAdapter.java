package com.yoki.im.ui;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public abstract class AbstractViewPagerAdapter<T> extends PagerAdapter {
    private List<T> mData;
    private SparseArray<View> mViews;

    public abstract View newView(int i);

    protected AbstractViewPagerAdapter(List<T> data) {
        this.mData = data;
        this.mViews = new SparseArray<>(data.size());
    }

    @Override // android.support.v4.view.PagerAdapter
    public int getCount() {
        return this.mData.size();
    }

    @Override // android.support.v4.view.PagerAdapter
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override // android.support.v4.view.PagerAdapter
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(this.mViews.get(position));
    }

    public T getData(int position) {
        return this.mData.get(position);
    }

    @Override // android.support.v4.view.PagerAdapter
    public Object instantiateItem(ViewGroup container, int position) {
        View view = this.mViews.get(position);
        if (view == null) {
            view = newView(position);
            this.mViews.put(position, view);
        }
        container.addView(view);
        return view;
    }
}
