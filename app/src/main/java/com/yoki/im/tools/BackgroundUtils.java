package com.yoki.im.tools;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;

import androidx.core.content.ContextCompat;

import com.yoki.im.R;

public class BackgroundUtils {
    public static final int CORNER_DIRECTION_ALL = 1;
    public static final int CORNER_DIRECTION_BOTTOM = 3;
    public static final int CORNER_DIRECTION_LEFT = 4;
    public static final int CORNER_DIRECTION_LEFT_BOTTOM = 7;
    public static final int CORNER_DIRECTION_LEFT_TOP = 6;
    public static final int CORNER_DIRECTION_NULL = 0;
    public static final int CORNER_DIRECTION_RIGHT = 5;
    public static final int CORNER_DIRECTION_RIGHT_BOTTOM = 9;
    public static final int CORNER_DIRECTION_RIGHT_TOP = 8;
    public static final int CORNER_DIRECTION_TOP = 2;
    private static final int CORNER_RADIUS = DensityUtils.dip2px(3.0f);
    public static final int STROKE_RULES_DEFAULT = 0;
    public static final int STROKE_RULES_LIST = 10;
    private static final int STROKE_WIDTH = DensityUtils.dip2px(1.0f);

    public static Drawable generateClickEffectDrawable(int cornerDirection) {
        return generateClickEffectDrawable(CORNER_RADIUS, cornerDirection);
    }

    public static Drawable generateClickEffectDrawable(int cornerDirection, int defaultColor, int pressColor) {
        return generateClickEffectDrawable(CORNER_RADIUS, cornerDirection, defaultColor, pressColor);
    }

    public static Drawable generateClickEffectDrawable(int cornerRadius, int cornerDirection) {
//        return generateClickEffectDrawable(cornerRadius, cornerDirection, ContextCompat.getColor(App.getContext(), R.color.bg_white), ContextCompat.getColor(App.getContext(), R.color.bg_gray));
        return null;
    }

    public static Drawable generateClickEffectDrawable(int cornerRadius, int cornerDirection, int defaultColor, int pressColor) {
        return generateClickEffectDrawable(generateGradientDrawable(cornerRadius, cornerDirection, defaultColor), generateGradientDrawable(cornerRadius, cornerDirection, pressColor), pressColor);
    }

    public static Drawable generateClickEffectDrawable(Drawable defaultDrawable, Drawable pressDrawable, int pressColor) {
        if (Build.VERSION.SDK_INT >= 21) {
            return new RippleDrawable(ColorStateList.valueOf(pressColor), defaultDrawable, null);
        }
        return generatePressDrawable(defaultDrawable, pressDrawable);
    }

    public static Drawable generateLayerDrawable() {
        RoundRectShape roundRectShape0 = new RoundRectShape(new float[]{(float) 10, (float) 10, (float) 10, (float) 10, (float) 10, (float) 10, (float) 10, (float) 10}, null, null);
        RoundRectShape roundRectShape1 = new RoundRectShape(new float[]{(float) 10, (float) 10, (float) 10, (float) 10, (float) 10, (float) 10, (float) 10, (float) 10}, null, null);
        ShapeDrawable shapeDrawableBg = new ShapeDrawable();
        shapeDrawableBg.setPadding(0, 0, 0, 0);
        shapeDrawableBg.setShape(roundRectShape0);
        shapeDrawableBg.getPaint().setStyle(Paint.Style.FILL);
        shapeDrawableBg.getPaint().setColor(-4473925);
        ShapeDrawable shapeDrawableFg = new ShapeDrawable();
        shapeDrawableFg.setPadding(23, 23, 23, 23);
        shapeDrawableFg.setShape(roundRectShape1);
        shapeDrawableFg.getPaint().setStyle(Paint.Style.FILL);
        shapeDrawableFg.getPaint().setColor(-2236963);
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[0]);
        layerDrawable.setLayerInset(0, 0, 0, 0, 0);
        return layerDrawable;
    }

    public static Drawable generateSelectedDrawable(Drawable defaultDrawable, Drawable selectedDrawable) {
        return generateStateListDrawable(new int[]{16842913}, defaultDrawable, selectedDrawable);
    }

    public static Drawable generatePressDrawable(Drawable defaultDrawable, Drawable pressDrawable) {
        return generateStateListDrawable(new int[]{16842919}, defaultDrawable, pressDrawable);
    }

    private static Drawable generateStateListDrawable(int[] state, Drawable defaultDrawable, Drawable activeDrawable) {
        StateListDrawable sld = new StateListDrawable();
        int[] falseState = new int[state.length];
        for (int i = 0; i < state.length; i++) {
            falseState[i] = -state[i];
        }
        sld.addState(falseState, defaultDrawable);
        sld.addState(state, activeDrawable);
        return sld;
    }

    public static Drawable generateGradientDrawable(int cornerDirection, int fillColor) {
        return generateGradientDrawable(CORNER_RADIUS, cornerDirection, fillColor);
    }

    public static Drawable generateGradientDrawable(int cornerRadius, int cornerDirection, int fillColor) {
        return generateGradientDrawable(cornerRadius, cornerDirection, fillColor, 0);
    }

    public static Drawable generateGradientDrawable(int cornerRadius, int cornerDirection, int fillColor, int strokeColor) {
        return generateGradientDrawable(cornerRadius, cornerDirection, fillColor, STROKE_WIDTH, 0, strokeColor);
    }

    public static Drawable generateGradientDrawable(int cornerRadius, int cornerDirection, int fillColor, int strokeWidth, int strokeRules, int strokeColor) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(fillColor);
        if (cornerDirection == 1) {
            drawable.setCornerRadius((float) cornerRadius);
        } else if (cornerDirection == 2) {
            drawable.setCornerRadii(new float[]{(float) cornerRadius, (float) cornerRadius, (float) cornerRadius, (float) cornerRadius, 0.0f, 0.0f, 0.0f, 0.0f});
        } else if (cornerDirection == 3) {
            drawable.setCornerRadii(new float[]{0.0f, 0.0f, 0.0f, 0.0f, (float) cornerRadius, (float) cornerRadius, (float) cornerRadius, (float) cornerRadius});
        } else if (cornerDirection == 4) {
            drawable.setCornerRadii(new float[]{(float) cornerRadius, (float) cornerRadius, 0.0f, 0.0f, 0.0f, 0.0f, (float) cornerRadius, (float) cornerRadius});
        } else if (cornerDirection == 5) {
            drawable.setCornerRadii(new float[]{0.0f, 0.0f, (float) cornerRadius, (float) cornerRadius, (float) cornerRadius, (float) cornerRadius, 0.0f, 0.0f});
        } else if (cornerDirection == 6) {
            drawable.setCornerRadii(new float[]{(float) cornerRadius, (float) cornerRadius, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f});
        } else if (cornerDirection == 7) {
            drawable.setCornerRadii(new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, (float) cornerRadius, (float) cornerRadius});
        } else if (cornerDirection == 8) {
            drawable.setCornerRadii(new float[]{0.0f, 0.0f, (float) cornerRadius, (float) cornerRadius, 0.0f, 0.0f, 0.0f, 0.0f});
        } else if (cornerDirection == 9) {
            drawable.setCornerRadii(new float[]{0.0f, 0.0f, 0.0f, 0.0f, (float) cornerRadius, (float) cornerRadius, 0.0f, 0.0f});
        }
        if (strokeColor != 0 && strokeRules == 0) {
            drawable.setStroke(strokeWidth, strokeColor);
        }
        return drawable;
    }

    @Deprecated
    private static Drawable getMark(int cornerDirection) {
        return getMark(CORNER_RADIUS, cornerDirection);
    }

    @Deprecated
    private static Drawable getMark(final int cornerRadius, final int cornerDirection) {
        return new ShapeDrawable(new RectShape() {
            /* class com.yoki.im.tools.BackgroundUtils.AnonymousClass1 */

            public void draw(Canvas canvas, Paint paint) {
                float width = getWidth();
                float height = getHeight();
                RectF rect = new RectF(0.0f, 0.0f, width, height);
                if (cornerDirection != 0) {
                    canvas.drawRoundRect(rect, (float) cornerRadius, (float) cornerRadius, paint);
                }
                BackgroundUtils.drawRect(canvas, paint, cornerRadius, cornerDirection, width, height);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            }
        });
    }

    /* access modifiers changed from: private */
    public static void drawRect(Canvas canvas, Paint paint, int radius, int direction, float width, float height) {
        if (direction == 2) {
            canvas.drawRect(0.0f, height - ((float) radius), width, height, paint);
        } else if (direction == 3) {
            canvas.drawRect(0.0f, 0.0f, width, (float) radius, paint);
        } else if (direction == 4) {
            canvas.drawRect(width - ((float) radius), 0.0f, width, height, paint);
        } else if (direction == 5) {
            canvas.drawRect(0.0f, 0.0f, (float) radius, height, paint);
        } else if (direction == 6) {
            canvas.drawRect(0.0f, height - ((float) radius), (float) radius, height, paint);
            drawRect(canvas, paint, radius, 4, width, height);
        } else if (direction == 7) {
            canvas.drawRect(0.0f, 0.0f, (float) radius, (float) radius, paint);
            drawRect(canvas, paint, radius, 4, width, height);
        } else if (direction == 8) {
            canvas.drawRect(width - ((float) radius), height - ((float) radius), width, height, paint);
            drawRect(canvas, paint, radius, 5, width, height);
        } else if (direction == 9) {
            canvas.drawRect(width - ((float) radius), 0.0f, width, (float) radius, paint);
            drawRect(canvas, paint, radius, 5, width, height);
        } else if (direction == 0) {
            canvas.drawRect(0.0f, 0.0f, width, height, paint);
        }
    }
}
