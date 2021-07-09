package com.yoki.im.tools.imageloader.glide;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import java.io.File;

public final class GlideApp {
    private GlideApp() {
    }

    @Nullable
    public static File getPhotoCacheDir(Context context) {
        return Glide.getPhotoCacheDir(context);
    }

    @Nullable
    public static File getPhotoCacheDir(Context context, String cacheName) {
        return Glide.getPhotoCacheDir(context, cacheName);
    }

    public static Glide get(Context context) {
        return Glide.get(context);
    }

    @VisibleForTesting
    @SuppressLint({"VisibleForTests"})
    public static void init(Glide glide) {
        Glide.init(glide);
    }

    @VisibleForTesting
    @SuppressLint({"VisibleForTests"})
    public static void tearDown() {
        Glide.tearDown();
    }

    public static GlideRequests with(Context context) {
        return (GlideRequests) Glide.with(context);
    }

    public static GlideRequests with(Activity activity) {
        return (GlideRequests) Glide.with(activity);
    }

    public static GlideRequests with(FragmentActivity activity) {
        return (GlideRequests) Glide.with(activity);
    }

    public static GlideRequests with(Fragment fragment) {
        return (GlideRequests) Glide.with(fragment);
    }

//    public static GlideRequests with(android.support.v4.app.Fragment fragment) {
//        return (GlideRequests) Glide.with(fragment);
//    }

    public static GlideRequests with(View view) {
        return (GlideRequests) Glide.with(view);
    }
}
