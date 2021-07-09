package com.yoki.im.ui.popup;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.yoki.im.base.BasePopupWindow;
import com.yoki.im.tools.StringUtils;
import com.yoki.im.R;
//import com.prolificinteractive.materialcalendarview.CalendarDay;
//import com.prolificinteractive.materialcalendarview.DayViewDecorator;
//import com.prolificinteractive.materialcalendarview.DayViewFacade;
//import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
//import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
//import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
//import com.prolificinteractive.materialcalendarview.format.TitleFormatter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PopupCalendar extends BasePopupWindow
//        implements OnDateSelectedListener
{
//    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
//    private static final String TAG = PopupCalendar.class.getSimpleName();
//    private Context mContext;
//    private MaterialCalendarView mCvCalendar;
//    private Date mDisabledMaxDate = null;
//    private Date mDisabledMinDate = null;
//    private EditText mEdtSelectedTime;
//    private View mView;
//
//    public PopupCalendar(Context context) {
//        this.mContext = context;
//        init();
//    }
//
    @Override // com.yoki.im.base.BasePopupWindow
    public void show() {
    }
//
//    private void init() {
//        this.mView = View.inflate(this.mContext, R.layout.ppw_calendar, null);
//        this.mCvCalendar = (MaterialCalendarView) this.mView.findViewById(R.id.ppw_calendarView);
//        this.mCvCalendar.setWeekDayFormatter(new ArrayWeekDayFormatter(this.mContext.getResources().getTextArray(R.array.custom_weekdays)));
//        this.mCvCalendar.setTitleFormatter(new TitleFormatter() {
//            /* class com.yoki.im.ui.popup.PopupCalendar.AnonymousClass1 */
//
//            @Override // com.prolificinteractive.materialcalendarview.format.TitleFormatter
//            public CharSequence format(CalendarDay day) {
//                return String.valueOf(day.getYear() + "年" + (day.getMonth() + 1) + "月");
//            }
//        });
//        this.mCvCalendar.setOnDateChangedListener(this);
//        this.mCvCalendar.addDecorator(new DisableDayDecorator());
//        setContentView(this.mView);
//    }
//
//    public void setSelectedDatesChanges(EditText edt) {
//        this.mEdtSelectedTime = edt;
//    }
//
//    public void setSelectedDatesChanges(TextView textView) {
//    }
//
//    public void setDataSelected(Date date) {
//        this.mCvCalendar.clearSelection();
//        if (date != null) {
//            this.mCvCalendar.setCurrentDate(date);
//            this.mCvCalendar.setDateSelected(date, true);
//        }
//    }
//
//    public void setDisabledMinDate(Date date) {
//        this.mDisabledMinDate = date;
//        this.mDisabledMaxDate = null;
//        this.mCvCalendar.invalidateDecorators();
//    }
//
//    public void setDisabledMinDate(Calendar cal) {
//    }
//
//    public void setDisabledMaxDate(Date date) {
//        this.mDisabledMaxDate = date;
//        this.mDisabledMinDate = null;
//        this.mCvCalendar.invalidateDecorators();
//    }
//
//    public void setDateRange(Date min, Date max) {
//        this.mCvCalendar.state().edit().setMinimumDate(min).setMaximumDate(max).commit();
//    }
//
//    public void show(EditText editText) {
//        setDataSelected(StringUtils.stringToDate(String.valueOf(editText.getText())));
//        setSelectedDatesChanges(editText);
//        showAsDropDown(editText, 0, 0, 80);
//    }
//
//    public void show(EditText editText, int gravity) {
//        setDataSelected(StringUtils.stringToDate(String.valueOf(editText.getText())));
//        setSelectedDatesChanges(editText);
//        showAtLocation(editText, gravity, 0, 0);
//    }
//
//    @Override // com.prolificinteractive.materialcalendarview.OnDateSelectedListener
//    public void onDateSelected(@NonNull MaterialCalendarView widget, @Nullable CalendarDay date, boolean selected) {
//        if (this.mEdtSelectedTime != null) {
//            this.mEdtSelectedTime.setText(getSelectedDatesString());
//            this.mEdtSelectedTime.setSelection(this.mEdtSelectedTime.getText().length());
//            dismiss();
//        }
//    }
//
//    private String getSelectedDatesString() {
//        CalendarDay date = this.mCvCalendar.getSelectedDate();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        if (date == null) {
//            return "No Selection";
//        }
//        return sdf.format(date.getDate());
//    }
//
//    /* access modifiers changed from: private */
//    public class DisableDayDecorator implements DayViewDecorator {
//        private DisableDayDecorator() {
//        }
//
//        @Override // com.prolificinteractive.materialcalendarview.DayViewDecorator
//        public boolean shouldDecorate(CalendarDay day) {
//            if (PopupCalendar.this.mDisabledMinDate == null || PopupCalendar.this.mDisabledMaxDate == null) {
//                if (PopupCalendar.this.mDisabledMinDate != null) {
//                    return day.getDate().before(PopupCalendar.this.mDisabledMinDate);
//                }
//                if (PopupCalendar.this.mDisabledMaxDate != null) {
//                    return day.getDate().after(PopupCalendar.this.mDisabledMaxDate);
//                }
//                return false;
//            } else if (!day.getDate().before(PopupCalendar.this.mDisabledMinDate) || !day.getDate().after(PopupCalendar.this.mDisabledMaxDate)) {
//                return false;
//            } else {
//                return true;
//            }
//        }
//
//        @Override // com.prolificinteractive.materialcalendarview.DayViewDecorator
//        public void decorate(DayViewFacade view) {
//            view.setDaysDisabled(true);
//        }
//    }
}
