package com.yoki.im.ui;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.widget.AppCompatEditText;

public class EditTextCustom extends AppCompatEditText {
    private InputMethodManager inputMethodManager;
    private Context mContext;
    private int mCurPos = 0;
    private int mLastPos = 0;
    private EditTextSelectChange mSelectChange;

    public interface EditTextSelectChange {
        void change(int i, int i2);
    }

    public void setEditTextSelectChange(EditTextSelectChange selectChange) {
        this.mSelectChange = selectChange;
    }

    public EditTextCustom(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public EditTextCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public EditTextCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    /* access modifiers changed from: protected */
    public void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        if (this.mSelectChange != null) {
            this.mCurPos = selEnd;
            this.mSelectChange.change(this.mLastPos, this.mCurPos);
            this.mLastPos = this.mCurPos;
        }
    }

    private void init() {
        this.inputMethodManager = (InputMethodManager) this.mContext.getSystemService("input_method");
        initEvent();
    }

    private void initEvent() {
        setOnTouchListener(new View.OnTouchListener() {
            /* class com.yoki.im.ui.EditTextCustom.AnonymousClass1 */

            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() != 0) {
                    return false;
                }
                EditTextCustom.this.setCursorVisible(true);
                return false;
            }
        });
    }

    public void closeKeyboard() {
        if (this.inputMethodManager.isActive()) {
            this.inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
        }
    }

    public void cleanAllState() {
        if (hasFocus()) {
            clearFocus();
        }
        if (Build.VERSION.SDK_INT >= 16 && isCursorVisible()) {
            setCursorVisible(false);
        }
        if (this.inputMethodManager.isActive()) {
            this.inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
        }
    }
}
