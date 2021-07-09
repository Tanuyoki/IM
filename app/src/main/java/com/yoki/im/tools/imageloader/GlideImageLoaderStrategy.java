package com.yoki.im.tools.imageloader;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.yoki.im.tools.BitmapUtils;
import com.yoki.im.tools.imageloader.glide.GlideApp;
import com.yoki.im.tools.imageloader.glide.GlideCircleTransform;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy {
    public SimpleTarget<Bitmap> intoImageView(final ImageView imageView) {
        return new SimpleTarget<Bitmap>() {
            /* class com.yoki.im.tools.imageloader.GlideImageLoaderStrategy.AnonymousClass1 */

//            @Override // com.bumptech.glide.request.target.Target
//            public /* bridge */ /* synthetic */ void onResourceReady(Object obj, Transition transition) {
//                onResourceReady((Bitmap) obj, (Transition<? super Bitmap>) transition);
//            }

            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                imageView.setImageBitmap(BitmapUtils.scaleImage(resource, imageView.getWidth(), imageView.getHeight(), false));
            }
        };
    }

    @Override // com.yoki.im.tools.imageloader.BaseImageLoaderStrategy
    public void loadImage(Object image, ImageView imageView) {
        loadImage(image, imageView, (RequestListener) null);
    }

    @Override // com.yoki.im.tools.imageloader.BaseImageLoaderStrategy
    public void loadImage(Object image, ImageView imageView, RequestListener listener) {
        GlideApp.with(imageView.getContext()).asBitmap().load(image).dontAnimate().listener((RequestListener<Bitmap>) listener).into(intoImageView(imageView));
    }

    @Override // com.yoki.im.tools.imageloader.BaseImageLoaderStrategy
    public void loadImage(Object image, ImageView imageView, int placeholder) {
        GlideApp.with(imageView.getContext()).load(image).placeholder(placeholder).into(imageView);
    }

    @Override // com.yoki.im.tools.imageloader.BaseImageLoaderStrategy
    public void loadImImage(Object image, ImageView imageView, int width, int height, RequestListener listener) {
        GlideApp.with(imageView.getContext()).load(image).override(width, height).listener((RequestListener<Drawable>) listener).centerCrop().into(imageView);
    }

    @Override // com.yoki.im.tools.imageloader.BaseImageLoaderStrategy
    public void loadImageCircle(Object image, ImageView imageView) {
        loadImageCircle(image, imageView, null);
    }

    @Override // com.yoki.im.tools.imageloader.BaseImageLoaderStrategy
    public void loadImageCircle(Object image, ImageView imageView, RequestListener listener) {
        GlideApp.with(imageView.getContext()).asBitmap().load(image).dontAnimate().apply(RequestOptions.bitmapTransform(new GlideCircleTransform())).listener((RequestListener<Bitmap>) listener).into(intoImageView(imageView));
    }
}
