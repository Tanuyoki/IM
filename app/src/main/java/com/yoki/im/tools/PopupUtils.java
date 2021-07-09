package com.yoki.im.tools;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;
import com.yoki.im.ui.dialog.DialogSingleSelection;
import java.util.ArrayList;
import java.util.Map;

public class PopupUtils {

    public interface OnPopupCallback {
        void onCreate(Dialog dialog);
    }

    public static DialogSingleSelection createAccountType(Activity activity, TextView textView) {
        return new DialogSingleSelection.Builder(activity, "正式用户", "试用用户").bindResultView(textView).build();
    }

    public static void createAgentLevel(final Context ctx, final TextView textView, final String level, final OnPopupCallback callback) {
        DataDictionaryUtils.getDictionaryReverse("代理级别", new DictionaryCallback() {
            /* class com.yoki.im.tools.PopupUtils.AnonymousClass1 */

            @Override // com.yoki.im.tools.DictionaryCallback
            public void complete(Map<String, String> dictionary) {
                PopupUtils.createAgentLevel(ctx, textView, Integer.valueOf(dictionary.get(level)).intValue(), callback);
            }
        });
    }

    public static void createAgentLevel(final Context ctx, final TextView textView, final int level, final OnPopupCallback callback) {
        DataDictionaryUtils.getDictionary("代理级别", new DictionaryCallback() {
            /* class com.yoki.im.tools.PopupUtils.AnonymousClass2 */

            @Override // com.yoki.im.tools.DictionaryCallback
            public void complete(Map<String, String> dictionary) {
                ArrayList data = new ArrayList();
                for (Map.Entry<String, String> entry : dictionary.entrySet()) {
                    if (Integer.valueOf(entry.getKey()).intValue() > level) {
                        data.add(entry.getValue());
                    }
                }
                DialogSingleSelection popupAgentLevel = null;
                if (!data.isEmpty()) {
                    popupAgentLevel = new DialogSingleSelection.Builder(ctx, data).bindResultView(textView).build();
                }
                if (callback != null) {
                    callback.onCreate(popupAgentLevel);
                }
            }
        });
    }
}
