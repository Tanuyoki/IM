package com.yoki.im.tools.im.iminput.holder;

import android.view.View;
import android.widget.TextView;
import cn.jiguang.imui.messages.MessageListStyle;
import cn.jiguang.imui.view.RoundImageView;
import com.yoki.im.tools.DensityUtils;
import com.yoki.im.tools.im.iminput.emoji.SpanStringUtils;
import com.yoki.im.tools.im.iminput.message.MessageBean;
import com.yoki.im.tools.im.message.BaseMsgHolder;
import com.yoki.im.R;

public class TxtViewHolder extends BaseMsgHolder {
    private RoundImageView mAvatar;
    private boolean mIsSend = false;
    private boolean mIsTopMsg = false;
    private TextView mTvDate;
    private TextView mTvText;
    private int padding;

    public TxtViewHolder(View itemView, boolean isSend) {
        super(itemView);
        super.initLoadHandle(itemView.findViewById(R.id.include_loading_small_pro), itemView.findViewById(R.id.layout_chat_text_resend));
        this.mIsSend = isSend;
        this.mTvText = (TextView) itemView.findViewById(R.id.layout_chat_text_msg_tv_text);
        this.mTvDate = (TextView) itemView.findViewById(R.id.layout_chat_text_msg_tv_data);
        this.mAvatar = (RoundImageView) itemView.findViewById(R.id.layout_chat_text_msg_round);
        this.padding = (int) ((DensityUtils.dip2pxFloat(80.0f) - ((float) this.mTvText.getLineHeight())) / 2.0f);
    }

    @Override // com.yoki.im.tools.im.message.BaseMsgHolder
    public void onBind(final MessageBean message) {
        super.onBind(message);
        if (message.getTimeString() != null && !message.getTimeString().isEmpty()) {
            this.mTvDate.setVisibility(0);
            this.mTvDate.setText(message.getTimeString());
        }
        conversionExpression(message.getText());
        this.mImageLoader.loadAvatarImage(this.mAvatar, message.getFromUser().getAvatarFilePath());
        this.mAvatar.setOnClickListener(new View.OnClickListener() {
            /* class com.yoki.im.tools.im.iminput.holder.TxtViewHolder.AnonymousClass1 */

            public void onClick(View view) {
                if (TxtViewHolder.this.mAvatarClickListener != null) {
                    TxtViewHolder.this.mAvatarClickListener.onAvatarClick(message);
                }
            }
        });
        this.mTvText.setPadding(this.padding, this.padding, this.padding, this.padding);
        View.OnLongClickListener onTextLongClick = new View.OnLongClickListener() {
            /* class com.yoki.im.tools.im.iminput.holder.TxtViewHolder.AnonymousClass2 */

            public boolean onLongClick(View v) {
                if (TxtViewHolder.this.mMsgLongClickListener == null) {
                    return false;
                }
                TxtViewHolder.this.mMsgLongClickListener.onMessageLongClick(v, message);
                return false;
            }
        };
        this.mTvText.setOnLongClickListener(onTextLongClick);
        this.mTvDate.setOnLongClickListener(onTextLongClick);
    }

    private boolean conversionExpression(String text) {
        this.mTvText.setText(SpanStringUtils.getEmotionContent(this.mContext, this.mTvText, text, 1));
        return false;
    }

    @Override // cn.jiguang.imui.messages.MsgListAdapter.DefaultMessageViewHolder, com.yoki.im.tools.im.message.BaseMsgHolder
    public void applyStyle(MessageListStyle style) {
        this.mTvText.setMaxWidth((int) (((float) style.getWindowWidth()) * style.getBubbleMaxWidth()));
        if (this.mIsSend) {
            this.mTvText.setBackgroundDrawable(style.getSendBubbleDrawable());
        } else {
            this.mTvText.setBackgroundDrawable(style.getReceiveBubbleDrawable());
        }
    }
}
