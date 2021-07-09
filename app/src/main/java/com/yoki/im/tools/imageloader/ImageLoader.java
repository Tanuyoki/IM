package com.yoki.im.tools.imageloader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.yoki.im.tools.ToastUtils;
import com.yoki.im.ui.dialog.DialogLoading;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

public class ImageLoader {
    private static ImageLoader mInstance;
    private BaseImageLoaderStrategy mStrategy = new GlideImageLoaderStrategy();

    private ImageLoader() {
    }

    public static ImageLoader getInstance() {
        if (mInstance == null) {
            synchronized (ImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoader();
                    return mInstance;
                }
            }
        }
        return mInstance;
    }

    public void loadImage(Object image, ImageView imageView) {
        this.mStrategy.loadImage(image, imageView);
    }

    public void loadImage(Object image, ImageView imageView, RequestListener listener) {
        this.mStrategy.loadImage(image, imageView, listener);
    }

    public void loadImage(Object image, ImageView imageView, int placeholder) {
        this.mStrategy.loadImage(image, imageView, placeholder);
    }

    public void loadImImage(Object image, ImageView imageView, int width, int height, RequestListener listener) {
        this.mStrategy.loadImImage(image, imageView, width, height, listener);
    }

    public void loadImageCircle(Object image, ImageView imageView) {
        this.mStrategy.loadImageCircle(image, imageView);
    }

    public void loadImageCircle(Object image, ImageView imageView, RequestListener listener) {
        this.mStrategy.loadImageCircle(image, imageView, listener);
    }

    public void loadImageCircleNetwork(Object image, ImageView imageView) {
        this.mStrategy.loadImageCircle("http://192.168.1.10/files/" + image, imageView);
    }

    public void loadImageCircleNetwork(Object image, ImageView imageView, RequestListener listener) {
        this.mStrategy.loadImageCircle("http://192.168.1.10/files/" + image, imageView, listener);
    }

    public void loadResource(Context context, String url, ImageLoaderListener listener) {
        loadBackground(context, url, null, listener);
    }

    public void loadBackground(Context context, String url, ViewGroup layout) {
        loadBackground(context, url, layout, null);
    }

    private void loadBackground(Context context, String url, final ViewGroup layout, final ImageLoaderListener listener) {
        Glide.with(context).load(url).into(new SimpleTarget<Drawable>() {
            /* class com.yoki.im.tools.imageloader.ImageLoader.AnonymousClass1 */

//            @Override // com.bumptech.glide.request.target.Target
//            public /* bridge */ /* synthetic */ void onResourceReady(Object obj, Transition transition) {
//                onResourceReady((Drawable) obj, (Transition<? super Drawable>) transition);
//            }

            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                if (layout != null) {
                    layout.setBackgroundDrawable(resource);
                }
                if (listener != null) {
                    listener.onResourceReady(resource);
                }
            }

            @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                DialogLoading.getInstances().cancel();
                ToastUtils.show("加载图片失败，请重试");
            }
        });
    }
}
