package com.yoki.im.tools;

public class DensityUtils {
    public static int dip2px(float dpValue) {
        return (int) dip2pxFloat(dpValue);
    }

    public static float dip2pxFloat(float dpValue) {
        return (dpValue * CommonData.ScreenDensity) + 0.5f;
    }

    public static int px2dip(float pxValue) {
        return (int) ((pxValue / CommonData.ScreenDensity) + 0.5f);
    }

    public static int px2sp(float pxValue) {
        return (int) ((pxValue / CommonData.ScreenScaledDensity) + 0.5f);
    }

    public static int sp2px(float spValue) {
        return (int) ((spValue * CommonData.ScreenScaledDensity) + 0.5f);
    }
}
