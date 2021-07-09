package com.yoki.im.tools.im.iminput.message;

import cn.jiguang.imui.commons.models.IUser;

public class DefaultUser implements IUser {
    private String avatar;
    private String displayName;
    private String id;

    public DefaultUser(String id2, String displayName2, String avatar2) {
        this.id = id2;
        this.displayName = displayName2;
        this.avatar = avatar2;
    }

    @Override // cn.jiguang.imui.commons.models.IUser
    public String getId() {
        return this.id;
    }

    @Override // cn.jiguang.imui.commons.models.IUser
    public String getDisplayName() {
        return this.displayName;
    }

    @Override // cn.jiguang.imui.commons.models.IUser
    public String getAvatarFilePath() {
        return this.avatar;
    }
}
