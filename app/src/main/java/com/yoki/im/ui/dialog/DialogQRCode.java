package com.yoki.im.ui.dialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.yoki.im.tools.CodeMapping;
import com.yoki.im.tools.CommonData;
import com.yoki.im.tools.QRCodeUtil;
import com.yoki.im.R;
import java.text.DecimalFormat;

public class DialogQRCode extends BaseDialog {
    public static final int PAYMODE_ALIPAY = 21;
    public static final int PAYMODE_WECHAT = 20;
    private View mClose;
    private ImageView mIvPayIcon;
    private ImageView mIvQrCode;
    private View mPopView;
    private TextView mTvHavePay;
    private TextView mTvPayMode;
    private TextView mTvPayMoney;

    public DialogQRCode(@NonNull Context context) {
        super(context);
        init();
    }

    private void init() {
//        this.mPopView = View.inflate(this.mContext, R.layout.ppw_qr_code, null);
//        this.mIvPayIcon = (ImageView) this.mPopView.findViewById(R.id.ppw_qr_code_iv_pay_mode);
//        this.mIvQrCode = (ImageView) this.mPopView.findViewById(R.id.ppw_qr_code_iv_qr_code);
//        this.mTvPayMode = (TextView) this.mPopView.findViewById(R.id.ppw_qr_code_tv_pay_mode);
//        this.mTvPayMoney = (TextView) this.mPopView.findViewById(R.id.ppw_qr_code_tv_pay_money);
//        this.mTvHavePay = (TextView) this.mPopView.findViewById(R.id.ppw_qr_code_tv_have_pay);
//        this.mClose = this.mPopView.findViewById(R.id.ppw_qr_code_iv_close);
        this.mClose.setOnClickListener(new View.OnClickListener() {
            /* class com.yoki.im.ui.dialog.DialogQRCode.AnonymousClass1 */

            public void onClick(View v) {
                DialogQRCode.this.dismiss();
            }
        });
        setContentView(this.mPopView);
    }

    public DialogQRCode setPayPrice(double price) {
        this.mTvPayMoney.setText(new DecimalFormat("￥0.00").format(price));
        return this;
    }

    public DialogQRCode setHavePayPrice(String text) {
        this.mTvHavePay.setText(text);
        return this;
    }

    public DialogQRCode setPayQrCode(Bitmap bitmap) {
        this.mIvQrCode.setImageBitmap(bitmap);
        return this;
    }

    public DialogQRCode setPayQrCode(String url) {
        int size = (int) (((double) CommonData.ScreenWidth) * 0.65d);
        this.mIvQrCode.setImageBitmap(QRCodeUtil.generateBitmap(url, size, size));
        return this;
    }

    public DialogQRCode setPayMode(int payMode) {
        switch (payMode) {
            case 20:
                this.mTvPayMode.setText("微信扫码支付");
//                this.mIvPayIcon.setBackgroundResource(CodeMapping.getPayModeImageId("微信扫码支付"));
                break;
            case 21:
                this.mTvPayMode.setText("支付宝扫码支付");
//                this.mIvPayIcon.setBackgroundResource(CodeMapping.getPayModeImageId("支付宝扫码支付"));
                break;
        }
        return this;
    }
}
