package com.yoki.im.base.adapter.interfaces;

import com.yoki.im.base.adapter.ViewHolder;

public interface OnItemChildClickListener<T> {
    void onItemChildClick(ViewHolder viewHolder, T t, int i);
}
