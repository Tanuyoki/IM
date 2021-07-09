package com.yoki.im.ui.dialog;

import android.content.Context;
import com.yoki.im.base.adapter.ViewHolder;
import com.yoki.im.base.adapter.base.CommonBaseAdapter;
import com.yoki.im.R;
import java.util.List;
import java.util.Map;

/* access modifiers changed from: package-private */
/* compiled from: DialogSingleSelection */
public class DialogSingleSelectionAdp extends CommonBaseAdapter<Map<String, Object>> {
    private int dataSize = 0;

    public DialogSingleSelectionAdp(Context context, List<Map<String, Object>> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
        if (datas == null) {
            throw new RuntimeException("DialogSingleSelection Datas is Null");
        }
        this.dataSize = datas.size();
    }

    /* access modifiers changed from: protected */
    public void convert(ViewHolder holder, Map<String, Object> data, int position) {
//        holder.setText(R.id.ppw_single_selected_list_item_tv_name, String.valueOf(data.get("text")));
//        if (((Boolean) data.get("state")).booleanValue()) {
//            holder.setSelected(R.id.ppw_single_selected_list_item_iv_state, true);
//        } else {
//            holder.setSelected(R.id.ppw_single_selected_list_item_iv_state, false);
//        }
    }

    @Override // com.yoki.im.base.adapter.base.CommonBaseAdapter
    public int getItemLayoutId(int viewType) {
//        return R.layout.d_ppw_single_selected_list_item;
        return 0;
    }
}
