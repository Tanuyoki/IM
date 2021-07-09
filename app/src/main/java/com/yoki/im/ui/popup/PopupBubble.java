package com.yoki.im.ui.popup;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.PopupWindow;
import com.yoki.im.ui.popup.PopupBubbleLayout;

public class PopupBubble extends PopupWindow {
    private PopupBubbleLayout bubbleView;
    private Context context;

    public PopupBubble(Context context2) {
        this.context = context2;
        setWidth(-2);
        setHeight(-2);
        setFocusable(true);
        setOutsideTouchable(false);
        setClippingEnabled(false);
        setBackgroundDrawable(new ColorDrawable(0));
    }

    public void setBubbleView(View view) {
        this.bubbleView = new PopupBubbleLayout(this.context);
        this.bubbleView.setBackgroundColor(0);
        this.bubbleView.addView(view);
        setContentView(this.bubbleView);
    }

    public void setParam(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    public void show(View parent) {
        show(parent, 48, (float) (getMeasuredWidth() / 2));
    }

    public void show(View parent, int gravity) {
        show(parent, gravity, (float) (getMeasuredWidth() / 2));
    }

    public void show(View parent, int gravity, float bubbleOffset) {
        PopupBubbleLayout.BubbleLegOrientation orientation = PopupBubbleLayout.BubbleLegOrientation.LEFT;
        if (!isShowing()) {
            switch (gravity) {
                case 3:
                    orientation = PopupBubbleLayout.BubbleLegOrientation.RIGHT;
                    break;
                case 5:
                    orientation = PopupBubbleLayout.BubbleLegOrientation.LEFT;
                    break;
                case 48:
                    orientation = PopupBubbleLayout.BubbleLegOrientation.BOTTOM;
                    break;
                case 80:
                    orientation = PopupBubbleLayout.BubbleLegOrientation.TOP;
                    break;
            }
            this.bubbleView.setBubbleParams(orientation, bubbleOffset);
            int[] location = new int[2];
            parent.getLocationOnScreen(location);
            switch (gravity) {
                case 3:
                    showAtLocation(parent, 0, location[0] - getMeasuredWidth(), location[1] - (parent.getHeight() / 2));
                    return;
                case 5:
                    showAtLocation(parent, 0, location[0] + parent.getWidth(), location[1] - (parent.getHeight() / 2));
                    return;
                case 48:
                    showAtLocation(parent, 0, location[0], location[1] - getMeasureHeight());
                    return;
                case 80:
                    showAsDropDown(parent);
                    return;
                default:
                    return;
            }
        } else {
            dismiss();
        }
    }

    public int getMeasureHeight() {
        getContentView().measure(0, 0);
        return getContentView().getMeasuredHeight();
    }

    public int getMeasuredWidth() {
        getContentView().measure(0, 0);
        return getContentView().getMeasuredWidth();
    }
}
