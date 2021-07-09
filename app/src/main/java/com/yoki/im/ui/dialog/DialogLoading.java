package com.yoki.im.ui.dialog;

import com.yoki.im.tools.CommonDialogImpl;

public class DialogLoading {
    private static DialogLoading instances;
    private static CommonDialogImpl mListener;

    private DialogLoading() {
    }

    public void setListener(CommonDialogImpl listener) {
        mListener = listener;
    }

    public static DialogLoading getInstances() {
        if (instances == null) {
            synchronized (DialogLoading.class) {
                if (instances == null) {
                    instances = new DialogLoading();
                }
            }
        }
        return instances;
    }

    public void showLoading() {
        showMsgLoading(null);
    }

    public void showMsgLoading() {
        showMsgLoading("加载中...");
    }

    public void showMsgLoading(String msg) {
        if (mListener != null) {
            mListener.show(msg);
        }
    }

    public void cancel() {
        if (mListener != null) {
            mListener.cancel();
        }
    }
}
