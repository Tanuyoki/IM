package com.yoki.im.tools.okhttp.request;

import java.util.HashMap;
import java.util.Map;

public class FormParams implements RequestParams {
    public HashMap<String, Object> fileParams;
    public HashMap<String, Object> urlParams;

    public FormParams() {
        this(null);
    }

    public FormParams(Map<String, String> source) {
        this.urlParams = new HashMap<>();
        this.fileParams = new HashMap<>();
        if (source != null) {
            for (Map.Entry<String, String> entry : source.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    public FormParams(final String key, final String value) {
        this(new HashMap<String, String>() {
            /* class com.yoki.im.tools.okhttp.request.FormParams.AnonymousClass1 */

            {
                put(key, value);
            }
        });
    }

    public FormParams put(String key, String value) {
        if (!(key == null || value == null)) {
            this.urlParams.put(key, value);
        }
        return this;
    }

    public FormParams put(String key, Object object) {
        if (key != null) {
            this.urlParams.put(key, object);
        }
        return this;
    }

    public FormParams putAll(Map<String, Object> params) {
        this.urlParams.putAll(params);
        return this;
    }

    public boolean hasParams() {
        if (this.urlParams.size() > 0 || this.fileParams.size() > 0) {
            return true;
        }
        return false;
    }
}
