package com.yoki.im.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

@Deprecated
public class CanvasUtils {
    public static Bitmap canvasChatBgTriangle(Context context, Bitmap bitmapimg, boolean isSend) {
        Bitmap output = Bitmap.createBitmap(bitmapimg.getWidth(), bitmapimg.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmapimg.getWidth(), bitmapimg.getHeight());
        int startY = (int) (((double) CommonData.ScreenWidth) * 0.03d);
        int width = (int) (((double) CommonData.ScreenWidth) * 0.025d);
        int height = (int) (((double) CommonData.ScreenWidth) * 0.04d);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-12434878);
        if (isSend) {
            canvas.drawRect(0.0f, 0.0f, (float) (bitmapimg.getWidth() - width), (float) bitmapimg.getHeight(), paint);
            Path path = new Path();
            path.moveTo((float) (bitmapimg.getWidth() - width), (float) startY);
            path.lineTo((float) bitmapimg.getWidth(), (float) (((double) startY) + (((double) height) * 0.5d)));
            path.lineTo((float) (bitmapimg.getWidth() - width), (float) (startY + height));
            path.lineTo((float) (bitmapimg.getWidth() - width), (float) startY);
            canvas.drawPath(path, paint);
        } else {
            canvas.drawRect((float) width, 0.0f, (float) bitmapimg.getWidth(), (float) bitmapimg.getHeight(), paint);
            Path path2 = new Path();
            path2.moveTo((float) width, (float) startY);
            path2.lineTo(0.0f, (float) (((double) startY) + (((double) height) * 0.5d)));
            path2.lineTo((float) width, (float) (startY + height));
            path2.lineTo((float) width, (float) startY);
            canvas.drawPath(path2, paint);
        }
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmapimg, rect, rect, paint);
        return output;
    }
}
