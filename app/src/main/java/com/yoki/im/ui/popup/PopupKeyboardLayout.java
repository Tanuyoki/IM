package com.yoki.im.ui.popup;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.yoki.im.tools.LookupUtils;
import com.yoki.im.R;

public class PopupKeyboardLayout implements View.OnTouchListener {
    private static final int KEYBOARD_CODE_BACK = 1;
    private static final int KEYBOARD_CODE_CONFIRM = 2;
    private static final int KEYBOARD_CODE_INPUT = 0;
    private int mAmplifyHeight;
    private int mClickRange = 50;
    private View mContentAmplify;
    private Context mContext;
    private Handler mDeleteHandler = null;
    private Runnable mDeleteUpdate = null;
    private View mEnglishLayout;
    private Handler mHandler = new Handler();
    private int mInputKeyboardCode;
    private boolean mIsFirstShowAmplify = true;
    private InputListener mListener;
    private long mLongDeleteTime = 500;
    private PopupWindow mPopupAmplify;
    private int mStartX;
    private int mStartY;
    private TextView mTvAmplify;
    private View mVehiclePlateLayout;
    private View mView;

    public interface InputListener {
        void onInputBackLongPress();

        void onInputClick(String str, int i);

        void onInputLongPress(View view, int i);
    }

    public PopupKeyboardLayout(Context context) {
        this.mContext = context;
        initView();
    }

    private void initView() {
        initVehiclePlate();
        initEnglish();
        initAmplifyPopup();
        longPressBack();
    }

    private void initVehiclePlate() {
//        View viewLayout = View.inflate(this.mContext, R.layout.layout_keyboard_vehicle_plate, null);
//        String[] text = this.mContext.getResources().getStringArray(R.array.keyboard_vehicle_plate);
//        Button[] view = new Button[text.length];
//        for (int i = 0; i < text.length; i++) {
//            view[i] = (Button) viewLayout.findViewById(LookupUtils.getResourceId(this.mContext, "layout_keyboard_vehicle_plate" + i));
//            view[i].setText(text[i]);
//            view[i].setOnTouchListener(this);
//            if (i == text.length - 1) {
//                view[i].setTag(2);
//            } else {
//                view[i].setTag(0);
//            }
//        }
//        this.mVehiclePlateLayout = viewLayout;
    }

    private void initEnglish() {
//        View viewLayout = View.inflate(this.mContext, R.layout.layout_keyboard_english, null);
//        String[] text = this.mContext.getResources().getStringArray(R.array.keyboard_english);
//        View[] view = new View[text.length];
//        for (int i = 0; i < text.length; i++) {
//            view[i] = viewLayout.findViewById(LookupUtils.getResourceId(this.mContext, "layout_keyboard_english" + i));
//            view[i].setOnTouchListener(this);
//            if (i != text.length - 2) {
//                ((TextView) view[i]).setText(text[i]);
//            }
//            if (i == text.length - 1) {
//                view[i].setTag(2);
//            } else if (i == text.length - 2) {
//                view[i].setTag(1);
//            } else {
//                view[i].setTag(0);
//            }
//        }
//        this.mEnglishLayout = viewLayout;
    }

    private void initAmplifyPopup() {
//        WindowManager windowManager = (WindowManager) this.mContext.getSystemService("window");
//        if (windowManager != null) {
//            int mScreenWidth = windowManager.getDefaultDisplay().getWidth();
//            this.mAmplifyHeight = (int) (((double) windowManager.getDefaultDisplay().getHeight()) * 0.06d);
//            this.mContentAmplify = LayoutInflater.from(this.mContext).inflate(R.layout.ppw_keyboard_amplify, (ViewGroup) null);
//            this.mTvAmplify = (TextView) this.mContentAmplify.findViewById(R.id.ppw_keyboard_amplify_tv);
//            this.mTvAmplify.setWidth((int) (((double) mScreenWidth) * 0.1033d));
//            this.mTvAmplify.setHeight(this.mAmplifyHeight);
//            this.mPopupAmplify = new PopupWindow();
//            this.mPopupAmplify.setWidth(-2);
//            this.mPopupAmplify.setHeight(-2);
//            this.mPopupAmplify.setContentView(this.mContentAmplify);
//        }
    }

    private void longPressBack() {
        this.mDeleteHandler = new Handler();
        this.mDeleteUpdate = new Runnable() {
            /* class com.yoki.im.ui.popup.PopupKeyboardLayout.AnonymousClass1 */

            public void run() {
                if (PopupKeyboardLayout.this.mListener != null) {
                    PopupKeyboardLayout.this.mListener.onInputBackLongPress();
                }
                PopupKeyboardLayout.this.mDeleteHandler.postDelayed(PopupKeyboardLayout.this.mDeleteUpdate, 20);
            }
        };
    }

    public void setInputListener(InputListener listener) {
        this.mListener = listener;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0:
                view.setSelected(true);
                this.mInputKeyboardCode = ((Integer) view.getTag()).intValue();
                if (this.mInputKeyboardCode == 0) {
                    this.mListener.onInputLongPress(view, 12);
                    if (this.mPopupAmplify != null) {
                        amplifyShow(view, motionEvent);
                    }
                } else if (this.mInputKeyboardCode != 2 && this.mInputKeyboardCode == 1) {
                    this.mHandler.postDelayed(new Runnable() {
                        /* class com.yoki.im.ui.popup.PopupKeyboardLayout.AnonymousClass2 */

                        public void run() {
                            PopupKeyboardLayout.this.mDeleteHandler.post(PopupKeyboardLayout.this.mDeleteUpdate);
                        }
                    }, this.mLongDeleteTime);
                }
                this.mStartX = (int) motionEvent.getX();
                this.mStartY = (int) motionEvent.getY();
                break;
            case 1:
                if (this.mPopupAmplify.isShowing()) {
                    this.mPopupAmplify.dismiss();
                }
                view.setSelected(false);
                inputState(view);
                removeContinuousDelete();
                break;
            case 2:
                int lastX = (int) motionEvent.getX();
                int lastY = (int) motionEvent.getY();
                if (Math.abs(lastX - this.mStartX) > this.mClickRange || Math.abs(lastY - this.mStartY) > this.mClickRange) {
                    if (this.mPopupAmplify.isShowing()) {
                    }
                    removeContinuousDelete();
                    break;
                }
        }
        return true;
    }

    private void amplifyShow(View v, MotionEvent event) {
        int showX = (int) (event.getRawX() - event.getX());
        int showY = (int) ((event.getRawY() - event.getY()) - ((float) (this.mAmplifyHeight * 2)));
        if (this.mIsFirstShowAmplify) {
            this.mIsFirstShowAmplify = false;
            int showY2 = showY + this.mAmplifyHeight;
        }
        View view = this.mContentAmplify;
    }

    private void inputState(View v) {
        if (this.mInputKeyboardCode == 0) {
            this.mListener.onInputClick("" + ((Object) ((Button) v).getText()), 12);
        } else if (this.mInputKeyboardCode == 2) {
            this.mListener.onInputClick("", 14);
        } else if (this.mInputKeyboardCode == 1) {
            this.mListener.onInputClick("", 13);
        }
    }

    private void removeContinuousDelete() {
        this.mDeleteHandler.removeCallbacks(this.mDeleteUpdate);
        this.mHandler.removeCallbacksAndMessages(null);
    }

    public void showLayout(int layout) {
        switch (layout) {
            case 10:
                layoutShowAndHide(this.mVehiclePlateLayout, this.mEnglishLayout);
                return;
            case 11:
                layoutShowAndHide(this.mEnglishLayout, this.mVehiclePlateLayout);
                return;
            default:
                return;
        }
    }

    private void layoutShowAndHide(View show, View hide) {
//        if (show.getVisibility() != 0) {
//            show.setVisibility(0);
//        }
//        if (hide.getVisibility() != 8) {
//            hide.setVisibility(8);
//        }
    }

    public FrameLayout getRootLayout(int layoutType) {
        FrameLayout rootLayout = newRootLayout();
        switch (layoutType) {
            case 10:
                rootLayout.addView(this.mVehiclePlateLayout, 0);
                rootLayout.addView(this.mEnglishLayout, 1);
//                this.mVehiclePlateLayout.setVisibility(0);
//                this.mEnglishLayout.setVisibility(8);
                break;
            case 11:
                if (rootLayout.getChildAt(1) == null) {
                    rootLayout.addView(this.mEnglishLayout, 1);
                    break;
                }
                break;
        }
        return rootLayout;
    }

    public FrameLayout newRootLayout() {
        RelativeLayout.LayoutParams lpContainer = new RelativeLayout.LayoutParams(-1, -2);
        FrameLayout layout = new FrameLayout(this.mContext);
        layout.setLayoutParams(lpContainer);
        return layout;
    }

    public PopupWindow getPopupAmplify() {
        return this.mPopupAmplify;
    }
}
