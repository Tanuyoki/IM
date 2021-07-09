package com.yoki.im.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.yoki.im.tools.Constants;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeTextView extends AppCompatTextView {
    private long Time;
    private SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
    @SuppressLint({"NewApi"})
    private Handler handler = new Handler(Looper.getMainLooper()) {
        /* class com.yoki.im.ui.TimeTextView.AnonymousClass1 */

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (!TimeTextView.this.run) {
//                        TimeTextView.this.setVisibility(8);
                        return;
                    } else if (TimeTextView.this.Time > 0) {
                        SpannableStringBuilder ssb = new SpannableStringBuilder();
                        ssb.append((CharSequence) "订单未支付,将在");
                        String showTime = TimeTextView.secToTime((int) TimeTextView.this.Time);
                        int ssbLastLength = ssb.length();
                        ForegroundColorSpan orange = new ForegroundColorSpan(Constants.COLORS.ORANGE);
                        ForegroundColorSpan black = new ForegroundColorSpan(Constants.COLORS.BLACK);
                        ssb.setSpan(black, 0, ssbLastLength, 33);
                        int ssbLastLength2 = ssb.length();
                        ssb.append((CharSequence) showTime);
                        ssb.setSpan(orange, ssbLastLength2, ssb.length(), 33);
                        int ssbLastLength3 = ssb.length();
                        ssb.append((CharSequence) "后将自动取消");
                        ssb.setSpan(black, ssbLastLength3, ssb.length(), 33);
//                        TimeTextView.this.setText(ssb);
                        TimeTextView.this.Time -= 1000;
                        TimeTextView.this.handler.sendEmptyMessageDelayed(0, 1000);
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }
    };
    private boolean run = true;

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.handler.removeMessages(0);
    }

    public TimeTextView(Context context) {
        super(context);
    }

    public TimeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @SuppressLint({"NewApi"})
    public void setTimes(long mT) {
        this.Time = mT - new Date().getTime();
        if (this.Time > 0) {
            this.handler.removeMessages(0);
            this.handler.sendEmptyMessage(0);
            return;
        }
        setVisibility(8);
    }

    public void stop() {
        this.run = false;
    }

    public static String secToTime(int time) {
        String timeStr;
        if (time <= 0) {
            return "00:00";
        }
        int minute = time / 60;
        if (minute < 60) {
            timeStr = unitFormat(minute) + ":" + unitFormat(time % 60);
        } else {
            int hour = minute / 60;
            if (hour > 99) {
                return "99:59:59";
            }
            int minute2 = minute % 60;
            timeStr = unitFormat(hour) + ":" + unitFormat(minute2) + ":" + unitFormat((time - (hour * 3600)) - (minute2 * 60));
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        if (i < 0 || i >= 10) {
            return "" + i;
        }
        return "0" + Integer.toString(i);
    }
}
