package com.yoki.im.ui.popup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import com.yoki.im.base.BasePopupWindow;
import com.yoki.im.tools.EditTextUtils;
import com.yoki.im.tools.ScreenUtils;
import com.yoki.im.tools.StringUtils;
import com.yoki.im.ui.EditTextCustom;
import com.yoki.im.ui.popup.PopupKeyboardLayout;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class PopupKeyboard extends BasePopupWindow implements PopupKeyboardLayout.InputListener {
    public static final int KEYBOARD_CODE_BACK = 13;
    public static final int KEYBOARD_CODE_CONFIRM = 14;
    public static final int KEYBOARD_CODE_TEXT = 12;
    public static final int KEYBOARD_TYPE_ENGLISH = 11;
    public static final int KEYBOARD_TYPE_VEHICLE_PLATE = 10;
    private static PopupKeyboard instance = null;
    private Activity mActivity;
    private Context mContext;
    private int mCurrPosition = 0;
    View.OnClickListener mEditOnClickListener = new View.OnClickListener() {
        /* class com.yoki.im.ui.popup.PopupKeyboard.AnonymousClass1 */

        public void onClick(View v) {
            if (PopupKeyboard.this.mRootLayout != null) {
                if (PopupKeyboard.this.isShowVehiclePlate()) {
                    PopupKeyboard.this.mLayout.showLayout(10);
                }
                PopupKeyboard.this.showAtLocation(PopupKeyboard.this.mRootLayout, 80, 0, 0);
            }
        }
    };
    View.OnFocusChangeListener mEditOnFocusChangeListener = new View.OnFocusChangeListener() {
        /* class com.yoki.im.ui.popup.PopupKeyboard.AnonymousClass2 */

        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
//                EditTextUtils.hideSoftKeyboard(v);
                PopupKeyboard.this.mCurrPosition = ((Integer) v.getTag()).intValue();
                if (PopupKeyboard.this.mIsAutoPop[PopupKeyboard.this.mCurrPosition]) {
                    PopupKeyboard.this.startShow();
                } else {
                    PopupKeyboard.this.mEditText[PopupKeyboard.this.mCurrPosition].performClick();
                }
            } else {
                PopupKeyboard.this.dismiss();
            }
        }
    };
    private EditTextCustom[] mEditText;
    private boolean mHasVehiclePlate = false;
    private boolean[] mIsAutoPop;
    private boolean mIsContainsCh = false;
    private KeyboardCallback mKeyboardCallback;
    private PopupKeyboardLayout mLayout;
    private int[] mLayoutType;
    private FrameLayout mRootLayout;
    private ViewGroup mScreen;
    private int mSelectCurrPosition = 0;
    private int mSelectLastPosition = 0;

    public interface KeyboardCallback {
        void onShow();
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface KeyboardInputType {
    }

    public PopupKeyboard(Context context, ViewGroup mScreen2) {
        this.mContext = context;
        this.mScreen = mScreen2;
        init();
    }

    @SuppressLint({"ClickableViewAccessibility"})
    private void init() {
        setFocusable(false);
        setOutsideTouchable(false);
        setOnDismissListener(new PopupWindow.OnDismissListener() {
            /* class com.yoki.im.ui.popup.PopupKeyboard.AnonymousClass3 */

            public void onDismiss() {
                if (PopupKeyboard.this.mLayout.getPopupAmplify().isShowing()) {
                    PopupKeyboard.this.mLayout.getPopupAmplify().dismiss();
                }
            }
        });
        this.mLayout = new PopupKeyboardLayout(this.mContext);
        this.mLayout.setInputListener(this);
        this.mScreen.setOnTouchListener(new View.OnTouchListener() {
            /* class com.yoki.im.ui.popup.PopupKeyboard.AnonymousClass4 */

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case 0:
                        return true;
                    case 1:
                        if (PopupKeyboard.this.isShowing()) {
                            PopupKeyboard.this.dismiss();
                            break;
                        }
                        break;
                }
                return false;
            }
        });
    }

    public static void dismissKeyboard() {
        if (instance.isShowing()) {
            instance.dismiss();
        }
    }

    public PopupKeyboard setKeyboardCallback(KeyboardCallback keyboardCallback) {
        this.mKeyboardCallback = keyboardCallback;
        return this;
    }

    @Override // com.yoki.im.base.BasePopupWindow
    public void show() {
        startShow();
    }

    public void startShow() {
        if (this.mRootLayout == null) {
            if (!this.mHasVehiclePlate) {
                this.mRootLayout = this.mLayout.getRootLayout(11);
            } else {
                this.mRootLayout = this.mLayout.getRootLayout(10);
            }
            setContentView(this.mRootLayout);
        }
        if (this.mLayoutType[this.mCurrPosition] != 10) {
            this.mLayout.showLayout(11);
        } else if (isShowVehiclePlate()) {
            this.mLayout.showLayout(10);
        } else {
            this.mLayout.showLayout(11);
        }
        if (this.mKeyboardCallback != null) {
            this.mKeyboardCallback.onShow();
        }
        if (ScreenUtils.checkDeviceHasNavigationBar(this.mContext)) {
            showAtLocation(this.mRootLayout, 80, 0, ScreenUtils.getVirtualBarHeight(this.mContext));
        } else {
            showAtLocation(this.mRootLayout, 80, 0, 0);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean isShowVehiclePlate() {
        return this.mLayoutType[this.mCurrPosition] == 10 && this.mSelectCurrPosition == 0 && !this.mIsContainsCh;
    }

    public PopupKeyboard setVehiclePlate(EditTextCustom editText) {
        setInputType(10);
        setRelevance(editText);
        setAutoPop(true);
        initEditText();
        return this;
    }

    public PopupKeyboard setInputType(int... type) {
        this.mLayoutType = type;
        return this;
    }

    public PopupKeyboard setRelevance(EditTextCustom... editText) {
        this.mEditText = editText;
        return this;
    }

    public PopupKeyboard setAutoPop(boolean... isAutoPop) {
        this.mIsAutoPop = isAutoPop;
        return this;
    }

    public PopupKeyboard initEditText() {
        initEdit();
        return this;
    }

    private void initEdit() {
        View.OnKeyListener onEditKeyListener = new View.OnKeyListener() {
            /* class com.yoki.im.ui.popup.PopupKeyboard.AnonymousClass5 */

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode != 4 || event.getAction() != 1 || !PopupKeyboard.this.isShowing()) {
                    return false;
                }
                PopupKeyboard.this.dismiss();
                return true;
            }
        };
        EditTextCustom.EditTextSelectChange onEditSelectChange = new EditTextCustom.EditTextSelectChange() {
            /* class com.yoki.im.ui.popup.PopupKeyboard.AnonymousClass6 */

            @Override // com.yoki.im.ui.EditTextCustom.EditTextSelectChange
            public void change(int lastPos, int curPos) {
                boolean z = false;
                PopupKeyboard.this.mSelectLastPosition = lastPos;
                PopupKeyboard.this.mSelectCurrPosition = curPos;
                if (PopupKeyboard.this.mLayoutType[PopupKeyboard.this.mCurrPosition] == 10) {
                    PopupKeyboard popupKeyboard = PopupKeyboard.this;
                    if (PopupKeyboard.this.mEditText[PopupKeyboard.this.mCurrPosition].length() > 0 && !StringUtils.isContainsType(Character.valueOf(PopupKeyboard.this.mEditText[PopupKeyboard.this.mCurrPosition].getText().charAt(0)), 13)) {
                        z = true;
                    }
                    popupKeyboard.mIsContainsCh = z;
                    if (curPos == 0 && !PopupKeyboard.this.mIsContainsCh) {
                        PopupKeyboard.this.mLayout.showLayout(10);
                    } else if (curPos != 0) {
                        PopupKeyboard.this.mLayout.showLayout(11);
                    }
                } else {
                    PopupKeyboard.this.mLayout.showLayout(11);
                }
            }
        };
        for (int i = 0; i < this.mEditText.length; i++) {
            if (this.mEditText != null) {
                if (!this.mHasVehiclePlate) {
                    this.mHasVehiclePlate = this.mLayoutType[i] == 10;
                }
                EditTextUtils.forbiddenShowSoftKeyboard(this.mEditText[i]);
                this.mEditText[i].addTextChangedListener(new EditChangedListener());
                this.mEditText[i].setOnFocusChangeListener(this.mEditOnFocusChangeListener);
                this.mEditText[i].setOnClickListener(this.mEditOnClickListener);
                this.mEditText[i].setOnKeyListener(onEditKeyListener);
                this.mEditText[i].setEditTextSelectChange(onEditSelectChange);
                this.mEditText[i].setTag(Integer.valueOf(i));
            }
        }
    }

    public int getCurrInputType() {
        return this.mLayoutType[this.mCurrPosition];
    }

    @Override // com.yoki.im.ui.popup.PopupKeyboardLayout.InputListener
    public void onInputClick(String input, int primaryCode) {
        switch (primaryCode) {
            case 12:
                if (this.mLayoutType[this.mCurrPosition] == 10 && this.mSelectCurrPosition == 0 && this.mIsContainsCh) {
                    this.mEditText[this.mCurrPosition].setSelection(this.mEditText[this.mCurrPosition].getText().length());
                }
                EditTextUtils.insertText(this.mEditText[this.mCurrPosition], input);
                return;
            case 13:
                EditTextUtils.deleteText(this.mEditText[this.mCurrPosition]);
                return;
            case 14:
                EditTextUtils.requestFocus(this.mEditText[this.mCurrPosition]);
                dismiss();
                return;
            default:
                return;
        }
    }

    @Override // com.yoki.im.ui.popup.PopupKeyboardLayout.InputListener
    public void onInputLongPress(View v, int primaryCode) {
    }

    @Override // com.yoki.im.ui.popup.PopupKeyboardLayout.InputListener
    public void onInputBackLongPress() {
        EditTextUtils.deleteText(this.mEditText[this.mCurrPosition]);
    }

    /* access modifiers changed from: package-private */
    public class EditChangedListener implements TextWatcher {
        EditChangedListener() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (PopupKeyboard.this.mLayoutType[PopupKeyboard.this.mCurrPosition] != 10) {
                return;
            }
            if (s.length() == 0) {
                PopupKeyboard.this.mLayout.showLayout(10);
            } else {
                PopupKeyboard.this.mLayout.showLayout(11);
            }
        }

        public void afterTextChanged(Editable s) {
        }
    }
}
