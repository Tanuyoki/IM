package com.yoki.im.ui.dialog;

import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import com.yoki.im.R;

public class DialogChooseGetPhotoWay extends BaseDialog {
    private OnClickListener mCallback;
    private boolean mIsAutoDismiss = true;
    View.OnClickListener onClickPopupItem = new View.OnClickListener() {
        /* class com.yoki.im.ui.dialog.DialogChooseGetPhotoWay.AnonymousClass1 */

        public void onClick(View v) {
            if (DialogChooseGetPhotoWay.this.mIsAutoDismiss) {
                DialogChooseGetPhotoWay.this.dismiss();
            }
            switch (v.getId()) {
//                case R.id.ppw_camera_need_btn_album /*{ENCODED_INT: 2131297338}*/:
//                    if (DialogChooseGetPhotoWay.this.mCallback != null) {
//                        DialogChooseGetPhotoWay.this.mCallback.onSelectAlbum();
//                        return;
//                    }
//                    return;
//                case R.id.ppw_camera_need_btn_camera /*{ENCODED_INT: 2131297339}*/:
//                    if (DialogChooseGetPhotoWay.this.mCallback != null) {
//                        DialogChooseGetPhotoWay.this.mCallback.onSelectCamera();
//                        return;
//                    }
//                    return;
//                case R.id.ppw_camera_need_btn_cancel /*{ENCODED_INT: 2131297340}*/:
//                    DialogChooseGetPhotoWay.this.dismiss();
//                    return;
//                default:
//                    return;
            }
        }
    };

    public interface OnClickListener {
        void onSelectAlbum();

        void onSelectCamera();
    }

    public DialogChooseGetPhotoWay(Context context) {
        super(context);
        initView();
    }

    private void initView() {
//        View view = View.inflate(this.mContext, R.layout.ppw_camera_need, null);
//        ((Button) view.findViewById(R.id.ppw_camera_need_btn_album)).setOnClickListener(this.onClickPopupItem);
//        ((Button) view.findViewById(R.id.ppw_camera_need_btn_camera)).setOnClickListener(this.onClickPopupItem);
//        ((Button) view.findViewById(R.id.ppw_camera_need_btn_cancel)).setOnClickListener(this.onClickPopupItem);
//        setContentView(view);
//        setCanceledOnTouchOutside(true);
//        Window window = getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = -1;
//        window.setGravity(80);
//        window.setAttributes(lp);
    }

    public void setCallback(OnClickListener mCallback2) {
        this.mCallback = mCallback2;
    }

    public void setIsAutoDismiss(boolean mIsAutoDismiss2) {
        this.mIsAutoDismiss = mIsAutoDismiss2;
    }

    public void show() {
        super.show();
    }
}
