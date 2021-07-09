package com.yoki.im.tools.okhttp.request;

import com.alibaba.fastjson.JSONObject;

public class JsonParams extends JSONObject implements RequestParams {
    @Override // com.alibaba.fastjson.JSONObject
    public JsonParams put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
