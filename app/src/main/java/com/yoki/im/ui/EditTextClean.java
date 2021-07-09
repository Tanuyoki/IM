package com.yoki.im.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import androidx.appcompat.widget.AppCompatEditText;

import com.yoki.im.R;

public class EditTextClean extends AppCompatEditText implements View.OnFocusChangeListener, TextWatcher {
    private int drawSpacing;
    private boolean hasFoucs;
    private boolean isShow;
    private Drawable mClearDrawable;

    public EditTextClean(Context context) {
        this(context, null);
    }

    public EditTextClean(Context context, AttributeSet attrs) {
        this(context, attrs, 16842862);
    }

    public EditTextClean(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.isShow = false;
        init();
    }

    private void init() {
        this.drawSpacing = (int) (((double) ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay().getWidth()) * 0.062d);
        this.mClearDrawable = getCompoundDrawables()[2];
        if (this.mClearDrawable == null) {
            this.mClearDrawable = getResources().getDrawable(R.mipmap.all_clean_nor);
        }
        this.mClearDrawable.setBounds(0, 0, this.drawSpacing, this.drawSpacing);
        setClearIconVisible(false);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    /* access modifiers changed from: protected */
    public void setClearIconVisible(boolean visible) {
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], this.mClearDrawable, getCompoundDrawables()[3]);
    }

    public void onFocusChange(View v, boolean hasFocus) {
        boolean z = false;
        this.hasFoucs = hasFocus;
        if (hasFocus) {
            if (getText().length() > 0) {
                z = true;
            }
            setClearIconVisible(z);
            return;
        }
        setClearIconVisible(false);
    }

    public void onTextChanged(CharSequence s, int start, int count, int after) {
        if (this.hasFoucs) {
            setClearIconVisible(s.length() > 0);
        }
        if (s.length() > 0) {
            this.mClearDrawable = getResources().getDrawable(R.mipmap.all_clean_hl);
        } else {
            this.mClearDrawable = getResources().getDrawable(R.mipmap.all_clean_nor);
        }
        this.mClearDrawable.setBounds(0, 0, this.drawSpacing, this.drawSpacing);
    }

    public boolean onTouchEvent(MotionEvent event) {
        boolean touchable = true;
        if (event.getAction() == 1 && getCompoundDrawables()[2] != null) {
            if (event.getX() <= ((float) (getWidth() - getTotalPaddingRight())) || event.getX() >= ((float) (getWidth() - getPaddingRight()))) {
                touchable = false;
            }
            if (touchable) {
                setText("");
            }
        }
        return super.onTouchEvent(event);
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void afterTextChanged(Editable s) {
    }

    public void setShakeAnimation() {
        startAnimation(shakeAnimation(5));
    }

    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0.0f, 10.0f, 0.0f, 0.0f);
        translateAnimation.setInterpolator(new CycleInterpolator((float) counts));
        translateAnimation.setDuration(200);
        return translateAnimation;
    }
}
