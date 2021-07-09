package com.yoki.im.ui.dialog;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.yoki.im.R;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DialogPay extends BaseDialog implements View.OnClickListener {
    private static final String TAG = DialogPay.class.getSimpleName();
    private int currentPosition;
    private int inputLenght;
    private int lastPosition;
    private List<Button> listNumber;
    private onPayChangeListener listener;
    private LinearLayout llContainer11;
    private View mClose;
    private StringBuilder password = new StringBuilder();
    private View ppwView;
    private TextView[] tvListPayPwd;
    private TextView tvPayPrice;

    public interface onPayChangeListener {
        void onPaySuccess();
    }

    public DialogPay(@NonNull Context context) {
        super(context);
        init();
    }

    public DialogPay setListener(onPayChangeListener listener2) {
        this.listener = listener2;
        return this;
    }

    private void init() {
//        this.ppwView = View.inflate(this.mContext, R.layout.ppw_confirm_pay, null);
//        setContentView(this.ppwView);
//        this.tvPayPrice = (TextView) this.ppwView.findViewById(R.id.ppw_confirm_pay_tv_pay_price);
//        this.tvListPayPwd = new TextView[6];
//        this.inputLenght = this.tvListPayPwd.length;
//        this.tvListPayPwd[0] = (TextView) this.ppwView.findViewById(R.id.ppw_confirm_pay_tv_pay_pwd1);
//        this.tvListPayPwd[1] = (TextView) this.ppwView.findViewById(R.id.ppw_confirm_pay_tv_pay_pwd2);
//        this.tvListPayPwd[2] = (TextView) this.ppwView.findViewById(R.id.ppw_confirm_pay_tv_pay_pwd3);
//        this.tvListPayPwd[3] = (TextView) this.ppwView.findViewById(R.id.ppw_confirm_pay_tv_pay_pwd4);
//        this.tvListPayPwd[4] = (TextView) this.ppwView.findViewById(R.id.ppw_confirm_pay_tv_pay_pwd5);
//        this.tvListPayPwd[5] = (TextView) this.ppwView.findViewById(R.id.ppw_confirm_pay_tv_pay_pwd6);
//        this.llContainer11 = (LinearLayout) this.ppwView.findViewById(R.id.ppw_confirm_pay_ll_container11);
//        this.llContainer11.setOnClickListener(this);
//        forFind();
//        this.mClose = this.ppwView.findViewById(R.id.ppw_confirm_pay_iv_close);
        this.mClose.setOnClickListener(new View.OnClickListener() {
            /* class com.yoki.im.ui.dialog.DialogPay.AnonymousClass1 */

            public void onClick(View v) {
                DialogPay.this.dismiss();
            }
        });
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = -1;
            lp.height = -1;
            window.setGravity(80);
            window.setAttributes(lp);
        }
    }

    public DialogPay setPayPrice(double price) {
        this.tvPayPrice.setText(new DecimalFormat("￥0.00").format(price));
        return this;
    }

    private void forFind() {
        this.listNumber = new ArrayList();
        Resources res = this.mContext.getResources();
        for (int i = 0; i < 12; i++) {
            int id = res.getIdentifier("ppw_confirm_pay_btn" + i, "id", this.mContext.getPackageName());
            this.listNumber.add((Button) this.ppwView.findViewById(id));
            this.listNumber.get(i).setOnClickListener(this);
        }
    }

    public void clearPassword() {
        this.currentPosition = 0;
        for (TextView tv : this.tvListPayPwd) {
            tv.setText("");
        }
        this.password.delete(0, this.password.length());
    }

    public String getPassword() {
        return this.password.toString();
    }

    public void onClick(View view) {
//        if (view.getId() == R.id.ppw_confirm_pay_btn11 || view.getId() == R.id.ppw_confirm_pay_ll_container11) {
//            showText("-1");
//        } else {
//            showText(((Button) view).getText());
//        }
    }

    private void showText(CharSequence mText) {
        if (this.currentPosition <= this.inputLenght && this.currentPosition >= 0) {
            if (mText.equals("-1")) {
                if (this.lastPosition >= 0) {
                    this.tvListPayPwd[this.lastPosition].setText("");
                    this.lastPosition--;
                }
                if (this.currentPosition - 1 > -1) {
                    this.currentPosition--;
                }
                if (this.password.length() > 0) {
                    this.password.delete(this.password.length() - 1, this.password.length());
                    return;
                }
                return;
            }
            this.tvListPayPwd[this.currentPosition].setText("·");
            this.lastPosition = this.currentPosition;
            if (this.currentPosition + 1 < this.inputLenght) {
                this.currentPosition++;
            }
            if (this.password.length() < 6) {
                this.password.append(mText);
            }
            if (this.password.length() == 6 && this.listener != null) {
                this.listener.onPaySuccess();
                clearPassword();
            }
        }
    }
}
