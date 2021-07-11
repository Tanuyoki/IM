package com.yoki.im.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import androidx.viewpager.widget.ViewPager;

import com.yoki.im.R;
import com.yoki.im.base.BaseFragment;
import com.yoki.im.tools.CommonData;
import com.yoki.im.tools.im.iminput.emoji.EmojiIndicatorView;
import com.yoki.im.tools.im.iminput.emoji.EmotionGridViewAdapter;
import com.yoki.im.tools.im.iminput.emoji.EmotionPagerAdapter;
import com.yoki.im.tools.im.iminput.emoji.EmotionUtils;
import com.yoki.im.tools.im.iminput.emoji.GlobalOnItemClickManagerUtils;

import java.util.ArrayList;
import java.util.List;

public class ImInputExpressionFragment extends BaseFragment {
    private int emotionMapType;
    private EmotionPagerAdapter emotionPagerGvAdapter;
    private ViewPager viewPagerEmotion;
    private EmojiIndicatorView viewPointGroup;

    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_emotion, container, false);
        initView(rootView);
        initEmotion();
        initListener();
        return rootView;
    }

    /* access modifiers changed from: protected */
    public void initView(View rootView) {
        this.viewPagerEmotion = rootView.findViewById(R.id.emotion_view_pager);
        this.viewPointGroup = rootView.findViewById(R.id.emotion_point_group);
        this.emotionMapType = 1;
    }

    /* access modifiers changed from: protected */
    public void initListener() {
        this.viewPagerEmotion.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /* class com.acarbang.android.fragment.ImInputExpressionFragment.AnonymousClass1 */
            int oldPagerPos = 0;

            @Override // android.support.v4.view.ViewPager.OnPageChangeListener
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override // android.support.v4.view.ViewPager.OnPageChangeListener
            public void onPageSelected(int position) {
                ImInputExpressionFragment.this.viewPointGroup.playByStartPointToNext(this.oldPagerPos, position);
                this.oldPagerPos = position;
            }

            @Override // android.support.v4.view.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initEmotion() {
        int screenWidth = CommonData.ScreenWidth;
        int spacing = CommonData.ScreenWidth / 30;
        int itemWidth = (screenWidth - (spacing * 8)) / 7;
        int gvHeight = (itemWidth * 3) + (spacing * 6);
        List<GridView> emotionViews = new ArrayList<>();
        List<String> emotionNames = new ArrayList<>();
        for (String emojiName : EmotionUtils.getEmojiMap(this.emotionMapType).keySet()) {
            emotionNames.add(emojiName);
            if (emotionNames.size() == 20) {
                emotionViews.add(createEmotionGridView(emotionNames, screenWidth, spacing, itemWidth, gvHeight));
                emotionNames = new ArrayList<>();
            }
        }
        if (emotionNames.size() > 0) {
            emotionViews.add(createEmotionGridView(emotionNames, screenWidth, spacing, itemWidth, gvHeight));
        }
        this.viewPointGroup.initIndicator(emotionViews.size());
        this.emotionPagerGvAdapter = new EmotionPagerAdapter(emotionViews);
        this.viewPagerEmotion.setAdapter(this.emotionPagerGvAdapter);
        this.viewPagerEmotion.setLayoutParams(new LinearLayout.LayoutParams(screenWidth, gvHeight));
    }

    private GridView createEmotionGridView(List<String> emotionNames, int gvWidth, int padding, int itemWidth, int gvHeight) {
        GridView gv = new GridView(getActivity());
        gv.setSelector(17170445);
        gv.setNumColumns(7);
        gv.setPadding(padding, padding, padding, padding);
        gv.setHorizontalSpacing(padding);
        gv.setVerticalSpacing(padding * 2);
        gv.setLayoutParams(new ViewGroup.LayoutParams(gvWidth, gvHeight));
        gv.setAdapter((ListAdapter) new EmotionGridViewAdapter(getActivity(), emotionNames, itemWidth, this.emotionMapType));
        gv.setOnItemClickListener(GlobalOnItemClickManagerUtils.getInstance(getActivity()).getOnItemClickListener(this.emotionMapType));
        return gv;
    }
}
