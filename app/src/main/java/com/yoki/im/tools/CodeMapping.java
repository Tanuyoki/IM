package com.yoki.im.tools;

import android.util.SparseIntArray;
import com.yoki.im.R;
import java.util.HashMap;
import java.util.Map;

public class CodeMapping {
    private static Map<String, String> accountTypeCodeMapping = new HashMap();

    static {
        accountTypeCodeMapping.put("0", "分站管理员");
    }

}
