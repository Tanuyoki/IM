package com.yoki.im.tools;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import com.yoki.im.base.BaseActivity;
import com.yoki.im.tools.PermissionUtils;
import java.io.File;

public class SystemIntentUtils extends Intent {
    private static String sCameraPath = "";
    private static boolean sIsCameraSave = false;

    public interface onIntentResultListener {
        void getPicture(String str);
    }

    public static Intent goToSettingPermission(Activity activity) {
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(uri);
        return intent;
    }

    public static void getAlbumPicture(final BaseActivity activity, final onIntentResultListener listener) {
        if (PermissionUtils.checkPermission(activity, "android.permission.WRITE_EXTERNAL_STORAGE")) {
            Intent intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            activity.startActivityForListener(intent, 0, new BaseActivity.OnActivityResultListener() {
                /* class com.yoki.im.tools.SystemIntentUtils.AnonymousClass1 */

                @Override // com.yoki.im.base.BaseActivity.OnActivityResultListener
                public void onResult(int requestCode, int resultCode, Intent result) {
                    if (result != null) {
                        String path = ContentUriUtils.getPath(activity, result.getData());
                        if (listener != null) {
                            listener.getPicture(path);
                        }
                    }
                }
            });
            return;
        }
        PermissionUtils.applyPermission(activity, new PermissionUtils.PermissionResultCallback() {
            /* class com.yoki.im.tools.SystemIntentUtils.AnonymousClass2 */

            @Override // com.yoki.im.tools.PermissionUtils.PermissionResultCallback
            public void onFinish() {
                SystemIntentUtils.getAlbumPicture(activity, listener);
            }
        }, "android.permission.WRITE_EXTERNAL_STORAGE");
    }

    public static void getCameraPicture(final BaseActivity activity, final String fileSavePath, final onIntentResultListener listener) {
        if (PermissionUtils.checkPermission(activity, "android.permission.WRITE_EXTERNAL_STORAGE")) {
            sCameraPath = "";
            if (fileSavePath == null || fileSavePath.isEmpty()) {
                sCameraPath = PathUtils.getStoragePicturesPathAndTime()[1];
            } else {
                sCameraPath = fileSavePath;
            }
            File file = new File(sCameraPath);
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            if (Build.VERSION.SDK_INT >= 24) {
                intent.addFlags(1);
                intent.putExtra("output", FileProvider.getUriForFile(activity, Constants.APP_FILE_PROVIDER, file));
            } else {
                intent.putExtra("output", Uri.fromFile(file));
            }
            activity.startActivityForListener(intent, 0, new BaseActivity.OnActivityResultListener() {
                /* class com.yoki.im.tools.SystemIntentUtils.AnonymousClass3 */

                @Override // com.yoki.im.base.BaseActivity.OnActivityResultListener
                public void onResult(int requestCode, int resultCode, Intent result) {
                    File file = new File(SystemIntentUtils.sCameraPath);
                    String unused = SystemIntentUtils.sCameraPath = PathUtils.getStoragePicturesPathAndTime()[1];
                    File newFile = new File(SystemIntentUtils.sCameraPath);
                    if (file.exists()) {
                        file.renameTo(newFile);
                    }
                    if (listener != null && resultCode == -1) {
                        SystemUtils.scanFile(activity, newFile);
                        listener.getPicture(SystemIntentUtils.sCameraPath);
                    }
                }
            });
            return;
        }
        PermissionUtils.applyPermission(activity, new PermissionUtils.PermissionResultCallback() {
            /* class com.yoki.im.tools.SystemIntentUtils.AnonymousClass4 */

            @Override // com.yoki.im.tools.PermissionUtils.PermissionResultCallback
            public void onFinish() {
                SystemIntentUtils.getCameraPicture(activity, fileSavePath, listener);
            }
        }, "android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE");
    }

    public static void getCropPicture(BaseActivity activity, String cropFilePath, final String fileSavePath, boolean freeSize, final onIntentResultListener listener) {
        final File outFile;
        Uri imageUri;
        if (cropFilePath != null && !cropFilePath.isEmpty()) {
            File imageFile = new File(cropFilePath);
            if (fileSavePath == null || fileSavePath.isEmpty()) {
                outFile = new File(PathUtils.getStoragePicturesPathAndTime()[1]);
            } else {
                outFile = new File(fileSavePath);
            }
            Uri outputUri = Uri.fromFile(outFile);
            Intent intent = new Intent("com.android.camera.action.CROP");
            if (Build.VERSION.SDK_INT >= 24) {
                intent.addFlags(1);
                imageUri = FileProvider.getUriForFile(activity, Constants.APP_FILE_PROVIDER, imageFile);
            } else {
                imageUri = Uri.fromFile(imageFile);
            }
            intent.setDataAndType(imageUri, "image/*");
            intent.putExtra("crop", "true");
            if (freeSize) {
                intent.putExtra("aspectX", 0.1d);
                intent.putExtra("aspectY", 0.1d);
            } else {
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
            }
            intent.putExtra("outputX", 300);
            intent.putExtra("outputY", 300);
            intent.putExtra("scale", true);
            intent.putExtra("output", outputUri);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", true);
            activity.startActivityForListener(intent, 0, new BaseActivity.OnActivityResultListener() {
                /* class com.yoki.im.tools.SystemIntentUtils.AnonymousClass5 */

                @Override // com.yoki.im.base.BaseActivity.OnActivityResultListener
                public void onResult(int requestCode, int resultCode, Intent result) {
                    String path;
                    if (fileSavePath == null || fileSavePath.isEmpty()) {
                        File file = new File(outFile.getPath());
                        File newFile = new File(PathUtils.getStoragePicturesPathAndTime()[1]);
                        if (file.exists()) {
                            file.renameTo(newFile);
                        }
                        path = newFile.getPath();
                    } else {
                        path = outFile.getPath();
                    }
                    if (resultCode == -1 && listener != null) {
                        SystemUtils.scanFile(activity, path);
                        listener.getPicture(path);
                    }
                }
            });
        }
    }

    public static void getAlbumAndCropPicture(final BaseActivity activity, final String fileName, final boolean freeSize, final onIntentResultListener listener) {
        getAlbumPicture(activity, new onIntentResultListener() {
            /* class com.yoki.im.tools.SystemIntentUtils.AnonymousClass6 */

            @Override // com.yoki.im.tools.SystemIntentUtils.onIntentResultListener
            public void getPicture(String path) {
                SystemIntentUtils.getCropPicture(activity, path, fileName, freeSize, listener);
            }
        });
    }

    public static void getCameraAndCropPicture(final BaseActivity activity, final String fileSavePath, boolean isCameraSave, final boolean freeSize, final onIntentResultListener listener) {
        sIsCameraSave = isCameraSave;
        getCameraPicture(activity, "", new onIntentResultListener() {
            /* class com.yoki.im.tools.SystemIntentUtils.AnonymousClass7 */

            @Override // com.yoki.im.tools.SystemIntentUtils.onIntentResultListener
            public void getPicture(String cameraPath) {
                SystemIntentUtils.getCropPicture(activity, cameraPath, fileSavePath, freeSize, new onIntentResultListener() {
                    /* class com.yoki.im.tools.SystemIntentUtils.AnonymousClass7.AnonymousClass1 */

                    @Override // com.yoki.im.tools.SystemIntentUtils.onIntentResultListener
                    public void getPicture(String path) {
                        SystemIntentUtils.removeCameraPicture();
                        if (listener != null && path != null) {
                            listener.getPicture(path);
                        }
                    }
                });
            }
        });
    }

    public static void mandatoryRemoveCameraPicture() {
        if (!sCameraPath.isEmpty()) {
            File file = new File(sCameraPath);
            if (file.exists()) {
                file.delete();
                sCameraPath = "";
                sIsCameraSave = true;
            }
        }
    }

    public static void removeCameraPicture() {
        if (!sCameraPath.isEmpty() && !sIsCameraSave) {
            File file = new File(sCameraPath);
            if (file.exists()) {
                file.delete();
                sCameraPath = "";
                sIsCameraSave = true;
            }
        }
    }
}
