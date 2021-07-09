package com.yoki.im.ui.dialog;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

import com.yoki.im.R;

public class BaseDialog extends Dialog {
    public Context mContext;

    public BaseDialog(@NonNull Context context) {
        super(context, R.style.Base_Dialog);
        this.mContext = context;
    }
}
