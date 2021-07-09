package com.yoki.im.ui.popup;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.yoki.im.base.BasePopupWindow;
import com.yoki.im.R;

public class PopupVoiceStateTip extends BasePopupWindow {
    public static final String STATE_TIP_TEXT_RELEASE_CANCEL = "松开取消";
    public static final String STATE_TIP_TEXT_RELEASE_SEND = "松开发送\n上滑取消";
    private Context mContext;
    private View mPopupView;
    private TextView mTvTip;

    public PopupVoiceStateTip(Context context) {
        this.mContext = context;
        init();
    }

    private void init() {
        this.mPopupView = View.inflate(this.mContext, R.layout.ppw_state_tip, null);
        this.mTvTip = (TextView) this.mPopupView.findViewById(R.id.ppw_state_tip_tv_tip);
        this.mTvTip.setText(STATE_TIP_TEXT_RELEASE_SEND);
        setFocusable(false);
        setOutsideTouchable(false);
        setBackgroundDrawable(new BitmapDrawable());
        setWidth(-2);
        setHeight(-2);
        setContentView(this.mPopupView);
    }

    public void setText(String text) {
        this.mTvTip.setText(text);
    }

    @Override // com.yoki.im.base.BasePopupWindow
    public void show() {
        this.mTvTip.setText(STATE_TIP_TEXT_RELEASE_SEND);
        showAtLocation(this.mPopupView, 17, 0, 0);
    }

    @Override // com.yoki.im.base.BasePopupWindow
    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        super.setOnDismissListener(onDismissListener);
    }
}
