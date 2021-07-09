package com.yoki.im.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yoki.im.base.adapter.ViewHolder;
import com.yoki.im.base.adapter.base.CommonBaseAdapter;
import com.yoki.im.base.adapter.interfaces.OnListItemClickListener;
import com.yoki.im.tools.DensityUtils;
import com.yoki.im.tools.ViewUtils;
import com.yoki.im.ui.CustomLinearLayoutManager;
import com.yoki.im.ui.LineItemDecoration;
import com.yoki.im.ui.popup.PopupSelectListener;
import com.yoki.im.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DialogSingleSelection extends BaseDialog {
    private final List<Map<String, Object>> list;
    private CommonBaseAdapter<Map<String, Object>> mAdapter;
    private EditText mBindEditView;
    private ImageView mBindImageView;
    private TextView mBindTextView;
    private int mLastPos;
    private final String[] mLeftText;
    private final int mPopupGravity;
    private View mPopupView;
    private final int mPopupX;
    private final int mPopupY;
    private RecyclerView mRecyclerView;
    private final String mSelectText;

    public static class Builder {
        public static final int POPUP_STYLE_NORMAL = 0;
        public static final int POPUP_STYLE_PAY = 1;
        private Context context;
        private EditText editText;
        private int gravity;
        private ImageView imageView;
        private String[] leftText;
        public PopupSelectListener listener;
        private OnDismissListener onDismissListener;
        private int popupStyle;
        private int popupX;
        private int popupY;
        private int[] rightImageId;
        private String selectText;
        private TextView textView;
        private Window window;

        public Builder(Context context2, String... leftText2) {
            this(context2, leftText2, 0);
        }

        public Builder(Context context2, ArrayList leftText2) {
            this(context2, (String[]) leftText2.toArray(new String[leftText2.size()]));
        }

        public Builder(Context context2, Map<String, String> leftText2) {
            this(context2, (String[]) leftText2.values().toArray(new String[0]), 0);
        }

        public Builder(Context context2, String[] leftText2, int popupStyle2) {
            this.gravity = 17;
            this.popupX = 0;
            this.popupY = 0;
            this.context = context2;
            this.leftText = leftText2;
            this.popupStyle = popupStyle2;
        }

        public Builder setOnItemSelectListener(PopupSelectListener listener2) {
            this.listener = listener2;
            return this;
        }

        public Builder setOnDismissListener(OnDismissListener listener2) {
            this.onDismissListener = listener2;
            return this;
        }

        public Builder bindResultView(TextView textView2) {
            this.textView = textView2;
            return this;
        }

        public Builder bindResultView(EditText editText2) {
            this.editText = editText2;
            return this;
        }

        public Builder setSelectPosition(String text) {
            this.selectText = text;
            return this;
        }

        public Builder setRightImageId(int[] rightImageId2) {
            this.rightImageId = rightImageId2;
            return this;
        }

        public Builder bindResultView(ImageView imageView2) {
            this.imageView = imageView2;
            return this;
        }

        public Builder setShowPosition(int gravity2, int x, int y) {
            this.gravity = gravity2;
            this.popupX = x;
            this.popupY = y;
            return this;
        }

        public DialogSingleSelection build() {
            return new DialogSingleSelection(this);
        }
    }

    private DialogSingleSelection(final Builder builder) {
        super(builder.context);
        View viewBackground = null;
        this.mPopupGravity = builder.gravity;
        this.mPopupX = builder.popupX;
        this.mPopupY = builder.popupY;
        this.mLeftText = builder.leftText;
        this.mSelectText = builder.selectText;
        int[] rightIconId = null;
        int style = builder.popupStyle;
        rightIconId = builder.rightImageId != null ? builder.rightImageId : rightIconId;
        if (builder.textView != null) {
            this.mBindTextView = builder.textView;
        }
        if (builder.editText != null) {
            this.mBindEditView = builder.editText;
        }
        if (builder.imageView != null) {
            this.mBindImageView = builder.imageView;
        }
        if (builder.onDismissListener != null) {
            setOnDismissListener(builder.onDismissListener);
        }
        this.mLastPos = 0;
        this.list = createData(this.mLeftText, rightIconId, this.mBindTextView, this.mBindEditView);
        OnListItemClickListener<Map<String, Object>> onListItemClickListener = new OnListItemClickListener<Map<String, Object>>() {
            /* class com.yoki.im.ui.dialog.DialogSingleSelection.AnonymousClass1 */

            public void onItemClick(ViewHolder viewHolder, Map<String, Object> data, int position) {
                ((Map) DialogSingleSelection.this.list.get(DialogSingleSelection.this.mLastPos)).put("state", false);
                data.put("state", true);
                DialogSingleSelection.this.mAdapter.notifyItemChanged(DialogSingleSelection.this.mLastPos);
                DialogSingleSelection.this.mAdapter.notifyItemChanged(position);
                DialogSingleSelection.this.mLastPos = position;
                String text = String.valueOf(data.get("text"));
                if (DialogSingleSelection.this.mBindTextView != null) {
                    DialogSingleSelection.this.mBindTextView.setText(text);
                }
                if (DialogSingleSelection.this.mBindEditView != null) {
                    DialogSingleSelection.this.mBindEditView.setText(text);
                }
                if (DialogSingleSelection.this.mBindImageView != null) {
                    DialogSingleSelection.this.mBindImageView.setBackgroundResource(((Integer) data.get("image")).intValue());
                }
                if (builder.listener != null) {
                    builder.listener.onItemClickListener(text);
                }
                DialogSingleSelection.this.dismiss();
            }
        };
        if (style == 1) {
//            this.mPopupView = View.inflate(this.mContext, R.layout.ppw_paymode, null);
//            this.mAdapter = new DialogPayModeAdp(this.mContext, this.list, false);
//            this.mRecyclerView = (RecyclerView) this.mPopupView.findViewById(R.id.ppw_paymode_recycler_view);
        } else {
//            this.mPopupView = View.inflate(this.mContext, R.layout.d_ppw_single_selected, null);
//            this.mAdapter = new DialogSingleSelectionAdp(this.mContext, this.list, false);
//            this.mRecyclerView = (RecyclerView) this.mPopupView.findViewById(R.id.ppw_single_selected_recycler_view);
//            viewBackground = this.mPopupView.findViewById(R.id.ppw_single_selected_background);
//            LineItemDecoration decoration = new LineItemDecoration(this.mContext, 1, R.drawable.all_list_line);
//            decoration.setHorizontalSpacing(R.drawable.all_list_line_transparent, DensityUtils.dip2px(15.0f));
//            this.mRecyclerView.addItemDecoration(decoration);
        }
        CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(this.mContext);
        layoutManager.setFixedCountItem(4);
        this.mAdapter.setOnItemClickListener(onListItemClickListener);
        this.mRecyclerView.setLayoutManager(layoutManager);
        this.mRecyclerView.setAdapter(this.mAdapter);
        if (viewBackground != null) {
            viewBackground.setMinimumHeight(ViewUtils.unDisplayViewSize(this.mRecyclerView)[1] + 40);
        }
        setContentView(this.mPopupView);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    private List<Map<String, Object>> createData(String[] leftText, int[] rightIconId, TextView textView, EditText editText) {
        if (leftText == null || leftText.length <= 0) {
            return null;
        }
        List<Map<String, Object>> list2 = new ArrayList<>();
        for (int i = 0; i < leftText.length; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("text", leftText[i]);
            if (rightIconId != null) {
                map.put("image", Integer.valueOf(rightIconId[i]));
            }
            if (this.mSelectText != null) {
                if (this.mSelectText.isEmpty()) {
                    map.put("state", false);
                } else {
                    map.put("state", true);
                    this.mLastPos = i;
                }
            } else if (textView != null) {
                if (textView.getText().equals(leftText[i])) {
                    map.put("state", true);
                    this.mLastPos = i;
                } else {
                    map.put("state", false);
                }
            } else if (editText != null) {
                if (String.valueOf(editText.getText()).equals(leftText[i])) {
                    map.put("state", true);
                    this.mLastPos = i;
                } else {
                    map.put("state", false);
                }
            } else if (i == 0) {
                map.put("state", true);
            } else {
                map.put("state", false);
            }
            list2.add(map);
        }
        return list2;
    }

    public void refreshBindViewSelection() {
        if (!(this.mLeftText == null || this.mBindTextView == null || this.list == null || this.mAdapter == null)) {
            for (int i = 0; i < this.mLeftText.length; i++) {
                if (this.mBindTextView.getText().equals(this.mLeftText[i])) {
                    this.list.get(i).put("state", true);
                    this.mLastPos = i;
                } else {
                    this.list.get(i).put("state", false);
                }
            }
        }
    }

    public void show() {
        super.show();
    }
}
