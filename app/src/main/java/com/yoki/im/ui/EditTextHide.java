package com.yoki.im.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import com.yoki.im.R;

public class EditTextHide extends EditText implements View.OnFocusChangeListener, TextWatcher {
    private boolean hasFoucs;
    private boolean isShow;
    private Drawable mHide;
    private Drawable mShow;

    public EditTextHide(Context context) {
        this(context, null);
    }

    public EditTextHide(Context context, AttributeSet attrs) {
        this(context, attrs, 16842862);
    }

    public EditTextHide(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.isShow = false;
        init();
    }

    private void init() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService("window");
        int drawWidth = (int) (((double) windowManager.getDefaultDisplay().getWidth()) * 0.058d);
        int drawHeight = (int) (((double) windowManager.getDefaultDisplay().getWidth()) * 0.06d);
        this.mHide = getCompoundDrawables()[2];
        if (this.mHide == null) {
//            this.mHide = getResources().getDrawable(R.mipmap.all_hide);
        }
        this.mHide.setBounds(0, 0, drawWidth, drawHeight);
        this.mShow = getCompoundDrawables()[2];
        if (this.mShow == null) {
//            this.mShow = getResources().getDrawable(R.mipmap.all_show);
        }
        this.mShow.setBounds(0, 0, drawWidth, drawHeight);
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], this.mHide, getCompoundDrawables()[3]);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == 1 && getCompoundDrawables()[2] != null) {
            boolean touchable = event.getX() > ((float) (getWidth() - getTotalPaddingRight())) && event.getX() < ((float) (getWidth() - getPaddingRight()));
            if (touchable && getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], this.mShow, getCompoundDrawables()[3]);
            } else if (touchable && getTransformationMethod() == HideReturnsTransformationMethod.getInstance()) {
                setTransformationMethod(PasswordTransformationMethod.getInstance());
                setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], this.mHide, getCompoundDrawables()[3]);
            }
        }
        return super.onTouchEvent(event);
    }

    public void setShakeAnimation() {
        startAnimation(shakeAnimation(5));
    }

    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0.0f, 10.0f, 0.0f, 0.0f);
        translateAnimation.setInterpolator(new CycleInterpolator((float) counts));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

    public void onFocusChange(View v, boolean hasFocus) {
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    public void afterTextChanged(Editable s) {
    }
}
