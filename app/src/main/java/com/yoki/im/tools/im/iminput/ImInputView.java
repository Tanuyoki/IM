package com.yoki.im.tools.im.iminput;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.yoki.im.fragment.ImInputExpressionFragment;
import com.yoki.im.fragment.ImInputModeFragment;
import com.yoki.im.tools.CommonData;
import com.yoki.im.tools.EditTextUtils;
import com.yoki.im.tools.KeyboardChangeUtils;
import com.yoki.im.tools.im.iminput.emoji.GlobalOnItemClickManagerUtils;
import com.yoki.im.ui.EditTextImInput;
import com.yoki.im.ui.TextViewVoice;
import com.yoki.im.R;
import java.util.ArrayList;
import java.util.List;

public class ImInputView extends RelativeLayout implements View.OnClickListener, ImInputModeFragment.ChatInputModeFragmentListener {
    private Activity mActivity;
    private ArrayList<View> mArrayOrder;
    private View mContainer;
    private View mEditLine;
    private EditTextImInput mEditView;
    private ImInputExpressionFragment mExpressionFragment;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private boolean mHasEditFocus = false;
    private InputMethodManager mInputManager;
    private boolean mIsKeyboardShowing = false;
    private OnInputChangeListener mListener;
    private View mMenu;
    private View mMenuExpression;
    private int mMenuHeight = 0;
    private View mMenuMode;
    private View mMenuVoice;
    private int mMinMenuHeight = 0;
    private ImInputModeFragment mModeFragment;
    private ViewPager mOrderPager;
    private TextView mSend;
    private int mState = 0;
    private TextViewVoice mVoice;
    private PagerAdapter pagerAdapter = new PagerAdapter() {
        /* class com.yoki.im.tools.im.iminput.ImInputView.AnonymousClass1 */
        private TextView mText;

        @Override // android.support.v4.view.PagerAdapter
        public int getCount() {
            return ImInputView.this.mArrayOrder.size();
        }

        @Override // android.support.v4.view.PagerAdapter
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView((View) ImInputView.this.mArrayOrder.get(position));
            this.mText = (TextView) ((View) ImInputView.this.mArrayOrder.get(position)).findViewById(R.id.layout_order_pager_tv_text);
            this.mText.setText("粤12345" + position);
            this.mText.setOnClickListener(new OnClickListener() {
                /* class com.yoki.im.tools.im.iminput.ImInputView.AnonymousClass1.AnonymousClass1 */

                public void onClick(View v) {
                    if (ImInputView.this.mListener != null) {
                        List<String> list = new ArrayList<>();
                        list.add(String.valueOf(((TextView) v).getText()));
                        list.add("256");
                        list.add("Test");
                        list.add("$100");
                        ImInputView.this.mListener.onClickOrder(list);
                    }
                }
            });
            return ImInputView.this.mArrayOrder.get(position);
        }

        @Override // android.support.v4.view.PagerAdapter
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) ImInputView.this.mArrayOrder.get(position));
        }

        @Override // android.support.v4.view.PagerAdapter
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override // android.support.v4.view.PagerAdapter
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override // android.support.v4.view.PagerAdapter
        public CharSequence getPageTitle(int position) {
            return null;
        }

        @Override // android.support.v4.view.PagerAdapter
        public float getPageWidth(int position) {
            return 0.2222f;
        }
    };
    private SharedPreferences sp;
    private TextWatcher textWatcher = new TextWatcher() {
        /* class com.yoki.im.tools.im.iminput.ImInputView.AnonymousClass8 */

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            boolean z = true;
            ImInputView imInputView = ImInputView.this;
            if (s.length() < 1) {
                z = false;
            }
            imInputView.setExpressionLayoutParams(z);
        }

        public void afterTextChanged(Editable s) {
        }
    };

    public interface OnInputChangeListener {
        void onClickImage(String str);

        void onClickOrder(List<String> list);

        void onClickText(String str);

        void onClickVoice(String str, long j);

        void onScrollToBottom();
    }

    public ImInputView(Context context) {
        super(context);
    }

    public ImInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnInputClickListener(OnInputChangeListener listener) {
        this.mListener = listener;
    }

    @Override // com.yoki.im.fragment.ImInputModeFragment.ChatInputModeFragmentListener
    public void getPhoto(String imagePath) {
        if (this.mListener != null) {
            this.mListener.onClickImage(imagePath);
        }
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public void init(AppCompatActivity activity) {
        this.mActivity = activity;
        this.mFragmentManager = activity.getSupportFragmentManager();
        this.mInputManager = (InputMethodManager) this.mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        this.mOrderPager = (ViewPager) activity.findViewById(R.id.im_input_order_pager);
        this.mMenuVoice = activity.findViewById(R.id.im_input_iv_voice);
        this.mMenuExpression = activity.findViewById(R.id.im_input_iv_expression);
        this.mMenuMode = activity.findViewById(R.id.im_input_iv_mode);
        this.mVoice = (TextViewVoice) activity.findViewById(R.id.im_input_tv_voice);
        this.mSend = (TextView) activity.findViewById(R.id.im_input_tv_send);
        this.mEditView = (EditTextImInput) activity.findViewById(R.id.im_input_edt_input);
        this.mEditLine = activity.findViewById(R.id.im_input_edt_line);
        this.mContainer = activity.findViewById(R.id.im_content_container);
        resetView();
        this.mVoice.init(activity);
        this.mEditView.addTextChangedListener(this.textWatcher);
        this.mMenuVoice.setOnClickListener(this);
        this.mMenuExpression.setOnClickListener(this);
        this.mMenuMode.setOnClickListener(this);
        this.mSend.setOnClickListener(this);
//        this.sp = AppUtils.getSharedPreferences();
        if (this.sp == null || this.sp.getInt("im_menu_height", 0) == 0) {
            this.mMinMenuHeight = (int) (((double) CommonData.ScreenHeight) * 0.4d);
        } else {
            this.mMinMenuHeight = this.sp.getInt("im_menu_height", 0);
        }
        LayoutInflater lf = activity.getLayoutInflater();
        this.mArrayOrder = new ArrayList<>();
//        this.mArrayOrder.add(lf.inflate(R.layout.layout_order_pager1, (ViewGroup) null));
        this.mOrderPager.setPageMargin((int) (((double) CommonData.ScreenWidth) * 0.0277d));
        this.mOrderPager.setAdapter(this.pagerAdapter);
        GlobalOnItemClickManagerUtils.getInstance(getContext()).attachToEditText(this.mEditView);
        this.mEditView.setOnKeyListener(new OnKeyListener() {
            /* class com.yoki.im.tools.im.iminput.ImInputView.AnonymousClass2 */

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode != 4 || event.getAction() != 1 || ImInputView.this.mMenu.getHeight() == 0) {
                    return false;
                }
                ImInputView.this.setMenuHeight(0);
                return true;
            }
        });
        this.mEditView.setOnFocusChangeListener(new OnFocusChangeListener() {
            /* class com.yoki.im.tools.im.iminput.ImInputView.AnonymousClass3 */

            public void onFocusChange(View v, boolean hasFocus) {
                ImInputView.this.mHasEditFocus = hasFocus;
            }
        });
        this.mEditView.setOnClickListener(new OnClickListener() {
            /* class com.yoki.im.tools.im.iminput.ImInputView.AnonymousClass4 */

            public void onClick(View v) {
                ImInputView.this.resetView();
                ImInputView.this.hideModeFragment();
                ImInputView.this.hideExpressionFragment();
            }
        });
        this.mEditView.setOnTextContextMenuItem(new EditTextImInput.OnTextContextMenuItem() {
            /* class com.yoki.im.tools.im.iminput.ImInputView.AnonymousClass5 */

            @Override // com.yoki.im.ui.EditTextImInput.OnTextContextMenuItem
            public void onPaste() {
                ImInputView.this.onScrollToBottom();
            }
        });
        this.mVoice.setOnTextVoiceListener(new TextViewVoice.TextVoiceListener() {
            /* class com.yoki.im.tools.im.iminput.ImInputView.AnonymousClass6 */

            @Override // com.yoki.im.ui.TextViewVoice.TextVoiceListener
            public void onStart() {
                ImInputView.this.onScrollToBottom();
            }

            @Override // com.yoki.im.ui.TextViewVoice.TextVoiceListener
            public void onEnd(String filePath, long durationTime) {
                if (ImInputView.this.mListener != null) {
                    ImInputView.this.mListener.onClickVoice(filePath, durationTime);
                }
            }
        });
    }

    public void addOnSoftKeyBoardVisibleListener() {
        new KeyboardChangeUtils().addOnSoftKeyBoardVisibleListener(this.mActivity, new KeyboardChangeUtils.KeyboardChangeListener() {
            /* class com.yoki.im.tools.im.iminput.ImInputView.AnonymousClass7 */

            @Override // com.yoki.im.tools.KeyboardChangeUtils.KeyboardChangeListener
            public void onSoftKeyBoardVisible(boolean visible, int height, int code) {
                if (code == 10 || code == 20) {
                    if ((visible || ImInputView.this.mHasEditFocus) && !ImInputView.this.mMenuVoice.isSelected()) {
                        ImInputView.this.mMenuHeight = height;
                        if (height != 0) {
                            ImInputView.this.setMenuHeight(height);
                            ImInputView.this.onScrollToBottom();
                        }
                    }
                } else if (code == 11 && !ImInputView.this.mMenuExpression.isSelected() && !ImInputView.this.mMenuMode.isSelected()) {
                    ImInputView.this.setMenuHeight(0);
                }
                ImInputView.this.mIsKeyboardShowing = visible;
            }
        });
    }

    public void setMenuHeight(int height) {
        if (height == 0) {
            hideModeFragment();
            hideExpressionFragment();
        } else {
            onScrollToBottom();
            if (this.sp != null) {
                this.sp.edit().putInt("im_menu_height", height).apply();
            }
        }
        this.mMenu.getLayoutParams().height = height;
        this.mMenu.setLayoutParams(this.mMenu.getLayoutParams());
    }

    public void bindContentView(View view) {
        this.mMenu = view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_input_iv_expression /*{ENCODED_INT: 2131296857}*/:
                switchState(false);
                return;
            case R.id.im_input_iv_mode /*{ENCODED_INT: 2131296858}*/:
                if (this.mMenuVoice.isSelected() || this.mMenuExpression.isSelected() || !this.mMenuMode.isSelected() || this.mIsKeyboardShowing) {
                    hideKeyboard();
                    this.mMenuMode.setSelected(true);
                    onScrollToBottom();
                    showModeFragment();
                    hideExpressionFragment();
                    if (this.mMenu.getHeight() == 0) {
                        setMenuHeight(this.mMenuHeight == 0 ? this.mMinMenuHeight : this.mMenuHeight);
                    }
                } else {
                    showKeyboard();
                    hideModeFragment();
                }
                this.mMenuExpression.setSelected(false);
                this.mMenuVoice.setSelected(false);
                this.mVoice.setVisibility(View.INVISIBLE);
                return;
            case R.id.im_input_iv_voice /*{ENCODED_INT: 2131296859}*/:
                switchState(true);
                return;
            case R.id.im_input_line /*{ENCODED_INT: 2131296860}*/:
            case R.id.im_input_order /*{ENCODED_INT: 2131296861}*/:
            case R.id.im_input_order_pager /*{ENCODED_INT: 2131296862}*/:
            case R.id.im_input_order_pager_tip /*{ENCODED_INT: 2131296863}*/:
            default:
                return;
            case R.id.im_input_tv_send /*{ENCODED_INT: 2131296864}*/:
                if (this.mListener != null) {
                    this.mListener.onClickText(String.valueOf(this.mEditView.getText()));
                }
                this.mEditView.setText("");
                return;
        }
    }

    private void setEditTextVisibility(int visibility) {
        this.mEditView.setVisibility(visibility);
        this.mEditLine.setVisibility(visibility);
    }

    private void switchState(boolean isClickVoice) {
        int i;
        this.mFragmentTransaction = this.mFragmentManager.beginTransaction();
        if (this.mExpressionFragment == null) {
            this.mExpressionFragment = new ImInputExpressionFragment();
            this.mFragmentTransaction.add(R.id.im_frame, this.mExpressionFragment);
        }
        if (isClickVoice) {
            setExpressionLayoutParams(this.mMenuVoice.isSelected());
            if (this.mMenuVoice.isSelected()) {
                this.mVoice.setVisibility(View.INVISIBLE);
                showKeyboard();
            } else {
                this.mMenuVoice.setSelected(true);
                this.mVoice.setVisibility(View.VISIBLE);
                hideKeyboard();
            }
            this.mMenuExpression.setSelected(false);
            this.mFragmentTransaction.hide(this.mExpressionFragment);
            this.mFragmentTransaction.commit();
            setMenuHeight(0);
            return;
        }
        if (this.mMenuExpression.isSelected()) {
            this.mFragmentTransaction.hide(this.mExpressionFragment);
            showKeyboard();
        } else {
            this.mMenuExpression.setSelected(true);
            this.mFragmentTransaction.show(this.mExpressionFragment);
            onScrollToBottom();
            hideKeyboard();
        }
        this.mMenuVoice.setSelected(false);
        this.mVoice.setVisibility(View.INVISIBLE);
        if (this.mModeFragment != null) {
            this.mFragmentTransaction.hide(this.mModeFragment);
        }
        this.mFragmentTransaction.commit();
        if (this.mMenu.getHeight() == 0) {
            if (this.mMenuHeight == 0) {
                i = this.mMinMenuHeight;
            } else {
                i = this.mMenuHeight;
            }
            setMenuHeight(i);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onScrollToBottom() {
        if (this.mListener != null) {
            this.mListener.onScrollToBottom();
        }
    }

    private void showKeyboard() {
        resetView();
        setEditTextVisibility(0);
        EditTextUtils.showSoftKeyboard(this.mActivity, this.mEditView);
    }

    private void hideKeyboard() {
        if (this.mIsKeyboardShowing) {
            EditTextUtils.hideSoftKeyboard(mActivity, this.mEditView);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void resetView() {
        this.mMenuVoice.setSelected(false);
        this.mMenuExpression.setSelected(false);
        this.mMenuMode.setSelected(false);
    }

    public boolean isShowing() {
        return this.mMenu.getHeight() != 0 || this.mIsKeyboardShowing;
    }

    public boolean isKeyboardShowing() {
        return this.mIsKeyboardShowing;
    }

    public void reset() {
        resetView();
        if (this.mIsKeyboardShowing) {
            hideKeyboard();
        } else {
            setMenuHeight(0);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setExpressionLayoutParams(boolean isShowSendView) {
        int leftOf;
        if (isShowSendView) {
            this.mMenuMode.setVisibility(View.INVISIBLE);
            this.mSend.setVisibility(View.VISIBLE);
            leftOf = R.id.im_input_tv_send;
        } else {
            this.mMenuMode.setVisibility(View.VISIBLE);
            this.mSend.setVisibility(View.GONE);
            leftOf = R.id.im_input_iv_mode;
        }
        LayoutParams lp = new LayoutParams(this.mMenuExpression.getWidth(), this.mMenuExpression.getHeight());
        lp.addRule(0, leftOf);
        lp.addRule(15);
        this.mMenuExpression.setLayoutParams(lp);
    }

    private void showModeFragment() {
        this.mFragmentTransaction = this.mFragmentManager.beginTransaction();
        if (this.mModeFragment == null) {
            this.mModeFragment = new ImInputModeFragment();
            this.mModeFragment.setChatInputModeListener(this);
            this.mFragmentTransaction.add(R.id.im_frame, this.mModeFragment);
        } else {
            this.mFragmentTransaction.show(this.mModeFragment);
        }
        this.mFragmentTransaction.commit();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void hideModeFragment() {
        if (this.mModeFragment != null) {
            this.mFragmentTransaction = this.mFragmentManager.beginTransaction();
            this.mFragmentTransaction.hide(this.mModeFragment);
            this.mFragmentTransaction.commit();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void hideExpressionFragment() {
        if (this.mExpressionFragment != null) {
            this.mFragmentTransaction = this.mFragmentManager.beginTransaction();
            this.mFragmentTransaction.hide(this.mExpressionFragment);
            this.mFragmentTransaction.commit();
        }
    }
}
