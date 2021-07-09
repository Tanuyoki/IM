package com.yoki.im.tools.touchgallery.GalleryWidget;

import android.content.Context;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class BasePagerAdapter extends PagerAdapter {
    protected final Context mContext;
    protected int mCurrentPosition;
    protected OnItemChangeListener mOnItemChangeListener;
    protected final List<String> mResources;

    public interface OnItemChangeListener {
        void onItemChange(int i);
    }

    public BasePagerAdapter() {
        this.mCurrentPosition = -1;
        this.mResources = null;
        this.mContext = null;
    }

    public BasePagerAdapter(Context context, List<String> resources) {
        this.mCurrentPosition = -1;
        this.mResources = resources;
        this.mContext = context;
    }

    @Override // android.support.v4.view.PagerAdapter
    public int getCount() {
        return this.mResources.size();
    }

    @Override // android.support.v4.view.PagerAdapter
    public void startUpdate(ViewGroup arg0) {
    }

    @Override // android.support.v4.view.PagerAdapter
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override // android.support.v4.view.PagerAdapter
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if (this.mCurrentPosition != position) {
            GalleryViewPager galleryContainer = (GalleryViewPager) container;
            if (galleryContainer.mCurrentView != null) {
                galleryContainer.mCurrentView.resetScale();
            }
            this.mCurrentPosition = position;
            if (this.mOnItemChangeListener != null) {
                this.mOnItemChangeListener.onItemChange(this.mCurrentPosition);
            }
        }
    }

    @Override // android.support.v4.view.PagerAdapter
    public void finishUpdate(ViewGroup arg0) {
    }

    @Override // android.support.v4.view.PagerAdapter
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override // android.support.v4.view.PagerAdapter
    public Parcelable saveState() {
        return null;
    }

    @Override // android.support.v4.view.PagerAdapter
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    public int getCurrentPosition() {
        return this.mCurrentPosition;
    }

    public void setOnItemChangeListener(OnItemChangeListener listener) {
        this.mOnItemChangeListener = listener;
    }
}
