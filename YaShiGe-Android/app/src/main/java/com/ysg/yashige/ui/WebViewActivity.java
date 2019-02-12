package com.ysg.yashige.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.ysg.yashige.R;
import com.ysg.yashige.base.BaseActivity;
import com.ysg.yashige.utils.JumpItent;

public class WebViewActivity extends BaseActivity {

    WebView webView;
    WebSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        String url = getIntent().getStringExtra("adurl");
        if (!getIntent().getStringExtra("adName").equals("ad")) {
            TextView textView = (TextView) findViewById(R.id.tv_title);
            textView.setText(getIntent().getStringExtra("adName"));
        }
        String adurl = "";
        if (url.startsWith("https://") || url.startsWith("http://")) {
            adurl = url;
        } else {
            adurl = "http://" + getIntent().getStringExtra("adurl");
        }

        webView = (WebView) findViewById(R.id.webView);
        settings = webView.getSettings();

        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        settings.setGeolocationDatabasePath(dir);
        settings.setGeolocationEnabled(true);
        settings.setJavaScriptEnabled(true); //如果访问的页面中有Javascript，则WebView必须设置支持Javascript
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportZoom(true); //支持缩放
        settings.setBuiltInZoomControls(true); //支持手势缩放
        settings.setDisplayZoomControls(false); //是否显示缩放按钮

        // >= 19(SDK4.4)启动硬件加速，否则启动软件加速
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            settings.setLoadsImagesAutomatically(false);
        }

        settings.setUseWideViewPort(true); //将图片调整到适合WebView的大小
        settings.setLoadWithOverviewMode(true); //自适应屏幕
        settings.setDomStorageEnabled(true);
        settings.setSaveFormData(true);
        settings.setSupportMultipleWindows(true);
        // settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT); //优先使用缓存

        webView.setHorizontalScrollbarOverlay(true);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER); // 取消WebView中滚动或拖动到顶部、底部时的阴影
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); // 取消滚动条白边效果
        webView.requestFocus();
        clearCookies();
        webView.loadUrl(adurl);
        webView.setWebChromeClient(new WebChromeClient() {
            public void onGeolocationPermissionsShowPrompt(String origin,
                                                           GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL,
                            Uri.parse(url));
                    startActivity(intent);
                } else {
                    view.loadUrl(url);
                }
                return true;
            }
        });
    }

    public void clearCookies() {
        webView.clearHistory();
        webView.clearFormData();
        webView.clearCache(true);

        CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();
        cookieManager.removeAllCookie();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initControl() {

    }

    @Override
    protected void initListener() {

    }

    public void onClick(View v) {
        if (getIntent().getStringExtra("adName").equals("ad")) {
            JumpItent.jump(this, MainActivity.class, true);
            overridePendingTransition(R.anim.screen_alpha_in, R.anim.screen_alpha);
            return;
        }
        finish();
    }

}
