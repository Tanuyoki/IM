package com.yoki.im.tools;

import com.yoki.im.tools.okhttp.CommonOkHttpClient;
import com.yoki.im.tools.okhttp.listener.DisposeDataListener;
import com.yoki.im.tools.okhttp.request.CommonRequest;
import com.yoki.im.tools.okhttp.request.FormParams;
import com.yoki.im.tools.okhttp.request.JsonParams;
import com.yoki.im.tools.okhttp.request.RequestParams;
import com.yoki.im.tools.okhttp.response.ApiResponse;
import com.yoki.im.ui.RefreshableRecyclerViewList;
import java.util.List;

public abstract class NetworkListHelper {
    private boolean mIsRefresh;
    private boolean mIsReload;
    private RefreshableRecyclerViewList mList = initList();
    private RequestParams mParams;
    private String mUrl;

    /* access modifiers changed from: protected */
    public abstract RefreshableRecyclerViewList initList();

    public abstract List onGenerateData(ApiResponse apiResponse);

    public abstract void onPreloading();

    protected NetworkListHelper() {
        if (this.mList.getOnListManageListener() == null) {
            this.mList.setOnListManageListener(new RefreshableRecyclerViewList.OnListManageListener() {
                /* class com.yoki.im.tools.NetworkListHelper.AnonymousClass1 */

                @Override // com.yoki.im.ui.RefreshableRecyclerViewList.OnListManageListener
                public void onRefresh() {
                    NetworkListHelper.this.onRefresh();
                }

                @Override // com.yoki.im.ui.RefreshableRecyclerViewList.OnListManageListener
                public void onLoadMore() {
                    NetworkListHelper.this.onLoadMore();
                }

                @Override // com.yoki.im.ui.RefreshableRecyclerViewList.OnListManageListener
                public void onPreloading() {
                    NetworkListHelper.this.mIsRefresh = true;
                    NetworkListHelper.this.onPreloading();
                }
            });
        }
    }

    public FormParams generateFormParams() {
        FormParams params = new FormParams();
        params.put("page", this.mList.getPage());
        params.put("pageSize", this.mList.getPageSize());
        return params;
    }

    public void loadList(String url, RequestParams params, boolean isReload) {
        loadList(url, params, isReload, true);
    }

    public void loadList(String url, RequestParams params, boolean isReload, boolean isClearData) {
        this.mIsRefresh = isClearData;
        if (this.mList.isLoadListData(isReload, this.mIsRefresh)) {
            this.mUrl = url;
            this.mParams = params;
            this.mIsReload = isReload;
            updateParams();
            loadList();
        }
    }

    private void loadList() {
        if (this.mUrl != null && !this.mUrl.isEmpty() && this.mParams != null) {
            CommonOkHttpClient.post(CommonRequest.createPostRequest(this.mUrl, this.mParams), new DisposeDataListener() {
                /* class com.yoki.im.tools.NetworkListHelper.AnonymousClass2 */

                @Override // com.yoki.im.tools.okhttp.listener.DisposeDataListener
                public void onSuccess(Object response) {
                    NetworkListHelper.this.onSuccess(response);
                }

                @Override // com.yoki.im.tools.okhttp.listener.DisposeDataListener
                public void onFailure(Object response) {
                    NetworkListHelper.this.onFailure(response);
                }
            });
        }
    }

    public void onSuccess(Object response) {
        ApiResponse api = (ApiResponse) response;
        if (api.isSuccess()) {
            this.mList.addListData(onGenerateData(api), this.mIsReload);
            return;
        }
        this.mList.onLoadFailed();
        ToastUtils.show(api.getMsg());
    }

    public void onFailure(Object response) {
        if (this.mIsRefresh) {
            this.mList.onLoadFailed();
        } else if (this.mList.isRefreshing()) {
            this.mList.setRefreshing(false);
            this.mList.onLoadModeFailed();
            ToastUtils.show("刷新失败");
        }
    }

    public void onRefresh() {
        loadList(this.mUrl, this.mParams, true, false);
    }

    public void onLoadMore() {
        loadList(this.mUrl, this.mParams, false);
    }

    public void updateParams() {
        if (this.mParams != null) {
            if (this.mParams instanceof FormParams) {
                ((FormParams) this.mParams).put("page", this.mList.getPage());
                ((FormParams) this.mParams).put("pageSize", this.mList.getPageSize());
            } else if (this.mParams instanceof JsonParams) {
                ((JsonParams) this.mParams).put("page", (Object) this.mList.getPage());
                ((JsonParams) this.mParams).put("pageSize", (Object) this.mList.getPageSize());
            }
        }
    }

    public boolean isReload() {
        return this.mIsReload;
    }

    public List getListData() {
        return this.mList.getData();
    }

    public void remove(int pos) {
        this.mList.remove(pos);
    }
}
