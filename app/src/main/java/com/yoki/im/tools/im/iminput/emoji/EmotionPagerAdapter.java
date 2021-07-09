package com.yoki.im.tools.im.iminput.emoji;

import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class EmotionPagerAdapter extends PagerAdapter {
    private List<GridView> gvs;

    public EmotionPagerAdapter(List<GridView> gvs2) {
        this.gvs = gvs2;
    }

    @Override // android.support.v4.view.PagerAdapter
    public int getCount() {
        return this.gvs.size();
    }

    @Override // android.support.v4.view.PagerAdapter
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override // android.support.v4.view.PagerAdapter
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(this.gvs.get(position));
    }

    @Override // android.support.v4.view.PagerAdapter
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(this.gvs.get(position));
        return this.gvs.get(position);
    }
}
