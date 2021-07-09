package com.yoki.im.base;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

public class BasePagerAdapter extends PagerAdapter {
    @Override // android.support.v4.view.PagerAdapter
    public int getCount() {
        return 0;
    }

    @Override // android.support.v4.view.PagerAdapter
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override // android.support.v4.view.PagerAdapter
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override // android.support.v4.view.PagerAdapter
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override // android.support.v4.view.PagerAdapter
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

    @Override // android.support.v4.view.PagerAdapter
    public float getPageWidth(int position) {
        return super.getPageWidth(position);
    }

    @Override // android.support.v4.view.PagerAdapter
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }
}
