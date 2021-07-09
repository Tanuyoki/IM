package com.yoki.im.tools;

import android.view.View;
import com.yoki.im.ui.popup.PopupMenu;
import com.yoki.im.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PopupMenuUtils {
    public static final int CODE_COPY = 1;
    public static final int CODE_NULL = 0;
    private static boolean IS_DISMISS = false;
    public static final int MENU_TYPE_PHOTO = 0;
    public static final int MENU_TYPE_TEXT = 1;

    public static PopupMenu init(List<String> list, final OnMenuItemClickImpl listener) {
        if (list == null || list.size() == 0) {
            return null;
        }
        final PopupMenu menu = new PopupMenu(CommonData.NowContext);
        menu.setOnMenuItemClickListener(new OnMenuItemClickImpl() {
            /* class com.yoki.im.tools.PopupMenuUtils.AnonymousClass1 */

            @Override // com.yoki.im.tools.OnMenuItemClickImpl
            public void onClick(String title, int functionCode) {
                if (listener != null) {
                    listener.onClick(title, functionCode);
                }
                menu.dismiss();
            }
        });
        int size = list.size();
        for (int i = 0; i < size; i++) {
            int code = 0;
            if ("复制".equals(list.get(i))) {
                code = 1;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("Code", Integer.valueOf(code));
            map.put("Title", list.get(i));
            menu.addItem(map);
        }
        IS_DISMISS = false;
        return menu;
    }

    public static PopupMenu init(int menuType, OnMenuItemClickImpl listener) {
        List<String> list = new ArrayList<>();
        switch (menuType) {
            case 0:
                list.add("保存到相册");
                break;
            case 1:
//                Collections.addAll(list, App.getContext().getResources().getStringArray(R.array.menu_text));
                break;
        }
        return init(list, listener);
    }

    public static void showMenu(View view, OnMenuItemClickImpl listener) {
        IS_DISMISS = true;
        init(1, listener).showAsDropDown(view);
    }

    public static void showMenu(int menuType, View view, OnMenuItemClickImpl listener) {
        IS_DISMISS = true;
        init(menuType, listener).showAsDropDown(view);
    }

    public static void showMenu(List<String> list, View view, OnMenuItemClickImpl listener) {
        IS_DISMISS = true;
        init(list, listener).showAsDropDown(view);
    }
}
