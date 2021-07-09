package com.yoki.im.tools;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.util.HashMap;
import java.util.Map;

public class QRCodeUtil {
    public static Bitmap generateBitmap(String content, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[(width * height)];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[(i * width) + j] = 0;
                    } else {
                        pixels[(i * width) + j] = -1;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap addLogo(Bitmap qrBitmap, Bitmap logoBitmap) {
        int qrBitmapWidth = qrBitmap.getWidth();
        int qrBitmapHeight = qrBitmap.getHeight();
        int logoBitmapWidth = logoBitmap.getWidth();
        int logoBitmapHeight = logoBitmap.getHeight();
        Bitmap blankBitmap = Bitmap.createBitmap(qrBitmapWidth, qrBitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(blankBitmap);
        canvas.drawBitmap(qrBitmap, 0.0f, 0.0f, (Paint) null);
//        canvas.save(31);
        canvas.save();
        float scaleSize = 1.0f;
        while (true) {
            if (((float) logoBitmapWidth) / scaleSize > ((float) (qrBitmapWidth / 5)) || ((float) logoBitmapHeight) / scaleSize > ((float) (qrBitmapHeight / 5))) {
                scaleSize *= 2.0f;
            } else {
                float sx = 1.0f / scaleSize;
                canvas.scale(sx, sx, (float) (qrBitmapWidth / 2), (float) (qrBitmapHeight / 2));
                canvas.drawBitmap(logoBitmap, (float) ((qrBitmapWidth - logoBitmapWidth) / 2), (float) ((qrBitmapHeight - logoBitmapHeight) / 2), (Paint) null);
                canvas.restore();
                return blankBitmap;
            }
        }
    }
}
