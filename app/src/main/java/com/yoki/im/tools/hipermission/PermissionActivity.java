package com.yoki.im.tools.hipermission;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.yoki.im.R;

import java.util.List;
import java.util.ListIterator;

public class PermissionActivity extends AppCompatActivity {
    public static int PERMISSION_TYPE_MUTI = 2;
    public static int PERMISSION_TYPE_SINGLE = 1;
    private static final int REQUEST_CODE_MUTI = 2;
    public static final int REQUEST_CODE_MUTI_SINGLE = 3;
    private static final int REQUEST_CODE_SINGLE = 1;
    private static final int REQUEST_SETTING = 110;
    private static final String TAG = "PermissionActivity";
    private static PermissionCallback mCallback;
    private int mAnimStyleId;
    private CharSequence mAppName;
    private List<PermissionItem> mCheckPermissions;
    private Dialog mDialog;
    private int mFilterColor;
    private String mMsg;
    private int mPermissionType;
    private int mRePermissionIndex;
    private int mStyleId;
    private String mTitle;

    public static void setCallBack(PermissionCallback callBack) {
        mCallback = callBack;
    }

    /* access modifiers changed from: protected */
    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onDestroy() {
        super.onDestroy();
        mCallback = null;
        if (this.mDialog != null && this.mDialog.isShowing()) {
            this.mDialog.dismiss();
        }
    }

    /* access modifiers changed from: protected */
    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDatas();
        if (this.mPermissionType != PERMISSION_TYPE_SINGLE) {
            this.mAppName = getApplicationInfo().loadLabel(getPackageManager());
            showPermissionDialog();
        } else if (this.mCheckPermissions != null && this.mCheckPermissions.size() != 0) {
            requestPermission(new String[]{this.mCheckPermissions.get(0).Permission}, 1);
        }
    }

    private String getPermissionTitle() {
        if (!TextUtils.isEmpty(this.mTitle)) {
            return this.mTitle;
        }
        return String.format(getString(R.string.permission_dialog_title), this.mAppName);
    }

    private void showPermissionDialog() {
        String msg;
        int i = 3;
        String title = getPermissionTitle();
        if (TextUtils.isEmpty(this.mMsg)) {
            msg = String.format(getString(R.string.permission_dialog_msg), this.mAppName);
        } else {
            msg = this.mMsg;
        }
        PermissionView contentView = new PermissionView(this);
        if (this.mCheckPermissions.size() < 3) {
            i = this.mCheckPermissions.size();
        }
        contentView.setGridViewColum(i);
        contentView.setTitle(title);
        contentView.setMsg(msg);
        contentView.setGridViewAdapter(new PermissionAdapter(this.mCheckPermissions));
        if (this.mStyleId == -1) {
            this.mStyleId = R.style.PermissionDefaultNormalStyle;
            this.mFilterColor = getResources().getColor(R.color.permissionColorGreen);
        }
        contentView.setStyleId(this.mStyleId);
        contentView.setFilterColor(this.mFilterColor);
        contentView.setBtnOnClickListener(new View.OnClickListener() {
            /* class com.library.hipermission.PermissionActivity.AnonymousClass1 */

            public void onClick(View v) {
                if (PermissionActivity.this.mDialog != null && PermissionActivity.this.mDialog.isShowing()) {
                    PermissionActivity.this.mDialog.dismiss();
                }
                ActivityCompat.requestPermissions(PermissionActivity.this, PermissionActivity.this.getPermissionStrArray(), 2);
            }
        });
        this.mDialog = new Dialog(this);
        this.mDialog.requestWindowFeature(1);
        this.mDialog.setContentView(contentView);
        if (this.mAnimStyleId != -1) {
            this.mDialog.getWindow().setWindowAnimations(this.mAnimStyleId);
        }
        this.mDialog.setCanceledOnTouchOutside(false);
        this.mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            /* class com.library.hipermission.PermissionActivity.AnonymousClass2 */

            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                if (PermissionActivity.mCallback != null) {
                    PermissionActivity.mCallback.onClose();
                }
                PermissionActivity.this.finish();
            }
        });
        this.mDialog.show();
    }

    private void reRequestPermission(final String permission) {
        String permissionName = getPermissionItem(permission).PermissionName;
        showAlertDialog(String.format(getString(R.string.permission_title), permissionName), String.format(getString(R.string.permission_denied), permissionName, this.mAppName), getString(R.string.permission_cancel), getString(R.string.permission_ensure), new DialogInterface.OnClickListener() {
            /* class com.library.hipermission.PermissionActivity.AnonymousClass3 */

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                PermissionActivity.this.requestPermission(new String[]{permission}, 3);
            }
        });
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void requestPermission(String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(this, permissions, requestCode);
    }

    private void showAlertDialog(String title, String msg, String cancelTxt, String PosTxt, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(this).setTitle(title).setMessage(msg).setCancelable(false).setNegativeButton(cancelTxt, new DialogInterface.OnClickListener() {
            /* class com.library.hipermission.PermissionActivity.AnonymousClass4 */

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                PermissionActivity.this.onClose();
            }
        }).setPositiveButton(PosTxt, onClickListener).create().show();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private String[] getPermissionStrArray() {
        String[] str = new String[this.mCheckPermissions.size()];
        for (int i = 0; i < this.mCheckPermissions.size(); i++) {
            str[i] = this.mCheckPermissions.get(i).Permission;
        }
        return str;
    }

    private void getDatas() {
        Intent intent = getIntent();
        this.mPermissionType = intent.getIntExtra(ConstantValue.DATA_PERMISSION_TYPE, PERMISSION_TYPE_SINGLE);
        this.mTitle = intent.getStringExtra(ConstantValue.DATA_TITLE);
        this.mMsg = intent.getStringExtra(ConstantValue.DATA_MSG);
        this.mFilterColor = intent.getIntExtra(ConstantValue.DATA_FILTER_COLOR, 0);
        this.mStyleId = intent.getIntExtra(ConstantValue.DATA_STYLE_ID, -1);
        this.mAnimStyleId = intent.getIntExtra(ConstantValue.DATA_ANIM_STYLE, -1);
        this.mCheckPermissions = (List) intent.getSerializableExtra(ConstantValue.DATA_PERMISSIONS);
    }

    @Override // android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback, android.support.v4.app.FragmentActivity
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                String permission = getPermissionItem(permissions[0]).Permission;
                if (grantResults[0] == 0) {
                    onGuarantee(permission, 0);
                } else {
                    onDeny(permission, 0);
                }
                finish();
                return;
            case 2:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == 0) {
                        this.mCheckPermissions.remove(getPermissionItem(permissions[i]));
                        onGuarantee(permissions[i], i);
                    } else {
                        onDeny(permissions[i], i);
                    }
                }
                if (this.mCheckPermissions.size() > 0) {
                    reRequestPermission(this.mCheckPermissions.get(this.mRePermissionIndex).Permission);
                    return;
                } else {
                    onFinish();
                    return;
                }
            case 3:
                if (grantResults[0] == -1) {
                    try {
                        String name = getPermissionItem(permissions[0]).PermissionName;
                        showAlertDialog(String.format(getString(R.string.permission_title), name), String.format(getString(R.string.permission_denied_with_naac), this.mAppName, name, this.mAppName), getString(R.string.permission_reject), getString(R.string.permission_go_to_setting), new DialogInterface.OnClickListener() {
                            /* class com.library.hipermission.PermissionActivity.AnonymousClass5 */

                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    PermissionActivity.this.startActivityForResult(new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.parse("package:" + PermissionActivity.this.getPackageName())), 110);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    PermissionActivity.this.onClose();
                                }
                            }
                        });
                        onDeny(permissions[0], 0);
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        onClose();
                        return;
                    }
                } else {
                    onGuarantee(permissions[0], 0);
                    if (this.mRePermissionIndex < this.mCheckPermissions.size() - 1) {
                        List<PermissionItem> list = this.mCheckPermissions;
                        int i2 = this.mRePermissionIndex + 1;
                        this.mRePermissionIndex = i2;
                        reRequestPermission(list.get(i2).Permission);
                        return;
                    }
                    onFinish();
                    return;
                }
            default:
                return;
        }
    }

    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onBackPressed() {
        finish();
    }

    /* access modifiers changed from: protected */
    @Override // android.support.v4.app.FragmentActivity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult--requestCode:" + requestCode + ",resultCode:" + resultCode);
        if (requestCode == 110) {
            if (this.mDialog != null && this.mDialog.isShowing()) {
                this.mDialog.dismiss();
            }
            checkPermission();
            if (this.mCheckPermissions.size() > 0) {
                this.mRePermissionIndex = 0;
                reRequestPermission(this.mCheckPermissions.get(this.mRePermissionIndex).Permission);
                return;
            }
            onFinish();
        }
    }

    private void checkPermission() {
        ListIterator<PermissionItem> iterator = this.mCheckPermissions.listIterator();
        while (iterator.hasNext()) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), iterator.next().Permission) == 0) {
                iterator.remove();
            }
        }
    }

    private void onFinish() {
        if (mCallback != null) {
            mCallback.onFinish();
        }
        finish();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onClose() {
        if (mCallback != null) {
            mCallback.onClose();
        }
        finish();
    }

    private void onDeny(String permission, int position) {
        if (mCallback != null) {
            mCallback.onDeny(permission, position);
        }
    }

    private void onGuarantee(String permission, int position) {
        if (mCallback != null) {
            mCallback.onGuarantee(permission, position);
        }
    }

    private PermissionItem getPermissionItem(String permission) {
        for (PermissionItem permissionItem : this.mCheckPermissions) {
            if (permissionItem.Permission.equals(permission)) {
                return permissionItem;
            }
        }
        return null;
    }
}
