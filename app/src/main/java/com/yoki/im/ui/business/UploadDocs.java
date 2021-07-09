package com.yoki.im.ui.business;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.yoki.im.base.BaseActivity;
import com.yoki.im.tools.BackgroundUtils;
import com.yoki.im.tools.Base64Utils;
import com.yoki.im.tools.DensityUtils;
import com.yoki.im.tools.GetPhotoUtils;
import com.yoki.im.tools.ToastUtils;
import com.yoki.im.tools.okhttp.listener.DisposeDataListener;
import com.yoki.im.tools.okhttp.response.ApiResponse;
import com.yoki.im.ui.SelectableRoundedImageView;
import com.yoki.im.ui.dialog.DialogLoading;
import com.yoki.im.R;

public class UploadDocs
//        extends RelativePercentLayout
        implements GetPhotoUtils.OnPhotoListener {
    private Context context;
    private String fileName;
    private ImageView mImg;
    private SelectableRoundedImageView mImgRounded;
    private TextView mTextView;

    public UploadDocs(Context context2) {
        this(context2, null);
    }

    public UploadDocs(Context context2, AttributeSet attrs) {
        this(context2, attrs, 0);
    }

    public UploadDocs(Context context2, AttributeSet attrs, int defStyle) {
//        super(context2, attrs, defStyle);
        init(context2, attrs);
    }

    private void init(Context context2, AttributeSet attrs) {
//        this.context = context2;
//        View view = View.inflate(context2, R.layout.layout_upload_docs, null);
//        this.mImg = (ImageView) view.findViewById(R.id.layout_upload_docs_iv);
//        this.mImgRounded = (SelectableRoundedImageView) view.findViewById(R.id.layout_upload_docs_thumbnail);
//        this.mTextView = (TextView) view.findViewById(R.id.layout_upload_docs_tv);
//        addView(view);
//        setBackgroundDrawable(BackgroundUtils.generateGradientDrawable(DensityUtils.dip2px(3.0f), 1, Color.parseColor("#f7f7f7"), Color.parseColor("#f1f1f1")));
//        if (attrs != null) {
//            TypedArray typed = context2.obtainStyledAttributes(attrs, com.yoki.im.R.styleable.UploadDocs);
//            String text = typed.getString(0);
//            if (text != null && !text.isEmpty()) {
//                this.mTextView.setText(text);
//            }
//            typed.recycle();
//        }
    }

    public void setText(String text) {
        this.mTextView.setText(text);
    }

    public void enableOnClickListener(final BaseActivity activity) {
//        setOnClickListener(new View.OnClickListener() {
//            /* class com.yoki.im.ui.business.UploadDocs.AnonymousClass1 */
//
//            public void onClick(View v) {
//                new GetPhotoUtils(false, 11).setCallbackListener(UploadDocs.this).showGetPhotoMode(activity);
//            }
//        });
    }

    @Override // com.yoki.im.tools.GetPhotoUtils.OnPhotoListener
    public void onResourceReady(String photoPath) {
        DialogLoading.getInstances().showLoading();
    }

    @Override // com.yoki.im.tools.GetPhotoUtils.OnPhotoListener
    public void onGenerateBitmap(Bitmap bitmap) {
//        if (bitmap != null) {
//            this.mImg.setVisibility(8);
//            this.mTextView.setVisibility(8);
//            setBackgroundDrawable(BackgroundUtils.generateGradientDrawable(DensityUtils.dip2px(3.0f), 1, ContextCompat.getColor(this.context, R.color.tran), Color.parseColor("#f1f1f1")));
//            this.mImgRounded.setImageDrawable(new BitmapDrawable(this.context.getResources(), bitmap));
//            this.mImgRounded.setCornerRadiiDP(3.0f, 3.0f, 3.0f, 3.0f);
//            RequestCenter.uploadFile(this.context, Base64Utils.bitmapToBase64(bitmap), new DisposeDataListener() {
//                /* class com.yoki.im.ui.business.UploadDocs.AnonymousClass2 */
//
//                @Override // com.yoki.im.tools.okhttp.listener.DisposeDataListener
//                public void onSuccess(Object response) {
//                    DialogLoading.getInstances().cancel();
//                    ApiResponse api = (ApiResponse) response;
//                    ToastUtils.show(api.getMsg());
//                    if (api.isSuccess()) {
//                        UploadDocs.this.fileName = api.getData().getString("fileName");
//                        return;
//                    }
//                    ToastUtils.show(api.getMsg());
//                }
//
//                @Override // com.yoki.im.tools.okhttp.listener.DisposeDataListener
//                public void onFailure(Object response) {
//                    DialogLoading.getInstances().cancel();
//                    ToastUtils.show("上传失败,请重试");
//                }
//            });
//        }
    }

    public String getFileName() {
        return this.fileName;
    }
}
