package com.yoki.im.ui.popup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.yoki.im.ActivityCollector;
import com.yoki.im.base.BasePopupWindow;
import com.yoki.im.tools.FragmentCollector;
import com.yoki.im.R;

public class PopupMessage extends BasePopupWindow implements View.OnClickListener {
    private static final String TAG = PopupMessage.class.getSimpleName();
    private Context mContext;
    private TextView mTvMessage;
    private View mView;

    public PopupMessage(Context context) {
        this.mContext = context;
        init();
    }

    private void init() {
        setWidth(-1);
        setHeight(-2);
        setFocusable(false);
        setOutsideTouchable(true);
//        this.mView = View.inflate(this.mContext, R.layout.ppw_msg, null);
//        this.mTvMessage = (TextView) this.mView.findViewById(R.id.ppw_info_tv_message);
        this.mView.setOnClickListener(this);
        setContentView(this.mView);
    }

    @Override // com.yoki.im.base.BasePopupWindow
    public void show() {
        if (!"ImAty".equals(ActivityCollector.getRunningActivityName(ActivityCollector.getCurrentActivity())) && "MainAty".equals(ActivityCollector.getRunningActivityName(ActivityCollector.getCurrentActivity())) && "MessageFragment".equals(FragmentCollector.getRunningFragmentName(FragmentCollector.getCurrFragment()))) {
            show(this.mView, 48, 0, 0);
        }
    }

    public void show(View anchor, int xoff, int yoff, int gravity) {
        showAtLocation(anchor, gravity, xoff, yoff);
    }

    public void onClick(View v) {
//        Activity mainActivity = ActivityCollector.getActivity(MainAty.class);
//        Activity currActivity = ActivityCollector.getCurrentActivity();
//        Fragment currFragment = FragmentCollector.getCurrFragment();
//        if (!"MainAty".equals(ActivityCollector.getRunningActivityName(currActivity))) {
//            dismiss();
//            ActivityCollector.removeAllActivity(MainAty.class);
//            if (mainActivity != null) {
//                ((MainAty) mainActivity).setChangeTag(3, false);
//                return;
//            }
//            Intent intent = new Intent(this.mContext, MainAty.class);
//            intent.putExtra("Tag", 3);
//            currActivity.startActivity(intent);
//        } else if (!"MessageFragment".equals(FragmentCollector.getRunningFragmentName(currFragment))) {
//            dismiss();
//            ((MainAty) mainActivity).setChangeTag(3, true);
//        }
    }

    public PopupMessage setMessage(String text) {
        this.mTvMessage.setText(text);
        return this;
    }
}
