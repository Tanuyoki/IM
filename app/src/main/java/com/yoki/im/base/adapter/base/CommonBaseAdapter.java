package com.yoki.im.base.adapter.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.yoki.im.base.adapter.ViewHolder;
import com.yoki.im.base.adapter.interfaces.OnItemChildClickListener;
import com.yoki.im.base.adapter.interfaces.OnListItemClickListener;
import com.yoki.im.base.adapter.interfaces.OnSwipeMenuClickListener;

import java.util.ArrayList;
import java.util.List;

public abstract class CommonBaseAdapter<T> extends BaseAdapter<T> {
    private static final String TAG = CommonBaseAdapter.class.getSimpleName();
    private ArrayList<Integer> mItemChildIds = new ArrayList<>();
    private ArrayList<OnItemChildClickListener<T>> mItemChildListeners = new ArrayList<>();
    private OnListItemClickListener<T> mItemClickListener;
    private ArrayList<OnSwipeMenuClickListener<T>> mListener = new ArrayList<>();
    private ArrayList<Integer> mViewId = new ArrayList<>();

    /* access modifiers changed from: protected */
    public abstract void convert(ViewHolder viewHolder, T t, int i);

    /* access modifiers changed from: protected */
    public abstract int getItemLayoutId(int i);

    public CommonBaseAdapter(Context context, List<T> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isCommonItemView(holder.getItemViewType())) {
            bindCommonItem(holder, position - getNotItemCount());
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r6v0, resolved type: com.yoki.im.base.adapter.base.CommonBaseAdapter<T> */
    /* JADX WARN: Multi-variable type inference failed */
    private void bindCommonItem(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        convert(viewHolder, this.mDatas.get(position), position);
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            /* class com.yoki.im.base.adapter.base.CommonBaseAdapter.AnonymousClass1 */

            /* JADX DEBUG: Multi-variable search result rejected for r0v3, resolved type: com.yoki.im.base.adapter.interfaces.OnListItemClickListener */
            /* JADX WARN: Multi-variable type inference failed */
            public void onClick(View view) {
                if (CommonBaseAdapter.this.mItemClickListener != null) {
                    CommonBaseAdapter.this.mItemClickListener.onItemClick(viewHolder, CommonBaseAdapter.this.mDatas.get(position), position);
                }
            }
        });
        for (int i = 0; i < this.mItemChildIds.size(); i++) {
            if (viewHolder.getConvertView().findViewById(this.mItemChildIds.get(i).intValue()) != null) {
                int finalI = i;
                viewHolder.getConvertView().findViewById(this.mItemChildIds.get(i).intValue()).setOnClickListener(new View.OnClickListener() {
                    /* class com.yoki.im.base.adapter.base.CommonBaseAdapter.AnonymousClass2 */

                    /* JADX DEBUG: Multi-variable search result rejected for r0v3, resolved type: com.yoki.im.base.adapter.interfaces.OnItemChildClickListener */
                    /* JADX WARN: Multi-variable type inference failed */
                    public void onClick(View v) {
                        ((OnItemChildClickListener) CommonBaseAdapter.this.mItemChildListeners.get(finalI)).onItemChildClick(viewHolder, CommonBaseAdapter.this.mDatas.get(position), position);
                    }
                });
            }
        }
        if (this.mViewId.size() > 0 && this.mListener.size() > 0 && viewHolder.getSwipeView() != null) {
            ViewGroup swipeView = (ViewGroup) viewHolder.getSwipeView();
            for ( int i2 = 0; i2 < this.mViewId.size(); i2++) {
                int finalI = i2;
                swipeView.findViewById(this.mViewId.get(i2).intValue()).setOnClickListener(new View.OnClickListener() {
                    /* class com.yoki.im.base.adapter.base.CommonBaseAdapter.AnonymousClass3 */

                    /* JADX DEBUG: Multi-variable search result rejected for r0v3, resolved type: com.yoki.im.base.adapter.interfaces.OnSwipeMenuClickListener */
                    /* JADX WARN: Multi-variable type inference failed */
                    public void onClick(View v) {
                        ((OnSwipeMenuClickListener) CommonBaseAdapter.this.mListener.get(finalI)).onSwipMenuClick(viewHolder, CommonBaseAdapter.this.mDatas.get(position), position);
                    }
                });
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.yoki.im.base.adapter.base.BaseAdapter
    public int getViewType(int position, T t) {
        return BaseAdapter.TYPE_COMMON_VIEW;
    }

    /* Return type fixed from 'com.yoki.im.base.adapter.ViewHolder' to match base method */
    @Override // com.yoki.im.base.adapter.base.BaseAdapter, com.yoki.im.base.adapter.base.BaseAdapter, android.support.v7.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isCommonItemView(viewType)) {
            return ViewHolder.create(this.mContext, getItemLayoutId(viewType), parent, viewType);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    public void setOnItemClickListener(OnListItemClickListener<T> itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void setOnSwipMenuClickListener(int viewId, OnSwipeMenuClickListener<T> swipeMenuClickListener) {
        this.mViewId.add(Integer.valueOf(viewId));
        this.mListener.add(swipeMenuClickListener);
    }

    public void setOnItemChildClickListener(int viewId, OnItemChildClickListener<T> itemChildClickListener) {
        this.mItemChildIds.add(Integer.valueOf(viewId));
        this.mItemChildListeners.add(itemChildClickListener);
    }
}
