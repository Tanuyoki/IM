package com.yoki.im.tools;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import com.yoki.im.R;
import java.util.List;
import java.util.UUID;

public class ViewUtils {
    private static int scrollToPosition = 0;

    public interface OnPayViewClickListener {
        void onPayViewClick(int i, View view);
    }

    public static int generateViewId() {
        if (Build.VERSION.SDK_INT >= 17) {
            return View.generateViewId();
        }
        return UUID.randomUUID().hashCode();
    }

    public static int[] unDisplayViewSize(View view) {
//        view.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        return new int[]{view.getMeasuredWidth(), view.getMeasuredHeight()};
    }

    public static void autoScrollView(View root, View... scrollToView) {
        for (View view : scrollToView) {
            autoScrollView(root, view);
        }
    }

    public static void autoScrollView(final View root, final View scrollToView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            /* class com.yoki.im.tools.ViewUtils.AnonymousClass1 */

            public void onGlobalLayout() {
                Rect rect = new Rect();
                root.getWindowVisibleDisplayFrame(rect);
                if (root.getRootView().getHeight() - rect.bottom > 150) {
                    int[] location = new int[2];
                    scrollToView.getLocationInWindow(location);
                    ViewUtils.scrollToPosition += (location[1] + scrollToView.getHeight()) - rect.bottom;
                } else {
                    int unused = ViewUtils.scrollToPosition = 0;
                }
                root.scrollTo(0, ViewUtils.scrollToPosition);
            }
        });
    }

    public static View generatePayResultView(Context ctx, String title, double amount, boolean isAddLine) {
//        View view = View.inflate(ctx, R.layout.layout_pay_result_item, null);
//        View viewLine = view.findViewById(R.id.layout_pay_result_item_line);
//        ((TextView) view.findViewById(R.id.layout_pay_result_item_tv_title)).setText(title);
//        ((TextView) view.findViewById(R.id.layout_pay_result_item_tv_amount)).setText("￥ " + amount);
//        if (!isAddLine) {
//            viewLine.setVisibility(8);
//        }
//        return view;
        return null;
    }

    public static class Created {
        public static final int ON_CLICK_LISTENER_TYPE_CHECK = 1;
        public static final int ON_CLICK_LISTENER_TYPE_SINGLE = 0;

        public static CheckBoxCustom generateCheckBoxCustom(Context ctx, String text, List list, int listenerType) {
            CheckBoxCustom box = generateCheckBoxCustom(ctx, text);
            if (listenerType == 0) {
                box.setOnClickListener(onSingleClickListener(list));
            } else if (listenerType == 1) {
                box.setOnClickListener(onCheckBoxClickListener());
            }
            return box;
        }

        public static CheckBoxCustom generateCheckBoxCustom(Context ctx, String text) {
            CheckBoxCustom box = new CheckBoxCustom(ctx);
            if (text == null || text.isEmpty()) {
//                box.setVisibility(4);
            } else {
                box.setText(text);
            }
            return box;
        }

        public static View.OnClickListener onSingleClickListener(final List list) {
            return new View.OnClickListener() {
                /* class com.yoki.im.tools.ViewUtils.Created.AnonymousClass1 */

                public void onClick(View v) {
                    if (list != null && list.size() > 0 && (v instanceof CheckBoxCustom)) {
                        CheckBoxCustom selectedBox = (CheckBoxCustom) v;
                        if (!selectedBox.isSelected()) {
//                            for (CheckBoxCustom box : list) {
//                                box.setSelected(false);
//                            }
                            selectedBox.setSelected(true);
                        }
                    }
                }
            };
        }

        public static View.OnClickListener onCheckBoxClickListener() {
            return new View.OnClickListener() {
                /* class com.yoki.im.tools.ViewUtils.Created.AnonymousClass2 */

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
        }
    }
}
