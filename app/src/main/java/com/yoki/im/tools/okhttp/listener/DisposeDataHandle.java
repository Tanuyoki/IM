package com.yoki.im.tools.okhttp.listener;

public class DisposeDataHandle {
    public Class<?> mClass = null;
    public DisposeDataListener mListener = null;
    public String mSource = null;

    public DisposeDataHandle(DisposeDataListener listener) {
        this.mListener = listener;
    }

    public DisposeDataHandle(DisposeDataListener listener, Class<?> clazz) {
        this.mListener = listener;
        this.mClass = clazz;
    }

    public DisposeDataHandle(DisposeDataListener listener, String source) {
        this.mListener = listener;
        this.mSource = source;
    }
}
