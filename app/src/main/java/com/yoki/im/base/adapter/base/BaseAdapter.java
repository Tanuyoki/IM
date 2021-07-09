package com.yoki.im.base.adapter.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.yoki.im.base.adapter.Util;
import com.yoki.im.base.adapter.ViewHolder;
import com.yoki.im.base.adapter.interfaces.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_COMMON_VIEW = 100000;
    public static final int TYPE_EMPTY_VIEW = 100003;
    public static final int TYPE_FOOTER_LAYOUT = 100012;
    public static final int TYPE_FOOTER_VIEW = 100002;
    public static final int TYPE_HEADER_LAYOUT = 100011;
    public static final int TYPE_NODATA_VIEW = 100004;
    public static final int TYPE_PRELOAD_FAILED_VIEW = 0;
    public static final int TYPE_RELOAD_VIEW = 100005;
    private boolean isAlreadyLoadMode = false;
    private boolean isAutoLoadMore = true;
    private boolean isLoadEnd = false;
    private boolean isLoading = false;
    private boolean isOpenLoadMore = false;
    private boolean isPreloadFailed = false;
    private boolean isPreloading = false;
    private boolean isRefreshing = false;
    private boolean isRemoveEmptyView = false;
    private boolean isReset = false;
    protected Context mContext;
    protected List<T> mDatas;
    private View mEmptyDataView;
    private RelativeLayout mFooterLayout;
    private View mFooterView;
    private View mHeaderView;
    private int mItemType = -1;
    private View mLoadEndView;
    private View mLoadFailedView;
    private OnLoadMoreListener mLoadMoreListener;
    private View mLoadingView;
    private int mNotItemCount = 0;
    private View mPreloadFailedView;
    private View mPreloadingView;
    private OnAdapterChangeListener onAdapterChangeListener;
    private View.OnClickListener onPreloadFailedClickListener;

    public interface OnAdapterChangeListener {
        void onItemChange(boolean z);
    }

    /* access modifiers changed from: protected */
    public abstract int getViewType(int i, T t);

    public BaseAdapter(Context context, List<T> datas, boolean isOpenLoadMore2) {
        this.mContext = context;
        this.mDatas = datas == null ? new ArrayList<>() : datas;
        this.isOpenLoadMore = isOpenLoadMore2;

    }

    public void setOnAdapterChangeListener(OnAdapterChangeListener listener) {
        this.onAdapterChangeListener = listener;
    }

    /* Return type fixed from 'com.yoki.im.base.adapter.ViewHolder' to match base method */
    @Override // android.support.v7.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return ViewHolder.create(this.mPreloadFailedView);
            case TYPE_FOOTER_VIEW /*{ENCODED_INT: 100002}*/:
                if (this.mFooterLayout == null) {
                    this.mFooterLayout = new RelativeLayout(this.mContext);
                }
                return ViewHolder.create(this.mFooterLayout);
            case TYPE_EMPTY_VIEW /*{ENCODED_INT: 100003}*/:
                return ViewHolder.create(this.mPreloadingView);
            case TYPE_NODATA_VIEW /*{ENCODED_INT: 100004}*/:
            default:
                return null;
            case TYPE_RELOAD_VIEW /*{ENCODED_INT: 100005}*/:
                return ViewHolder.create(this.mEmptyDataView);
            case TYPE_HEADER_LAYOUT /*{ENCODED_INT: 100011}*/:
                return ViewHolder.create(this.mHeaderView);
            case TYPE_FOOTER_LAYOUT /*{ENCODED_INT: 100012}*/:
                return ViewHolder.create(this.mFooterView);
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        if (!this.mDatas.isEmpty()) {
            this.isPreloading = false;
            if (this.onAdapterChangeListener != null) {
                this.onAdapterChangeListener.onItemChange(false);
            }
            if (isFooterView(position)) {
                return TYPE_FOOTER_VIEW;
            }
            if (this.mHeaderView != null && position == 0) {
                return TYPE_HEADER_LAYOUT;
            }
            if (this.mFooterView != null && position == getItemCount() - 1) {
                return TYPE_FOOTER_VIEW;
            }
            if (this.mItemType != -1) {
            }
            return getViewType(position, this.mDatas.get(position - this.mNotItemCount));
        } else if (this.mPreloadingView == null || this.isRemoveEmptyView || this.isPreloadFailed) {
            this.isPreloading = false;
            if (this.onAdapterChangeListener != null) {
                this.onAdapterChangeListener.onItemChange(false);
            }
            if (this.isRemoveEmptyView && !this.isPreloadFailed && this.mEmptyDataView != null) {
                return TYPE_RELOAD_VIEW;
            }
            if (!this.isPreloadFailed || this.mPreloadFailedView == null) {
                return TYPE_NODATA_VIEW;
            }
            return 0;
        } else {
            if (this.onAdapterChangeListener != null && !this.isPreloading) {
                this.isPreloading = true;
                this.onAdapterChangeListener.onItemChange(true);
            }
            return TYPE_EMPTY_VIEW;
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        if (!this.mDatas.isEmpty() || this.mPreloadingView == null) {
            return this.mDatas.size() + getFooterViewCount() + this.mNotItemCount;
        }
        return 1;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams lp;
        super.onViewAttachedToWindow(holder);
        if (isFooterView(holder.getLayoutPosition()) && (lp = holder.itemView.getLayoutParams()) != null && (lp instanceof StaggeredGridLayoutManager.LayoutParams)) {
            ((StaggeredGridLayoutManager.LayoutParams) lp).setFullSpan(true);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean isFooterView(int position) {
        return this.isOpenLoadMore && position >= getItemCount() + -1;
    }

    public int getFooterViewCount() {
        return (!this.isOpenLoadMore || this.mDatas.isEmpty()) ? 0 : 1;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = (GridLayoutManager) layoutManager;
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                /* class com.yoki.im.base.adapter.base.BaseAdapter.AnonymousClass1 */

                @Override // android.support.v7.widget.GridLayoutManager.SpanSizeLookup
                public int getSpanSize(int position) {
                    if (BaseAdapter.this.isFooterView(position)) {
                        return gridManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
        startLoadMore(recyclerView, layoutManager);
    }

    public T getItem(int position) {
        if (this.mDatas.isEmpty()) {
            return null;
        }
        return this.mDatas.get(position);
    }

    /* access modifiers changed from: protected */
    public boolean isCommonItemView(int viewType) {
        return (viewType == 100003 || viewType == 100002 || viewType == 100004 || viewType == 100005 || viewType == 0 || viewType == 100011 || viewType == 100012) ? false : true;
    }

    private void startLoadMore(RecyclerView recyclerView, final RecyclerView.LayoutManager layoutManager) {
        if (this.isOpenLoadMore) {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                /* class com.yoki.im.base.adapter.base.BaseAdapter.AnonymousClass2 */

                @Override // android.support.v7.widget.RecyclerView.OnScrollListener
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (newState == 0 && !BaseAdapter.this.isAutoLoadMore && BaseAdapter.this.findLastVisibleItemPosition(layoutManager) + 1 == BaseAdapter.this.getItemCount()) {
                        BaseAdapter.this.scrollLoadMore();
                    }
                }

                @Override // android.support.v7.widget.RecyclerView.OnScrollListener
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (!BaseAdapter.this.isAutoLoadMore || BaseAdapter.this.findLastVisibleItemPosition(layoutManager) + 1 != BaseAdapter.this.getItemCount()) {
                        if (BaseAdapter.this.isAutoLoadMore) {
                            BaseAdapter.this.isAutoLoadMore = false;
                        }
                    } else if (!BaseAdapter.this.mDatas.isEmpty() || BaseAdapter.this.mPreloadingView == null) {
                        BaseAdapter.this.scrollLoadMore();
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void scrollLoadMore() {
        if (!this.isLoadEnd && !this.isReset && this.mFooterLayout.getChildAt(0) == this.mLoadingView && this.mLoadMoreListener != null) {
            this.isAlreadyLoadMode = true;
            this.mLoadMoreListener.onLoadMore(false);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private int findLastVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        }
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            return Util.findMax(((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null));
        }
        return -1;
    }

    public List<T> getData() {
        return this.mDatas;
    }

    public void clearAndAdd(List<T> datas) {
        if (isOnNoFoundData(datas)) {
            onNoFoundData();
            return;
        }
        this.isRemoveEmptyView = false;
        if (this.isReset) {
            this.isReset = false;
        }
        this.mDatas.clear();
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public boolean isOnNoFoundData(List list) {
        return list.size() <= 0;
    }

    public void onNoFoundData() {
        this.isRemoveEmptyView = true;
        clearData();
        notifyDataSetChanged();
    }

    public void clearData() {
        int dataSize = this.mDatas.size();
        this.mDatas.clear();
        if (dataSize > 0) {
            notifyDataSetChanged();
        }
    }

    public void addData(List<T> datas) {
        this.mDatas.size();
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void reset(boolean isClearData) {
        this.isReset = true;
        this.isAutoLoadMore = true;
        this.isPreloadFailed = false;
        if (this.mLoadingView != null) {
            addFooterView(this.mLoadingView);
        }
        if (this.isRemoveEmptyView) {
            this.isRemoveEmptyView = false;
        }
        if (!this.isRefreshing) {
            this.isRefreshing = false;
        }
        if (isClearData) {
            clearData();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void addFooterView(View footerView) {
        if (footerView != null) {
            if (this.mFooterLayout == null) {
                this.mFooterLayout = new RelativeLayout(this.mContext);
            }
            removeFooterView();
            this.mFooterLayout.addView(footerView, new RelativeLayout.LayoutParams(-1, -2));
        }
    }

    private void removeFooterView() {
        this.mFooterLayout.removeAllViews();
    }

    public void remove(int position) {
        this.mDatas.remove(position);
        notifyItemRemoved(position);
        if (isOnNoFoundData(this.mDatas)) {
            onNoFoundData();
        }
    }

    public void setHeaderView(View headerView) {
        this.mNotItemCount++;
        this.mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public void setFooterView(View footerView) {
        this.mNotItemCount++;
        this.mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }

    public void setLoadingView(int loadingId) {
        setLoadingView(Util.inflate(this.mContext, loadingId));
    }

    public void setLoadingView(View loadingView) {
        this.mLoadingView = loadingView;
        addFooterView(this.mLoadingView);
    }

    public void setLoadFailedView(int loadFailedId) {
        setLoadFailedView(Util.inflate(this.mContext, loadFailedId));
    }

    public void setLoadFailedView(View loadFailedView) {
        this.mLoadFailedView = loadFailedView;
        this.mLoadFailedView.setOnClickListener(new View.OnClickListener() {
            /* class com.yoki.im.base.adapter.base.BaseAdapter.AnonymousClass3 */

            public void onClick(View view) {
                BaseAdapter.this.addFooterView(BaseAdapter.this.mLoadingView);
                if (BaseAdapter.this.mLoadMoreListener != null) {
                    BaseAdapter.this.mLoadMoreListener.onLoadMore(true);
                }
            }
        });
    }

    public void setLoadEndView(int loadEndId) {
        setLoadEndView(Util.inflate(this.mContext, loadEndId));
    }

    public void setLoadEndView(View loadEndView) {
        this.mLoadEndView = loadEndView;
    }

    public void setPreloadingView(View preloadingView) {
        this.mPreloadingView = preloadingView;
    }

    public void removeEmptyView() {
        this.isRemoveEmptyView = true;
        notifyDataSetChanged();
    }

    public void setEmptyDataView(View reloadView) {
        this.mEmptyDataView = reloadView;
    }

    public void setPreloadFailedView(View view) {
        setPreloadFailedView(view, null);
    }

    public void setPreloadFailedView(View view, View.OnClickListener onClickListener) {
        this.onPreloadFailedClickListener = onClickListener;
        this.mPreloadFailedView = view;
        this.mPreloadFailedView.setOnClickListener(new View.OnClickListener() {
            /* class com.yoki.im.base.adapter.base.BaseAdapter.AnonymousClass4 */

            public void onClick(View v) {
                BaseAdapter.this.isPreloadFailed = false;
                BaseAdapter.this.notifyDataSetChanged();
                if (BaseAdapter.this.onPreloadFailedClickListener != null) {
                    BaseAdapter.this.onPreloadFailedClickListener.onClick(v);
                }
            }
        });
    }

    public void setOnPreloadFailedClickListener(View.OnClickListener listener) {
        this.onPreloadFailedClickListener = listener;
    }

    public void onPreloadFailed() {
        if (this.mPreloadFailedView == null) {
            throw new RuntimeException("PreloadFailedView == null");
        }
        this.isPreloadFailed = true;
        if (this.mDatas != null && !this.mDatas.isEmpty()) {
            clearData();
        }
        notifyDataSetChanged();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.mLoadMoreListener = loadMoreListener;
    }

    public void onNoModeData() {
        addFooterView(this.mLoadEndView);
    }

    public void loadFailed() {
        addFooterView(this.mLoadFailedView);
    }

    public void hideLoadEndView() {
        if (this.mLoadingView != null) {
            this.mLoadingView.setVisibility(8);
        }
        if (this.mLoadEndView != null) {
            this.mLoadEndView.setVisibility(8);
        }
    }

    public void showLoadEndView() {
        if (this.mLoadingView != null) {
            this.mLoadingView.setVisibility(0);
        }
        if (this.mLoadEndView != null) {
            this.mLoadEndView.setVisibility(0);
        }
    }

    public void setLoadEnd(boolean isLoadEnd2) {
        this.isLoadEnd = isLoadEnd2;
    }

    public void setRefreshing(boolean refreshing) {
        this.isRefreshing = refreshing;
    }

    public int getNotItemCount() {
        return this.mNotItemCount;
    }

    public void setItemType(int itemType) {
        this.mItemType = itemType;
    }

    public boolean isEmpty() {
        return this.mDatas == null || this.mDatas.isEmpty();
    }

    public boolean isAlreadyLoadMode() {
        return this.isAlreadyLoadMode;
    }
}
