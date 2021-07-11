package com.yoki.im.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import cn.jiguang.imui.commons.ImageLoader;
import cn.jiguang.imui.commons.models.IMessage;
import cn.jiguang.imui.messages.CustomMsgConfig;
import cn.jiguang.imui.messages.MessageList;
import cn.jiguang.imui.messages.MsgListAdapter;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.yoki.im.R;
import com.yoki.im.base.BaseActivity;
import com.yoki.im.tools.BitmapUtils;
import com.yoki.im.tools.CommonData;
import com.yoki.im.tools.EditTextUtils;
import com.yoki.im.tools.OnMenuItemClickImpl;
import com.yoki.im.tools.PopupMenuUtils;
import com.yoki.im.tools.ToastUtils;
import com.yoki.im.tools.im.iminput.ImInputView;
import com.yoki.im.tools.im.iminput.holder.PhotoHolder;
import com.yoki.im.tools.im.iminput.holder.TxtOrderHolder;
import com.yoki.im.tools.im.iminput.holder.TxtViewHolder;
import com.yoki.im.tools.im.iminput.holder.VoiceHolder;
import com.yoki.im.tools.im.iminput.message.DefaultUser;
import com.yoki.im.tools.im.iminput.message.MessageBean;
import com.yoki.im.ui.TitleBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ImAty extends BaseActivity {
    private static final int CUSTOM_VIEW_TYPE = 13;
    private static final String RECEIVE_ICON = "R.mipmap.msg_customer_service";
    private static final String RECEIVE_NAME = "";
    private static final String SEND_ICON = ("http://192.168.1.10/files/" + CommonData.UserInfo.avatar);
//    private static final String SEND_ICON = "R.mipmap.msg_customer_service";
    private static final String SEND_NAME = "";
    private final ImageLoader imageLoader = new ImageLoader() {
        /* class com.acarbang.android.activities.message.ImAty.AnonymousClass1 */
        private HashMap<String, Integer> imgCacheSize = new HashMap<>();

        @Override // cn.jiguang.imui.commons.ImageLoader
        public void loadAvatarImage(ImageView avatarImageView, String string) {
            if (string.contains("R.drawable")) {
                avatarImageView.setImageResource(Integer.valueOf(ImAty.this.getResources().getIdentifier(string.replace("R.drawable.", ""), "drawable", ImAty.this.getPackageName())).intValue());
            } else if (string.contains("R.mipmap")) {
                avatarImageView.setImageResource(Integer.valueOf(ImAty.this.getResources().getIdentifier(string.replace("R.mipmap.", ""), "mipmap", ImAty.this.getPackageName())).intValue());
            } else {
                avatarImageView.setBackgroundResource(R.mipmap.list_icon4);
//                com.yoki.im.tools.imageloader.ImageLoader.getInstance().loadImage(string, avatarImageView, R.mipmap.list_icon4);
            }
        }

        @Override // cn.jiguang.imui.commons.ImageLoader
        public void loadImage(ImageView imageView, String string) {
            int[] imageSize;
            String cacheWidthKey = string + "_width";
            String cacheHeightKey = string + "_height";
            if (this.imgCacheSize.get(cacheWidthKey) == null || this.imgCacheSize.get(cacheHeightKey) == null) {
                imageSize = BitmapUtils.getImPictureSize(string);
                this.imgCacheSize.put(cacheWidthKey, Integer.valueOf(imageSize[0]));
                this.imgCacheSize.put(cacheHeightKey, Integer.valueOf(imageSize[1]));
            } else {
                imageSize = new int[]{this.imgCacheSize.get(cacheWidthKey).intValue(), this.imgCacheSize.get(cacheHeightKey).intValue()};
            }
            com.yoki.im.tools.imageloader.ImageLoader.getInstance().loadImImage(string, imageView, imageSize[0], imageSize[1], new RequestListener() {
                /* class com.acarbang.android.activities.message.ImAty.AnonymousClass1.AnonymousClass1 */

                @Override // com.bumptech.glide.request.RequestListener
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
                    return false;
                }

                @Override // com.bumptech.glide.request.RequestListener
                public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                    if (ImAty.this.isScrollToBottom) {
                        ImAty.this.isScrollToBottom = false;
                        ImAty.this.scrollBottom();
                    }
                    return false;
                }
            });
        }

        @Override // cn.jiguang.imui.commons.ImageLoader
        public void loadVideo(ImageView imageCover, String uri) {
        }
    };
    private boolean isScrollToBottom;
    private ArrayList<String> mArrayImagePath = new ArrayList<>();
    private ImInputView mInput;
    private MsgListAdapter<MessageBean> mListAdapter;
    private MessageList mMsgList;

    /* access modifiers changed from: protected */
    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity, com.acarbang.android.base.BaseActivity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(19);
        setContentView(R.layout.aty_im1);
        ((TitleBar) findViewById(R.id.im_title)).setCenterText("Chat");
        this.mMsgList = (MessageList) findViewById(R.id.im_msg_list);
        this.mInput = (ImInputView) findViewById(R.id.im_input);
        initMessageList();
        initInputView();
        scrollBottom();
    }

    private void initMessageList() {
        MsgListAdapter.HoldersConfig holdersConfig = new MsgListAdapter.HoldersConfig();
        holdersConfig.setSenderTxtMsg(TxtViewHolder.class, R.layout.im_send_text_msg1);
        holdersConfig.setReceiverTxtMsg(TxtViewHolder.class, R.layout.im_receive_text_msg1);
        holdersConfig.setSendPhotoMsg(PhotoHolder.class, R.layout.im_send_photo_msg1);
        holdersConfig.setReceivePhotoMsg(PhotoHolder.class, R.layout.im_receive_photo_msg1);
        holdersConfig.setSenderVoiceMsg(VoiceHolder.class, R.layout.im_send_voice_msg1);
        holdersConfig.setReceiverVoiceMsg(VoiceHolder.class, R.layout.im_receive_voice_msg1);
        CustomMsgConfig customConfig = new CustomMsgConfig(13, R.layout.im_order_msg1, true, TxtOrderHolder.class);
        this.mListAdapter = new MsgListAdapter<>("0", holdersConfig, this.imageLoader);
        this.mListAdapter.addCustomMsgType(13, customConfig);
        this.mListAdapter.addToEnd(addRecordMessages());
        this.mListAdapter.setOnMsgClickListener(new MsgListAdapter.OnMsgClickListener() {
            /* class com.acarbang.android.activities.message.ImAty.AnonymousClass2 */

            @Override // cn.jiguang.imui.messages.MsgListAdapter.OnMsgClickListener
            public void onMessageClick(IMessage message) {
                if (message.getType() == IMessage.MessageType.SEND_IMAGE.ordinal() || message.getType() == IMessage.MessageType.RECEIVE_IMAGE.ordinal()) {
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("ImagePath", ImAty.this.mArrayImagePath);
                    bundle.putString("ClickImagePath", message.getMediaFilePath());
                    Intent intent = new Intent();
                    intent.setClass(ImAty.this, ShowOriginalPictureAty.class);
                    intent.putExtras(bundle);
                    ImAty.this.startActivity(intent);
                } else if (message.getType() == IMessage.MessageType.SEND_CUSTOM.ordinal() || message.getType() == IMessage.MessageType.RECEIVE_CUSTOM.ordinal()) {
//                    ImAty.this.startActivity(new Intent(ImAty.this, OrderDetailsAty.class));
                }
            }
        });
        this.mListAdapter.setMsgLongClickListener(new MsgListAdapter.OnMsgLongClickListener<MessageBean>() {
            /* class com.acarbang.android.activities.message.ImAty.AnonymousClass3 */

            /* JADX WARNING: Code restructure failed: missing block: B:9:0x003a, code lost:
                r2 = (android.media.AudioManager) r6.this$0.getSystemService("audio");
             */
            public void onMessageLongClick(final View view, MessageBean message) {
                final AudioManager audioManager = null;
                if (message.getType() == IMessage.MessageType.SEND_TEXT.ordinal() || message.getType() == IMessage.MessageType.RECEIVE_TEXT.ordinal()) {
                    PopupMenuUtils.showMenu(1, view, new OnMenuItemClickImpl() {
                        /* class com.acarbang.android.activities.message.ImAty.AnonymousClass3.AnonymousClass1 */

                        @Override // com.acarbang.android.tools.OnMenuItemClickImpl
                        public void onClick(String title, int functionCode) {
                            ClipboardManager cm;
                            if (functionCode == 1 && (cm = (ClipboardManager) ImAty.this.getSystemService("clipboard")) != null) {
                                cm.setPrimaryClip(ClipData.newPlainText(null, ((TextView) view).getText()));
                                ToastUtils.show("复制成功");
                            }
                            EditTextUtils.forceVisibleKeyboard(ImAty.this.getWindow(), ImAty.this.mInput.isKeyboardShowing());
                        }
                    });
                } else if ((message.getType() == IMessage.MessageType.SEND_VOICE.ordinal() || message.getType() == IMessage.MessageType.RECEIVE_VOICE.ordinal()) && audioManager != null) {
                    ArrayList<String> list = new ArrayList<>();
                    list.add(0, audioManager.isSpeakerphoneOn() ? "切换为听筒" : "切换为扬声器");
                    PopupMenuUtils.showMenu(list, view, new OnMenuItemClickImpl() {
                        /* class com.acarbang.android.activities.message.ImAty.AnonymousClass3.AnonymousClass2 */

                        @Override // com.acarbang.android.tools.OnMenuItemClickImpl
                        public void onClick(String title, int functionCode) {
                            audioManager.setSpeakerphoneOn(title.equals("切换为扬声器"));
                            EditTextUtils.forceVisibleKeyboard(ImAty.this.getWindow(), ImAty.this.mInput.isKeyboardShowing());
                        }
                    });
                }
            }
        });
        this.mMsgList.setAdapter((MsgListAdapter) this.mListAdapter);
        this.mMsgList.setSendBubbleDrawable(R.drawable.im_send_yellow_bubble);
        this.mMsgList.setReceiveBubbleDrawable(R.drawable.im_receiver_white_bubble);
        this.mMsgList.setOnTouchListener(new View.OnTouchListener() {
            /* class com.acarbang.android.activities.message.ImAty.AnonymousClass4 */

            public boolean onTouch(View v, MotionEvent event) {
                if ((event.getAction() != 0 && event.getAction() != 2) || !ImAty.this.mInput.isShowing()) {
                    return false;
                }
                ImAty.this.mInput.reset();
                return false;
            }
        });
    }

    private void initInputView() {
        this.mInput.init(this);
        this.mInput.bindContentView(findViewById(R.id.im_frame));
        this.mInput.addOnSoftKeyBoardVisibleListener();
        this.mInput.setOnInputClickListener(new ImInputView.OnInputChangeListener() {
            /* class com.acarbang.android.activities.message.ImAty.AnonymousClass5 */

            @Override // com.acarbang.android.tools.im.iminput.ImInputView.OnInputChangeListener
            public void onClickText(String text) {
                final MessageBean message = new MessageBean(text, IMessage.MessageType.SEND_TEXT);
                message.setUserInfo(new DefaultUser("1", "", ImAty.SEND_ICON));
                message.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
                message.setLoadState(MessageBean.STATE_LOADING);
                message.setOnClickListener(new MessageBean.OnClickListener() {
                    /* class com.acarbang.android.activities.message.ImAty.AnonymousClass5.AnonymousClass1 */

                    @Override // com.acarbang.android.tools.im.iminput.message.MessageBean.OnClickListener
                    public void onResendClick(View v, MessageBean bean) {
                        message.setLoadState(MessageBean.STATE_LOADING);
                        new Thread(new Runnable() {
                            /* class com.acarbang.android.activities.message.ImAty.AnonymousClass5.AnonymousClass1.AnonymousClass1 */

                            public void run() {
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                message.setLoadState(new Random().nextInt(2) == 0 ? MessageBean.STATE_LOAD_SUCCESS : MessageBean.STATE_LOAD_FAILED);
                                ImAty.this.runOnUiThread(new Runnable() {
                                    /* class com.acarbang.android.activities.message.ImAty.AnonymousClass5.AnonymousClass1.AnonymousClass1.AnonymousClass1 */

                                    public void run() {
                                        if (message.getLoadState() == MessageBean.STATE_LOAD_SUCCESS) {
                                            ImAty.this.mListAdapter.delete(message);
                                            message.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
                                            ImAty.this.mListAdapter.addToStart(message, true);
                                            return;
                                        }
                                        ImAty.this.mListAdapter.updateMessage(message);
                                    }
                                });
                            }
                        }).start();
                        ImAty.this.mListAdapter.updateMessage(message);
                    }
                });
                ImAty.this.mListAdapter.addToStart(message, true);
                new Thread(new Runnable() {
                    /* class com.acarbang.android.activities.message.ImAty.AnonymousClass5.AnonymousClass2 */

                    public void run() {
                        try {
                            Thread.sleep(new Random().nextInt(2) == 0 ? 3000 : 500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        message.setLoadState(new Random().nextInt(2) == 0 ? MessageBean.STATE_LOAD_SUCCESS : MessageBean.STATE_LOAD_FAILED);
                        ImAty.this.runOnUiThread(new Runnable() {
                            /* class com.acarbang.android.activities.message.ImAty.AnonymousClass5.AnonymousClass2.AnonymousClass1 */

                            public void run() {
                                ImAty.this.mListAdapter.updateMessage(message);
                            }
                        });
                    }
                }).start();
            }

            @Override // com.acarbang.android.tools.im.iminput.ImInputView.OnInputChangeListener
            public void onClickImage(String filePath) {
                ImAty.this.mInput.reset();
                ImAty.this.mArrayImagePath.add(filePath);
                MessageBean message = new MessageBean(null, IMessage.MessageType.SEND_IMAGE);
                message.setUserInfo(new DefaultUser("1", "", ImAty.SEND_ICON));
                message.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
                message.setMediaFilePath(filePath);
                ImAty.this.mListAdapter.addToStart(message, false);
                ImAty.this.isScrollToBottom = true;
            }

            @Override // com.acarbang.android.tools.im.iminput.ImInputView.OnInputChangeListener
            public void onClickVoice(String filePath, long durationTime) {
                MessageBean message = new MessageBean(null, IMessage.MessageType.SEND_VOICE);
                message.setMediaFilePath(filePath);
                message.setDuration(durationTime / 1000);
                message.setUserInfo(new DefaultUser("1", "", ImAty.SEND_ICON));
                message.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
                ImAty.this.mListAdapter.addToStart(message, true);
            }

            @Override // com.acarbang.android.tools.im.iminput.ImInputView.OnInputChangeListener
            public void onClickOrder(List list) {
                MessageBean message = new MessageBean(null, IMessage.MessageType.SEND_CUSTOM);
                message.setOrderInfo(String.valueOf(list.get(0)), String.valueOf(list.get(1)), String.valueOf(list.get(2)), String.valueOf(list.get(3)));
                message.setUserInfo(new DefaultUser("1", "", ImAty.SEND_ICON));
                message.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
                ImAty.this.mListAdapter.addToStart(message, true);
            }

            @Override // com.acarbang.android.tools.im.iminput.ImInputView.OnInputChangeListener
            public void onScrollToBottom() {
                ImAty.this.scrollBottom();
            }
        });
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void scrollBottom() {
        ((LinearLayoutManager) this.mMsgList.getLayoutManager()).scrollToPositionWithOffset(0, 0);
    }

    private List<MessageBean> addRecordMessages() {
        MessageBean message;
        List<MessageBean> list = new ArrayList<>();
        String[] messages = getResources().getStringArray(R.array.messages_array);
        for (int i = 0; i < messages.length; i++) {
            if (i % 2 == 0) {
                message = new MessageBean(messages[i], IMessage.MessageType.RECEIVE_TEXT);
                message.setUserInfo(new DefaultUser("0", "", RECEIVE_ICON));
            } else {
                message = new MessageBean(messages[i], IMessage.MessageType.SEND_TEXT);
                message.setUserInfo(new DefaultUser("1", "", SEND_ICON));
            }
            message.setLoadState(MessageBean.STATE_LOAD_SUCCESS);
            if (i == 0) {
                message.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
            }
            list.add(message);
        }
        Collections.reverse(list);
        return list;
    }
}
