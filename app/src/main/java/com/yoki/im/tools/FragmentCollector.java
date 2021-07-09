package com.yoki.im.tools;

import androidx.fragment.app.Fragment;

public class FragmentCollector {
    private static Fragment mCurrFragment;

    public static void setCurrFragment(Fragment fragment) {
        mCurrFragment = fragment;
    }

    public static Fragment getCurrFragment() {
        return mCurrFragment;
    }

    public static String getRunningFragmentName(Fragment fragment) {
        String contextString = String.valueOf(fragment.getClass());
        return contextString.substring(contextString.lastIndexOf(".") + 1, contextString.length());
    }
}
