package com.yoki.im.tools.im.iminput.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.jiguang.imui.messages.MessageListStyle;
import cn.jiguang.imui.view.RoundImageView;
import com.yoki.im.tools.im.iminput.message.MessageBean;
import com.yoki.im.tools.im.message.BaseMsgHolder;
import com.yoki.im.R;

public class PhotoHolder extends BaseMsgHolder {
    private RoundImageView mAvatar;
    private ImageView mImageView;
    private boolean mIsSend = false;
    private boolean mIsTopMsg = false;
    private RelativeLayout mLayout;
    private TextView mTvDate;

    public PhotoHolder(View itemView, boolean isSend) {
        super(itemView);
        super.initLoadHandle(itemView.findViewById(R.id.include_loading_small_pro), itemView.findViewById(R.id.layout_chat_photo_resend));
        this.mIsSend = isSend;
        this.mTvDate = (TextView) itemView.findViewById(R.id.layout_chat_photo_msg_tv_data);
        this.mAvatar = (RoundImageView) itemView.findViewById(R.id.layout_chat_photo_msg_round);
        this.mImageView = (ImageView) itemView.findViewById(R.id.layout_chat_photo_msg_image);
    }

    @Override // com.yoki.im.tools.im.message.BaseMsgHolder
    public void onBind(final MessageBean message) {
        super.onBind(message);
        if (message.getTimeString() != null) {
            this.mTvDate.setVisibility(0);
            this.mTvDate.setText(message.getTimeString());
        }
        if (this.mScroll) {
            this.mImageView.setImageResource(R.drawable.aurora_picture_not_found);
        } else {
            this.mImageLoader.loadImage(this.mImageView, message.getMediaFilePath());
        }
        this.mImageLoader.loadAvatarImage(this.mAvatar, message.getFromUser().getAvatarFilePath());
        this.mImageView.setOnClickListener(new View.OnClickListener() {
            /* class com.yoki.im.tools.im.iminput.holder.PhotoHolder.AnonymousClass1 */

            public void onClick(View view) {
                if (PhotoHolder.this.mMsgClickListener != null) {
                    PhotoHolder.this.mMsgClickListener.onMessageClick(message);
                }
            }
        });
        this.mAvatar.setOnClickListener(new View.OnClickListener() {
            /* class com.yoki.im.tools.im.iminput.holder.PhotoHolder.AnonymousClass2 */

            public void onClick(View view) {
                if (PhotoHolder.this.mAvatarClickListener != null) {
                    PhotoHolder.this.mAvatarClickListener.onAvatarClick(message);
                }
            }
        });
    }

    @Override // cn.jiguang.imui.messages.MsgListAdapter.DefaultMessageViewHolder, com.yoki.im.tools.im.message.BaseMsgHolder
    public void applyStyle(MessageListStyle style) {
        if (this.mIsSend) {
        }
    }
}
