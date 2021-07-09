package com.yoki.im.tools;

import android.os.Handler;
import android.os.Looper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadManager {
    private static DownloadManager mInstance;
    private static OkHttpClient mOkHttpClient;
    private Call downCall;
    private Handler mDelivery = new Handler(Looper.getMainLooper());

    public static abstract class ResultCallback<T> {
        public abstract void onError(Request request, Exception exc);

        public abstract void onProgress(double d, double d2);

        public abstract void onProgressThread(double d, double d2);

        public abstract void onResponse(T t);
    }

    public static DownloadManager getInstance() {
        if (mInstance == null) {
            synchronized (DownloadManager.class) {
                if (mInstance == null) {
                    mInstance = new DownloadManager();
                }
            }
        }
        return mInstance;
    }

    public synchronized OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            try {
                mOkHttpClient = newOkHttpClient();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mOkHttpClient;
    }

    private OkHttpClient newOkHttpClient() throws Exception {
        return new OkHttpClient();
    }

    private void okHttpDownload(String url, final String destFileDir, final ResultCallback callback) {
        final Request request = new Request.Builder().url(url).build();
        this.downCall = getOkHttpClient().newCall(request);
        this.downCall.enqueue(new Callback() {
            /* class com.yoki.im.tools.DownloadManager.AnonymousClass1 */

            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException e) {
                DownloadManager.this.sendFailedStringCallback(request, e, callback);
            }

            /* JADX WARNING: Removed duplicated region for block: B:13:0x0059 A[SYNTHETIC, Splitter:B:13:0x0059] */
            /* JADX WARNING: Removed duplicated region for block: B:16:0x005e A[SYNTHETIC, Splitter:B:16:0x005e] */
            /* JADX WARNING: Removed duplicated region for block: B:36:0x0098 A[SYNTHETIC, Splitter:B:36:0x0098] */
            /* JADX WARNING: Removed duplicated region for block: B:39:0x009d A[SYNTHETIC, Splitter:B:39:0x009d] */
            /* JADX WARNING: Removed duplicated region for block: B:51:? A[RETURN, SYNTHETIC] */
            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) throws IOException {
                Throwable th;
                InputStream is = null;
                byte[] buf = new byte[4096];
                FileOutputStream fos = null;
                double current = 0.0d;
                try {
                    double total = (double) response.body().contentLength();
                    is = response.body().byteStream();
                    File file = new File(destFileDir);
                    FileOutputStream fos2 = new FileOutputStream(file);
                    while (true) {
                        try {
                            int len = is.read(buf);
                            if (len == -1) {
                                break;
                            }
                            current += (double) len;
                            fos2.write(buf, 0, len);
                            DownloadManager.this.sendProgressCallBack(total, current, callback);
                        } catch (IOException e) {
                            e = e;
                            fos = fos2;
                            try {
                                DownloadManager.this.sendFailedStringCallback(response.request(), e, callback);
                                if (is != null) {
                                    try {
                                        is.close();
                                    } catch (IOException e2) {
                                        e2.printStackTrace();
                                    }
                                }
                                if (fos == null) {
                                    try {
                                        fos.close();
                                        return;
                                    } catch (IOException e3) {
                                        e3.printStackTrace();
                                        return;
                                    }
                                } else {
                                    return;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                if (is != null) {
                                    try {
                                        is.close();
                                    } catch (IOException e4) {
                                        e4.printStackTrace();
                                    }
                                }
                                if (fos != null) {
                                    try {
                                        fos.close();
                                    } catch (IOException e5) {
                                        e5.printStackTrace();
                                    }
                                }
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            fos = fos2;
                            if (is != null) {
                            }
                            if (fos != null) {
                            }
                        }
                    }
                    fos2.flush();
                    DownloadManager.this.sendSuccessResultCallback(file.getAbsolutePath(), callback);
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        }
                    }
                    if (fos2 != null) {
                        try {
                            fos2.close();
                        } catch (IOException e7) {
                            e7.printStackTrace();
                        }
                    }
                } catch (IOException e8) {
                    DownloadManager.this.sendFailedStringCallback(response.request(), e8, callback);
                    if (is != null) {
                    }
                    if (fos == null) {
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void sendFailedStringCallback(final Request request, final Exception e, final ResultCallback callback) {
        this.mDelivery.post(new Runnable() {
            /* class com.yoki.im.tools.DownloadManager.AnonymousClass2 */

            public void run() {
                if (callback != null) {
                    callback.onError(request, e);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void sendSuccessResultCallback(final Object object, final ResultCallback callback) {
        this.mDelivery.post(new Runnable() {
            /* class com.yoki.im.tools.DownloadManager.AnonymousClass3 */

            public void run() {
                if (callback != null) {
                    callback.onResponse(object);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private <T> void sendProgressCallBack(final double total, final double current, final ResultCallback<T> callBack) {
        this.mDelivery.post(new Runnable() {
            /* class com.yoki.im.tools.DownloadManager.AnonymousClass4 */

            public void run() {
                if (callBack != null) {
                    callBack.onProgress(total, current);
                }
            }
        });
        if (callBack != null) {
            callBack.onProgressThread(total, current);
        }
    }

    public static void downloadFile(String url, String destDir, ResultCallback callback) {
        getInstance().okHttpDownload(url, destDir, callback);
    }

    public static void cancleDown() {
        getInstance().downCall.cancel();
    }
}
