package com.yoki.im.tools;

import android.app.Activity;
import android.view.OrientationEventListener;

public class OrientationUtils extends OrientationEventListener {
    private Activity mActivity;

    public OrientationUtils(Activity activity) {
        super(activity);
        this.mActivity = activity;
    }

    public void onOrientationChanged(int orientation) {
        int screenOrientation = this.mActivity.getResources().getConfiguration().orientation;
        if ((orientation < 0 || orientation >= 45) && orientation <= 315) {
            if (orientation <= 225 || orientation >= 315) {
                if (orientation <= 45 || orientation >= 135) {
                    if (orientation > 135 && orientation < 225 && screenOrientation != 9) {
                        this.mActivity.setRequestedOrientation(9);
                    }
                } else if (screenOrientation != 8) {
                    this.mActivity.setRequestedOrientation(8);
                }
            } else if (screenOrientation != 0) {
                this.mActivity.setRequestedOrientation(0);
            }
        } else if (screenOrientation != 1 && orientation != 9) {
            this.mActivity.setRequestedOrientation(1);
        }
    }
}
