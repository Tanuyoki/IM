package com.yoki.im.tools.im.message;

import android.view.View;
import cn.jiguang.imui.messages.BaseMessageViewHolder;
import cn.jiguang.imui.messages.MessageListStyle;
import cn.jiguang.imui.messages.MsgListAdapter;
import com.yoki.im.tools.im.iminput.message.MessageBean;

public class BaseMsgHolder extends BaseMessageViewHolder<MessageBean> implements MsgListAdapter.DefaultMessageViewHolder {
    private MessageBean mCurrMessageBean;
    private View mLoading;
    private MessageBean.OnClickListener mOnClickListener;
    private View mResend;

    public BaseMsgHolder(View itemView) {
        super(itemView);
    }

    public void onBind(MessageBean messageBean) {
        this.mCurrMessageBean = messageBean;
        this.mOnClickListener = messageBean.getOnClickListener();
        if (messageBean.getLoadState() == MessageBean.STATE_LOADING) {
            loading();
        } else if (messageBean.getLoadState() == MessageBean.STATE_LOAD_SUCCESS) {
            loadSuccess();
        } else if (messageBean.getLoadState() == MessageBean.STATE_LOAD_FAILED) {
            loadFailed();
        }
    }

    @Override // cn.jiguang.imui.messages.MsgListAdapter.DefaultMessageViewHolder
    public void applyStyle(MessageListStyle style) {
    }

    public void initLoadHandle(View loading, View resend) {
        this.mLoading = loading;
        this.mResend = resend;
        if (this.mLoading != null) {
            this.mLoading.setVisibility(8);
        }
        if (this.mResend != null) {
            this.mResend.setVisibility(8);
            this.mResend.setOnClickListener(new View.OnClickListener() {
                /* class com.yoki.im.tools.im.message.BaseMsgHolder.AnonymousClass1 */

                public void onClick(View v) {
                    if (BaseMsgHolder.this.mOnClickListener != null) {
                        BaseMsgHolder.this.mOnClickListener.onResendClick(v, BaseMsgHolder.this.mCurrMessageBean);
                    }
                }
            });
        }
    }

    public void loading() {
        if (this.mLoading != null) {
            this.mLoading.setVisibility(0);
        }
        if (this.mResend != null) {
            this.mResend.setVisibility(8);
        }
    }

    public void loadSuccess() {
        if (this.mLoading != null) {
            this.mLoading.setVisibility(8);
        }
        if (this.mResend != null) {
            this.mResend.setVisibility(8);
        }
    }

    public void loadFailed() {
        if (this.mLoading != null) {
            this.mLoading.setVisibility(8);
        }
        if (this.mResend != null) {
            this.mResend.setVisibility(0);
        }
    }

    public void onFailedClick() {
    }
}
