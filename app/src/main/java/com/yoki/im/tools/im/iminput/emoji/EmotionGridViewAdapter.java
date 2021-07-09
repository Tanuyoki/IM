package com.yoki.im.tools.im.iminput.emoji;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.yoki.im.R;

import java.util.List;

public class EmotionGridViewAdapter extends BaseAdapter {
    private Context context;
    private List<String> emotionNames;
    private int emotion_map_type;
    private int itemWidth;

    public EmotionGridViewAdapter(Context context2, List<String> emotionNames2, int itemWidth2, int emotion_map_type2) {
        this.context = context2;
        this.emotionNames = emotionNames2;
        this.itemWidth = itemWidth2;
        this.emotion_map_type = emotion_map_type2;
    }

    public int getCount() {
        return this.emotionNames.size() + 1;
    }

    public String getItem(int position) {
        return this.emotionNames.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView iv_emotion = new ImageView(this.context);
        iv_emotion.setPadding(this.itemWidth / 8, this.itemWidth / 8, this.itemWidth / 8, this.itemWidth / 8);
        iv_emotion.setLayoutParams(new AbsListView.LayoutParams(this.itemWidth, this.itemWidth));
        if (position == getCount() - 1) {
            iv_emotion.setImageResource(R.mipmap.keyboard_delete);
        } else {
            iv_emotion.setImageResource(EmotionUtils.getImgByName(this.emotion_map_type, this.emotionNames.get(position)));
        }
        return iv_emotion;
    }
}
