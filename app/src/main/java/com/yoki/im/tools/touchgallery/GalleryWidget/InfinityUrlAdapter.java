package com.yoki.im.tools.touchgallery.GalleryWidget;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.yoki.im.tools.touchgallery.TouchView.UrlTouchImageView;
import java.util.List;

public class InfinityUrlAdapter extends BasePagerAdapter {
    public int FIRST_PAGE = 1;
    private int MIN_LOOPS = 1000;
    private int TOTAL_PAGES = -1;
    private ImageView.ScaleType mScaleType = null;

    public InfinityUrlAdapter(Context context, List<String> resources) {
        super(context, resources);
        this.TOTAL_PAGES = resources.size();
        this.FIRST_PAGE = (this.TOTAL_PAGES * this.MIN_LOOPS) / 2;
    }

    @Override // com.yoki.im.tools.touchgallery.GalleryWidget.BasePagerAdapter, android.support.v4.view.PagerAdapter
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, this.FIRST_PAGE, object);
        ((GalleryViewPager) container).mCurrentView = ((UrlTouchImageView) object).getImageView();
    }

    @Override // android.support.v4.view.PagerAdapter
    public Object instantiateItem(ViewGroup collection, int position) {
        int position2 = position % this.TOTAL_PAGES;
        UrlTouchImageView iv = new UrlTouchImageView(this.mContext);
        iv.setUrl((String) this.mResources.get(position2));
        iv.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        if (this.mScaleType != null) {
            iv.setScaleType(this.mScaleType);
        }
        collection.addView(iv, 0);
        return iv;
    }

    public void setScaleTypeForImageView(ImageView.ScaleType scaletype) {
        this.mScaleType = scaletype;
    }

    @Override // com.yoki.im.tools.touchgallery.GalleryWidget.BasePagerAdapter, android.support.v4.view.PagerAdapter
    public int getCount() {
        return this.TOTAL_PAGES * this.MIN_LOOPS;
    }
}
