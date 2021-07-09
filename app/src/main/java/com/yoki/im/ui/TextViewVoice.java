package com.yoki.im.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.yoki.im.tools.AudioRecoderUtils;
import com.yoki.im.tools.PermissionUtils;
import com.yoki.im.tools.VibrateUtils;
import com.yoki.im.ui.popup.PopupVoiceStateTip;

public class TextViewVoice extends AppCompatTextView {
    private Activity mActivity;
    private int mDownTime;
    private Handler mHandler;
    private boolean mIsSendVoice;
    private TextVoiceListener mListener;
    private int mMoveRange;
    private PopupVoiceStateTip mPopupVoiceStateTip;
    private int mStartX;
    private int mStartY;
    private String mTextCancel;
    private String mTextDefault;
    private String mTextUp;
    private AudioRecoderUtils mVoice;
    private long mVoiceDurationTime;

    public interface TextVoiceListener {
        void onEnd(String str, long j);

        void onStart();
    }

    public void setOnTextVoiceListener(TextVoiceListener listener) {
        this.mListener = listener;
    }

    public TextViewVoice(Context context) {
        this(context, null);
    }

    public TextViewVoice(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextViewVoice(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mDownTime = 300;
        this.mMoveRange = 300;
        this.mVoiceDurationTime = 0;
        this.mIsSendVoice = true;
        this.mTextDefault = "按住说话";
        this.mTextUp = "松开发送";
        this.mTextCancel = PopupVoiceStateTip.STATE_TIP_TEXT_RELEASE_CANCEL;
        this.mHandler = new Handler();
        this.mPopupVoiceStateTip = null;
        this.mVoice = null;
        this.mPopupVoiceStateTip = new PopupVoiceStateTip(context);
        this.mVoice = new AudioRecoderUtils();
        this.mVoice.setOnAudioStatusUpdateListener(new AudioRecoderUtils.OnAudioStatusUpdateListener() {
            /* class com.yoki.im.ui.TextViewVoice.AnonymousClass1 */

            @Override // com.yoki.im.tools.AudioRecoderUtils.OnAudioStatusUpdateListener
            public void onUpdate(double db, long time) {
                TextViewVoice.this.mVoiceDurationTime = time;
            }

            @Override // com.yoki.im.tools.AudioRecoderUtils.OnAudioStatusUpdateListener
            public void onStop(String filePath) {
                if (TextViewVoice.this.mListener != null) {
                    TextViewVoice.this.mListener.onEnd(filePath, TextViewVoice.this.mVoiceDurationTime);
                    TextViewVoice.this.mVoiceDurationTime = 0;
                }
            }
        });
    }

    public void init(Activity activity) {
        this.mActivity = activity;
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
//                if (PermissionUtils.checkPermission("android.permission.RECORD_AUDIO")) {
//                    setText(this.mTextUp);
//                    this.mIsSendVoice = true;
//                    this.mHandler.postDelayed(new Runnable() {
//                        /* class com.yoki.im.ui.TextViewVoice.AnonymousClass2 */
//
//                        public void run() {
//                            if (TextViewVoice.this.mListener != null) {
//                                TextViewVoice.this.mListener.onStart();
//                            }
//                            if (TextViewVoice.this.mActivity != null) {
//                                VibrateUtils.vibrate(TextViewVoice.this.mActivity);
//                            }
//                            if (!TextViewVoice.this.mPopupVoiceStateTip.isShowing()) {
//                                TextViewVoice.this.mPopupVoiceStateTip.show();
//                            }
//                            if (TextViewVoice.this.mVoice != null) {
//                                TextViewVoice.this.mVoice.startRecord();
//                            }
//                        }
//                    }, (long) this.mDownTime);
//                    this.mStartX = (int) event.getX();
//                    this.mStartY = (int) event.getY();
//                    break;
//                } else {
//                    PermissionUtils.applyPermission("android.permission.RECORD_AUDIO");
//                    break;
//                }
            case 1:
                setText(this.mTextDefault);
                if (this.mVoice != null) {
                    if (this.mIsSendVoice) {
                        this.mVoice.stopRecord();
                    } else {
                        this.mVoice.cancelRecord();
                    }
                }
                dismissPopup();
                this.mHandler.removeCallbacksAndMessages(null);
                break;
            case 2:
                int x = (int) event.getX();
                if (Math.abs(((int) event.getY()) - this.mStartY) <= this.mMoveRange) {
                    setText(this.mTextUp);
                    if (!this.mIsSendVoice) {
                        this.mPopupVoiceStateTip.setText(PopupVoiceStateTip.STATE_TIP_TEXT_RELEASE_SEND);
                    }
                    this.mIsSendVoice = true;
                    break;
                } else {
                    setText(this.mTextCancel);
                    this.mPopupVoiceStateTip.setText(PopupVoiceStateTip.STATE_TIP_TEXT_RELEASE_CANCEL);
                    this.mIsSendVoice = false;
                    break;
                }
        }
        return true;
    }

    private void dismissPopup() {
        if (this.mPopupVoiceStateTip.isShowing()) {
            this.mPopupVoiceStateTip.dismiss();
        }
    }
}
