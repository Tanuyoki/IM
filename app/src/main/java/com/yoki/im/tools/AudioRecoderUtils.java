package com.yoki.im.tools;

import android.media.MediaRecorder;
import android.os.Handler;
import java.io.File;
import java.io.IOException;

public class AudioRecoderUtils {
    public static final int MAX_LENGTH = 600000;
    private int BASE = 1;
    private int SPACE = 100;
    private long endTime;
    private String filePath;
    private final Handler mHandler = new Handler();
    private OnAudioStatusUpdateListener mListener;
    private MediaRecorder mMediaRecorder;
    private Runnable mUpdateMicStatusTimer = new Runnable() {
        /* class com.yoki.im.tools.AudioRecoderUtils.AnonymousClass1 */

        public void run() {
            AudioRecoderUtils.this.updateMicStatus();
        }
    };
    private long startTime;

    public interface OnAudioStatusUpdateListener {
        void onStop(String str);

        void onUpdate(double d, long j);
    }

    public void startRecord() {
        if (this.mMediaRecorder == null) {
            this.mMediaRecorder = new MediaRecorder();
        }
        try {
            this.mMediaRecorder.setAudioSource(1);
            this.mMediaRecorder.setOutputFormat(0);
            this.mMediaRecorder.setAudioEncoder(1);
            this.filePath = PathUtils.getVoicePath();
            this.mMediaRecorder.setOutputFile(this.filePath);
            this.mMediaRecorder.setMaxDuration(MAX_LENGTH);
            this.mMediaRecorder.prepare();
            this.mMediaRecorder.start();
            this.startTime = System.currentTimeMillis();
            updateMicStatus();
            LogUtils.i("startTime" + this.startTime);
        } catch (IllegalStateException e) {
            LogUtils.i("call startAmr(File mRecAudioFile) failed!" + e.getMessage());
        } catch (IOException e2) {
            LogUtils.i("call startAmr(File mRecAudioFile) failed!" + e2.getMessage());
        }
    }

    public long stopRecord() {
        if (this.mMediaRecorder == null) {
            return 0;
        }
        this.endTime = System.currentTimeMillis();
        try {
            this.mMediaRecorder.stop();
            this.mMediaRecorder.reset();
            this.mMediaRecorder.release();
            this.mMediaRecorder = null;
            this.mListener.onStop(this.filePath);
            this.filePath = "";
        } catch (RuntimeException e) {
            this.mMediaRecorder.reset();
            this.mMediaRecorder.release();
            this.mMediaRecorder = null;
            File file = new File(this.filePath);
            if (file.exists()) {
                file.delete();
            }
            this.filePath = "";
        }
        return this.endTime - this.startTime;
    }

    public void cancelRecord() {
        try {
            this.mMediaRecorder.stop();
            this.mMediaRecorder.reset();
            this.mMediaRecorder.release();
            this.mMediaRecorder = null;
        } catch (RuntimeException e) {
            if (this.mMediaRecorder != null) {
                this.mMediaRecorder.reset();
                this.mMediaRecorder.release();
                this.mMediaRecorder = null;
            }
        }
        File file = new File(this.filePath);
        if (file.exists()) {
            file.delete();
        }
        this.filePath = "";
    }

    public void setOnAudioStatusUpdateListener(OnAudioStatusUpdateListener audioStatusUpdateListener) {
        this.mListener = audioStatusUpdateListener;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateMicStatus() {
        if (this.mMediaRecorder != null) {
            double ratio = ((double) this.mMediaRecorder.getMaxAmplitude()) / ((double) this.BASE);
            if (ratio > 1.0d) {
                double db = 20.0d * Math.log10(ratio);
                if (this.mListener != null) {
                    this.mListener.onUpdate(db, System.currentTimeMillis() - this.startTime);
                }
            }
            this.mHandler.postDelayed(this.mUpdateMicStatusTimer, (long) this.SPACE);
        }
    }
}
