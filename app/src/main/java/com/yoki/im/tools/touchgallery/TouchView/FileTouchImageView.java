package com.yoki.im.tools.touchgallery.TouchView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import com.yoki.im.tools.BitmapUtils;
import com.yoki.im.tools.CommonData;
import com.yoki.im.tools.touchgallery.TouchView.InputStreamWrapper;
import com.yoki.im.tools.touchgallery.TouchView.UrlTouchImageView;
import java.io.File;
import java.io.FileInputStream;

public class FileTouchImageView extends UrlTouchImageView {
    public FileTouchImageView(Context ctx) {
        super(ctx);
    }

    public FileTouchImageView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
    }

    @Override // com.yoki.im.tools.touchgallery.TouchView.UrlTouchImageView
    public void setUrl(String imagePath) {
        new ImageLoadTask().execute(new String[]{imagePath});
    }

    public class ImageLoadTask extends UrlTouchImageView.ImageLoadTask {
        public ImageLoadTask() {
            super();
        }

        /* access modifiers changed from: protected */
        @Override // com.yoki.im.tools.touchgallery.TouchView.UrlTouchImageView.ImageLoadTask
        public Bitmap doInBackground(String... strings) {
            String path = strings[0];
            Bitmap bm = null;
            try {
                File file = new File(path);
                InputStreamWrapper bis = new InputStreamWrapper(new FileInputStream(file), 8192, file.length());
                bis.setProgressListener(new InputStreamWrapper.InputStreamProgressListener() {
                    /* class com.yoki.im.tools.touchgallery.TouchView.FileTouchImageView.ImageLoadTask.AnonymousClass1 */

                    @Override // com.yoki.im.tools.touchgallery.TouchView.InputStreamWrapper.InputStreamProgressListener
                    public void onProgress(float progressValue, long bytesLoaded, long bytesTotal) {
                        ImageLoadTask.this.publishProgress(new Integer[]{Integer.valueOf((int) (100.0f * progressValue))});
                    }
                });
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = true;
                opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
                BitmapFactory.decodeFile(path, opts);
                opts.inSampleSize = BitmapUtils.calculateInSampleSize(opts, CommonData.ScreenWidth, CommonData.ScreenHeight);
                opts.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeStream(bis, null, opts);
                bis.close();
                return bm;
            } catch (Exception e) {
                e.printStackTrace();
                return bm;
            }
        }
    }
}
