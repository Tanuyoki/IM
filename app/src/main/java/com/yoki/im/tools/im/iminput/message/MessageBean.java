package com.yoki.im.tools.im.iminput.message;

import android.view.View;
import cn.jiguang.imui.commons.models.IMessage;
import cn.jiguang.imui.commons.models.IUser;
import java.util.HashMap;
import java.util.UUID;

public class MessageBean implements IMessage {
    public static int STATE_LOADING = 1;
    public static int STATE_LOAD_FAILED = 2;
    public static int STATE_LOAD_SUCCESS = 3;
    private long duration;
    private long id = UUID.randomUUID().getLeastSignificantBits();
    private int loadState;
    private String mediaFilePath;
    private OnClickListener onClickListener;
    private String orderCounts;
    private String orderMoney;
    private String orderName;
    private String orderNum;
    private String progress;
    private String text;
    private String timeString;
    private IMessage.MessageType type;
    private IUser user;

    public interface OnClickListener {
        void onResendClick(View view, MessageBean messageBean);
    }

    public MessageBean(String text2, IMessage.MessageType type2) {
        this.text = text2;
        this.type = type2;
    }

    @Override // cn.jiguang.imui.commons.models.IMessage
    public String getMsgId() {
        return String.valueOf(this.id);
    }

    @Override // cn.jiguang.imui.commons.models.IMessage
    public IUser getFromUser() {
        if (this.user == null) {
            return new DefaultUser("0", "user1", null);
        }
        return this.user;
    }

    public void setUserInfo(IUser user2) {
        this.user = user2;
    }

    public void setMediaFilePath(String path) {
        this.mediaFilePath = path;
    }

    public void setDuration(long duration2) {
        this.duration = duration2;
    }

    public void setLoadState(int loadState2) {
        this.loadState = loadState2;
    }

    public void setOnClickListener(OnClickListener onClickListener2) {
        this.onClickListener = onClickListener2;
    }

    @Override // cn.jiguang.imui.commons.models.IMessage
    public long getDuration() {
        return this.duration;
    }

    public void setProgress(String progress2) {
        this.progress = progress2;
    }

    @Override // cn.jiguang.imui.commons.models.IMessage
    public String getProgress() {
        return this.progress;
    }

    @Override // cn.jiguang.imui.commons.models.IMessage
    public HashMap<String, String> getExtras() {
        return null;
    }

    public void setTimeString(String timeString2) {
        this.timeString = timeString2;
    }

    @Override // cn.jiguang.imui.commons.models.IMessage
    public String getTimeString() {
        return this.timeString;
    }

    @Override // cn.jiguang.imui.commons.models.IMessage
    public int getType() {
        return this.type.ordinal();
    }

    @Override // cn.jiguang.imui.commons.models.IMessage
    public IMessage.MessageStatus getMessageStatus() {
        return IMessage.MessageStatus.SEND_SUCCEED;
    }

    @Override // cn.jiguang.imui.commons.models.IMessage
    public String getText() {
        return this.text;
    }

    @Override // cn.jiguang.imui.commons.models.IMessage
    public String getMediaFilePath() {
        return this.mediaFilePath;
    }

    public int getLoadState() {
        return this.loadState;
    }

    public void setOrderInfo(String number, String counts, String name, String money) {
        this.orderNum = number;
        this.orderCounts = counts;
        this.orderName = name;
        this.orderMoney = money;
    }

    public String getOrderNum() {
        return this.orderNum;
    }

    public String getOrderCounts() {
        return this.orderCounts;
    }

    public String getOrderName() {
        return this.orderName;
    }

    public String getOrderMoney() {
        return this.orderMoney;
    }

    public OnClickListener getOnClickListener() {
        return this.onClickListener;
    }
}
