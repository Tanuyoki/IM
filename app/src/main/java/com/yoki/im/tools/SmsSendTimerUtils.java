package com.yoki.im.tools;

import android.os.CountDownTimer;
import android.widget.TextView;

public class SmsSendTimerUtils {
    private static CountDownTimer countDownTimer;
    private static Long lastSendTime = null;
    private TextView mTextView;

    public SmsSendTimerUtils(TextView textView) {
        this.mTextView = textView;
        if (countDownTimer != null) {
            refreshText();
        }
    }

    public void refreshText() {
        long offset = (lastSendTime.longValue() - System.currentTimeMillis()) / 1000;
        if (offset > 0) {
            this.mTextView.setClickable(false);
            this.mTextView.setText(offset + "秒后重发");
            return;
        }
        countDownTimer = null;
        this.mTextView.setClickable(true);
        this.mTextView.setText("重新发送");
    }

    public static Long getLastSendTime() {
        return lastSendTime;
    }

    public void start() {
        this.mTextView.setClickable(false);
        if (countDownTimer == null) {
            countDownTimer = getTimer();
        }
        countDownTimer.start();
    }

    private CountDownTimer getTimer() {
        lastSendTime = Long.valueOf(System.currentTimeMillis() + 60000);
        return new CountDownTimer(lastSendTime.longValue(), 1000) {
            /* class com.yoki.im.tools.SmsSendTimerUtils.AnonymousClass1 */

            public void onTick(long millisUntilFinished) {
                SmsSendTimerUtils.this.refreshText();
            }

            public void onFinish() {
                SmsSendTimerUtils.this.refreshText();
            }
        };
    }
}
