package com.yoki.im.tools;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

public class AnimUtils {
    private long duration;
    private float end;
    private EndListener endListener;
    private Interpolator interpolator;
    private float start;
    private UpdateListener updateListener;
    private ValueAnimator valueAnimator;

    public interface EndListener {
        void endUpdate(Animator animator);
    }

    public interface UpdateListener {
        void progress(float f);
    }

    public AnimUtils() {
        this.interpolator = new LinearInterpolator();
        this.duration = 1000;
        this.start = 0.0f;
        this.end = 1.0f;
        this.interpolator = new LinearInterpolator();
    }

    public void setDuration(int timeLength) {
        this.duration = (long) timeLength;
    }

    public void setValueAnimator(float start2, float end2, long duration2) {
        this.start = start2;
        this.end = end2;
        this.duration = duration2;
    }

    public void setInterpolator(Interpolator interpolator2) {
        this.interpolator = interpolator2;
    }

    public void startAnimator() {
        if (this.valueAnimator != null) {
            this.valueAnimator = null;
        }
        this.valueAnimator = ValueAnimator.ofFloat(this.start, this.end);
        this.valueAnimator.setDuration(this.duration);
        this.valueAnimator.setInterpolator(this.interpolator);
        this.valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class com.yoki.im.tools.AnimUtils.AnonymousClass1 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (AnimUtils.this.updateListener != null) {
                    AnimUtils.this.updateListener.progress(((Float) valueAnimator.getAnimatedValue()).floatValue());
                }
            }
        });
        this.valueAnimator.addListener(new Animator.AnimatorListener() {
            /* class com.yoki.im.tools.AnimUtils.AnonymousClass2 */

            public void onAnimationStart(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                if (AnimUtils.this.endListener != null) {
                    AnimUtils.this.endListener.endUpdate(animator);
                }
            }

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationRepeat(Animator animator) {
            }
        });
        this.valueAnimator.start();
    }

    public void addUpdateListener(UpdateListener updateListener2) {
        this.updateListener = updateListener2;
    }

    public void addEndListener(EndListener endListener2) {
        this.endListener = endListener2;
    }
}
