package com.yoki.im.ui.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.yoki.im.tools.Constants;
import com.yoki.im.tools.DataDictionaryUtils;
import com.yoki.im.tools.DictionaryCallback;
import com.yoki.im.R;
import com.alibaba.fastjson.JSONObject;
import java.util.Map;

public class DialogDetail extends BaseDialog {
    private LinearLayout container;
    private TextView mTitle;

    public DialogDetail(Context ctx) {
        super(ctx);
        init();
    }

    private void init() {
//        View view = View.inflate(this.mContext, R.layout.ppw_detail1, null);
//        this.mTitle = (TextView) view.findViewById(R.id.ppw_detail_tv_title);
//        this.container = (LinearLayout) view.findViewById(R.id.ppw_detail_container);
//        View back = view.findViewById(R.id.ppw_detail_root);
//        view.findViewById(R.id.ppw_detail_close).setOnClickListener(new View.OnClickListener() {
//            /* class com.yoki.im.ui.dialog.DialogDetail.AnonymousClass1 */
//
//            public void onClick(View v) {
//                DialogDetail.this.dismiss();
//            }
//        });
//        back.setOnKeyListener(new View.OnKeyListener() {
//            /* class com.yoki.im.ui.dialog.DialogDetail.AnonymousClass2 */
//
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode != 4) {
//                    return false;
//                }
//                DialogDetail.this.dismiss();
//                return false;
//            }
//        });
//        setContentView(view);
//        Window window = getWindow();
//        if (window != null) {
//            WindowManager.LayoutParams lp = window.getAttributes();
//            lp.width = -1;
//            lp.height = -2;
//            lp.gravity = 80;
//            window.setAttributes(lp);
//        }
    }

    public DialogDetail setTitleText(String text) {
        this.mTitle.setText(text);
        return this;
    }

    public TextView generateView(String title) {
        return generateView(title, this.container.getChildCount() > 0);
    }

    public TextView generateView(String title, boolean isAddLine) {
//        View view = View.inflate(this.mContext, R.layout.ppw_detail_item1, null);
//        TextView tvContent = (TextView) view.findViewById(R.id.ppw_detail_item_tv_content);
//        View viewLine = view.findViewById(R.id.ppw_detail_item_line);
//        ((TextView) view.findViewById(R.id.ppw_detail_item_tv_title)).setText(title);
//        if (!isAddLine) {
//            viewLine.setVisibility(8);
//        }
//        this.container.addView(view);
//        return tvContent;
        return null;
    }

    public void show() {
        super.show();
    }

    public static class ViolationDetail {
        private DialogDetail mDialogDetail;
        private TextView mTxtCarInfo = this.mDialogDetail.generateView("????????????");
        private TextView mTxtCreatedTime = this.mDialogDetail.generateView("????????????");
        private TextView mTxtEngineNum = this.mDialogDetail.generateView("????????????");
        private TextView mTxtFrameNum = this.mDialogDetail.generateView("????????????");
        private TextView mTxtRepeat = this.mDialogDetail.generateView("????????????");
        private TextView mTxtSuccess = this.mDialogDetail.generateView("????????????");

        public ViolationDetail(Context context) {
            this.mDialogDetail = new DialogDetail(context);
            this.mDialogDetail.setTitleText("????????????");
        }

        public ViolationDetail show(final JSONObject data) {
            DataDictionaryUtils.getDictionary("????????????", new DictionaryCallback() {
                /* class com.yoki.im.ui.dialog.DialogDetail.ViolationDetail.AnonymousClass1 */

                @Override // com.yoki.im.tools.DictionaryCallback
                public void complete(final Map<String, String> carTypeDictionary) {
                    DataDictionaryUtils.getDictionary("????????????", new DictionaryCallback() {
                        /* class com.yoki.im.ui.dialog.DialogDetail.ViolationDetail.AnonymousClass1.AnonymousClass1 */

                        @Override // com.yoki.im.tools.DictionaryCallback
                        public void complete(Map<String, String> carUsageDictionary) {
                            boolean isRepeat;
                            boolean isSuccess;
                            String carInfo = data.getString("carNumber") + " " + ((String) carTypeDictionary.get(data.getString("carType"))) + " " + carUsageDictionary.get(data.getString("carUsage"));
                            String carFrameNumber = data.getString("carFrameNumber");
                            String carEngineNumber = data.getString("carEngineNumber");
                            String updateTime = data.getString("createdTime");
                            if (data.getIntValue("isRepeat") == 1) {
                                isRepeat = true;
                            } else {
                                isRepeat = false;
                            }
                            if (data.getIntValue("isSuccess") == 1) {
                                isSuccess = true;
                            } else {
                                isSuccess = false;
                            }
                            ViolationDetail.this.setInfo(carInfo, carFrameNumber, carEngineNumber, isRepeat, isSuccess, updateTime).show();
                        }
                    });
                }
            });
            return this;
        }

        @SuppressLint({"SetTextI18n"})
        public DialogDetail setInfo(String carInfo, String frameNum, String engineNum, boolean isRepeat, boolean isSuccess, String updateTime) {
            this.mTxtCarInfo.setText(carInfo);
            this.mTxtFrameNum.setText(frameNum);
            this.mTxtEngineNum.setText(engineNum);
            this.mTxtRepeat.setText(isRepeat ? "???" : "???");
            this.mTxtSuccess.setText(isSuccess ? "???" : "???");
            this.mTxtCreatedTime.setText(updateTime);
            this.mTxtRepeat.setTextColor(isRepeat ? Constants.COLORS.GREEN : Constants.COLORS.RED);
            this.mTxtSuccess.setTextColor(isSuccess ? Constants.COLORS.GREEN : Constants.COLORS.RED);
            return this.mDialogDetail;
        }

        public DialogDetail getPopup() {
            return this.mDialogDetail;
        }
    }

    public static class OrderDetail {
        private DialogDetail mDialogDetail;
        private TextView mTxtBehavior = this.mDialogDetail.generateView("????????????");
        private TextView mTxtCarNum = this.mDialogDetail.generateView("????????????");
        private TextView mTxtHandleResult = this.mDialogDetail.generateView("????????????");
        private TextView mTxtPaymentDetails = this.mDialogDetail.generateView("????????????");
        private TextView mTxtViolationArea = this.mDialogDetail.generateView("????????????");
        private TextView mTxtViolationTime = this.mDialogDetail.generateView("????????????");

        public OrderDetail(Context context) {
            this.mDialogDetail = new DialogDetail(context);
            this.mDialogDetail.setTitleText("????????????");
        }

        @SuppressLint({"SetTextI18n"})
        public OrderDetail show(JSONObject data) {
            this.mTxtCarNum.setText(data.getString("carNumber"));
            this.mTxtViolationTime.setText(data.getString("violationTime"));
            this.mTxtViolationArea.setText("[ " + data.getString("violationProvince") + data.getString("violationCity") + " ] " + data.getString("violationLocation"));
            this.mTxtBehavior.setText(data.getString("violationDesc"));
            this.mTxtPaymentDetails.setText("?????? " + data.getString("demeritPoints") + " ???  ?????? " + data.getString("forfeit") + " ???  ????????? " + data.getString("demurrage") + " ???  ????????? " + data.getString("serviceCharge") + " ???  ");
            this.mTxtHandleResult.setText(data.getString("handleResultRemark"));
            this.mDialogDetail.show();
            return this;
        }

        public DialogDetail getPopup() {
            return this.mDialogDetail;
        }
    }
}
