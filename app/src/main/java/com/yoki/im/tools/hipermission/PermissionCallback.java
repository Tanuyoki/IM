package com.yoki.im.tools.hipermission;

import java.io.Serializable;

public interface PermissionCallback extends Serializable {
    void onClose();

    void onDeny(String str, int i);

    void onFinish();

    void onGuarantee(String str, int i);
}
