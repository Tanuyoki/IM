package com.yoki.im.tools;

import android.view.View;
import android.widget.GridLayout;

public class LayoutParamsUtils {
    private LayoutParamsUtils(Builder builder) {
        int pos = 0;
        if (builder.mCreateViewListener != null) {
            for (int i = 0; i < builder.viewMaxCounts; i++) {
                if (builder.mCreateViewListener.onCreate(i, generate(pos, builder))) {
                    pos++;
                }
            }
        }
    }

    private GridLayout.LayoutParams generate(int pos, Builder builder) {
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        layoutParams.width = builder.viewWidth;
        layoutParams.height = builder.viewHeight;
        if (builder.viewWidth == 0) {
            layoutParams.columnSpec = GridLayout.spec(pos % builder.rowCounts, 1.0f);
        } else {
            layoutParams.columnSpec = GridLayout.spec(pos % builder.rowCounts);
        }
        if (builder.viewHeight == 0) {
            layoutParams.rowSpec = GridLayout.spec(pos / builder.rowCounts, 1.0f);
        } else {
            layoutParams.rowSpec = GridLayout.spec(pos / builder.rowCounts);
        }
        if (pos % builder.rowCounts != 0) {
            layoutParams.leftMargin = builder.spacingWidth;
        }
        if (pos / builder.rowCounts != 0) {
            layoutParams.topMargin = builder.spacingHeight;
        }
        return layoutParams;
    }

    public static class Builder {
        public static final int LAYOUT_PARAMS_TYPE_FILTER = 0;
        private OnCreateViewImpl mCreateViewListener;
        public View.OnClickListener onCheckBoxCustomClickListener = new View.OnClickListener() {
            /* class com.yoki.im.tools.LayoutParamsUtils.Builder.AnonymousClass1 */

            public void onClick(View v) {
                if (v instanceof CheckBoxCustom) {
                    CheckBoxCustom box = (CheckBoxCustom) v;
                    if (box.isRepeatSelected()) {
                        box.setSelected(false);
                    } else if (box.isSelected()) {
                        box.setSelected(false);
                    } else {
                        box.setSelected(true);
                    }
                }
            }
        };
        public View.OnClickListener onSingleSelectionClickListener = new View.OnClickListener() {
            /* class com.yoki.im.tools.LayoutParamsUtils.Builder.AnonymousClass2 */

            public void onClick(View v) {
                if (v instanceof CheckBoxCustom) {
                }
            }
        };
        private int rowCounts;
        private int spacingHeight;
        private int spacingWidth;
        private int viewHeight;
        private int viewMaxCounts;
        private int viewWidth;

        public Builder setLayoutParamsType(int layoutParamsType) {
            if (layoutParamsType == 0) {
                setSpacingWidth(CommonData.ScreenWidth / 36).setSpacingHeight(CommonData.ScreenWidth / 46).setViewHeight(CommonData.ScreenWidth / 12).setRowCounts(3);
            }
            return this;
        }

        public Builder setOnCreateViewListener(OnCreateViewImpl listener) {
            this.mCreateViewListener = listener;
            return this;
        }

        public Builder setMaxCounts(int viewMaxCounts2) {
            this.viewMaxCounts = viewMaxCounts2;
            return this;
        }

        public Builder setSpacingWidth(int spacingWidth2) {
            this.spacingWidth = spacingWidth2;
            return this;
        }

        public Builder setSpacingHeight(int spacingHeight2) {
            this.spacingHeight = spacingHeight2;
            return this;
        }

        public Builder setViewWidth(int viewWidth2) {
            this.viewWidth = viewWidth2;
            return this;
        }

        public Builder setViewHeight(int viewHeight2) {
            this.viewHeight = viewHeight2;
            return this;
        }

        public Builder setRowCounts(int rowCounts2) {
            this.rowCounts = rowCounts2;
            return this;
        }

        public Builder build() {
            new LayoutParamsUtils(this);
            return this;
        }
    }
}
