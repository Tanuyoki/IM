package com.yoki.im.tools;

import android.content.Context;

import androidx.core.content.res.ResourcesCompat;

import com.yoki.im.R;
import com.yoki.im.tools.hipermission.HiPermission;
import com.yoki.im.tools.hipermission.PermissionCallback;
import com.yoki.im.tools.hipermission.PermissionItem;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtils {

    public interface PermissionResultCallback {
        void onFinish();
    }

    public static boolean checkPermission(Context context, String permission) {
        return HiPermission.checkPermission(context, permission);
    }

    public static boolean checkPermission(Context context, String... permission) {
        return HiPermission.checkPermission(context, permission);
    }

    public static void applyPermission(Context context, String... permission) {
        applyPermission(context, null, permission);
    }

    public static void applyPermission(Context context, final PermissionResultCallback resultCallback, String... permission) {
        List<PermissionItem> permissionItems = new ArrayList<>();
        for (String strPermission : permission) {
            if (strPermission.equals("android.permission.WRITE_EXTERNAL_STORAGE")) {
                permissionItems.add(new PermissionItem(strPermission, "存储空间", R.drawable.permission_ic_storage));
            } else if (strPermission.equals("android.permission.CAMERA")) {
                permissionItems.add(new PermissionItem(strPermission, "相机", R.drawable.permission_ic_camera));
            } else if (strPermission.equals("android.permission.RECORD_AUDIO")) {
                permissionItems.add(new PermissionItem(strPermission, "麦克风", R.drawable.permission_ic_micro_phone));
            } else if (!strPermission.equals("android.settings.MANAGE_UNKNOWN_APP_SOURCES")) {
                return;
            }
        }
        HiPermission.create(context).permissions(permissionItems).msg("为了能正常使用该功能，需要以下权限")
                .filterColor(ResourcesCompat.getColor(context.getResources(), R.color.colorBlue, context.getTheme()))
                .style(R.style.Permission)
                .checkMutiPermission(new PermissionCallback() {
            /* class com.yoki.im.tools.PermissionUtils.AnonymousClass1 */

            @Override // com.library.hipermission.PermissionCallback
            public void onClose() {
            }

            @Override // com.library.hipermission.PermissionCallback
            public void onFinish() {
                if (resultCallback != null) {
                    resultCallback.onFinish();
                }
            }

            @Override // com.library.hipermission.PermissionCallback
            public void onDeny(String permission, int position) {
            }

            @Override // com.library.hipermission.PermissionCallback
            public void onGuarantee(String permission, int position) {
            }
        });
    }
}
