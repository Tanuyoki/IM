package com.yoki.im.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class BaseWebView extends WebView {
    public BaseWebView(Context context) {
        super(context);
        initView();
    }

    public BaseWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BaseWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void initView() {
        getSettings().setJavaScriptEnabled(true);
        getSettings().setSupportZoom(true);
        getSettings().setBuiltInZoomControls(true);
        getSettings().setUseWideViewPort(true);
        getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        getSettings().setDisplayZoomControls(false);
        getSettings().setLoadWithOverviewMode(true);
        getSettings().setCacheMode(2);
        setWebViewClient(new WebViewClient() {
            /* class com.yoki.im.ui.BaseWebView.AnonymousClass1 */

            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    public void enableKeyListener() {
        setOnKeyListener(new OnKeyListener() {
            /* class com.yoki.im.ui.BaseWebView.AnonymousClass2 */

            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction() != 0 || keyCode != 4 || !BaseWebView.this.canGoBack()) {
                    return false;
                }
                BaseWebView.this.goBack();
                return true;
            }
        });
    }

    public void setLoading(final ProgressBar progressBar) {
        setWebChromeClient(new WebChromeClient() {
            /* class com.yoki.im.ui.BaseWebView.AnonymousClass3 */

            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(8);
                    return;
                }
                progressBar.setVisibility(0);
                progressBar.setProgress(newProgress);
            }
        });
    }
}
