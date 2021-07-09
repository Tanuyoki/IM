package com.yoki.im.base.adapter.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.yoki.im.base.adapter.ViewHolder;
import com.yoki.im.base.adapter.interfaces.OnItemChildClickListener;
import com.yoki.im.base.adapter.interfaces.OnMultiItemClickListeners;

import java.util.ArrayList;
import java.util.List;

public abstract class MultiBaseAdapter<T> extends BaseAdapter<T> {
    private ArrayList<Integer> mItemChildIds = new ArrayList<>();
    private ArrayList<OnItemChildClickListener<T>> mItemChildListeners = new ArrayList<>();
    private OnMultiItemClickListeners<T> mItemClickListener;

    /* access modifiers changed from: protected */
    public abstract void convert(ViewHolder viewHolder, T t, int i, int i2);

    /* access modifiers changed from: protected */
    public abstract int getItemLayoutId(int i);

    public MultiBaseAdapter(Context context, List<T> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    /* Return type fixed from 'com.yoki.im.base.adapter.ViewHolder' to match base method */
    @Override // com.yoki.im.base.adapter.base.BaseAdapter, com.yoki.im.base.adapter.base.BaseAdapter, android.support.v7.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isCommonItemView(viewType)) {
            return ViewHolder.create(this.mContext, getItemLayoutId(viewType), parent, viewType);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        if (isCommonItemView(viewType)) {
            bindCommonItem(holder, position, viewType);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: com.yoki.im.base.adapter.base.MultiBaseAdapter<T> */
    /* JADX WARN: Multi-variable type inference failed */
    private void bindCommonItem(RecyclerView.ViewHolder holder, final int position, final int viewType) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        convert(viewHolder, this.mDatas.get(position), position, viewType);
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            /* class com.yoki.im.base.adapter.base.MultiBaseAdapter.AnonymousClass1 */

            /* JADX DEBUG: Multi-variable search result rejected for r0v3, resolved type: com.yoki.im.base.adapter.interfaces.OnMultiItemClickListeners */
            /* JADX WARN: Multi-variable type inference failed */
            public void onClick(View view) {
                if (MultiBaseAdapter.this.mItemClickListener != null) {
                    MultiBaseAdapter.this.mItemClickListener.onItemClick(viewHolder, MultiBaseAdapter.this.mDatas.get(position), position, viewType);
                }
            }
        });
        for (int i = 0; i < this.mItemChildIds.size(); i++) {
            if (viewHolder.getConvertView().findViewById(this.mItemChildIds.get(i).intValue()) != null) {
                int finalI = i;
                viewHolder.getConvertView().findViewById(this.mItemChildIds.get(i).intValue()).setOnClickListener(new View.OnClickListener() {
                    /* class com.yoki.im.base.adapter.base.MultiBaseAdapter.AnonymousClass2 */

                    /* JADX DEBUG: Multi-variable search result rejected for r0v3, resolved type: com.yoki.im.base.adapter.interfaces.OnItemChildClickListener */
                    /* JADX WARN: Multi-variable type inference failed */
                    public void onClick(View v) {
                        ((OnItemChildClickListener) MultiBaseAdapter.this.mItemChildListeners.get(finalI)).onItemChildClick(viewHolder, MultiBaseAdapter.this.mDatas.get(position), position);
                    }
                });
            }
        }
    }

    public void setOnMultiItemClickListener(OnMultiItemClickListeners<T> itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void setOnItemChildClickListener(int viewId, OnItemChildClickListener<T> itemChildClickListener) {
        this.mItemChildIds.add(Integer.valueOf(viewId));
        this.mItemChildListeners.add(itemChildClickListener);
    }
}
