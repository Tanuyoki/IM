package com.yoki.im.tools.im.iminput.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import cn.jiguang.imui.messages.BaseMessageViewHolder;
import cn.jiguang.imui.messages.MessageListStyle;
import cn.jiguang.imui.messages.MsgListAdapter;
import cn.jiguang.imui.view.RoundImageView;
import com.yoki.im.tools.im.iminput.message.MessageBean;
import com.yoki.im.R;

public class TxtOrderHolder extends BaseMessageViewHolder<MessageBean> implements MsgListAdapter.DefaultMessageViewHolder {
    private RoundImageView mAvatarIv;
    private ImageView mIvImage;
    private View mRootLayout;
    private TextView mTvCounts;
    private TextView mTvMoney;
    private TextView mTvName;
    private TextView mTvNumber;

    public TxtOrderHolder(View itemView, boolean isSend) {
        super(itemView);
        this.mTvNumber = (TextView) itemView.findViewById(R.id.layout_chat_order_msg_tv_number);
        this.mTvCounts = (TextView) itemView.findViewById(R.id.layout_chat_order_msg_tv_counts);
        this.mTvName = (TextView) itemView.findViewById(R.id.layout_chat_order_msg_tv_name);
        this.mTvMoney = (TextView) itemView.findViewById(R.id.layout_chat_order_msg_tv_money);
        this.mAvatarIv = (RoundImageView) itemView.findViewById(R.id.layout_chat_order_msg_round);
        this.mRootLayout = itemView.findViewById(R.id.layout_chat_order_root);
    }

    public void onBind(final MessageBean message) {
        this.mTvNumber.setText(message.getOrderNum());
        this.mTvCounts.setText(message.getOrderCounts());
        this.mTvName.setText(message.getOrderName());
        this.mTvMoney.setText(message.getOrderMoney());
        this.mImageLoader.loadAvatarImage(this.mAvatarIv, message.getFromUser().getAvatarFilePath());
        this.mAvatarIv.setOnClickListener(new View.OnClickListener() {
            /* class com.yoki.im.tools.im.iminput.holder.TxtOrderHolder.AnonymousClass1 */

            public void onClick(View view) {
                if (TxtOrderHolder.this.mAvatarClickListener != null) {
                    TxtOrderHolder.this.mAvatarClickListener.onAvatarClick(message);
                }
            }
        });
        View.OnLongClickListener onTextLongClick = new View.OnLongClickListener() {
            /* class com.yoki.im.tools.im.iminput.holder.TxtOrderHolder.AnonymousClass2 */

            public boolean onLongClick(View v) {
                if (TxtOrderHolder.this.mMsgLongClickListener == null) {
                    return false;
                }
                TxtOrderHolder.this.mMsgLongClickListener.onMessageLongClick(v, message);
                return false;
            }
        };
        this.mTvNumber.setOnLongClickListener(onTextLongClick);
        this.mTvCounts.setOnLongClickListener(onTextLongClick);
        this.mTvName.setOnLongClickListener(onTextLongClick);
        this.mTvMoney.setOnLongClickListener(onTextLongClick);
        this.mRootLayout.setOnClickListener(new View.OnClickListener() {
            /* class com.yoki.im.tools.im.iminput.holder.TxtOrderHolder.AnonymousClass3 */

            public void onClick(View v) {
                if (TxtOrderHolder.this.mMsgClickListener != null) {
                    TxtOrderHolder.this.mMsgClickListener.onMessageClick(message);
                }
            }
        });
    }

    @Override // cn.jiguang.imui.messages.MsgListAdapter.DefaultMessageViewHolder
    public void applyStyle(MessageListStyle style) {
    }
}
