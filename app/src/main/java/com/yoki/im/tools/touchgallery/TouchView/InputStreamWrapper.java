package com.yoki.im.tools.touchgallery.TouchView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamWrapper extends BufferedInputStream {
    protected long mBytesLoaded = 0;
    protected long mContentLen;
    protected InputStreamProgressListener mProgressListener;

    public interface InputStreamProgressListener {
        void onProgress(float f, long j, long j2);
    }

    public InputStreamWrapper(InputStream in, int size, long contentLen) {
        super(in, size);
        this.mContentLen = contentLen;
    }

    @Override // java.io.FilterInputStream, java.io.BufferedInputStream, java.io.InputStream
    public synchronized int read(byte[] buffer, int offset, int byteCount) throws IOException {
        this.mBytesLoaded += (long) byteCount;
        if (this.mProgressListener != null) {
            this.mProgressListener.onProgress((((float) this.mBytesLoaded) * 1.0f) / ((float) this.mContentLen), this.mBytesLoaded, this.mContentLen);
        }
        return super.read(buffer, offset, byteCount);
    }

    public void setProgressListener(InputStreamProgressListener listener) {
        this.mProgressListener = listener;
    }
}
