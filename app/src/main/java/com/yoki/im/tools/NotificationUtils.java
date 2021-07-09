package com.yoki.im.tools;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.yoki.im.R;

public class NotificationUtils {
    private static final String CHANNELID = "Channel_Id";
    public static final int NOTIFICATION_TYPE_DOWNLOAD = 1;
    private NotificationCompat.Builder mBuilder;
    private Context mContext;
    private NotificationManager mNotificationManager;
    private int mProgress;
    private int mType;

    public NotificationUtils(Context context) {
        this.mContext = context;
        init();
    }

    private void init() {
        this.mNotificationManager = (NotificationManager) this.mContext.getSystemService("notification");
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel(CHANNELID, "通知", 2);
            channel.setDescription("This is description");
            channel.enableLights(true);
            this.mNotificationManager.createNotificationChannel(channel);
            this.mBuilder = new NotificationCompat.Builder(this.mContext, CHANNELID);
            return;
        }
        this.mBuilder = new NotificationCompat.Builder(this.mContext);
    }

    private void initBuilder() {
        if (this.mType == 1) {
            this.mBuilder.setTicker("开始下载").setContentTitle("下载安装包...").setContentText("0%").setAutoCancel(false).setOngoing(true).setSmallIcon(R.mipmap.ic_launcher_round);
        }
    }

    public NotificationUtils setNoticeType(int type) {
        this.mType = type;
        initBuilder();
        return this;
    }

    public NotificationUtils setProgress(int progress) {
        this.mProgress = progress;
        this.mBuilder.setProgress(100, this.mProgress, false);
//        this.mBuilder.setContentText(this.mProgress + PercentHelper.PercentLayoutInfo.BASEMODE.PERCENT);
        this.mNotificationManager.notify(1, this.mBuilder.build());
        return this;
    }

    public NotificationUtils cancel() {
        this.mNotificationManager.cancel(1);
        return this;
    }
}
