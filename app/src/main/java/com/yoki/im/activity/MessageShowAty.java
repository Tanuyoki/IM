package com.yoki.im.activity;

import android.os.Bundle;;import com.yoki.im.base.BaseActivity;
import com.yoki.im.ui.popup.PopupMessage;

public class MessageShowAty extends BaseActivity {
    /* access modifiers changed from: protected */
    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity, com.acarbang.android.base.BaseActivity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new PopupMessage(this).setMessage("HelloWorld").show();
    }
}
