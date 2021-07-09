package com.yoki.im.tools.im.iminput.emoji;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.yoki.im.tools.CommonData;
import com.yoki.im.R;
import java.util.ArrayList;

public class EmojiIndicatorView extends LinearLayout {
    private Context mContext;
    private ArrayList<View> mImageViews;
    private int marginLeft;
    private int pointSize;

    public EmojiIndicatorView(Context context) {
        this(context, null);
    }

    public EmojiIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmojiIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        this.pointSize = (int) (((double) CommonData.ScreenWidth) * 0.016d);
        this.marginLeft = (int) (((double) CommonData.ScreenWidth) * 0.025d);
    }

    public void initIndicator(int count) {
        this.mImageViews = new ArrayList<>();
        removeAllViews();
        for (int i = 0; i < count; i++) {
            View v = new View(this.mContext);
            LayoutParams lp = new LayoutParams(this.pointSize, this.pointSize);
            if (i != 0) {
                lp.leftMargin = this.marginLeft;
            }
            v.setLayoutParams(lp);
            if (i == 0) {
                v.setBackgroundResource(R.drawable.shape_bg_indicator_point_select);
            } else {
                v.setBackgroundResource(R.drawable.shape_bg_indicator_point_nomal);
            }
            this.mImageViews.add(v);
            addView(v);
        }
    }

    public void playByStartPointToNext(int startPosition, int nextPosition) {
        if (startPosition < 0 || nextPosition < 0 || nextPosition == startPosition) {
            nextPosition = 0;
            startPosition = 0;
        }
        this.mImageViews.get(nextPosition).setBackgroundResource(R.drawable.shape_bg_indicator_point_select);
        this.mImageViews.get(startPosition).setBackgroundResource(R.drawable.shape_bg_indicator_point_nomal);
    }
}
