package com.yoki.im.activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.yoki.im.R;
import com.yoki.im.base.BaseActivity;
import com.yoki.im.tools.ContentUriUtils;
import com.yoki.im.tools.ToastUtils;
import com.yoki.im.tools.touchgallery.GalleryWidget.BasePagerAdapter;
import com.yoki.im.tools.touchgallery.GalleryWidget.FilePagerAdapter;
import com.yoki.im.tools.touchgallery.GalleryWidget.GalleryViewPager;

import java.util.ArrayList;
import java.util.List;

public class ShowOriginalPictureAty extends BaseActivity implements View.OnClickListener {
    /* access modifiers changed from: protected */
    @Override // android.support.v4.app.SupportActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, com.acarbang.android.base.BaseActivity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.aty_original_picture_show);
        initView();
    }

    private void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ArrayList<String> imagePath = bundle.getStringArrayList("ImagePath");
            String clickImagePath = bundle.getString("ClickImagePath");
            if (clickImagePath != null && clickImagePath.contains("content")) {
                clickImagePath = ContentUriUtils.getPath(this, Uri.parse(clickImagePath));
            }
            if (!(clickImagePath == null || imagePath == null)) {
                int position = 0;
                List<String> items = new ArrayList<>();
                for (int i = 0; i < imagePath.size(); i++) {
                    String imageUri = imagePath.get(i);
                    if (imageUri.contains("content")) {
                        imageUri = ContentUriUtils.getPath(this, Uri.parse(imageUri));
                    }
                    if (clickImagePath.equals(imageUri)) {
                        position = i;
                    }
                    items.add(i, imageUri);
                }
                FilePagerAdapter pagerAdapter = new FilePagerAdapter(this, items);
                pagerAdapter.setOnItemChangeListener(new BasePagerAdapter.OnItemChangeListener() {
                    /* class com.acarbang.android.activities.ShowOriginalPictureAty.AnonymousClass1 */

                    @Override // com.acarbang.android.tools.touchgallery.GalleryWidget.BasePagerAdapter.OnItemChangeListener
                    public void onItemChange(int currentPosition) {
                        ToastUtils.show("Current item is " + currentPosition);
                    }
                });
                GalleryViewPager viewPager = (GalleryViewPager) findViewById(R.id.aty_original_picture_show_view_pager);
                viewPager.setOffscreenPageLimit(3);
                viewPager.setAdapter(pagerAdapter);
                viewPager.setCurrentItem(position);
            }
        }
    }

    public void onClick(View v) {
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
