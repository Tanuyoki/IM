package com.yoki.im.tools;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.yoki.im.R;

public class CheckBoxCustom extends RelativeLayout {
    private boolean mClickTag;
    private Context mContext;
    private ImageView mImageView;
    private View mLayout;
    private boolean mRepeatClickTag;
    private TextView mTextView;

    public CheckBoxCustom(Context context) {
        this(context, null);
    }

    public CheckBoxCustom(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckBoxCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        this.mLayout = View.inflate(this.mContext, R.layout.view_check_box_select1, null);
        this.mTextView = (TextView) this.mLayout.findViewById(R.id.include_check_box_select_tv);
        this.mImageView = (ImageView) this.mLayout.findViewById(R.id.include_check_box_select_iv);
        this.mLayout.setBackgroundDrawable(BackgroundUtils.generateSelectedDrawable(BackgroundUtils.generateGradientDrawable(1, Color.parseColor("#EDF0F3")),
                BackgroundUtils.generateGradientDrawable(DensityUtils.dip2px(3.0f), 1,
                        ContextCompat.getColor(this.mContext, R.color.tran), Color.parseColor("#23a3e9"))));
        addView(this.mLayout);
    }

    public String getText() {
        return String.valueOf(this.mTextView.getText());
    }

    public void setText(String text) {
        this.mTextView.setText(text);
    }

    public boolean isRepeatSelected() {
        return this.mRepeatClickTag;
    }

    public boolean isSelected() {
        return this.mClickTag;
    }

    public void setSelected(boolean selected) {
        this.mLayout.setSelected(selected);
        if (selected) {
            if (this.mClickTag) {
                this.mRepeatClickTag = true;
            }
            this.mClickTag = true;
            this.mTextView.setTextColor(getResources().getColor(R.color.txt_blue_shallow));
            this.mImageView.setVisibility(0);
            return;
        }
        this.mClickTag = false;
        this.mRepeatClickTag = false;
        this.mTextView.setTextColor(getResources().getColor(R.color.txt_black));
        this.mImageView.setVisibility(4);
    }

}
