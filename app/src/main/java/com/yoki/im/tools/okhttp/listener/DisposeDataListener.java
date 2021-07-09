package com.yoki.im.tools.okhttp.listener;

import com.yoki.im.tools.LogUtils;
import com.yoki.im.tools.ToastUtils;
import com.yoki.im.ui.dialog.DialogLoading;

public abstract class DisposeDataListener {
    public abstract void onSuccess(Object obj);

    public void onFailure(Object response) {
        DialogLoading.getInstances().cancel();
//        ToastUtils.show("网络错误，请重试");
        LogUtils.w(String.valueOf(response));
    }
}
