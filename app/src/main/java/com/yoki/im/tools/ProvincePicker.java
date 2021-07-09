package com.yoki.im.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
//import com.yoki.im.bean.ProvinceBean;
import com.yoki.im.tools.Constants;
import com.yoki.im.tools.okhttp.CommonOkHttpClient;
import com.yoki.im.tools.okhttp.listener.DisposeDataListener;
import com.yoki.im.tools.okhttp.request.CommonRequest;
import com.yoki.im.tools.okhttp.request.FormParams;
import com.yoki.im.tools.okhttp.response.ApiResponse;
import com.yoki.im.R;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
//import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
//import com.bigkoo.pickerview.listener.CustomListener;
//import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
//import com.bigkoo.pickerview.view.OptionsPickerView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProvincePicker {
    private static final int MSG_LOAD_DATA = 0;
    private static final int MSG_LOAD_SUCCESS = 1;
    private Context mContext;
    @SuppressLint({"HandlerLeak"})
    private Handler mHandler = new Handler() {
        /* class com.yoki.im.tools.ProvincePicker.AnonymousClass1 */

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (ProvincePicker.this.mThread == null) {
                        ProvincePicker.this.mThread = new Thread(new Runnable() {
                            /* class com.yoki.im.tools.ProvincePicker.AnonymousClass1.AnonymousClass1 */

                            public void run() {
                                ProvincePicker.this.loadData();
                            }
                        });
                        ProvincePicker.this.mThread.start();
                        return;
                    }
                    return;
                case 1:
                    ProvincePicker.this.initView();
                    if (ProvincePicker.this.mIsShowToast) {
                        ProvincePicker.this.mIsShowToast = false;
                        ToastUtils.show("数据加载完成");
                    }
                    ProvincePicker.this.mIsLoadSuccess = true;
                    return;
                default:
                    return;
            }
        }
    };
    private boolean mIsLoadSuccess = false;
    private boolean mIsShowToast = false;
//    private OptionsPickerView mPvOptions;
    private Thread mThread;
    private OnClickListener onClickListener;
//    private List<ProvinceBean> options1Items = new ArrayList();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    public interface OnClickListener {
//        void onAddClick(ProvinceBean provinceBean, ProvinceBean.CityBean cityBean, ProvinceBean.CityBean.AreaBean areaBean);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.onClickListener = listener;
    }

    public ProvincePicker(Context context) {
        this.mContext = context;
        this.mHandler.sendEmptyMessage(0);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void loadData() {
        CommonOkHttpClient.post(CommonRequest.createPostRequest(Constants.API.AREA_LIST, new FormParams().put("hasCounty", "1")), new DisposeDataListener() {
            /* class com.yoki.im.tools.ProvincePicker.AnonymousClass2 */

            @Override // com.yoki.im.tools.okhttp.listener.DisposeDataListener
            public void onSuccess(Object response) {
                ApiResponse api = (ApiResponse) response;
                if (api.isSuccess()) {
                    ProvincePicker.this.initData(api.getData());
                }
            }
        });
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void initData(JSONObject jsonObject) {
//        this.options1Items = JSONObject.parseArray(String.valueOf(jsonObject.getJSONArray("list")), ProvinceBean.class);
        Iterator<Object> it = jsonObject.getJSONArray("list").iterator();
        while (it.hasNext()) {
            ArrayList<String> cityList = new ArrayList<>();
            ArrayList<ArrayList<String>> provinceAreaList = new ArrayList<>();
            JSONArray cityArray = ((JSONObject) it.next()).getJSONArray("children");
            Iterator<Object> it2 = cityArray.iterator();
            while (it2.hasNext()) {
                JSONObject city = (JSONObject) it2.next();
                cityList.add(city.getString("name"));
                ArrayList<String> areaList = new ArrayList<>();
                JSONArray areaArray = city.getJSONArray("children");
                Iterator<Object> it3 = areaArray.iterator();
                while (it3.hasNext()) {
                    areaList.add(((JSONObject) it3.next()).getString("name"));
                }
                if (areaArray.size() == 0) {
                    areaList.add("");
                }
                provinceAreaList.add(areaList);
            }
            if (cityArray.size() == 0) {
                cityList.add("");
                ArrayList<String> list = new ArrayList<>();
                list.add("");
                provinceAreaList.add(list);
            }
            this.options2Items.add(cityList);
            this.options3Items.add(provinceAreaList);
        }
        this.mHandler.sendEmptyMessage(1);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void initView() {
//        this.mPvOptions = new OptionsPickerBuilder(this.mContext, new OnOptionsSelectListener() {
//            /* class com.yoki.im.tools.ProvincePicker.AnonymousClass4 */
//
//            @Override // com.bigkoo.pickerview.listener.OnOptionsSelectListener
//            public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                ProvinceBean provinceBean = (ProvinceBean) ProvincePicker.this.options1Items.get(options1);
//                ProvinceBean.CityBean cityBean = null;
//                ProvinceBean.CityBean.AreaBean areaBean = null;
//                if (!(provinceBean.getChildren() == null || provinceBean.getChildren().size() == 0)) {
//                    cityBean = provinceBean.getChildren().get(options2);
//                }
//                if (!(cityBean == null || cityBean.getChildren().size() == 0)) {
//                    areaBean = cityBean.getChildren().get(options3);
//                }
//                if (ProvincePicker.this.onClickListener != null) {
//                    ProvincePicker.this.onClickListener.onAddClick(provinceBean, cityBean, areaBean);
//                }
//            }
//        }).setLayoutRes(R.layout.layout_province_picker, new CustomListener() {
//            /* class com.yoki.im.tools.ProvincePicker.AnonymousClass3 */
//
//            @Override // com.bigkoo.pickerview.listener.CustomListener
//            public void customLayout(View v) {
//                ((TextView) v.findViewById(R.id.layout_province_picker_tv_add)).setOnClickListener(new View.OnClickListener() {
//                    /* class com.yoki.im.tools.ProvincePicker.AnonymousClass3.AnonymousClass1 */
//
//                    public void onClick(View v) {
//                        ProvincePicker.this.mPvOptions.returnData();
//                        ProvincePicker.this.mPvOptions.dismiss();
//                    }
//                });
//            }
//        }).setOutSideCancelable(true).setBackgroundId(ViewCompat.MEASURED_SIZE_MASK).setLineSpacingMultiplier(1.9f).setContentTextSize(26).setDecorView(null).build();
//        this.mPvOptions.setPicker(this.options1Items, this.options2Items, this.options3Items);
//        this.mPvOptions.setKeyBackCancelable(true);
    }

    public void show() {
        if (this.mIsLoadSuccess) {
//            this.mPvOptions.show(false);
            return;
        }
        ToastUtils.show("加载数据中...");
        this.mIsShowToast = true;
    }
}
