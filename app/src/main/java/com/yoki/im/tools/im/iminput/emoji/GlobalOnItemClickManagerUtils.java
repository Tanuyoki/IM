package com.yoki.im.tools.im.iminput.emoji;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;

public class GlobalOnItemClickManagerUtils {
    private static GlobalOnItemClickManagerUtils instance;
    private static Context mContext;
    private EditText mEditText;

    public static GlobalOnItemClickManagerUtils getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            synchronized (GlobalOnItemClickManagerUtils.class) {
                if (instance == null) {
                    instance = new GlobalOnItemClickManagerUtils();
                }
            }
        }
        return instance;
    }

    public void attachToEditText(EditText editText) {
        this.mEditText = editText;
    }

    public AdapterView.OnItemClickListener getOnItemClickListener(final int emotionMapType) {
        return new AdapterView.OnItemClickListener() {
            /* class com.yoki.im.tools.im.iminput.emoji.GlobalOnItemClickManagerUtils.AnonymousClass1 */

            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Adapter itemAdapter = parent.getAdapter();
                if (itemAdapter instanceof EmotionGridViewAdapter) {
                    EmotionGridViewAdapter emotionGvAdapter = (EmotionGridViewAdapter) itemAdapter;
                    if (position == emotionGvAdapter.getCount() - 1) {
                        GlobalOnItemClickManagerUtils.this.mEditText.dispatchKeyEvent(new KeyEvent(0, 67));
                        return;
                    }
                    String emotionName = emotionGvAdapter.getItem(position);
                    int curPosition = GlobalOnItemClickManagerUtils.this.mEditText.getSelectionStart();
                    StringBuilder sb = new StringBuilder(GlobalOnItemClickManagerUtils.this.mEditText.getText().toString());
                    sb.insert(curPosition, emotionName);
                    GlobalOnItemClickManagerUtils.this.mEditText.setText(SpanStringUtils.getEmotionContent(GlobalOnItemClickManagerUtils.mContext, GlobalOnItemClickManagerUtils.this.mEditText, sb.toString(), emotionMapType));
                    GlobalOnItemClickManagerUtils.this.mEditText.setSelection(emotionName.length() + curPosition);
                }
            }
        };
    }
}
