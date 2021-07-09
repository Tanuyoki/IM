package com.yoki.im.ui.popup;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.yoki.im.base.BasePopupWindow;
import com.yoki.im.tools.BackgroundUtils;
import com.yoki.im.R;

public class PopupUpdate extends BasePopupWindow {
    private Button mBtnCancel;
    private Button mBtnConfirm;
    private Context mContext;
    private OnPopupUpdateListener mListener;
    private View mPopupView;
    private TextView mTvUpdateContent;
    private TextView mTvUpdateTitle;

    public interface OnPopupUpdateListener {
        boolean onClickCancel();

        boolean onClickConfirm();
    }

    public PopupUpdate setOnPopupUpdateListener(OnPopupUpdateListener listener) {
        this.mListener = listener;
        return this;
    }

    public PopupUpdate(Context context) {
        this.mContext = context;
        init();
    }

    @Override // com.yoki.im.base.BasePopupWindow
    public void show() {
        showAtLocation(this.mPopupView, 17, 0, 0);
    }

    private void init() {
        setFocusable(true);
        setOutsideTouchable(false);
        setWidth(-1);
        setHeight(-1);
//        this.mPopupView = View.inflate(this.mContext, R.layout.ppw_update, null);
        this.mPopupView.setOnKeyListener(new View.OnKeyListener() {
            /* class com.yoki.im.ui.popup.PopupUpdate.AnonymousClass1 */

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode != 4 || event.getAction() != 1 || !PopupUpdate.this.isShowing()) {
                    return false;
                }
                PopupUpdate.this.dismiss();
                return true;
            }
        });
//        this.mTvUpdateTitle = (TextView) this.mPopupView.findViewById(R.id.ppw_update_tv_title);
//        this.mTvUpdateContent = (TextView) this.mPopupView.findViewById(R.id.ppw_update_tv_content);
//        this.mBtnCancel = (Button) this.mPopupView.findViewById(R.id.ppw_update_btn_cancel);
//        this.mBtnConfirm = (Button) this.mPopupView.findViewById(R.id.ppw_update_btn_update);
//        this.mTvUpdateContent.setLineSpacing(2.0f, 2.0f);
        this.mTvUpdateContent.setHeight(50);
        this.mTvUpdateContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        Drawable leftDrawable = BackgroundUtils.generateClickEffectDrawable(7);
        Drawable rightDrawable = BackgroundUtils.generateClickEffectDrawable(9);
        this.mBtnCancel.setBackgroundDrawable(leftDrawable);
        this.mBtnConfirm.setBackgroundDrawable(rightDrawable);
        this.mBtnCancel.setOnClickListener(new View.OnClickListener() {
            /* class com.yoki.im.ui.popup.PopupUpdate.AnonymousClass2 */

            public void onClick(View v) {
                if (PopupUpdate.this.mListener != null && PopupUpdate.this.mListener.onClickCancel() && PopupUpdate.this.isShowing()) {
                    PopupUpdate.this.dismiss();
                }
            }
        });
        this.mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            /* class com.yoki.im.ui.popup.PopupUpdate.AnonymousClass3 */

            public void onClick(View v) {
                if (PopupUpdate.this.mListener != null && PopupUpdate.this.mListener.onClickConfirm() && PopupUpdate.this.isShowing()) {
                    PopupUpdate.this.dismiss();
                }
            }
        });
        setContentView(this.mPopupView);
    }

    public PopupUpdate setUpdateContent(CharSequence text) {
        this.mTvUpdateContent.setText(text);
        return this;
    }
}
