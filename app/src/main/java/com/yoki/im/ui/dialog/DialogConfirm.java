package com.yoki.im.ui.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.yoki.im.tools.BackgroundUtils;
import com.yoki.im.tools.DensityUtils;
import com.yoki.im.R;

public class DialogConfirm extends BaseDialog implements View.OnClickListener {
    private TextView mCancel;
    private TextView mConfirm;
    private View mDiv;
    private EditText mEdtContent;
    private OnClickListener mListener;
    private TextView mTitle;

    public DialogConfirm(Context context) {
        super(context);
        init();
    }

    private void init() {
//        View view = View.inflate(this.mContext, R.layout.ppw_confirm1, null);
//        this.mTitle = (TextView) view.findViewById(R.id.ppw_confirm_tv_tip);
//        this.mCancel = (TextView) view.findViewById(R.id.ppw_confirm_tv_left);
//        this.mConfirm = (TextView) view.findViewById(R.id.ppw_confirm_tv_right);
//        this.mDiv = view.findViewById(R.id.ppw_confirm_div);
//        this.mEdtContent = (EditText) view.findViewById(R.id.ppw_confirm_edt_tip);
//        this.mCancel.setVisibility(8);
//        this.mConfirm.setVisibility(8);
//        this.mCancel.setOnClickListener(this);
//        this.mConfirm.setOnClickListener(this);
//        setContentView(view);
    }

    public DialogConfirm setOnClickListener(OnClickListener listener) {
        this.mListener = listener;
        return this;
    }

    public DialogConfirm setTitle(String text) {
        this.mTitle.setText(text);
        return this;
    }

    public DialogConfirm setCancel(String text) {
        this.mCancel.setText(text);
        return this;
    }

    public DialogConfirm setConfirm(String text) {
        this.mConfirm.setText(text);
        return this;
    }

    public DialogConfirm showEditText() {
        showEditText("请输入拒绝原因");
        return this;
    }

    public DialogConfirm showEditText(String hint) {
        this.mTitle.setVisibility(8);
        this.mEdtContent.setVisibility(0);
        this.mEdtContent.setHint(hint);
        return this;
    }

    public String getEditTextContent() {
        return String.valueOf(this.mEdtContent.getText());
    }

    public void show() {
        String cancel = String.valueOf(this.mCancel.getText());
        String confirm = String.valueOf(this.mConfirm.getText());
        if (cancel.isEmpty() || "null".equals(cancel)) {
            this.mCancel.setVisibility(8);
        } else {
            this.mCancel.setVisibility(0);
        }
        if (confirm.isEmpty() || "null".equals(confirm)) {
            this.mConfirm.setVisibility(8);
        } else {
            this.mConfirm.setVisibility(0);
        }
        if (this.mCancel.getVisibility() == 0 && this.mConfirm.getVisibility() == 0) {
            Drawable leftDrawable = BackgroundUtils.generateClickEffectDrawable(DensityUtils.dip2px(5.0f), 7);
            Drawable rightDrawable = BackgroundUtils.generateClickEffectDrawable(DensityUtils.dip2px(5.0f), 9);
            this.mCancel.setBackgroundDrawable(leftDrawable);
            this.mConfirm.setBackgroundDrawable(rightDrawable);
            this.mDiv.setVisibility(0);
        } else if (this.mCancel.getVisibility() == 0) {
            this.mCancel.setBackgroundDrawable(BackgroundUtils.generateClickEffectDrawable(DensityUtils.dip2px(5.0f), 3));
            this.mDiv.setVisibility(8);
        } else if (this.mConfirm.getVisibility() == 0) {
            this.mConfirm.setBackgroundDrawable(BackgroundUtils.generateClickEffectDrawable(DensityUtils.dip2px(5.0f), 3));
            this.mDiv.setVisibility(8);
        }
        super.show();
    }

    public void onClick(View v) {
//        if (v.getId() == R.id.ppw_confirm_tv_left) {
//            if (this.mListener == null) {
//                dismiss();
//            } else if (this.mListener.onCancel()) {
//                dismiss();
//            }
//        } else if (v.getId() != R.id.ppw_confirm_tv_right) {
//        } else {
//            if (this.mListener == null) {
//                dismiss();
//            } else if (this.mListener.onConfirm()) {
//                dismiss();
//            }
//        }
    }

    public static abstract class OnClickListener {
        public boolean onCancel() {
            return true;
        }

        public boolean onConfirm() {
            return true;
        }
    }
}
