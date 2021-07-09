package com.yoki.im.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import com.yoki.im.tools.StringUtils;
import com.yoki.im.ui.popup.PopupCalendar;
import com.yoki.im.R;
//import com.library.percentlayout.RelativePercentLayout;
import java.util.Date;

public class TimeFilterBar
//        extends RelativePercentLayout
        implements View.OnClickListener {
    private View mBelowView;
    private Context mContext;
    private PopupCalendar mPopupCalendar;
    private View mSearch;
    private TextView mTvEnd;
    private TextView mTvStart;

    public TimeFilterBar(Context context) {
        this(context, null);
    }

    public TimeFilterBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeFilterBar(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
        this.mContext = context;
//        init();
    }

//    private void init() {
//        setVisibility(8);
//        View view = View.inflate(this.mContext, R.layout.include_time_filter, null);
//        this.mSearch = view.findViewById(R.id.include_time_filter_search);
//        this.mTvStart = (TextView) view.findViewById(R.id.include_time_filter_tv_start);
//        this.mTvEnd = (TextView) view.findViewById(R.id.include_time_filter_tv_end);
//        this.mTvStart.setOnClickListener(this);
//        this.mTvEnd.setOnClickListener(this);
//        this.mTvStart.setText("2017-10-01");
//        this.mPopupCalendar = new PopupCalendar(this.mContext);
//        this.mPopupCalendar.setFocusable(false);
//        this.mPopupCalendar.setOutsideTouchable(false);
//        addView(view);
//    }
//
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.include_time_filter_tv_end /*{ENCODED_INT: 2131296891}*/:
//                this.mPopupCalendar.setDisabledMinDate(getTimeTextView(this.mTvStart));
//                showCalendar(this.mTvEnd);
//                return;
//            case R.id.include_time_filter_tv_filter /*{ENCODED_INT: 2131296892}*/:
//            case R.id.include_time_filter_tv_search /*{ENCODED_INT: 2131296893}*/:
//            default:
//                return;
//            case R.id.include_time_filter_tv_start /*{ENCODED_INT: 2131296894}*/:
//                this.mPopupCalendar.setDisabledMaxDate(getTimeTextView(this.mTvEnd));
//                showCalendar(this.mTvStart);
//                return;
        }
    }
//
//    public boolean onTouchEvent(MotionEvent event) {
//        if (this.mPopupCalendar.isShowing()) {
//            this.mPopupCalendar.dismiss();
//        }
//        return super.onTouchEvent(event);
//    }
//
//    private void showCalendar(TextView tv) {
//        if (this.mBelowView != null) {
//            this.mPopupCalendar.setDataSelected(getTimeTextView(tv));
//            this.mPopupCalendar.setSelectedDatesChanges(tv);
//            this.mPopupCalendar.showAsDropDown(this.mBelowView, 0, 0);
//        }
//    }

    public void setOnSearchClickListener(View.OnClickListener listener) {
        this.mSearch.setOnClickListener(listener);
    }

    public String getStartTime() {
        return String.valueOf(this.mTvStart.getText());
    }

    public String getEndTime() {
        return String.valueOf(this.mTvEnd.getText());
    }

    private Date getTimeTextView(TextView tv) {
        return StringUtils.stringToDate(String.valueOf(tv.getText()));
    }

    public void setBelowView(View view) {
        this.mBelowView = view;
    }

    public boolean isCalendarShowing() {
        return this.mPopupCalendar.isShowing();
    }

    public void calendarDismiss() {
        this.mPopupCalendar.dismiss();
    }
}
