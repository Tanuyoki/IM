package com.yoki.im.ui.business;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yoki.im.tools.CodeMapping;
import com.yoki.im.tools.ViewUtils;
import com.yoki.im.R;

public class PayView extends FrameLayout {
    private Context ctx;
    private ImageView imgStatus;

    public PayView(@NonNull Context context) {
        super(context);
        this.ctx = context;
    }

    public PayView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.ctx = context;
    }

    public PayView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.ctx = context;
    }

    public void init(final int tag, String payTitle, String payExplain, final ViewUtils.OnPayViewClickListener listener) {
//        View view = View.inflate(this.ctx, R.layout.aty_cashier_item1, null);
//        this.imgStatus = (ImageView) view.findViewById(R.id.aty_cashier_item_iv_status);
//        ((TextView) view.findViewById(R.id.aty_cashier_item_tv_title)).setText(payTitle);
//        ((TextView) view.findViewById(R.id.aty_cashier_item_tv_explain)).setText(payExplain);
//        ((ImageView) view.findViewById(R.id.aty_cashier_item_iv_icon)).setBackgroundResource(CodeMapping.getPayModeImageId(payTitle));
//        view.setOnClickListener(new OnClickListener() {
//            /* class com.yoki.im.ui.business.PayView.AnonymousClass1 */
//
//            public void onClick(View v) {
//                if (listener != null) {
//                    listener.onPayViewClick(tag, PayView.this.imgStatus);
//                }
//            }
//        });
//        addView(view);
    }

    public void setSelected(boolean selected) {
        this.imgStatus.setSelected(selected);
    }

    public boolean isSelected() {
        return this.imgStatus != null && this.imgStatus.isSelected();
    }
}
