package com.yoki.im.ui.popup;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import com.yoki.im.base.BasePopupWindow;
import com.yoki.im.R;

public class PopupDownload extends BasePopupWindow {
    private Activity mActivity;
    private boolean mCanCancel = true;
    private Context mContext;
    private boolean mIsFirst = true;
    private OnPopupDownloadListener mListener;
    private View mPopupView;
    private ProgressBar mProgress;

    public interface OnPopupDownloadListener {
        boolean onClickCancel();
    }

    public PopupDownload setOnPopupDownloadListener(OnPopupDownloadListener listener) {
        this.mListener = listener;
        return this;
    }

    public PopupDownload(Context context, Activity activity, boolean canCancel) {
        this.mContext = context;
        this.mActivity = activity;
        this.mCanCancel = canCancel;
        init();
    }

    private void init() {
        setFocusable(true);
        setOutsideTouchable(false);
        setWidth(-1);
        setHeight(-1);
//        this.mPopupView = View.inflate(this.mContext, R.layout.ppw_download, null);
//        this.mProgress = (ProgressBar) this.mPopupView.findViewById(R.id.ppw_download_progress);
//        this.mPopupView.setOnKeyListener(new View.OnKeyListener() {
//            /* class com.yoki.im.ui.popup.PopupDownload.AnonymousClass1 */
//
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (!PopupDownload.this.mCanCancel || keyCode != 4 || event.getAction() != 1 || !PopupDownload.this.isShowing()) {
//                    return false;
//                }
//                PopupDownload.this.dismiss();
//                return true;
//            }
//        });
//        setContentView(this.mPopupView);
    }

    public void setDownloadProgress(int progress) {
        this.mProgress.setProgress(progress);
    }

    @Override // com.yoki.im.base.BasePopupWindow
    public void showAnimation() {
        if (this.mIsFirst) {
            super.bindBackgroundAnimation(this.mActivity.getWindow());
            this.mIsFirst = false;
        }
        show();
    }

    @Override // com.yoki.im.base.BasePopupWindow
    public void show() {
        showAtLocation(this.mPopupView, 17, 0, 0);
    }
}
