package com.yoki.im.tools.touchgallery.TouchView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.yoki.im.tools.touchgallery.TouchView.InputStreamWrapper;
import com.yoki.im.R;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class UrlTouchImageView extends RelativeLayout {
    protected Context mContext;
    protected TouchImageView mImageView;
    protected ProgressBar mProgressBar;

    public UrlTouchImageView(Context ctx) {
        super(ctx);
        this.mContext = ctx;
        init();
    }

    public UrlTouchImageView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        this.mContext = ctx;
        init();
    }

    public TouchImageView getImageView() {
        return this.mImageView;
    }

    /* access modifiers changed from: protected */
    public void init() {
        this.mImageView = new TouchImageView(this.mContext);
        this.mImageView.setLayoutParams(new LayoutParams(-1, -1));
        addView(this.mImageView);
//        this.mImageView.setVisibility(8);
        this.mProgressBar = new ProgressBar(this.mContext, null, 16842872);
        LayoutParams params = new LayoutParams(-1, -2);
        params.addRule(15);
        params.setMargins(30, 0, 30, 0);
        this.mProgressBar.setLayoutParams(params);
        this.mProgressBar.setIndeterminate(false);
        this.mProgressBar.setMax(100);
        addView(this.mProgressBar);
    }

    public void setUrl(String imageUrl) {
        new ImageLoadTask().execute(imageUrl);
    }

    public void setScaleType(ImageView.ScaleType scaleType) {
        this.mImageView.setScaleType(scaleType);
    }

    public class ImageLoadTask extends AsyncTask<String, Integer, Bitmap> {
        public ImageLoadTask() {
        }

        /* access modifiers changed from: protected */
        public Bitmap doInBackground(String... strings) {
            Bitmap bm = null;
            try {
                URLConnection conn = new URL(strings[0]).openConnection();
                conn.connect();
                InputStream is = conn.getInputStream();
                InputStreamWrapper bis = new InputStreamWrapper(is, 8192, (long) conn.getContentLength());
                bis.setProgressListener(new InputStreamWrapper.InputStreamProgressListener() {
                    /* class com.yoki.im.tools.touchgallery.TouchView.UrlTouchImageView.ImageLoadTask.AnonymousClass1 */

                    @Override // com.yoki.im.tools.touchgallery.TouchView.InputStreamWrapper.InputStreamProgressListener
                    public void onProgress(float progressValue, long bytesLoaded, long bytesTotal) {
                        ImageLoadTask.this.publishProgress(new Integer[]{Integer.valueOf((int) (100.0f * progressValue))});
                    }
                });
                bm = BitmapFactory.decodeStream(bis);
                bis.close();
                is.close();
                return bm;
            } catch (Exception e) {
                e.printStackTrace();
                return bm;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Bitmap bitmap) {
            if (bitmap == null) {
                UrlTouchImageView.this.mImageView.setScaleType(ImageView.ScaleType.CENTER);
//                UrlTouchImageView.this.mImageView.setImageBitmap(BitmapFactory.decodeResource(UrlTouchImageView.this.getResources(), R.drawable.all_close));
            } else {
                UrlTouchImageView.this.mImageView.setScaleType(ImageView.ScaleType.MATRIX);
                UrlTouchImageView.this.mImageView.setImageBitmap(bitmap);
            }
//            UrlTouchImageView.this.mImageView.setVisibility(0);
//            UrlTouchImageView.this.mProgressBar.setVisibility(8);
        }

        /* access modifiers changed from: protected */
        public void onProgressUpdate(Integer... values) {
            UrlTouchImageView.this.mProgressBar.setProgress(values[0].intValue());
        }
    }
}
