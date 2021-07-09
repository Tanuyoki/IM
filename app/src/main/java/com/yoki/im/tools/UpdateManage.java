package com.yoki.im.tools;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.Window;

import androidx.core.content.FileProvider;

import com.yoki.im.tools.DownloadManager;
import com.yoki.im.ui.popup.PopupDownload;
import com.yoki.im.ui.popup.PopupUpdate;
import java.io.File;
import okhttp3.Request;

public class UpdateManage implements PopupUpdate.OnPopupUpdateListener {
    private String apkUrl;
    private Context mContext;
    private NotificationUtils mNotification;
    private PopupDownload mPopupDownload;
    private PopupUpdate mPopupUpdate;
    private int mProgress = 0;
    private final String mSaveFilePath = PathUtils.getApkPath();
    private int mServiceVersion;

    public boolean hasNewVersion(Context context, int serviceCode) {
        this.mContext = context;
        this.mServiceVersion = serviceCode;
        return this.mServiceVersion > getVersionCode();
    }

    private int getVersionCode() {
        PackageInfo packInfo = null;
        try {
            packInfo = this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packInfo != null) {
            return packInfo.versionCode;
        }
        return 0;
    }

    private int getFileVersionCode() {
        PackageInfo info = this.mContext.getPackageManager().getPackageArchiveInfo(this.mSaveFilePath, 1);
        if (info == null) {
            return 0;
        }
        return info.versionCode;
    }

    public void showUpdate(Window window, String releaseNote) {
        this.mPopupUpdate = new PopupUpdate(this.mContext);
        this.mPopupUpdate.setOnPopupUpdateListener(this);
        this.mPopupUpdate.bindBackgroundAnimation(window);
        this.mPopupUpdate.setUpdateContent(releaseNote);
        this.mPopupUpdate.showAnimation();
    }

    private void startUpdate() {
        ToastUtils.show("开始更新，下载完成后将自动安装");
        this.mNotification = new NotificationUtils(this.mContext);
        this.mNotification.setNoticeType(1);
        DownloadManager.downloadFile(this.apkUrl, this.mSaveFilePath, new DownloadCallBack());
    }

    @Override // com.yoki.im.ui.popup.PopupUpdate.OnPopupUpdateListener
    public boolean onClickCancel() {
        return true;
    }

    @Override // com.yoki.im.ui.popup.PopupUpdate.OnPopupUpdateListener
    public boolean onClickConfirm() {
        this.mPopupUpdate.dismiss();
        if (!new File(this.mSaveFilePath).exists() || this.mServiceVersion != getFileVersionCode()) {
            startUpdate();
            return false;
        }
        installApk();
        return false;
    }

    /* access modifiers changed from: package-private */
    public class DownloadCallBack extends DownloadManager.ResultCallback {
        DownloadCallBack() {
        }

        @Override // com.yoki.im.tools.DownloadManager.ResultCallback
        public void onError(Request request, Exception e) {
            UpdateManage.this.mNotification.cancel();
        }

        @Override // com.yoki.im.tools.DownloadManager.ResultCallback
        public void onResponse(Object response) {
            UpdateManage.this.mNotification.cancel();
            UpdateManage.this.installApk();
        }

        @Override // com.yoki.im.tools.DownloadManager.ResultCallback
        public void onProgress(double total, double current) {
        }

        @Override // com.yoki.im.tools.DownloadManager.ResultCallback
        public void onProgressThread(double total, double current) {
            UpdateManage.this.mProgress = (int) ((((double) ((float) current)) / total) * 100.0d);
            UpdateManage.this.mNotification.setProgress(UpdateManage.this.mProgress);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void installApk() {
        Uri uri;
        File file = new File(this.mSaveFilePath);
        if (file.exists()) {
            Intent intent = new Intent("android.intent.action.VIEW");
            if (Build.VERSION.SDK_INT >= 24) {
                uri = FileProvider.getUriForFile(this.mContext, Constants.APP_FILE_PROVIDER, file);
            } else {
                uri = Uri.parse("file://" + file.getPath());
            }
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            intent.addFlags(3);
            this.mContext.startActivity(intent);
        }
    }

    public void setApkUrl(String apkUrl2) {
        this.apkUrl = apkUrl2;
    }
}
