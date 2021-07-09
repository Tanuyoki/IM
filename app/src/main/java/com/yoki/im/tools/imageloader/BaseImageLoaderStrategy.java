package com.yoki.im.tools.imageloader;

import android.widget.ImageView;
import com.bumptech.glide.request.RequestListener;

public interface BaseImageLoaderStrategy {
    void loadImImage(Object obj, ImageView imageView, int i, int i2, RequestListener requestListener);

    void loadImage(Object obj, ImageView imageView);

    void loadImage(Object obj, ImageView imageView, int i);

    void loadImage(Object obj, ImageView imageView, RequestListener requestListener);

    void loadImageCircle(Object obj, ImageView imageView);

    void loadImageCircle(Object obj, ImageView imageView, RequestListener requestListener);
}
