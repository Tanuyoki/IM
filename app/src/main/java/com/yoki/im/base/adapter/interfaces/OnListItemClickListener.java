package com.yoki.im.base.adapter.interfaces;

import com.yoki.im.base.adapter.ViewHolder;

public interface OnListItemClickListener<T> {
    void onItemClick(ViewHolder viewHolder, T t, int i);
}
