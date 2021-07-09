package com.yoki.im.tools;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentUtils {
    public static void add(FragmentManager manager, int containerViewId, Fragment fragment) {
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(containerViewId, fragment);
        ft.commit();
    }
}
