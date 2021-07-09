package com.yoki.im.tools.okhttp.response;

import android.os.Handler;
import android.os.Looper;

import androidx.core.app.NotificationCompat;

import com.yoki.im.tools.okhttp.exception.OkHttpException;
import com.yoki.im.tools.okhttp.listener.DisposeDataListener;
import com.yoki.im.tools.okhttp.listener.DisposeHandleCookieListener;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Response;

public class CommonJsonCallback implements Callback {
    protected final String COOKIE_STORE;
    protected final String EMPTY_MSG;
    protected final int JSON_ERROR;
    protected final int NETWORK_ERROR;
    protected final int OTHER_ERROR;
    private Class<?> mClass;
    private Handler mDeliveryHandler;
    private DisposeDataListener mListener;

    public CommonJsonCallback(DisposeDataListener disposeDataListener) {
        this(disposeDataListener, null);
    }

    public CommonJsonCallback(DisposeDataListener disposeDataListener, Class clazz) {
        this.EMPTY_MSG = "";
        this.COOKIE_STORE = "Set-Cookie";
        this.NETWORK_ERROR = -1;
        this.JSON_ERROR = -2;
        this.OTHER_ERROR = -3;
        this.mListener = disposeDataListener;
        this.mClass = clazz;
        this.mDeliveryHandler = new Handler(Looper.getMainLooper());
    }

    @Override // okhttp3.Callback
    public void onFailure(Call call, final IOException ioexception) {
        this.mDeliveryHandler.post(new Runnable() {
            /* class com.yoki.im.tools.okhttp.response.CommonJsonCallback.AnonymousClass1 */

            public void run() {
                CommonJsonCallback.this.mListener.onFailure(new OkHttpException(-1, ioexception));
            }
        });
    }

    @Override // okhttp3.Callback
    public void onResponse(Call call, Response response) {
        try {
            final String result = response.body().string();
            final ArrayList<String> cookieLists = handleCookie(response.headers());
            this.mDeliveryHandler.post(new Runnable() {
                /* class com.yoki.im.tools.okhttp.response.CommonJsonCallback.AnonymousClass2 */

                public void run() {
                    CommonJsonCallback.this.handleResponse(result);
                    if (CommonJsonCallback.this.mListener instanceof DisposeHandleCookieListener) {
                        ((DisposeHandleCookieListener) CommonJsonCallback.this.mListener).onCookie(cookieLists);
                    }
                }
            });
        } catch (IOException e) {
            this.mDeliveryHandler.post(new Runnable() {
                /* class com.yoki.im.tools.okhttp.response.CommonJsonCallback.AnonymousClass3 */

                public void run() {
                    CommonJsonCallback.this.mListener.onFailure(new OkHttpException(-1, e));
                }
            });
        }
    }

    private ArrayList<String> handleCookie(Headers headers) {
        ArrayList<String> tempList = new ArrayList<>();
        for (int i = 0; i < headers.size(); i++) {
            if (headers.name(i).equalsIgnoreCase("Set-Cookie")) {
                tempList.add(headers.value(i));
            }
        }
        return tempList;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handleResponse(Object responseObj) {
        if (responseObj == null || responseObj.toString().trim().equals("")) {
            this.mListener.onFailure(new OkHttpException(-1, ""));
            return;
        }
        try {
            JSONObject result = JSONObject.parseObject(responseObj.toString());
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setSuccess(result.getBoolean("success").booleanValue());
            apiResponse.setCode(result.getString("code"));
            apiResponse.setMsg(result.getString(NotificationCompat.CATEGORY_MESSAGE));
            apiResponse.setData(result.getJSONObject("data"));
            apiResponse.setExtData(result.getJSONObject("extData"));
            if (apiResponse.getCode().equals("100001")) {
//                AppUtils.clearSharePreferences();
            } else if (apiResponse.getCode().equals("100002")) {
//                App.login();
            } else if (this.mClass == null) {
                this.mListener.onSuccess(apiResponse);
            } else {
                this.mListener.onSuccess(apiResponse.getData().toJavaObject(this.mClass));
            }
        } catch (Exception e) {
            this.mListener.onFailure(new OkHttpException(-3, e.getMessage()));
            e.printStackTrace();
        }
    }
}
