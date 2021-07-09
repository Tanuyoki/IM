package com.yoki.im.tools.im.iminput.holder;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import cn.jiguang.imui.messages.BaseMessageViewHolder;
import cn.jiguang.imui.messages.MessageListStyle;
import cn.jiguang.imui.messages.MsgListAdapter;
import cn.jiguang.imui.messages.ViewHolderController;
import cn.jiguang.imui.view.RoundImageView;
import com.yoki.im.tools.DensityUtils;
import com.yoki.im.tools.im.iminput.message.MessageBean;
import com.yoki.im.R;
import java.io.FileInputStream;
import java.io.IOException;

public class VoiceHolder extends BaseMessageViewHolder<MessageBean> implements MsgListAdapter.DefaultMessageViewHolder {
    private RoundImageView mAvatar;
    private ViewHolderController mController;
    private FileInputStream mFIS;
    private boolean mIsSend = false;
    private boolean mIsSpeakerMode = false;
    private ImageView mIvVoice;
    private View mLayout;
    private int mPlayReceiveAnim;
    private int mPlaySendAnim;
    private int mReceiveDrawable;
    private int mSendDrawable;
    private boolean mSetData = false;
    private TextView mTvDate;
    private TextView mTvText;
    private AnimationDrawable mVoiceAnimation;
    private int padding;

    public VoiceHolder(View itemView, boolean isSend) {
        super(itemView);
        this.mIsSend = isSend;
        this.mTvDate = (TextView) itemView.findViewById(R.id.layout_chat_voice_msg_tv_data);
        this.mTvText = (TextView) itemView.findViewById(R.id.layout_chat_voice_msg_tv_text);
        this.mAvatar = (RoundImageView) itemView.findViewById(R.id.layout_chat_voice_msg_round);
        this.mIvVoice = (ImageView) itemView.findViewById(R.id.layout_chat_voice_msg_voice_anim);
        this.mLayout = itemView.findViewById(R.id.layout_chat_voice_msg_layout);
        this.mController = ViewHolderController.getInstance();
        this.padding = (int) ((DensityUtils.dip2pxFloat(80.0f) - ((float) this.mTvText.getLineHeight())) / 2.0f);
    }

    public void onBind(final MessageBean message) {
        if (message.getTimeString() != null) {
            this.mTvDate.setVisibility(0);
            this.mTvDate.setText(message.getTimeString());
        }
        this.mMediaPlayer.setAudioStreamType(2);
        this.mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            /* class com.yoki.im.tools.im.iminput.holder.VoiceHolder.AnonymousClass1 */

            public boolean onError(MediaPlayer mp, int what, int extra) {
                return false;
            }
        });
        this.mImageLoader.loadAvatarImage(this.mAvatar, message.getFromUser().getAvatarFilePath());
        this.mAvatar.setOnClickListener(new View.OnClickListener() {
            /* class com.yoki.im.tools.im.iminput.holder.VoiceHolder.AnonymousClass2 */

            public void onClick(View view) {
                if (VoiceHolder.this.mAvatarClickListener != null) {
                    VoiceHolder.this.mAvatarClickListener.onAvatarClick(message);
                }
            }
        });
        long duration = message.getDuration();
        this.mTvText.setWidth((int) (((float) ((int) ((-0.04d * ((double) duration) * ((double) duration)) + (4.526d * ((double) duration)) + 75.214d))) * this.mDensity));
        this.mTvText.setText(duration + this.mContext.getString(R.string.aurora_symbol_second));
        this.mLayout.setPadding(this.padding, this.padding, this.padding, this.padding);
        this.mLayout.setOnClickListener(new View.OnClickListener() {
            /* class com.yoki.im.tools.im.iminput.holder.VoiceHolder.AnonymousClass3 */

            public void onClick(View view) {
                if (VoiceHolder.this.mMsgClickListener != null) {
                    VoiceHolder.this.mMsgClickListener.onMessageClick(message);
                }
                VoiceHolder.this.mController.notifyAnimStop();
                VoiceHolder.this.mController.setMessage(message);
                if (VoiceHolder.this.mIsSend) {
                    VoiceHolder.this.mIvVoice.setImageResource(VoiceHolder.this.mPlaySendAnim);
                } else {
                    VoiceHolder.this.mIvVoice.setImageResource(VoiceHolder.this.mPlayReceiveAnim);
                }
                VoiceHolder.this.mVoiceAnimation = (AnimationDrawable) VoiceHolder.this.mIvVoice.getDrawable();
                VoiceHolder.this.mController.addView(VoiceHolder.this.getAdapterPosition(), VoiceHolder.this.mIvVoice);
                Log.e("VoiceViewHolder", "MediaPlayer playing " + VoiceHolder.this.mMediaPlayer.isPlaying() + "now position " + VoiceHolder.this.getAdapterPosition());
                if (VoiceHolder.this.mController.getLastPlayPosition() != VoiceHolder.this.getAdapterPosition()) {
                    VoiceHolder.this.playVoice(VoiceHolder.this.getAdapterPosition(), message);
                } else if (VoiceHolder.this.mMediaPlayer.isPlaying()) {
                    VoiceHolder.this.pauseVoice();
                    VoiceHolder.this.mVoiceAnimation.stop();
                    if (VoiceHolder.this.mIsSend) {
                        VoiceHolder.this.mIvVoice.setImageResource(VoiceHolder.this.mSendDrawable);
                    } else {
                        VoiceHolder.this.mIvVoice.setImageResource(VoiceHolder.this.mReceiveDrawable);
                    }
                } else if (VoiceHolder.this.mSetData) {
                    VoiceHolder.this.mMediaPlayer.start();
                    VoiceHolder.this.mVoiceAnimation.start();
                } else {
                    VoiceHolder.this.playVoice(VoiceHolder.this.getAdapterPosition(), message);
                }
            }
        });
        this.mLayout.setOnLongClickListener(new View.OnLongClickListener() {
            /* class com.yoki.im.tools.im.iminput.holder.VoiceHolder.AnonymousClass4 */

            public boolean onLongClick(View v) {
                if (VoiceHolder.this.mMsgLongClickListener == null) {
                    return true;
                }
                VoiceHolder.this.mMsgLongClickListener.onMessageLongClick(v, message);
                return true;
            }
        });
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void pauseVoice() {
        this.mMediaPlayer.pause();
        this.mSetData = true;
    }

    public void playVoice(int position, MessageBean message) {
        this.mController.setLastPlayPosition(position, this.mIsSend);
        try {
            this.mMediaPlayer.reset();
            this.mFIS = new FileInputStream(message.getMediaFilePath());
            this.mMediaPlayer.setDataSource(this.mFIS.getFD());
            this.mMediaPlayer.setAudioStreamType(0);
            this.mMediaPlayer.prepare();
            this.mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                /* class com.yoki.im.tools.im.iminput.holder.VoiceHolder.AnonymousClass5 */

                public void onPrepared(MediaPlayer mp) {
                    VoiceHolder.this.mVoiceAnimation.start();
                    mp.start();
                }
            });
            this.mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                /* class com.yoki.im.tools.im.iminput.holder.VoiceHolder.AnonymousClass6 */

                public void onCompletion(MediaPlayer mp) {
                    VoiceHolder.this.mVoiceAnimation.stop();
                    mp.reset();
                    VoiceHolder.this.mSetData = false;
                    if (VoiceHolder.this.mIsSend) {
                        VoiceHolder.this.mIvVoice.setImageResource(VoiceHolder.this.mSendDrawable);
                    } else {
                        VoiceHolder.this.mIvVoice.setImageResource(VoiceHolder.this.mReceiveDrawable);
                    }
                }
            });
            try {
                if (this.mFIS != null) {
                    this.mFIS.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            try {
                if (this.mFIS != null) {
                    this.mFIS.close();
                }
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        } catch (Throwable th) {
            try {
                if (this.mFIS != null) {
                    this.mFIS.close();
                }
            } catch (IOException e4) {
                e4.printStackTrace();
            }
            throw th;
        }
    }

    @Override // cn.jiguang.imui.messages.MsgListAdapter.DefaultMessageViewHolder
    public void applyStyle(MessageListStyle style) {
        this.mSendDrawable = style.getSendVoiceDrawable();
        this.mReceiveDrawable = style.getReceiveVoiceDrawable();
        this.mPlaySendAnim = style.getPlaySendVoiceAnim();
        this.mPlayReceiveAnim = style.getPlayReceiveVoiceAnim();
        this.mController.setDrawable(this.mSendDrawable, this.mReceiveDrawable);
        this.mVoiceAnimation = (AnimationDrawable) this.mIvVoice.getBackground();
        if (this.mIsSend) {
            this.mLayout.setBackgroundDrawable(style.getSendBubbleDrawable());
            this.mIvVoice.setImageResource(this.mSendDrawable);
            return;
        }
        this.mLayout.setBackgroundDrawable(style.getReceiveBubbleDrawable());
        this.mIvVoice.setImageResource(this.mReceiveDrawable);
    }
}
