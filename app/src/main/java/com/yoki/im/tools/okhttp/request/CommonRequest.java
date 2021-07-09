package com.yoki.im.tools.okhttp.request;

import java.io.File;
import java.util.Map;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class CommonRequest {
    private static final MediaType FILE_TYPE = MediaType.parse("application/octet-stream");
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    public static Request createPostRequest(String url, RequestParams params) {
        return createPostRequest(url, params, null);
    }

    public static Request createPostRequest(String url, RequestParams params, FormParams headers) {
        RequestBody requestBody;
        if (params instanceof JsonParams) {
            requestBody = RequestBody.create(MEDIA_TYPE_JSON, ((JsonParams) params).toJSONString());
        } else {
            FormParams formParams = (FormParams) params;
            FormBody.Builder mFormBodyBuild = new FormBody.Builder();
            if (params != null) {
                for (Map.Entry<String, Object> entry : formParams.urlParams.entrySet()) {
                    if (entry.getValue() != null) {
                        mFormBodyBuild.add(entry.getKey(), entry.getValue().toString());
                    }
                }
            }
            requestBody = mFormBodyBuild.build();
        }
        Headers.Builder mHeaderBuild = new Headers.Builder();
        if (headers != null) {
            for (Map.Entry<String, Object> entry2 : headers.urlParams.entrySet()) {
                if (entry2.getValue() != null) {
                    mHeaderBuild.add(entry2.getKey(), entry2.getValue().toString());
                }
            }
        }
        return new Request.Builder().url(url).post(requestBody).headers(mHeaderBuild.build()).build();
    }

    public static Request createGetRequest(String url, FormParams params) {
        return createGetRequest(url, params, null);
    }

    public static Request createGetRequest(String url, FormParams params, FormParams headers) {
        StringBuilder urlBuilder = new StringBuilder(url).append("/");
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.urlParams.entrySet()) {
                if (entry.getValue() != null) {
                    urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("/");
                }
            }
        }
        Headers.Builder mHeaderBuild = new Headers.Builder();
        if (headers != null) {
            for (Map.Entry<String, Object> entry2 : headers.urlParams.entrySet()) {
                if (entry2.getValue() != null) {
                    mHeaderBuild.add(entry2.getKey(), entry2.getValue().toString());
                }
            }
        }
        return new Request.Builder().url(urlBuilder.substring(0, urlBuilder.length() - 1)).get().headers(mHeaderBuild.build()).build();
    }

    public static Request createMonitorRequest(String url, FormParams params) {
        StringBuilder urlBuilder = new StringBuilder(url).append("/");
        if (params != null && params.hasParams()) {
            for (Map.Entry<String, Object> entry : params.urlParams.entrySet()) {
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("/");
            }
        }
        return new Request.Builder().url(urlBuilder.substring(0, urlBuilder.length() - 1)).get().build();
    }

    public static Request createMultiPostRequest(String url, FormParams params) {
        MultipartBody.Builder requestBody = new MultipartBody.Builder();
        requestBody.setType(MultipartBody.FORM);
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.fileParams.entrySet()) {
                if (entry.getValue() instanceof File) {
                    requestBody.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""), RequestBody.create(FILE_TYPE, (File) entry.getValue()));
                } else if (entry.getValue() instanceof String) {
                    requestBody.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""), RequestBody.create((MediaType) null, (String) entry.getValue()));
                }
            }
        }
        return new Request.Builder().url(url).post(requestBody.build()).build();
    }
}
