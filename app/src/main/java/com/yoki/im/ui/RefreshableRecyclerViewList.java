package com.yoki.im.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yoki.im.base.adapter.ViewHolder;
import com.yoki.im.base.adapter.base.BaseAdapter;
import com.yoki.im.base.adapter.base.CommonBaseAdapter;
import com.yoki.im.base.adapter.interfaces.OnListItemClickListener;
import com.yoki.im.base.adapter.interfaces.OnLoadMoreListener;
import com.yoki.im.tools.CommonData;
import com.yoki.im.tools.LineItemDecorationUtils;
import com.yoki.im.tools.LinearLayoutManagerTryCatch;
import com.yoki.im.tools.ToastUtils;
import com.yoki.im.R;
import java.util.List;

public class RefreshableRecyclerViewList<T> extends FrameLayout {
    private static final String TAG = RefreshableRecyclerViewList.class.getSimpleName();
    private OnAdapterListener mAdapterListener;
    private CommonBaseAdapter<T> mCommonBaseAdapter;
    private Context mContext;
    private boolean mIsDisableRefresh;
    private boolean mIsFirstLoadListTag;
    private boolean mIsLoadMoreData;
    private boolean mIsNoMoreData;
    private boolean mIsOpenLoadMore;
    private boolean mIsRefreshing;
    private boolean mIsSliding;
    private View mLoading;
    private ImageView mNoDataImage;
    private View mNoDataView;
    private ViewGroup mNoFoundContainer;
    private ImageView mNoFoundDataImage;
    private TextView mNoFoundDataText;
    private View mNoFoundDataView;
    private int mPage;
    private int mPageSize;
    private RecyclerView mRecyclerView;
    private BaseSwipeRefreshLayout mSwipeRefresh;
    private TextView mTxtLoadFailed;
    private View mView;
    private OnListManageListener onListManageListener;

    public interface OnAdapterListener<T> {
        void convert(Context context, ViewHolder viewHolder, T t, int i);

        int getItemLayoutId(int i);
    }

    public interface OnListManageListener {
        void onLoadMore();

        void onPreloading();

        void onRefresh();
    }

    public RefreshableRecyclerViewList(Context context) {
        this(context, null);
    }

    public RefreshableRecyclerViewList(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshableRecyclerViewList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mPage = 1;
        this.mPageSize = 20;
        this.mIsNoMoreData = false;
        this.mIsLoadMoreData = true;
        this.mIsRefreshing = false;
        this.mIsFirstLoadListTag = true;
        this.mIsDisableRefresh = false;
        this.mIsSliding = false;
        this.mContext = context;
    }

    public RefreshableRecyclerViewList setOnFailedClickListener(OnClickListener listener) {
        this.mCommonBaseAdapter.setOnPreloadFailedClickListener(listener);
        return this;
    }

    public RefreshableRecyclerViewList loadList(OnAdapterListener listener) {
        loadList(false, listener);
        return this;
    }

    public RefreshableRecyclerViewList loadList(boolean isOpenLoadMore, OnAdapterListener listener) {
        this.mIsOpenLoadMore = isOpenLoadMore;
        this.mAdapterListener = listener;
        loadList();
        return this;
    }

    @SuppressLint({"ClickableViewAccessibility"})
    private void loadList() {
        this.mRecyclerView = new RecyclerView(this.mContext);
        this.mRecyclerView.setOverScrollMode(2);
        this.mSwipeRefresh = new BaseSwipeRefreshLayout(this.mContext);
        this.mSwipeRefresh.setLayoutParams(new RelativeLayout.LayoutParams(-1, -2));
        this.mSwipeRefresh.addView(this.mRecyclerView);
        this.mCommonBaseAdapter = new CommonBaseAdapter<T>(this.mContext, null, this.mIsOpenLoadMore) {
            /* class com.yoki.im.ui.RefreshableRecyclerViewList.AnonymousClass1 */

            /* access modifiers changed from: protected */
            @Override // com.yoki.im.base.adapter.base.CommonBaseAdapter
            public void convert(ViewHolder holder, T data, int position) {
                if (RefreshableRecyclerViewList.this.mAdapterListener != null) {
                    try {
                        RefreshableRecyclerViewList.this.mAdapterListener.convert(this.mContext, holder, data, position);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            /* access modifiers changed from: protected */
            @Override // com.yoki.im.base.adapter.base.CommonBaseAdapter
            public int getItemLayoutId(int viewType) {
                if (RefreshableRecyclerViewList.this.mAdapterListener != null) {
                    return RefreshableRecyclerViewList.this.mAdapterListener.getItemLayoutId(viewType);
                }
                return 0;
            }
        };
//        View preloadFailedView = LayoutInflater.from(this.mContext).inflate(R.layout.list_preload_failed, (ViewGroup) this.mRecyclerView.getRootView(), false);
//        View loadFailedView = LayoutInflater.from(this.mContext).inflate(R.layout.list_load_failed, (ViewGroup) this.mRecyclerView.getRootView(), false);
//        this.mLoading = LayoutInflater.from(this.mContext).inflate(R.layout.list_preloading, (ViewGroup) this.mRecyclerView.getRootView(), false);
//        this.mNoDataView = LayoutInflater.from(this.mContext).inflate(R.layout.list_no_data, (ViewGroup) this.mRecyclerView.getRootView(), false);
//        this.mNoFoundDataText = (TextView) this.mNoDataView.findViewById(R.id.layout_reload_tv);
//        this.mNoFoundDataImage = (ImageView) this.mNoDataView.findViewById(R.id.layout_reload_iv);
//        this.mNoFoundContainer = (ViewGroup) this.mNoDataView.findViewById(R.id.layout_reload_container);
//        this.mTxtLoadFailed = (TextView) loadFailedView.findViewById(R.id.layout_load_failed_tv);
//        this.mCommonBaseAdapter.setEmptyDataView(this.mNoDataView);
//        this.mCommonBaseAdapter.setPreloadingView(this.mLoading);
//        this.mCommonBaseAdapter.setPreloadFailedView(preloadFailedView);
//        this.mCommonBaseAdapter.setLoadingView((int) R.layout.list_loading);
//        this.mCommonBaseAdapter.setLoadFailedView(loadFailedView);
//        this.mCommonBaseAdapter.setLoadEndView((int) R.layout.list_load_end);
        this.mCommonBaseAdapter.setOnAdapterChangeListener(new BaseAdapter.OnAdapterChangeListener() {
            /* class com.yoki.im.ui.RefreshableRecyclerViewList.AnonymousClass2 */

            @Override // com.yoki.im.base.adapter.base.BaseAdapter.OnAdapterChangeListener
            public void onItemChange(boolean isLoading) {
                if (isLoading) {
                    RefreshableRecyclerViewList.this.mSwipeRefresh.setRefreshing(false);
                    RefreshableRecyclerViewList.this.mSwipeRefresh.setEnabled(false);
                    if (RefreshableRecyclerViewList.this.onListManageListener != null && !RefreshableRecyclerViewList.this.mIsRefreshing) {
                        RefreshableRecyclerViewList.this.onListManageListener.onPreloading();
                    }
                } else if (!RefreshableRecyclerViewList.this.mIsDisableRefresh) {
                    RefreshableRecyclerViewList.this.openRefresh();
                }
            }
        });
        this.mRecyclerView.setLayoutManager(new LinearLayoutManagerTryCatch(this.mContext));
        this.mRecyclerView.setAdapter(this.mCommonBaseAdapter);
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.setFocusableInTouchMode(true);
        this.mRecyclerView.setOnTouchListener(new OnTouchListener() {
            /* class com.yoki.im.ui.RefreshableRecyclerViewList.AnonymousClass3 */

            public boolean onTouch(View v, MotionEvent event) {
                return RefreshableRecyclerViewList.this.mIsSliding;
            }
        });
        setOnLoadMoreListener();
        setOnRefreshListener();
        addView(this.mSwipeRefresh);
    }

    public RefreshableRecyclerViewList openRefresh() {
        this.mSwipeRefresh.setEnabled(true);
        this.mIsDisableRefresh = false;
        return this;
    }

    private RefreshableRecyclerViewList setOnLoadMoreListener() {
        this.mCommonBaseAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            /* class com.yoki.im.ui.RefreshableRecyclerViewList.AnonymousClass4 */

            @Override // com.yoki.im.base.adapter.interfaces.OnLoadMoreListener
            public void onLoadMore(boolean isReload) {
                if (RefreshableRecyclerViewList.this.mIsNoMoreData) {
                    RefreshableRecyclerViewList.this.onNoModeData();
                } else if (RefreshableRecyclerViewList.this.mIsLoadMoreData && RefreshableRecyclerViewList.this.onListManageListener != null) {
                    RefreshableRecyclerViewList.this.onListManageListener.onLoadMore();
                }
            }
        });
        return this;
    }

    private RefreshableRecyclerViewList setOnRefreshListener() {
        this.mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            /* class com.yoki.im.ui.RefreshableRecyclerViewList.AnonymousClass5 */

            @Override // android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener
            public void onRefresh() {
                RefreshableRecyclerViewList.this.mIsRefreshing = true;
                if (RefreshableRecyclerViewList.this.onListManageListener != null) {
                    RefreshableRecyclerViewList.this.onListManageListener.onRefresh();
                }
            }
        });
        this.mSwipeRefresh.addOnAttachStateChangeListener(new OnAttachStateChangeListener() {
            /* class com.yoki.im.ui.RefreshableRecyclerViewList.AnonymousClass6 */

            public void onViewAttachedToWindow(View v) {
            }

            public void onViewDetachedFromWindow(View v) {
            }
        });
        return this;
    }

    public RefreshableRecyclerViewList onNoModeData() {
        this.mCommonBaseAdapter.onNoModeData();
        return this;
    }

    public RefreshableRecyclerViewList disableRefresh() {
        this.mIsDisableRefresh = true;
        this.mSwipeRefresh.setRefreshing(false);
        this.mSwipeRefresh.setEnabled(false);
        return this;
    }

    public RefreshableRecyclerViewList setListMinimumHeight(int height) {
        if (Build.VERSION.SDK_INT >= 16 && this.mView.getMinimumHeight() < height) {
            this.mView.setMinimumHeight(height);
        }
        return this;
    }

    public RefreshableRecyclerViewList setNoDataView(String text, int imageResId) {
        setNoDataView(text, imageResId, Double.valueOf(0.0d));
        return this;
    }

    public RefreshableRecyclerViewList setNoDataView(String text, int imageResId, Double widthPercent) {
        this.mNoFoundDataText.setText(text);
        this.mNoFoundDataImage.setBackgroundResource(imageResId);
        if (widthPercent.doubleValue() > 0.0d) {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) this.mNoFoundContainer.getLayoutParams();
            lp.addRule(10);
            lp.topMargin = (int) (((double) CommonData.ScreenWidth) * widthPercent.doubleValue());
            this.mNoFoundContainer.setLayoutParams(lp);
        }
        return this;
    }

    public RefreshableRecyclerViewList onLoadModeComplete() {
        this.mCommonBaseAdapter.onNoModeData();
        this.mCommonBaseAdapter.setLoadEnd(true);
        return this;
    }

    public RefreshableRecyclerViewList setLoadFailedView(View loadFailedView) {
        this.mCommonBaseAdapter.setLoadFailedView(loadFailedView);
        return this;
    }

    public RefreshableRecyclerViewList setOnItemClickListener(OnListItemClickListener listener) {
        this.mCommonBaseAdapter.setOnItemClickListener(listener);
        return this;
    }

    public OnListManageListener getOnListManageListener() {
        return this.onListManageListener;
    }

    public RefreshableRecyclerViewList setOnListManageListener(OnListManageListener listener) {
        this.onListManageListener = listener;
        return this;
    }

    public RefreshableRecyclerViewList addItemDecoration() {
        addItemDecoration(-1);
        return this;
    }

    public RefreshableRecyclerViewList addItemDecoration(int spacing) {
        this.mRecyclerView.addItemDecoration(LineItemDecorationUtils.addItemDecoration(this.mContext, spacing));
        return this;
    }

    public RefreshableRecyclerViewList remove(int position) {
        this.mCommonBaseAdapter.remove(position);
        return this;
    }

    public RefreshableRecyclerViewList addListData(List list, boolean isReload) {
        if (list == null) {
            onLoadFailed();
        } else {
            int resultSize = list.size();
            this.mIsNoMoreData = resultSize < this.mPageSize;
            if (resultSize > 0) {
                if (isReload) {
                    clearAndAdd(list);
                    scrollToTop();
                } else {
                    addData(list);
                }
                this.mPage++;
            } else {
                onNoFoundData();
            }
            if (isShowLoadEndView()) {
                this.mCommonBaseAdapter.showLoadEndView();
                this.mIsLoadMoreData = true;
            } else {
                this.mCommonBaseAdapter.hideLoadEndView();
                this.mIsLoadMoreData = false;
            }
            if (isRefreshing()) {
                onRefreshCompleteToast();
            }
        }
        return this;
    }

    public RefreshableRecyclerViewList onLoadFailed() {
        if (this.mCommonBaseAdapter.isEmpty() || this.mSwipeRefresh.isRefreshing()) {
            onPreloadFailed();
        } else {
            onLoadModeFailed();
        }
        return this;
    }

    public RefreshableRecyclerViewList clearAndAdd(List list) {
        this.mCommonBaseAdapter.clearAndAdd(list);
        return this;
    }

    public RefreshableRecyclerViewList scrollToTop() {
        this.mRecyclerView.scrollToPosition(0);
        return this;
    }

    public RefreshableRecyclerViewList addData(List list) {
        if (list == null) {
            throw new NullPointerException("List cannot be null");
        }
        this.mCommonBaseAdapter.addData(list);
        return this;
    }

    public RefreshableRecyclerViewList onNoFoundData() {
        this.mCommonBaseAdapter.onNoFoundData();
        return this;
    }

    public List<T> getData() {
        return this.mCommonBaseAdapter.getData();
    }

    public boolean isRefreshing() {
        return this.mSwipeRefresh.isRefreshing();
    }

    public RefreshableRecyclerViewList onRefreshCompleteToast() {
        onRefreshComplete();
        ToastUtils.show("刷新完成");
        return this;
    }

    public RefreshableRecyclerViewList onPreloadFailed() {
        this.mCommonBaseAdapter.onPreloadFailed();
        onRefreshFailed();
        return this;
    }

    public RefreshableRecyclerViewList onLoadModeFailed() {
        if (isShowLoadEndView()) {
            this.mCommonBaseAdapter.loadFailed();
            onRefreshFailed();
        } else {
            this.mCommonBaseAdapter.hideLoadEndView();
        }
        return this;
    }

    private boolean isShowLoadEndView() {
        return getData().size() >= this.mPageSize;
    }

    public RefreshableRecyclerViewList onRefreshComplete() {
        setRefreshing(false);
        return this;
    }

    public RefreshableRecyclerViewList onRefreshFailed() {
        if (this.mSwipeRefresh.isRefreshing()) {
            this.mSwipeRefresh.setRefreshing(false);
        }
        return this;
    }

    public RefreshableRecyclerViewList setRefreshing(boolean refreshing) {
        this.mIsRefreshing = refreshing;
        this.mSwipeRefresh.setRefreshing(refreshing);
        return this;
    }

    public boolean isLoadListData(boolean isReload, boolean isClearData) {
        if (isReload) {
            reset(isClearData);
            return true;
        } else if (isNoMoreDate()) {
            return false;
        } else {
            return true;
        }
    }

    public RefreshableRecyclerViewList reset(boolean isClearData) {
        this.mIsFirstLoadListTag = true;
        this.mIsNoMoreData = false;
        this.mIsLoadMoreData = true;
        this.mPage = 1;
        this.mPageSize = 20;
        this.mCommonBaseAdapter.setRefreshing(this.mIsRefreshing);
        this.mCommonBaseAdapter.setLoadEnd(false);
        this.mCommonBaseAdapter.reset(isClearData);
        return this;
    }

    public boolean isNoMoreDate() {
        return this.mIsNoMoreData;
    }

    public RefreshableRecyclerViewList setDisableSliding(boolean disableSliding) {
        this.mIsSliding = disableSliding;
        return this;
    }

    public RefreshableRecyclerViewList clearData() {
        this.mCommonBaseAdapter.clearData();
        return this;
    }

    public int getPageInt() {
        return this.mPage;
    }

    public int getPageSizeInt() {
        return this.mPageSize;
    }

    public String getPage() {
        return String.valueOf(this.mPage);
    }

    public RefreshableRecyclerViewList setPage(int page) {
        this.mPage = page;
        return this;
    }

    public String getPageSize() {
        return String.valueOf(this.mPageSize);
    }

    public RefreshableRecyclerViewList setPageSize(int pageSize) {
        this.mPageSize = pageSize;
        return this;
    }

    public boolean isEmptyData() {
        return this.mCommonBaseAdapter.isEmpty();
    }
}
