package com.yoki.im.ui;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.SpannableString;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.yoki.im.tools.im.iminput.emoji.SpanStringUtils;

public class EditTextImInput extends AppCompatEditText {
    private static final String EXPRESSION = "Expression";
    private String lastPaste = "";
    private OnTextContextMenuItem mListener;

    public interface OnTextContextMenuItem {
        void onPaste();
    }

    public EditTextImInput(Context context) {
        super(context);
    }

    public EditTextImInput(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditTextImInput(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint({"SetTextI18n"})
    public boolean onTextContextMenuItem(int id) {
        ClipboardManager clip;
        SpannableString spn;
        if (id == 16908322 && (clip = (ClipboardManager) getContext().getSystemService("clipboard")) != null) {
            ClipData clipData = clip.getPrimaryClip();
            clip.setPrimaryClip(ClipData.newPlainText(EXPRESSION, ""));
            String text = String.valueOf(getText());
            String pasteText = String.valueOf(clipData.getItemAt(0).getText());
            if (pasteText.isEmpty()) {
                spn = SpanStringUtils.getEmotionContent(getContext(), this, text + this.lastPaste, 1);
            } else {
                spn = SpanStringUtils.getEmotionContent(getContext(), this, text + pasteText, 1);
                this.lastPaste = pasteText;
            }
            setText(spn);
            setSelection(getText().length());
            if (this.mListener != null) {
                this.mListener.onPaste();
            }
        }
        return super.onTextContextMenuItem(id);
    }

    public void setOnTextContextMenuItem(OnTextContextMenuItem listener) {
        this.mListener = listener;
    }
}
