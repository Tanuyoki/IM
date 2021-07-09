package com.yoki.im.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.yoki.im.base.BaseActivity;
import com.yoki.im.tools.SystemIntentUtils;
import com.yoki.im.ui.dialog.DialogChooseGetPhotoWay;

public class GetPhotoUtils implements DialogChooseGetPhotoWay.OnClickListener {
    public static final int PHOTO_TYPE_AVATAR = 10;
    public static final int PHOTO_TYPE_LICENSE = 11;
    private BaseActivity mActivity;
    private boolean mIsFreeSize;
    private boolean mIsTailor;
    private OnPhotoListener mListener;
    private String mPhotoPath;
    private DialogChooseGetPhotoWay mPopup;

    public interface OnPhotoListener {
        void onGenerateBitmap(Bitmap bitmap);

        void onResourceReady(String str);
    }

    public GetPhotoUtils(boolean isTailor, int photoType) {
        this.mIsTailor = isTailor;
        switch (photoType) {
            case 10:
                this.mIsFreeSize = false;
                return;
            default:
                this.mIsFreeSize = true;
                return;
        }
    }

    public GetPhotoUtils setCallbackListener(OnPhotoListener mListener2) {
        this.mListener = mListener2;
        return this;
    }

    public GetPhotoUtils showGetPhotoMode(BaseActivity mActivity2) {
        this.mActivity = mActivity2;
        this.mPopup = new DialogChooseGetPhotoWay(mActivity2);
        this.mPopup.setCallback(this);
        this.mPopup.setIsAutoDismiss(false);
        this.mPopup.show();
        return this;
    }

    @Override // com.yoki.im.ui.dialog.DialogChooseGetPhotoWay.OnClickListener
    public void onSelectAlbum() {
        this.mPopup.dismiss();
        if (this.mIsTailor) {
            SystemIntentUtils.getAlbumAndCropPicture(this.mActivity, Constants.FILE_PATH_USER_AVATAR, this.mIsFreeSize, new SystemIntentUtils.onIntentResultListener() {
                /* class com.yoki.im.tools.GetPhotoUtils.AnonymousClass1 */

                @Override // com.yoki.im.tools.SystemIntentUtils.onIntentResultListener
                public void getPicture(String path) {
                    GetPhotoUtils.this.returnBitmap(path, false);
                }
            });
        } else {
            SystemIntentUtils.getAlbumPicture(this.mActivity, new SystemIntentUtils.onIntentResultListener() {
                /* class com.yoki.im.tools.GetPhotoUtils.AnonymousClass2 */

                @Override // com.yoki.im.tools.SystemIntentUtils.onIntentResultListener
                public void getPicture(String path) {
                    GetPhotoUtils.this.returnBitmap(path, true);
                }
            });
        }
    }

    @Override // com.yoki.im.ui.dialog.DialogChooseGetPhotoWay.OnClickListener
    public void onSelectCamera() {
        this.mPopup.dismiss();
        if (this.mIsTailor) {
            SystemIntentUtils.getCameraAndCropPicture(this.mActivity, Constants.FILE_PATH_USER_AVATAR, true, this.mIsFreeSize, new SystemIntentUtils.onIntentResultListener() {
                /* class com.yoki.im.tools.GetPhotoUtils.AnonymousClass3 */

                @Override // com.yoki.im.tools.SystemIntentUtils.onIntentResultListener
                public void getPicture(String path) {
                    GetPhotoUtils.this.returnBitmap(path, false);
                }
            });
        } else {
            SystemIntentUtils.getCameraPicture(this.mActivity, "", new SystemIntentUtils.onIntentResultListener() {
                /* class com.yoki.im.tools.GetPhotoUtils.AnonymousClass4 */

                @Override // com.yoki.im.tools.SystemIntentUtils.onIntentResultListener
                public void getPicture(String path) {
                    GetPhotoUtils.this.returnBitmap(path, true);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private Bitmap generateBitmap(String path) {
        return BitmapFactory.decodeFile(path);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void returnBitmap(final String path, final boolean isCompressBitmap) {
        if (this.mListener != null) {
            this.mListener.onResourceReady(path);
            new Thread(new Runnable() {
                /* class com.yoki.im.tools.GetPhotoUtils.AnonymousClass5 */

                public void run() {
                    Bitmap bitmap = GetPhotoUtils.this.generateBitmap(path);
                    if (!isCompressBitmap) {
                        GetPhotoUtils.this.runOnUiThread(bitmap);
                        return;
                    }
                    GetPhotoUtils.this.runOnUiThread(BitmapUtils.compressionBitmap(bitmap));
                }
            }).start();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void runOnUiThread(final Bitmap bitmap) {
        this.mActivity.runOnUiThread(new Runnable() {
            /* class com.yoki.im.tools.GetPhotoUtils.AnonymousClass6 */

            public void run() {
                GetPhotoUtils.this.mListener.onGenerateBitmap(bitmap);
            }
        });
    }
}
