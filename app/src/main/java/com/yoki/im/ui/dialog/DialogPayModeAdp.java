package com.yoki.im.ui.dialog;

import android.content.Context;
import com.yoki.im.base.adapter.ViewHolder;
import com.yoki.im.base.adapter.base.CommonBaseAdapter;
import com.yoki.im.R;
import java.util.List;
import java.util.Map;

/* access modifiers changed from: package-private */
/* compiled from: DialogSingleSelection */
public class DialogPayModeAdp extends CommonBaseAdapter<Map<String, Object>> {
    public DialogPayModeAdp(Context context, List<Map<String, Object>> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    /* access modifiers changed from: protected */
    public void convert(ViewHolder holder, Map<String, Object> data, int position) {
//        holder.setText(R.id.ppw_paymode_list_item_tv_paymode, String.valueOf(data.get("text")));
//        holder.setBackgroundResource(R.id.ppw_paymode_list_item_iv_icon, ((Integer) data.get("image")).intValue());
//        if (((Boolean) data.get("state")).booleanValue()) {
//            holder.setVisibility(R.id.ppw_paymode_list_item_iv_state, 0);
//        } else {
//            holder.setVisibility(R.id.ppw_paymode_list_item_iv_state, 8);
//        }
    }

    @Override // com.yoki.im.base.adapter.base.CommonBaseAdapter
    public int getItemLayoutId(int viewType) {
//        return R.layout.ppw_paymode_list_item;
        return 0;
    }
}
