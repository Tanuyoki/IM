package com.yoki.im.ui;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.core.content.ContextCompat;

import com.yoki.im.R;
import com.yoki.im.tools.DensityUtils;
import com.yoki.im.tools.ViewUtils;
import com.yoki.im.tools.WindowUtils;

public class TitleBar extends ConstraintLayout {
    private static final int GRAVITY_BOTTOM = 1;
    private static final int GRAVITY_LEFT = 0;
    private static final int GRAVITY_RIGHT = 1;
    private static final int GRAVITY_TOP = 0;
    private static final int MATCH_PARENT = -1;
    private static final int ON_CLICK_FINISH = 1;
    private static final int ON_CLICK_NOTHING = 0;
    private static final int ORIENTATION_HORIZONTAL = 0;
    private static final int ORIENTATION_VERTICAL = 1;
    private static final int POSITION_CENTER = 1;
    private static final int POSITION_LEFT = 0;
    private static final int POSITION_RIGHT = 2;
    private static final int WRAP_CONTENT = -2;
    private Activity activity;
    private View containerCenter;
    private View containerLeft;
    private View containerRight;
    private Context context;
    private ImageView ivCenter;
    private ImageView ivLeft;
    private ImageView ivRight;
    private ImageView statusBar;
    private TextView tvCenter;
    private TextView tvLeft;
    private TextView tvRight;

    public TitleBar(Context context2) {
        this(context2, null);
    }

    public TitleBar(Context context2, AttributeSet attrs) {
        this(context2, attrs, 0);
    }

    public TitleBar(Context context2, AttributeSet attrs, int defStyleAttr) {
        super(context2, attrs, defStyleAttr);
        this.context = context2;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        int defaultTextSize = DensityUtils.dip2px(20.0f);
        int defaultTextColor = Color.parseColor("#353535");
        TypedArray typed = this.context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        int statusBarColorId = typed.getResourceId(R.styleable.TitleBar_statusBarColor, 0);
        int titleBarColorId = typed.getResourceId(R.styleable.TitleBar_titleBarColor, 0);
        String centerText = typed.getString(R.styleable.TitleBar_centerText);
        int centerLayoutId = typed.getResourceId(R.styleable.TitleBar_centerLayoutId, 0);
        int centerTextSize = typed.getDimensionPixelSize(R.styleable.TitleBar_centerTextSize, defaultTextSize);
        int centerTextColor = typed.getColor(R.styleable.TitleBar_centerTextColor, defaultTextColor);
        int centerDrawableResId = typed.getResourceId(R.styleable.TitleBar_centerDrawable, 0);
        int centerDrawableWidth = typed.getDimensionPixelSize(R.styleable.TitleBar_centerDrawableWidth, -1);
        int centerDrawableHeight = typed.getDimensionPixelSize(R.styleable.TitleBar_centerDrawableHeight, -1);
        int centerDrawablePadding = typed.getDimensionPixelSize(R.styleable.TitleBar_statusBarColor, 0);
        int centerDrawableGravity = typed.getInt(R.styleable.TitleBar_statusBarColor, 0);
        int centerOrientation = typed.getInt(R.styleable.TitleBar_statusBarColor, 0);
        String leftText = typed.getString(R.styleable.TitleBar_statusBarColor);
        int[] leftContainerPadding = {typed.getDimensionPixelSize(R.styleable.TitleBar_statusBarColor, 0), typed.getDimensionPixelSize(12, 0), typed.getDimensionPixelSize(14, 0), typed.getDimensionPixelSize(13, 0), typed.getDimensionPixelSize(11, 0)};
        int leftTextSize = typed.getDimensionPixelSize(R.styleable.TitleBar_statusBarColor, defaultTextSize);
        int leftTextColor = typed.getColor(R.styleable.TitleBar_statusBarColor, defaultTextColor);
        int leftDrawableResId = typed.getResourceId(R.styleable.TitleBar_statusBarColor, 0);
        int leftDrawableWidth = typed.getDimensionPixelSize(R.styleable.TitleBar_statusBarColor, -1);
        int leftDrawableHeight = typed.getDimensionPixelSize(R.styleable.TitleBar_statusBarColor, -1);
        int leftDrawablePadding = typed.getDimensionPixelOffset(R.styleable.TitleBar_statusBarColor, 0);
        int leftDrawableGravity = typed.getInt(R.styleable.TitleBar_statusBarColor, 0);
        int leftOrientation = typed.getInt(R.styleable.TitleBar_statusBarColor, 0);
        final int leftOnClick = typed.getInt(R.styleable.TitleBar_statusBarColor, 0);
        String rightText = typed.getString(R.styleable.TitleBar_statusBarColor);
        int[] rightContainerPadding = {typed.getDimensionPixelSize(R.styleable.TitleBar_statusBarColor, 0), typed.getDimensionPixelSize(27, 0), typed.getDimensionPixelSize(14, 0), typed.getDimensionPixelSize(13, 0), typed.getDimensionPixelSize(11, 0)};
        int rightTextSize = typed.getDimensionPixelSize(R.styleable.TitleBar_statusBarColor, defaultTextSize);
        int rightTextColor = typed.getColor(R.styleable.TitleBar_statusBarColor, defaultTextColor);
        int rightDrawableResId = typed.getResourceId(R.styleable.TitleBar_statusBarColor, 0);
        int rightDrawableWidth = typed.getDimensionPixelSize(R.styleable.TitleBar_statusBarColor, -1);
        int rightDrawableHeight = typed.getDimensionPixelSize(R.styleable.TitleBar_statusBarColor, -1);
        int rightDrawablePadding = typed.getDimensionPixelOffset(R.styleable.TitleBar_statusBarColor, 0);
        int rightDrawableGravity = typed.getInt(R.styleable.TitleBar_statusBarColor, 0);
        int rightOrientation = typed.getInt(R.styleable.TitleBar_statusBarColor, 0);
        typed.recycle();
        initStatusBar(statusBarColorId);
        this.tvLeft = generateTextView(leftText, (float) leftTextSize, leftTextColor);
        this.ivLeft = generateImageView(leftDrawableResId, leftDrawablePadding);
        this.containerLeft = generateContainerLayout(leftContainerPadding, 0, leftOrientation, leftDrawableGravity, this.tvLeft, this.ivLeft, generateImageViewLayoutParams(this.ivLeft.getBackground(), leftDrawableWidth, leftDrawableHeight));
        addView(this.containerLeft);
        if (centerLayoutId == 0) {
            this.tvCenter = generateTextView(centerText, (float) centerTextSize, centerTextColor);
            this.ivCenter = generateImageView(centerDrawableResId, centerDrawablePadding);
            this.containerCenter = generateContainerLayout(null, 1, centerOrientation, centerDrawableGravity, this.tvCenter, this.ivCenter, generateImageViewLayoutParams(this.ivCenter.getBackground(), centerDrawableWidth, centerDrawableHeight));
        } else {
            this.containerCenter = inflate(this.context, centerLayoutId, null);
            ConstraintLayout.LayoutParams params = generateCommonLayoutParams(-2, -2);
            params.leftToLeft = 0;
            params.rightToRight = 0;
            this.containerCenter.setLayoutParams(params);
        }
        addView(this.containerCenter);
        this.tvRight = generateTextView(rightText, (float) rightTextSize, rightTextColor);
        this.ivRight = generateImageView(rightDrawableResId, rightDrawablePadding);
        this.containerRight = generateContainerLayout(rightContainerPadding, 2, rightOrientation, rightDrawableGravity, this.tvRight, this.ivRight, generateImageViewLayoutParams(this.ivRight.getBackground(), rightDrawableWidth, rightDrawableHeight));
        addView(this.containerRight);
        setBackgroundColor(ContextCompat.getColor(this.context, titleBarColorId));
        this.containerLeft.setOnClickListener(new View.OnClickListener() {
            /* class com.yoki.im.ui.TitleBar.AnonymousClass1 */

            public void onClick(View v) {
                if (leftOnClick == 1) {
                    if (TitleBar.this.activity == null) {
                        TitleBar.this.activity = WindowUtils.getActivity(TitleBar.this.context, TitleBar.this.getRootView());
                    }
                    TitleBar.this.activity.finish();
                }
            }
        });
    }

    private void initStatusBar(int backgroundId) {
        int statusBarHeight;
        this.activity = WindowUtils.getActivity(this.context, getRootView());
        WindowUtils.setTransparentStatusBar(this.activity);
        if (Build.VERSION.SDK_INT >= 19) {
            statusBarHeight = WindowUtils.getStatusBarHeight();
        } else {
            statusBarHeight = 0;
        }
        this.statusBar = new ImageView(this.context);
        this.statusBar.setId(ViewUtils.generateViewId());
        this.statusBar.setBackgroundResource(backgroundId);
        ConstraintLayout.LayoutParams statusBarParams = new ConstraintLayout.LayoutParams(-1, statusBarHeight);
        statusBarParams.topToTop = 0;
        this.statusBar.setLayoutParams(statusBarParams);
        addView(this.statusBar);
    }

    private TextView generateTextView(String text, float textSize, int textColor) {
        TextView textView = new TextView(this.context);
        textView.setId(ViewUtils.generateViewId());
        textView.setText(text);
        textView.setTextSize(0, textSize);
        textView.setTextColor(textColor);
        return textView;
    }

    private ImageView generateImageView(int resId, int padding) {
        ImageView imageView = new ImageView(this.context);
        imageView.setId(ViewUtils.generateViewId());
        imageView.setBackgroundResource(resId);
        imageView.setPadding(padding, padding, padding, padding);
        return imageView;
    }

    private ConstraintLayout.LayoutParams generateImageViewLayoutParams(Drawable drawable, int width, int height) {
        if (width == -1 && drawable != null) {
            width = drawable.getIntrinsicWidth();
        }
        if (height == -1 && drawable != null) {
            height = drawable.getIntrinsicHeight();
        }
        return new Constraints.LayoutParams(width, height);
    }

    private ConstraintLayout.LayoutParams generateCommonLayoutParams(int width, int height) {
        ConstraintLayout.LayoutParams params = new Constraints.LayoutParams(width, height);
        params.topToBottom = this.statusBar.getId();
        params.bottomToBottom = 0;
        return params;
    }

    private ViewGroup generateContainerLayout(int[] layoutPadding, int position, int orientation, int drawableGravity, TextView tv, ImageView iv, ConstraintLayout.LayoutParams ivParams) {
        ConstraintLayout.LayoutParams containerParams = generateCommonLayoutParams(-2, -1);
        ConstraintLayout layout = new ConstraintLayout(this.context);
        if (layoutPadding != null) {
            if (layoutPadding[0] != 0 || layoutPadding.length < 5) {
                layout.setPadding(layoutPadding[0], layoutPadding[0], layoutPadding[0], layoutPadding[0]);
            } else {
                layout.setPadding(layoutPadding[1], layoutPadding[2], layoutPadding[3], layoutPadding[4]);
            }
        }
        ConstraintLayout.LayoutParams tvParams = new Constraints.LayoutParams(-2, -2);
        if (position == 0) {
            containerParams.leftToLeft = 0;
        } else if (position == 2) {
            containerParams.rightToRight = 0;
        } else {
            containerParams.leftToLeft = 0;
            containerParams.rightToRight = 0;
        }
        if (orientation == 0) {
            if (drawableGravity == 0) {
                ivParams.leftToLeft = 0;
                ivParams.rightToLeft = tv.getId();
                tvParams.leftToRight = iv.getId();
                tvParams.rightToRight = 0;
            } else {
                tvParams.leftToLeft = 0;
                tvParams.rightToLeft = iv.getId();
                ivParams.leftToRight = tv.getId();
                ivParams.rightToRight = 0;
            }
            tvParams.topToTop = 0;
            tvParams.bottomToBottom = 0;
            ivParams.topToTop = 0;
            ivParams.bottomToBottom = 0;
        } else if (drawableGravity == 0) {
            ivParams.topToTop = 0;
            ivParams.bottomToTop = tv.getId();
            tvParams.topToBottom = iv.getId();
            tvParams.bottomToBottom = 0;
        } else {
            tvParams.topToTop = 0;
            tvParams.bottomToTop = iv.getId();
            ivParams.topToBottom = tv.getId();
            ivParams.bottomToBottom = 0;
        }
        tv.setLayoutParams(tvParams);
        iv.setLayoutParams(ivParams);
        layout.addView(tv);
        layout.addView(iv);
        layout.setLayoutParams(containerParams);
        return layout;
    }

    public void setCenterText(String text) {
        this.tvCenter.setText(text);
    }

    public void setCenterTextColor(int color) {
        this.tvCenter.setTextColor(color);
    }

    public int getCenterTextViewHeight() {
        return this.tvCenter.getHeight();
    }

    public void setOnLeftClickListener(View.OnClickListener listener) {
        this.containerLeft.setOnClickListener(listener);
    }

    public void setOnRightClickListener(View.OnClickListener listener) {
        this.containerRight.setOnClickListener(listener);
    }
}
