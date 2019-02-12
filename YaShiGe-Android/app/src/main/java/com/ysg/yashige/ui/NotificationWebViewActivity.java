package com.ysg.yashige.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.ysg.yashige.R;
import com.ysg.yashige.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 点击通知的详情页面
 */
public class NotificationWebViewActivity extends BaseActivity {

    @BindView(R.id.finish_iV)
    ImageView finishIV;
    @BindView(R.id.title_tV)
    TextView titleTV;
    @BindView(R.id.notifi_wV)
    WebView notifiWV;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_notification_web_view);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        titleTV.setText(bundle.getString("title"));
        WebSettings settings = notifiWV.getSettings();
        settings.setJavaScriptEnabled(true); //如果访问的页面中有Javascript，则WebView必须设置支持Javascript
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportZoom(true); //支持缩放
        settings.setBuiltInZoomControls(true); //支持手势缩放
        settings.setDisplayZoomControls(false); //是否显示缩放按钮

        // >= 19(SDK4.4)启动硬件加速，否则启动软件加速
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            notifiWV.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        } else {
            notifiWV.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            settings.setLoadsImagesAutomatically(false);
        }
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        settings.setGeolocationDatabasePath(dir);
        settings.setGeolocationEnabled(true);
        settings.setUseWideViewPort(true); //将图片调整到适合WebView的大小
        settings.setLoadWithOverviewMode(true); //自适应屏幕
        settings.setDomStorageEnabled(true);
        settings.setSaveFormData(true);
        settings.setSupportMultipleWindows(true);
        // settings.setCacheMode(WebSettings.LOAD_DEFAULT); //优先使用缓存;
        // settings.setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        // settings.setAppCachePath(appCachePath);
        settings.setAllowFileAccess(true);
        // settings.setAppCacheEnabled(true);
        notifiWV.setHorizontalScrollbarOverlay(true);
        notifiWV.setHorizontalScrollBarEnabled(false);
        notifiWV.setOverScrollMode(View.OVER_SCROLL_NEVER); // 取消WebView中滚动或拖动到顶部、底部时的阴影
        notifiWV.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); // 取消滚动条白边效果
        notifiWV.requestFocus();
        notifiWV.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        notifiWV.loadUrl(bundle.getString("url"));

    }

    @Override
    protected void initControl() {

    }

    @Override
    protected void initListener() {
        finishIV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.finish_iV:
                finishThis();
                break;
        }
    }

    private void finishThis() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        finishThis();
        return super.onKeyDown(keyCode, event);
    }
}
