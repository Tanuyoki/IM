package com.yoki.im.tools;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import java.lang.reflect.Method;

public class EditTextUtils {
    public static int getEditTextCursorIndex(EditText mEditText) {
        return mEditText.getSelectionStart();
    }

    public static void insertText(EditText mEditText, String mText) {
        mEditText.getText().insert(getEditTextCursorIndex(mEditText), mText);
    }

    public static void deleteText(EditText mEditText) {
        if (!String.valueOf(mEditText.getText()).isEmpty() && getEditTextCursorIndex(mEditText) - 1 >= 0) {
            mEditText.getText().delete(getEditTextCursorIndex(mEditText) - 1, getEditTextCursorIndex(mEditText));
        }
    }

    public static void showSoftKeyboard(Activity activity, EditText editText) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService("input_method");
        editText.setVisibility(View.VISIBLE);
        if (imm != null) {
            editText.requestFocus();
            editText.setSelection(editText.getText().length());
            imm.showSoftInput(editText, 1);
        }
    }

    public static void forbiddenShowSoftKeyboard(EditText editText) {
        try {
            Method method = EditText.class.getMethod("setShowSoftInputOnFocus", Boolean.TYPE);
            method.setAccessible(true);
            method.invoke(editText, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideSoftKeyboard(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService("input_method");
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void requestFocus(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }

    public static void forceVisibleKeyboard(Window window, boolean visible) {
        if (window != null) {
            window.setSoftInputMode(visible ? 4 : 2);
        }
    }
}
