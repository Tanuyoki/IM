package com.yoki.im.ui.popup;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yoki.im.base.adapter.ViewHolder;
import com.yoki.im.base.adapter.base.CommonBaseAdapter;
import com.yoki.im.base.adapter.interfaces.OnListItemClickListener;
import com.yoki.im.tools.BackgroundUtils;
import com.yoki.im.tools.DensityUtils;
import com.yoki.im.tools.OnMenuItemClickImpl;
import com.yoki.im.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PopupMenu {
    private OnMenuItemClickImpl listener;
    private MenuAdapter mAdapter;
    private Context mContext;
    private List<Map<String, Object>> mListData = new ArrayList();
    private RecyclerView mListView;
    private PopupWindow mPopupWindow;

    public PopupMenu(Context context) {
        this.mContext = context;
        View view = null;
//        View view = LayoutInflater.from(context).inflate(R.layout.popup_menu_list, (ViewGroup) null);
//        view.setFocusableInTouchMode(true);
        this.mAdapter = new MenuAdapter(context, this.mListData, false);
//        this.mListView = (RecyclerView) view.findViewById(R.id.popup_menu_list_recycler);
        this.mListView.setAdapter(this.mAdapter);
        this.mListView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mListView.setBackgroundDrawable(BackgroundUtils.generateGradientDrawable(DensityUtils.dip2px(3.0f), 1, ContextCompat.getColor(context, R.color.tran), ContextCompat.getColor(context, R.color.bg_gray)));
        this.mAdapter.setOnItemClickListener(new OnListItemClickListener<Map<String, Object>>() {
            /* class com.yoki.im.ui.popup.PopupMenu.AnonymousClass1 */

            public void onItemClick(ViewHolder viewHolder, Map<String, Object> data, int position) {
                if (PopupMenu.this.listener != null) {
                    PopupMenu.this.listener.onClick(String.valueOf(data.get("Title")), ((Integer) data.get("Code")).intValue());
                }
            }
        });
        view.setOnKeyListener(new View.OnKeyListener() {
            /* class com.yoki.im.ui.popup.PopupMenu.AnonymousClass2 */

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode != 4 || !PopupMenu.this.mPopupWindow.isShowing()) {
                    return false;
                }
                PopupMenu.this.mPopupWindow.dismiss();
                return true;
            }
        });
        this.mPopupWindow = new PopupWindow(view);
        this.mPopupWindow.setContentView(view);
        this.mPopupWindow.setWidth(-2);
        this.mPopupWindow.setHeight(-2);
        this.mPopupWindow.setFocusable(true);
    }

    public void addItem(Map<String, Object> item) {
        this.mListData.add(item);
        this.mAdapter.notifyDataSetChanged();
    }

    public void addItems(List<Map<String, Object>> datas) {
        if (datas != null) {
            this.mListData.clear();
            this.mListData.addAll(datas);
            this.mAdapter.notifyDataSetChanged();
        }
    }

    public void showAsDropDown(View parent) {
        this.mPopupWindow.showAsDropDown(parent);
    }

    public void showAsDropDown(View parent, int xoff, int yoff) {
        this.mPopupWindow.showAsDropDown(parent, xoff, yoff);
    }

    public void showAtLocation(View view, int gravity, int x, int y) {
        this.mPopupWindow.showAtLocation(view, gravity, x, y);
    }

    public boolean isShowing() {
        return this.mPopupWindow.isShowing();
    }

    public void dismiss() {
        this.mPopupWindow.dismiss();
    }

    public void setOnMenuItemClickListener(OnMenuItemClickImpl listener2) {
        this.listener = listener2;
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener listener2) {
        this.mPopupWindow.setOnDismissListener(listener2);
    }

    /* access modifiers changed from: package-private */
    public class MenuAdapter extends CommonBaseAdapter<Map<String, Object>> {
        public MenuAdapter(Context context, List<Map<String, Object>> datas, boolean isOpenLoadMore) {
            super(context, datas, isOpenLoadMore);
        }

        /* access modifiers changed from: protected */
        public void convert(ViewHolder holder, Map<String, Object> data, int position) {
//            holder.setText(R.id.popup_menu_list_item_tv_title, String.valueOf(data.get("Title")));
//            if (getItemCount() == 1) {
//                holder.setBackgroundDrawable(R.id.popup_menu_list_item_root, BackgroundUtils.generateClickEffectDrawable(1));
//            } else if (position == 0) {
//                holder.setBackgroundDrawable(R.id.popup_menu_list_item_root, BackgroundUtils.generateClickEffectDrawable(2));
//            } else if (position == getItemCount() - 1) {
//                holder.setBackgroundDrawable(R.id.popup_menu_list_item_root, BackgroundUtils.generateClickEffectDrawable(3));
//            } else {
//                holder.setBackgroundDrawable(R.id.popup_menu_list_item_root, BackgroundUtils.generateClickEffectDrawable(0));
//            }
        }

        /* access modifiers changed from: protected */
        @Override // com.yoki.im.base.adapter.base.CommonBaseAdapter
        public int getItemLayoutId(int viewType) {
//            return R.layout.popup_menu_list_item;
            return 0;
        }
    }
}
