package com.ysg.yashige.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ysg.yashige.R;
import com.ysg.yashige.base.BaseActivity;
import com.ysg.yashige.base.C;
import com.ysg.yashige.base.MyApplication;
import com.ysg.yashige.utils.LoadingDialog;
import com.ysg.yashige.utils.Toastutils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("SetJavaScriptEnabled")
public abstract class WebPageActivity extends BaseActivity {
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.web_loading)
    TextView webLoading;

    /**
     * 视频全屏参数
     */
    private View customView;
    private FrameLayout fullscreenContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;

    private boolean isFirstLoadOver = true;
    private boolean loading;

    //file控件上传需要
    private ValueCallback mUploadMessage;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_web_page);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initControl() {

        WebSettings settings = webView.getSettings();
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
        webView.setHorizontalScrollbarOverlay(true);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER); // 取消WebView中滚动或拖动到顶部、底部时的阴影
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); // 取消滚动条白边效果
        webView.requestFocus();
        initJSInterface();
        loading = false;
        webView.loadUrl(C.network.home_url);
        LoadingDialog.getInstance(this).showDialog();
        if (Build.VERSION.SDK_INT >= 21) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(
                    WebView view,
                    String url,
                    String message,
                    JsResult result
            ) {
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    LoadingDialog.getInstance(WebPageActivity.this).hideDialog();
                    openVuePage();
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                if (s.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL,
                            Uri.parse(s));
                    startActivity(intent);
                } else {
                    webView.loadUrl(s);
                }
                return true;
            }
        });
    }

    public void openVuePage() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 1) {
            if (null == mUploadMessage)
                return;
            Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
            if (result != null)
                mUploadMessage.onReceiveValue(new Uri[]{result});
            else
                mUploadMessage.onReceiveValue(null);
            mUploadMessage = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 视频播放全屏
     **/
    private void showCustomView(View view, WebChromeClient.CustomViewCallback callback) {
        // if a view already exists then immediately terminate the new one
        if (customView != null) {
            callback.onCustomViewHidden();
            return;
        }

        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        fullscreenContainer = new FullscreenHolder(WebPageActivity.this);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        fullscreenContainer.addView(view, layoutParams);
        decor.addView(fullscreenContainer, layoutParams);
        customView = view;
        setStatusBarVisibility(false);
        customViewCallback = callback;
    }

    /**
     * 隐藏视频全屏
     */
    private void hideCustomView() {
        if (customView == null) {
            return;
        }

        setStatusBarVisibility(true);
        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        decor.removeView(fullscreenContainer);
        fullscreenContainer = null;
        customView = null;
        customViewCallback.onCustomViewHidden();
        webView.setVisibility(View.VISIBLE);
    }

    /**
     * 全屏容器界面
     */
    static class FullscreenHolder extends FrameLayout {

        public FullscreenHolder(Context ctx) {
            super(ctx);
            setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
        }

        @Override
        public boolean onTouchEvent(MotionEvent evt) {
            return true;
        }
    }

    private void setStatusBarVisibility(boolean visible) {
        int flag = visible ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    protected void onFirstLoadOver() {

    }

    protected abstract void initJSInterface();

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            /** 回退键 事件处理 优先级:视频播放全屏-网页回退-关闭页面 */
            if (customView != null) {
                hideCustomView();
            } else if (webView.canGoBack()) {
//                if (webView.getUrl().equals(C.network.home_url + "#/raiders") ||
//                        webView.getUrl().equals(C.network.home_url + "#/promotion") ||
//                        webView.getUrl().equals(C.network.home_url + "#/home") ||
//                        webView.getUrl().equals(C.network.home_url + "#/communityLife") ||
//                        webView.getUrl().equals(C.network.home_url + "#/unlogin")) {
//                    ExitApp();
                if (MyApplication.isHome == 1) {
                    ExitApp();
                    return true;
                }
                webView.goBack();
                webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                return true;
            } else {
                ExitApp();      //调用双击退出函数
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private long exitTime = 0;

    public void ExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toastutils.show("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            System.exit(0);//正常退出App
        }
    }

    public void clearWebViewCache() {
        webView.clearCache(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        // WebView 缓存文件
        File appCacheDir = new File("data/data/" + getPackageName() + "/app_webview");
        //清除WebView缓存
        if (appCacheDir.exists()) {
            deleteFile(appCacheDir);
        }
        // 清除cookie即可彻底清除缓存
        CookieSyncManager.createInstance(this);
        CookieManager.getInstance().removeAllCookie();
    }


    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    public void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        }
    }
}
