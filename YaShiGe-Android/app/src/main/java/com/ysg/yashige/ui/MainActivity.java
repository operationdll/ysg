package com.ysg.yashige.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.ysg.yashige.base.C;
import com.ysg.yashige.base.MyApplication;
import com.ysg.yashige.networks.commons.NetWorkManager;
import com.ysg.yashige.networks.model.VersionModel;
import com.ysg.yashige.utils.CheckVersionUtil;
import com.ysg.yashige.utils.JumpItent;
import com.ysg.yashige.utils.KeyBoardListener;
import com.ysg.yashige.utils.SharePreUtil;
import com.ysg.yashige.utils.Toastutils;
import com.ysg.yashige.utils.UpdateVersionManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends WebPageActivity {
    private final static String TAG = "#####";
    public static MainActivity activity;
    private String lat = "0";
    private String lng = "0";
    public Boolean flag = false;
    private Boolean isWebView = true;
    private String endLat = "";
    private String endlng = "";
    private JSInterface jsInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NetWorkManager.requestTime();
        KeyBoardListener.getInstance(this).init();
    }

    @Override
    protected void initJSInterface() {
        //设置本地调用对象及其接口
        jsInterface = new JSInterface();
        webView.addJavascriptInterface(jsInterface, "JSInterface");
    }

    private void getPermission() {
        PermissionGen.with(this)
                .addRequestCode(100)
                .permissions(
                        Manifest.permission.LOCATION_HARDWARE,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 100)
    public void requestSdcardSuccess() {
        jsInterface.mLocationClient.startLocation();
    }

    @PermissionFail(requestCode = 100)
    public void requestSdcardFailed() {
        webView.loadUrl("javascript:setLocationLat(-1,-1)");
    }

    @Override
    public void openVuePage() {
        if (!flag) return;
        super.openVuePage();
        if (getIntent().getStringExtra("data") != null && getIntent().getStringExtra("data").length() > 0) {
            Message message = new Message();
            message.what = 2;
            handler.sendMessage(message);
        }
        flag = false;
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (isWebView) {
                    webView.loadUrl("javascript:setLocationLat(" + lat + "," + lng + ")");
                } else {
                    new PopupWindows(MainActivity.this, getRootView(MainActivity.this), endLat, endlng, lat, lng, "", new PopupWindows.OnClickListener() {
                        @Override
                        public void onClickListener(String comind) {

                        }
                    });
                }
            } else if (msg.what == 2) {
                webView.loadUrl(C.network.home_url + "/#/home/news/newDetail");
            }
            super.handleMessage(msg);
        }
    };

    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharePreUtil.getInstance().putBoolean("first", false);
    }

    public class JSInterface {

        public JSInterface() {
            mLocationClient = new AMapLocationClient(getApplicationContext());
            mLocationClient.setLocationListener(mLocationListener);
            getPermission();
        }

        public AMapLocationClient mLocationClient = null;
        //声明定位回调监听器
        public AMapLocationListener mLocationListener = new AMapLocationListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {

                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        mLocationClient.stopLocation();
                        lat = String.format("%.4f", aMapLocation.getLatitude());
                        lng = String.format("%.4f", aMapLocation.getLongitude());
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    } else {
                        mLocationClient.stopLocation();
                    }
                }

            }
        };

        /**
         * Android端js入口。示例：AndroidJS.request(0, "", "");
         *
         * @param method_type int类型，0：GET；1：POST
         * @param address     string类型，接口地址
         * @param params      string类型，入参json
         */
        @JavascriptInterface //sdk17版本以上加上注解
        public String request(final int method_type, final String address, final String params) {
            if (C.app.time_offset == -1) {
                return C.app.msg;
            } else {
                if (TextUtils.isEmpty(params)) {
                    return "参数为空";
                }
                TreeMap<String, Object> paramMap = new TreeMap<>(new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        // 升序排序
                        return obj1.compareTo(obj2);
                    }
                });
                try {
                    paramMap.putAll((Map<? extends String, ?>) JSON.parse(params));
                } catch (Exception e) {
                    paramMap.clear();
                    e.printStackTrace();
                    return "参数格式错误";
                }
                String time = (C.app.time_offset + System.currentTimeMillis()) + "";
                String sign = C.fun.encrypt(paramMap);
                String json = "{\"time\":\"" + time + "\",\"sign\":\"" + sign + "\"}";
                Log.e("json", json);
                return json;
            }
        }

        @JavascriptInterface
        public void openMap(String lng, String lat) {
            endLat = lat;
            endlng = lng;
            isWebView = false;
            mLocationClient.startLocation();
        }

        @JavascriptInterface
        public void saveJSInfo(String jsonStr) {
            SharePreUtil.getInstance().putString("JSInfo", jsonStr);
        }

        @JavascriptInterface
        public String getJSInfo() {
            return SharePreUtil.getInstance().getString("JSInfo");
        }

        @JavascriptInterface
        public void getLocation() {
            isWebView = true;
            mLocationClient.startLocation();
        }

        @JavascriptInterface
        public String getLocationL() {
            return lng + "#" + lat;
        }

        @JavascriptInterface
        public boolean getFirstLaunch() {
            return SharePreUtil.getInstance().getBoolean("first", true);
        }

        @JavascriptInterface
        public void setMessageTag(String a, String b, String[] c) {
            MyApplication.getInstance().setAliasTag(a, b, c);
        }

        @JavascriptInterface
        public void removeMessageTag(String a, String b, String[] c) {
            MyApplication.getInstance().removeMessageTag(a, b, c);
        }

        @JavascriptInterface
        public void openPDF(String url) {
            JumpItent.jump(MainActivity.this, PDFViewActivity.class, "pdfUrl", url);
        }

        @JavascriptInterface
        public void openVideo(String url) {
            JumpItent.jump(MainActivity.this, VideoActivity.class, "videoUrl", url);
        }

        @JavascriptInterface
        public void openUrl(String url, String title) {
            Bundle bundle = new Bundle();
            bundle.putString("adurl", url);
            bundle.putString("adName", title + "");
            JumpItent.jump(MainActivity.this, WebViewActivity.class, bundle);
        }

        @JavascriptInterface
        public String getNotifaction() {
            Boolean result = SharePreUtil.getInstance().containFirst(C.Key_Notification);
            String strNotification = "[]";
            if (result) {
                strNotification = SharePreUtil.getInstance().getString(C.Key_Notification);
            }
            return strNotification;
        }

        @JavascriptInterface
        public void isHomePage(int isHome) {
            MyApplication.isHome = isHome;
        }

        //开始xml的网络请求
        @JavascriptInterface
        public void startXmlNetWork(String xmlUrl) {
            requestXmlNetWork(xmlUrl);
        }

        //跳转外部浏览器
        @JavascriptInterface
        public void getSystemBrowser(String url) {
            openSystemBrowser(url);
        }

        //检查版本更新
        @JavascriptInterface
        public void checkVersion(String modelJson) {
            reminderUpdate(modelJson);
        }
    }

    /**
     * 提示更新
     *
     * @param modelJson
     */
    private void reminderUpdate(String modelJson) {
        VersionModel versionModel = JSON.parseObject(modelJson,VersionModel.class);
        versionModel.setVersion(versionModel.getVersion().replaceAll("v",""));
        if (CheckVersionUtil.isUpVersion(MainActivity.this, versionModel.getVersion())) {
            //传入下载地址，以及版本更新消息
            UpdateVersionManager versionManager = new UpdateVersionManager(this, versionModel.isMust(),
                    versionModel.getLink(), versionModel.getDoneTitle(), versionModel.getCancelTitle(), versionModel.getDesc());
            versionManager.showNoticeDialog();
        } else {
            Toastutils.toast(versionModel.getNewDesc());
        }
    }

    /**
     * 跳转外部浏览器
     *
     * @param url
     */
    private void openSystemBrowser(String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    /**
     * 请求xml数据
     */
    private void requestXmlNetWork(String xmlUrl) {
        NetWorkManager.requestUrl(xmlUrl, new NetWorkManager.OnSucceed() {
            @Override
            public void succeed(final String data) {
                //返回xml数据
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadUrl("javascript:setXmlData('" + data + "')");
                    }
                });
            }
        });
    }

    private static View getRootView(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }

}
