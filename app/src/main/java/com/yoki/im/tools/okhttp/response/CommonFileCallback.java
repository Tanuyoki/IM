package com.yoki.im.tools.okhttp.response;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.yoki.im.tools.okhttp.exception.OkHttpException;
import com.yoki.im.tools.okhttp.listener.DisposeDataHandle;
import com.yoki.im.tools.okhttp.listener.DisposeDownloadListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CommonFileCallback implements Callback {
    private static final int PROGRESS_MESSAGE = 1;
    protected final String EMPTY_MSG = "";
    protected final int IO_ERROR = -2;
    protected final int NETWORK_ERROR = -1;
    private Handler mDeliveryHandler;
    private String mFilePath;
    private DisposeDownloadListener mListener;
    private int mProgress;

    public CommonFileCallback(DisposeDataHandle handle) {
        this.mListener = (DisposeDownloadListener) handle.mListener;
        this.mFilePath = handle.mSource;
        this.mDeliveryHandler = new Handler(Looper.getMainLooper()) {
            /* class com.yoki.im.tools.okhttp.response.CommonFileCallback.AnonymousClass1 */

            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        CommonFileCallback.this.mListener.onProgress(((Integer) msg.obj).intValue());
                        return;
                    default:
                        return;
                }
            }
        };
    }

    @Override // okhttp3.Callback
    public void onFailure(Call call, final IOException ioexception) {
        this.mDeliveryHandler.post(new Runnable() {
            /* class com.yoki.im.tools.okhttp.response.CommonFileCallback.AnonymousClass2 */

            public void run() {
                CommonFileCallback.this.mListener.onFailure(new OkHttpException(-1, ioexception));
            }
        });
    }

    @Override // okhttp3.Callback
    public void onResponse(Call call, Response response) throws IOException {
        final File file = handleResponse(response);
        this.mDeliveryHandler.post(new Runnable() {
            /* class com.yoki.im.tools.okhttp.response.CommonFileCallback.AnonymousClass3 */

            public void run() {
                if (file != null) {
                    CommonFileCallback.this.mListener.onSuccess(file);
                } else {
                    CommonFileCallback.this.mListener.onFailure(new OkHttpException(-2, ""));
                }
            }
        });
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0066 A[SYNTHETIC, Splitter:B:16:0x0066] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x006b A[Catch:{ IOException -> 0x006f }] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x008f A[SYNTHETIC, Splitter:B:33:0x008f] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0094 A[Catch:{ IOException -> 0x0098 }] */
    /* JADX WARNING: Removed duplicated region for block: B:48:? A[RETURN, SYNTHETIC] */
    private File handleResponse(Response response) {
        Throwable th;
        FileOutputStream fos;
        if (response == null) {
            return null;
        }
        InputStream inputStream = null;
        FileOutputStream fos2 = null;
        byte[] buffer = new byte[2048];
        int currentLength = 0;
        try {
            checkLocalFilePath(this.mFilePath);
            File file = new File(this.mFilePath);
            try {
                fos = new FileOutputStream(file);
            } catch (Exception e) {
                if (fos2 != null) {
                    try {
                        fos2.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        return null;
                    }
                }
                if (0 == 0) {
                    return null;
                }
                inputStream.close();
                return null;
            } catch (Throwable th2) {
                th = th2;
                if (fos2 != null) {
                    try {
                        fos2.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                        throw th;
                    }
                }
                if (0 != 0) {
                    inputStream.close();
                }
                throw th;
            }
            try {
                InputStream inputStream2 = response.body().byteStream();
                double sumLength = (double) response.body().contentLength();
                while (true) {
                    int length = inputStream2.read(buffer);
                    if (length == -1) {
                        break;
                    }
                    fos.write(buffer, 0, length);
                    currentLength += length;
                    this.mProgress = (int) ((((double) currentLength) / sumLength) * 100.0d);
                    this.mDeliveryHandler.obtainMessage(1, Integer.valueOf(this.mProgress)).sendToTarget();
                }
                fos.flush();
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                        return file;
                    }
                }
                if (inputStream2 != null) {
                    inputStream2.close();
                }
                return file;
            } catch (Exception e5) {
                fos2 = fos;
                if (fos2 != null) {
                }
                if (0 == 0) {
                }
            } catch (Throwable th3) {
                th = th3;
                fos2 = fos;
                if (fos2 != null) {
                }
                if (0 != 0) {
                }
                throw th;
            }
        } catch (Exception e6) {
            if (fos2 != null) {
            }
            if (0 == 0) {
            }
        } catch (Throwable th4) {
            th = th4;
            if (fos2 != null) {
            }
            if (0 != 0) {
            }
        }
        return null;
    }

    private void checkLocalFilePath(String localFilePath) {
        File path = new File(localFilePath.substring(0, localFilePath.lastIndexOf("/") + 1));
        File file = new File(localFilePath);
        if (!path.exists()) {
            path.mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
