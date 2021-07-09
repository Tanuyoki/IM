package com.yoki.im.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.yoki.im.ActivityCollector;
import com.yoki.im.tools.ScreenUtils;
import com.yoki.im.ui.dialog.DialogConfirm;

public abstract class BaseActivity extends AppCompatActivity {
    private static String TAG;
    private OnActivityResultListener mResultListener;

    public interface OnActivityResultListener {
        void onResult(int i, int i2, Intent intent);
    }

    public void startActivityForListener(Intent intent, int requestCode, OnActivityResultListener onActivityResultListener) {
        this.mResultListener = onActivityResultListener;
        startActivityForResult(intent, requestCode);
    }

    /* access modifiers changed from: protected */
    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getComponentName().getShortClassName();
        ScreenUtils.adapterScreen(this);
        ActivityCollector.addActivity(this, getClass());
    }

    /* access modifiers changed from: protected */
    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    /* access modifiers changed from: protected */
    @Override // android.support.v4.app.FragmentActivity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.mResultListener != null) {
            this.mResultListener.onResult(requestCode, resultCode, data);
        }
    }

    /* access modifiers changed from: protected */
    @Override // android.support.v4.app.FragmentActivity
    public void onResume() {
        super.onResume();
        ActivityCollector.setCurrentActivity(this);
    }

    public void onRefresh() {
    }

    public void alert(String msg, DialogConfirm.OnClickListener onClickListener) {
        alert(msg, "", "确定", onClickListener);
    }

    public void alert(String msg, String confirmBtn, DialogConfirm.OnClickListener onClickListener) {
        alert(msg, "", confirmBtn, onClickListener);
    }

    public void alert(String msg, String cancelBtn, String confirmBtn, DialogConfirm.OnClickListener onClickListener) {
        new DialogConfirm(this).setTitle(msg).setCancel(cancelBtn).setConfirm(confirmBtn).setOnClickListener(onClickListener).show();
    }
}
