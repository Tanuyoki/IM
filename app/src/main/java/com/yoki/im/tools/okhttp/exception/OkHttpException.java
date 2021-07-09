package com.yoki.im.tools.okhttp.exception;

public class OkHttpException extends Exception {
    private static final long serialVersionUID = 1;
    private int ecode;
    private Object emsg;

    public OkHttpException(int ecode2, Object emsg2) {
        this.ecode = ecode2;
        this.emsg = emsg2;
    }

    public int getEcode() {
        return this.ecode;
    }

    public Object getEmsg() {
        return this.emsg;
    }
}
