package com.yoki.im.tools.imageloader.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.manager.Lifecycle;
import com.bumptech.glide.manager.RequestManagerTreeNode;
import com.bumptech.glide.request.RequestOptions;
import java.io.File;

public class GlideRequests extends RequestManager {
    public GlideRequests(@NonNull Glide glide, @NonNull Lifecycle lifecycle, @NonNull RequestManagerTreeNode treeNode, @NonNull Context context) {
        super(glide, lifecycle, treeNode, context);
    }

    @Override // com.bumptech.glide.RequestManager
    @CheckResult
    @NonNull
    public <ResourceType> GlideRequest<ResourceType> as(@NonNull Class<ResourceType> resourceClass) {
        return new GlideRequest<>(this.glide, this, resourceClass, this.context);
    }

    @Override // com.bumptech.glide.RequestManager
    @NonNull
    public GlideRequests applyDefaultRequestOptions(RequestOptions requestOptions) {
        return (GlideRequests) super.applyDefaultRequestOptions(requestOptions);
    }

    @Override // com.bumptech.glide.RequestManager
    @NonNull
    public GlideRequests setDefaultRequestOptions(RequestOptions requestOptions) {
        return (GlideRequests) super.setDefaultRequestOptions(requestOptions);
    }

    @Override // com.bumptech.glide.RequestManager
    @CheckResult
    public GlideRequest<Bitmap> asBitmap() {
        return (GlideRequest) super.asBitmap();
    }

    @Override // com.bumptech.glide.RequestManager
    @CheckResult
    public GlideRequest<GifDrawable> asGif() {
        return (GlideRequest) super.asGif();
    }

    @Override // com.bumptech.glide.RequestManager
    @CheckResult
    public GlideRequest<Drawable> asDrawable() {
        return (GlideRequest) super.asDrawable();
    }

    @Override // com.bumptech.glide.RequestManager
    @CheckResult
    public GlideRequest<Drawable> load(@Nullable Object arg0) {
        return (GlideRequest) super.load(arg0);
    }

    @Override // com.bumptech.glide.RequestManager
    @CheckResult
    public GlideRequest<File> downloadOnly() {
        return (GlideRequest) super.downloadOnly();
    }

    @Override // com.bumptech.glide.RequestManager
    @CheckResult
    public GlideRequest<File> download(@Nullable Object arg0) {
        return (GlideRequest) super.download(arg0);
    }

    @Override // com.bumptech.glide.RequestManager
    @CheckResult
    public GlideRequest<File> asFile() {
        return (GlideRequest) super.asFile();
    }

    /* access modifiers changed from: protected */
    @Override // com.bumptech.glide.RequestManager
    public void setRequestOptions(@NonNull RequestOptions toSet) {
        if (toSet instanceof GlideOptions) {
            super.setRequestOptions(toSet);
        } else {
            super.setRequestOptions(new GlideOptions().apply(toSet));
        }
    }
}
