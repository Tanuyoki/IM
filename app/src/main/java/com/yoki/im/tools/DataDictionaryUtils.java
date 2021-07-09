package com.yoki.im.tools;

import com.alibaba.fastjson.JSONObject;
import com.yoki.im.tools.Constants;
import com.yoki.im.tools.okhttp.CommonOkHttpClient;
import com.yoki.im.tools.okhttp.listener.DisposeDataListener;
import com.yoki.im.tools.okhttp.request.CommonRequest;
import com.yoki.im.tools.okhttp.request.FormParams;
import com.yoki.im.tools.okhttp.response.ApiResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataDictionaryUtils {
    private static Map<String, Map<String, String>> CACHE_CODE_NAME = new HashMap();
    private static Map<String, Map<String, String>> CACHE_NAME_CODE = new HashMap();
    private static ConcurrentHashMap<String, String> locks = new ConcurrentHashMap<>();

    public static String get(String name, String code) {
        if (CACHE_CODE_NAME.get(name) == null) {
            return null;
        }
        return CACHE_CODE_NAME.get(name).get(code);
    }

    public static void getDictionary(final String name, final DictionaryCallback callback) {
        if (CACHE_CODE_NAME.get(name) == null) {
            CommonOkHttpClient.post(CommonRequest.createPostRequest(Constants.API.DATA_DICTIONARY, new FormParams().put("name", name)), new DisposeDataListener() {
                /* class com.yoki.im.tools.DataDictionaryUtils.AnonymousClass1 */

                @Override // com.yoki.im.tools.okhttp.listener.DisposeDataListener
                public void onSuccess(Object response) {
                    HashMap hashMap = new HashMap();
                    if (((ApiResponse) response).isSuccess()) {
                        Iterator<Object> it = ((ApiResponse) response).getData().getJSONArray("list").iterator();
                        while (it.hasNext()) {
                            JSONObject dic = (JSONObject) it.next();
                            hashMap.put(dic.getString("dicCode"), dic.getString("name"));
                        }
                    }
                    DataDictionaryUtils.CACHE_CODE_NAME.put(name, hashMap);
                    callback.complete(hashMap);
                }

                @Override // com.yoki.im.tools.okhttp.listener.DisposeDataListener
                public void onFailure(Object response) {
                    callback.complete(Collections.emptyMap());
                }
            });
            return;
        }
        callback.complete(CACHE_CODE_NAME.get(name));
    }

    public static void getDictionaryReverse(final String name, final DictionaryCallback callback) {
        if (CACHE_NAME_CODE.get(name) == null) {
            CommonOkHttpClient.post(CommonRequest.createPostRequest(Constants.API.DATA_DICTIONARY, new FormParams().put("name", name)), new DisposeDataListener() {
                /* class com.yoki.im.tools.DataDictionaryUtils.AnonymousClass2 */

                @Override // com.yoki.im.tools.okhttp.listener.DisposeDataListener
                public void onSuccess(Object response) {
                    HashMap hashMap = new HashMap();
                    if (((ApiResponse) response).isSuccess()) {
                        Iterator<Object> it = ((ApiResponse) response).getData().getJSONArray("list").iterator();
                        while (it.hasNext()) {
                            JSONObject dic = (JSONObject) it.next();
                            hashMap.put(dic.getString("name"), dic.getString("dicCode"));
                        }
                    }
                    DataDictionaryUtils.CACHE_NAME_CODE.put(name, hashMap);
                    callback.complete(hashMap);
                }

                @Override // com.yoki.im.tools.okhttp.listener.DisposeDataListener
                public void onFailure(Object response) {
                    callback.complete(Collections.emptyMap());
                }
            });
            return;
        }
        callback.complete(CACHE_NAME_CODE.get(name));
    }

    public static void init() {
        CommonOkHttpClient.post(CommonRequest.createPostRequest(Constants.API.DATA_DICTIONARY_ALL, new FormParams()), new DisposeDataListener() {
            /* class com.yoki.im.tools.DataDictionaryUtils.AnonymousClass3 */

            @Override // com.yoki.im.tools.okhttp.listener.DisposeDataListener
            public void onSuccess(Object response) {
                if (((ApiResponse) response).isSuccess()) {
                    try {
                        for (Map.Entry<String, Object> entry : ((ApiResponse) response).getData().entrySet()) {
                            String name = entry.getKey();
                            HashMap hashMap = new HashMap();
                            HashMap hashMap2 = new HashMap();
                            for (Map.Entry<String, Object> valueEntry : ((JSONObject) entry.getValue()).entrySet()) {
                                hashMap.put(valueEntry.getKey().substring(1), valueEntry.getValue().toString());
                                hashMap2.put(valueEntry.getValue().toString(), valueEntry.getKey().substring(1));
                            }
                            DataDictionaryUtils.CACHE_CODE_NAME.put(name, hashMap);
                            DataDictionaryUtils.CACHE_NAME_CODE.put(name, hashMap2);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
