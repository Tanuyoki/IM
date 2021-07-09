package com.yoki.im.tools.touchgallery.GalleryWidget;

import android.content.Context;
import android.view.ViewGroup;
import com.yoki.im.tools.touchgallery.TouchView.UrlTouchImageView;
import java.util.List;

public class UrlPagerAdapter extends BasePagerAdapter {
    public UrlPagerAdapter(Context context, List<String> resources) {
        super(context, resources);
    }

    @Override // com.yoki.im.tools.touchgallery.GalleryWidget.BasePagerAdapter, android.support.v4.view.PagerAdapter
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        ((GalleryViewPager) container).mCurrentView = ((UrlTouchImageView) object).getImageView();
    }

    @Override // android.support.v4.view.PagerAdapter
    public Object instantiateItem(ViewGroup collection, int position) {
        UrlTouchImageView iv = new UrlTouchImageView(this.mContext);
        iv.setUrl((String) this.mResources.get(position));
        iv.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        collection.addView(iv, 0);
        return iv;
    }
}
