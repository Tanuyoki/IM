package com.yoki.im.tools.okhttp.listener;

import java.util.ArrayList;

public abstract class DisposeHandleCookieListener extends DisposeDataListener {
    public abstract void onCookie(ArrayList<String> arrayList);
}
