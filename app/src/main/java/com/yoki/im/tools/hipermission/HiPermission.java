package com.yoki.im.tools.hipermission;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.content.ContextCompat;

import com.yoki.im.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HiPermission {
    private int mAnimStyleId = -1;
    private PermissionCallback mCallback;
    private List<PermissionItem> mCheckPermissions;
    private final Context mContext;
    private int mFilterColor = 0;
    private String mMsg;
    private int[] mNormalPermissionIconRes = {R.drawable.permission_ic_storage, R.drawable.permission_ic_location, R.drawable.permission_ic_camera};
    private String[] mNormalPermissionNames;
    private String[] mNormalPermissions = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.ACCESS_FINE_LOCATION", "android.permission.CAMERA"};
    private int mPermissionType;
    private int mStyleResId = -1;
    private String mTitle;

    public static HiPermission create(Context context) {
        return new HiPermission(context);
    }

    public HiPermission(Context context) {
        this.mContext = context;
        this.mNormalPermissionNames = this.mContext.getResources().getStringArray(R.array.permissionNames);
    }

    public HiPermission title(String title) {
        this.mTitle = title;
        return this;
    }

    public HiPermission msg(String msg) {
        this.mMsg = msg;
        return this;
    }

    public HiPermission permissions(List<PermissionItem> permissionItems) {
        this.mCheckPermissions = permissionItems;
        return this;
    }

    public HiPermission filterColor(int color) {
        this.mFilterColor = color;
        return this;
    }

    public HiPermission animStyle(int styleId) {
        this.mAnimStyleId = styleId;
        return this;
    }

    public HiPermission style(int styleResIdsId) {
        this.mStyleResId = styleResIdsId;
        return this;
    }

    private List<PermissionItem> getNormalPermissions() {
        List<PermissionItem> permissionItems = new ArrayList<>();
        for (int i = 0; i < this.mNormalPermissionNames.length; i++) {
            permissionItems.add(new PermissionItem(this.mNormalPermissions[i], this.mNormalPermissionNames[i], this.mNormalPermissionIconRes[i]));
        }
        return permissionItems;
    }

    public static boolean checkPermission(Context context, String permission) {
        if (ContextCompat.checkSelfPermission(context, permission) == 0) {
            return true;
        }
        return false;
    }

    public static boolean checkPermission(Context context, String... permission) {
        int havePermissionCounts = 0;
        for (String strPermission : permission) {
            if (ContextCompat.checkSelfPermission(context, strPermission) != 0) {
                return false;
            }
            havePermissionCounts++;
        }
        if (havePermissionCounts == permission.length) {
            return true;
        }
        return false;
    }

    public void checkMutiPermission(PermissionCallback callback) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (this.mCheckPermissions == null) {
                this.mCheckPermissions = new ArrayList();
                this.mCheckPermissions.addAll(getNormalPermissions());
            }
            Iterator<PermissionItem> iterator = this.mCheckPermissions.listIterator();
            while (iterator.hasNext()) {
                if (checkPermission(this.mContext, iterator.next().Permission)) {
                    iterator.remove();
                }
            }
            this.mCallback = callback;
            if (this.mCheckPermissions.size() > 0) {
                startActivity();
            } else if (callback != null) {
                callback.onFinish();
            }
        } else if (callback != null) {
            callback.onFinish();
        }
    }

    public void checkSinglePermission(String permission, PermissionCallback callback) {
        if (Build.VERSION.SDK_INT >= 23 && !checkPermission(this.mContext, permission)) {
            this.mCallback = callback;
            this.mPermissionType = PermissionActivity.PERMISSION_TYPE_SINGLE;
            this.mCheckPermissions = new ArrayList();
            this.mCheckPermissions.add(new PermissionItem(permission));
            startActivity();
        } else if (callback != null) {
            callback.onGuarantee(permission, 0);
        }
    }

    private void startActivity() {
        PermissionActivity.setCallBack(this.mCallback);
        Intent intent = new Intent(this.mContext, PermissionActivity.class);
        intent.putExtra(ConstantValue.DATA_TITLE, this.mTitle);
        intent.putExtra(ConstantValue.DATA_PERMISSION_TYPE, this.mPermissionType);
        intent.putExtra(ConstantValue.DATA_MSG, this.mMsg);
        intent.putExtra(ConstantValue.DATA_FILTER_COLOR, this.mFilterColor);
        intent.putExtra(ConstantValue.DATA_STYLE_ID, this.mStyleResId);
        intent.putExtra(ConstantValue.DATA_ANIM_STYLE, this.mAnimStyleId);
        intent.putExtra(ConstantValue.DATA_PERMISSIONS, (Serializable) this.mCheckPermissions);
        intent.addFlags(268435456);
        this.mContext.startActivity(intent);
    }
}
