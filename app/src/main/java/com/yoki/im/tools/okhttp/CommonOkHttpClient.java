package com.yoki.im.tools.okhttp;

import com.yoki.im.tools.okhttp.cookie.SimpleCookieJar;
import com.yoki.im.tools.okhttp.https.HttpsUtils;
import com.yoki.im.tools.okhttp.listener.DisposeDataHandle;
import com.yoki.im.tools.okhttp.listener.DisposeDataListener;
import com.yoki.im.tools.okhttp.response.CommonFileCallback;
import com.yoki.im.tools.okhttp.response.CommonJsonCallback;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CommonOkHttpClient {
    private static final int TIME_OUT = 30;
    private static OkHttpClient mOkHttpClient;

    static {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
            /* class com.yoki.im.tools.okhttp.CommonOkHttpClient.AnonymousClass1 */

            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        okHttpClientBuilder.addInterceptor(new Interceptor() {
            /* class com.yoki.im.tools.okhttp.CommonOkHttpClient.AnonymousClass2 */

            @Override // okhttp3.Interceptor
            public Response intercept(Interceptor.Chain chain) throws IOException {
//                return chain.proceed(chain.request().newBuilder().addHeader("User-Agent", "ACK-ANDROID").addHeader("authorization", AppUtils.getSharedPreferences().getString("token", "")).build());
                return chain.proceed(chain.request().newBuilder().addHeader("User-Agent", "ACK-ANDROID").build());
            }
        });
        okHttpClientBuilder.cookieJar(new SimpleCookieJar());
        okHttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(30, TimeUnit.SECONDS);
        okHttpClientBuilder.writeTimeout(30, TimeUnit.SECONDS);
        okHttpClientBuilder.followRedirects(true);
        okHttpClientBuilder.sslSocketFactory(HttpsUtils.initSSLSocketFactory(), HttpsUtils.initTrustManager());
        mOkHttpClient = okHttpClientBuilder.build();
    }

    public static OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public static Call get(Request request, DisposeDataListener disposeDataListener) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(disposeDataListener));
        return call;
    }

    public static Call post(Request request, DisposeDataListener disposeDataListener) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(disposeDataListener));
        return call;
    }

    public static Call downloadFile(Request request, DisposeDataHandle handle) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonFileCallback(handle));
        return call;
    }
}
