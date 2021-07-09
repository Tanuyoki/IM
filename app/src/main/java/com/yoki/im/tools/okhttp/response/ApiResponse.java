package com.yoki.im.tools.okhttp.response;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ApiResponse {
    private String code;
    private JSONObject data;
    private JSONArray dataArray;
    private JSONObject extData;
    private String msg;
    private boolean success;

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success2) {
        this.success = success2;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg2) {
        this.msg = msg2;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code2) {
        this.code = code2;
    }

    public JSONObject getData() {
        return this.data;
    }

    public void setData(JSONObject data2) {
        this.data = data2;
    }

    public JSONObject getExtData() {
        return this.extData;
    }

    public void setExtData(JSONObject extData2) {
        this.extData = extData2;
    }

    public JSONArray getDataArray() {
        return this.dataArray;
    }

    public void setDataArray(JSONArray dataArray2) {
        this.dataArray = dataArray2;
    }
}
