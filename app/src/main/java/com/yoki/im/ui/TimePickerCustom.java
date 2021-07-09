package com.yoki.im.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import com.yoki.im.tools.CommonData;
import com.yoki.im.tools.EditTextUtils;
import com.yoki.im.R;
//import com.bigkoo.pickerview.builder.TimePickerBuilder;
//import com.bigkoo.pickerview.listener.CustomListener;
//import com.bigkoo.pickerview.listener.OnTimeSelectListener;
//import com.bigkoo.pickerview.view.TimePickerView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimePickerCustom {
    private Context ctx;
    private EditText mBindEditText;
    private TextView mBindTextView;
//    private TimePickerView mTimeView;

    public TimePickerCustom(Context ctx2) {
        this.ctx = ctx2;
        initTimePicker();
    }

    private void initTimePicker() {
        Window window;
        Calendar selectedDate = Calendar.getInstance();
        Calendar.getInstance().set(1990, 1, 0);
        Calendar.getInstance().set(2200, 1, 0);
//        this.mTimeView = new TimePickerBuilder(this.ctx, new OnTimeSelectListener() {
//            /* class com.yoki.im.ui.TimePickerCustom.AnonymousClass2 */
//
//            @Override // com.bigkoo.pickerview.listener.OnTimeSelectListener
//            public void onTimeSelect(Date date, View v) {
//                String showDate = TimePickerCustom.this.getTime(date);
//                if (TimePickerCustom.this.mBindEditText != null) {
//                    TimePickerCustom.this.mBindEditText.setText(showDate);
//                    TimePickerCustom.this.mBindEditText.setSelection(showDate.length());
//                } else if (TimePickerCustom.this.mBindTextView != null) {
//                    TimePickerCustom.this.mBindTextView.setText(showDate);
//                }
//            }
//        }).setContentTextSize(26).setTitleSize(26).setDate(selectedDate).setLayoutRes(R.layout.ppw_time_picker1, new CustomListener() {
//            /* class com.yoki.im.ui.TimePickerCustom.AnonymousClass1 */
//
//            @Override // com.bigkoo.pickerview.listener.CustomListener
//            public void customLayout(View v) {
//                ((TextView) v.findViewById(R.id.popup_time_picker_btn_finish)).setOnClickListener(new View.OnClickListener() {
//                    /* class com.yoki.im.ui.TimePickerCustom.AnonymousClass1.AnonymousClass1 */
//
//                    public void onClick(View v) {
//                        TimePickerCustom.this.mTimeView.returnData();
//                        TimePickerCustom.this.mTimeView.dismiss();
//                    }
//                });
//            }
//        }).setType(new boolean[]{true, true, true, false, false, false}).setLabel("年", "月", "日", "时", "分", "秒").isCenterLabel(false).setDividerColor(805306368).isDialog(true).build();
//        Dialog dialog = this.mTimeView.getDialog();
//        if (dialog != null && (window = dialog.getWindow()) != null) {
//            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//            lp.copyFrom(window.getAttributes());
//            lp.width = (int) (((double) CommonData.ScreenWidth) * 0.75d);
//            lp.height = -2;
//            lp.gravity = 17;
//            dialog.getWindow().setAttributes(lp);
//        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private String getTime(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public TimePickerCustom setBindEditText(EditText editText) {
        this.mBindEditText = editText;
        return this;
    }

    public TimePickerCustom addEditListener(EditText editText) {
        if (editText != null) {
            EditTextUtils.forbiddenShowSoftKeyboard(editText);
            editText.setOnClickListener(new View.OnClickListener() {
                /* class com.yoki.im.ui.TimePickerCustom.AnonymousClass3 */

                public void onClick(View v) {
                    TimePickerCustom.this.show((EditText) v);
                }
            });
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                /* class com.yoki.im.ui.TimePickerCustom.AnonymousClass4 */

                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        TimePickerCustom.this.show((EditText) v);
                    }
                }
            });
        }
        return this;
    }

    public TimePickerCustom setBindTextView(TextView textView) {
        this.mBindTextView = textView;
        return this;
    }

    public boolean isShowing() {
//        return this.mTimeView.isShowing();
        return false;
    }

    public TimePickerCustom show() {
//        this.mTimeView.show();
        return this;
    }

    public TimePickerCustom show(EditText editText) {
        this.mBindEditText = editText;
        show();
        return this;
    }
}
