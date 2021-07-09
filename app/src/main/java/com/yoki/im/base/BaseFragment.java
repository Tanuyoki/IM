package com.yoki.im.base;

import android.view.View;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {
    private boolean isFirstRefresh = true;

    public <T extends View> T findViewById(int resId) {
        return (T) super.getActivity().findViewById(resId);
    }

    @Override // android.support.v4.app.Fragment
    public void onHiddenChanged(boolean hidden) {
//        if (App.isLogin()) {
//            App.login();
//        } else if (!hidden) {
//            onRefresh(this.isFirstRefresh);
//            this.isFirstRefresh = false;
//        }
    }

    public void onRefresh(boolean isFirstRefresh2) {
    }
}
