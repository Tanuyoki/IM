package com.yoki.im;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.yoki.im.tools.CommonData;
import com.yoki.im.tools.ToastUtils;
import com.yoki.im.ui.dialog.DialogLoading;

public class App extends Application implements Application.ActivityLifecycleCallbacks {
    private static Context context;
    private static boolean inLoginActivity = false;
    private static boolean isAppStartSuccess = false;
    private static boolean isCheckVersion = true;
    private static boolean isNeedStartLogin = false;
    private static boolean isRefreshPage = false;
    private static LocalBroadcastManager localBroadcastManager;

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        registerActivityLifecycleCallbacks(this);
        initCommon();
        initDialog();
    }

    private void initCommon() {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(metric);
            CommonData.ScreenWidth = metric.widthPixels;
            CommonData.ScreenHeight = metric.heightPixels;
            CommonData.ScreenDensity = metric.density;
            CommonData.ScreenScaledDensity = metric.scaledDensity;
            CommonData.IsAppAcarbang = getResources().getString(R.string.app_name).equals("Chat");
        }
        CommonData.applicationContext = this;
    }

    private void initDialog() {
//        startService(new Intent(this, DialogService.class));
    }

    public static Context getContext() {
        return context;
    }

    public static LocalBroadcastManager getLocalBroadcastManager() {
        return localBroadcastManager;
    }

    public static void login() {
        if (!isAppStartSuccess) {
            isNeedStartLogin = true;
        } else if (!inLoginActivity) {
            inLoginActivity = true;
            ToastUtils.show(context, "登录状态过期，请重新登录");
//            Intent intent = new Intent(context, LoginAty.class);
//            intent.setFlags(131072);
//            intent.addFlags(268435456);
//            context.startActivity(intent);
        }
    }

//    public static boolean isLogin() {
//        SharedPreferences sp;
//        return isNeedStartLogin || (sp = AppUtils.getSharedPreferences()) == null || !"1".equals(sp.getString("accountStatus", "0")) || AppUtils.getCurrentUser() == null;
//    }

    public static void leaveLogin() {
        inLoginActivity = false;
    }

    public static void setCheckVersion(boolean isCheckVersion2) {
        isCheckVersion = isCheckVersion2;
    }

    public static boolean isCheckVersion() {
        return isCheckVersion;
    }

    public static void appStartSuccess() {
        isAppStartSuccess = true;
    }

    public static void setNeedStartLogin(boolean isNeedStartLogin2) {
        isNeedStartLogin = isNeedStartLogin2;
    }

    public static boolean isNeedStartLogin() {
        return isNeedStartLogin;
    }

    public static void setRefreshPage(boolean isRefresh) {
        isRefreshPage = isRefresh;
    }

    public static boolean isRefreshPage() {
        return isRefreshPage;
    }

    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (activity.getParent() != null) {
            CommonData.NowContext = activity.getParent();
        } else {
            CommonData.NowContext = activity;
        }
    }

    public void onActivityStarted(Activity activity) {
        if (activity.getParent() != null) {
            CommonData.NowContext = activity.getParent();
        } else {
            CommonData.NowContext = activity;
        }
    }

    public void onActivityResumed(Activity activity) {
        if (activity.getParent() != null) {
            CommonData.NowContext = activity.getParent();
        } else {
            CommonData.NowContext = activity;
        }
    }

    public void onActivityPaused(Activity activity) {
        DialogLoading.getInstances().cancel();
    }

    public void onActivityStopped(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    public void onActivityDestroyed(Activity activity) {
    }
}
