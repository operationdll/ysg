package com.ysg.yashige.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ysg.yashige.R;
import com.ysg.yashige.utils.JumpItent;

import java.io.File;


/**
 * Created by wangtiansoft_flavin on 16/8/12.
 */
public class PopupWindows extends PopupWindow implements View.OnClickListener {

    private String lat;
    private String lng;
    private OnClickListener listener;
    private Context context;
    private String address;
    public PopupWindows(Context mContext, View parent,String lat,String lng,String startLat,String startLng,String address, OnClickListener listener) {
        this.listener = listener;
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        context = mContext;
        View view = View.inflate(mContext, R.layout.item_popupwindow, null);
        view.startAnimation(AnimationUtils.loadAnimation(mContext,
                R.anim.fade_ins));
        LinearLayout ll_popup = (LinearLayout) view
                .findViewById(R.id.ll_popup);
        ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
                R.anim.push_bottom_in_2));

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        update();
        Button btn = (Button)view.findViewById(R.id.item_popupwindows_cancel);
        btn.setOnClickListener(this);
        if (isInstallByRead("com.baidu.BaiduMap")){
            addView("百度地图",view);
        }
        if (isInstallByRead("com.autonavi.minimap")){
            addView("高德地图",view);
        }
//        addView("腾讯地图",view);
        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.line1);
        if (linearLayout.getChildCount() == 0){
            String key= "2b5e7c8259b227ddc1b49916a0fed6b3";//key
            String endLat = lat; //结束位置
            String endLng = lng; //结束位置
            String destName = "目的地";
            String url  ="http://m.amap.com/navi/?start="+startLng+","+startLat+"&dest="+endLng+","+endLat+"&destName="+destName+"&naviBy=car&key="+key;
            Bundle bundle = new Bundle();
            bundle.putString("adurl",url);
            bundle.putString("adName", "导航");
            JumpItent.jump((Activity) mContext, WebViewActivity.class,bundle);
            return;
        }else {
            if(!isOffset()) {
                showAtLocation(parent, Gravity.BOTTOM, 0,  0);
            } else{
                showAtLocation(parent, Gravity.BOTTOM, 0,  -getNavigationBarHeight());
            }
            showAtLocation(parent, Gravity.BOTTOM, 0,  -getNavigationBarHeight());
        }
    }

    void addView(String str, View view){
        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.line1);
        View v = LayoutInflater.from(context).inflate(R.layout.item_pop_window_map, null);
        TextView text = (TextView) v.findViewById(R.id.pop_text);
        text.setText(str);
        linearLayout.addView(v);
        text.setOnClickListener(this);
    }
    private int getNavigationBarHeight() {
        Resources resources = this.context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }
    public boolean isOffset() {
        return getDecorViewHeight() > getScreenHeight();
    }


    public int getDecorViewHeight(){
        return ((Activity)this.context).getWindow().getDecorView().getHeight();
    }

    public int getScreenHeight(){
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)this.context).getWindowManager().getDefaultDisplay().getMetrics(dm);//当前activity
        return dm.heightPixels;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pop_text:
                TextView textView = (TextView)v;
                String comind = textView.getText().toString();
                String appName = context.getResources().getString(R.string.app_name);
                if (comind.equals("百度地图")){
                    Intent i1 = new Intent();
                    i1.setData(Uri.parse("baidumap://map/geocoder?location="+ lat +"," + lng));
                    context.startActivity(i1);
                }else if (comind.equals("高德地图")){
                    if (isInstallByRead("com.autonavi.minimap")){
                        goToNaviActivity(context,appName,address,lat,lng,"1","2");
                    }
                }else if (comind.equals("腾讯地图")){
                    String url = "http://apis.map.qq.com/uri/v1/geocoder?coord="+ lat +","+ lng + "&referer=" + appName + "&coord_type=1";
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra("adurl", url);
                    intent.putExtra("adName", "腾讯地图");
                    context.startActivity(intent);
                }
                if (listener!=null){
                    listener.onClickListener(textView.getText().toString());
                }
                dismiss();
                break;
            case R.id.item_popupwindows_cancel:
                dismiss();
                break;
        }
    }


    /**
     * 启动高德App进行导航
     * <h3>Version</h3> 1.0
     * <h3>CreateTime</h3> 2016/6/27,13:58
     * <h3>UpdateTime</h3> 2016/6/27,13:58
     * <h3>CreateAuthor</h3>
     * <h3>UpdateAuthor</h3>
     * <h3>UpdateInfo</h3> (此处输入修改内容,若无修改可不写.)
     *
     * @param sourceApplication 必填 第三方调用应用名称。如 amap
     * @param poiname 非必填 POI 名称
     * @param lat 必填 纬度
     * @param lon 必填 经度
     * @param dev 必填 是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密)
     * @param style 必填 导航方式(0 速度快; 1 费用少; 2 路程短; 3 不走高速；4 躲避拥堵；5 不走高速且避免收费；6 不走高速且躲避拥堵；7 躲避收费和拥堵；8 不走高速躲避收费和拥堵))
     */
    public static  void goToNaviActivity(Context context,String sourceApplication , String poiname , String lat , String lon , String dev , String style){
        StringBuffer stringBuffer  = new StringBuffer("androidamap://viewReGeo?sourceApplication=")
                .append(sourceApplication);
        if (!TextUtils.isEmpty(poiname)){
            stringBuffer.append("&poiname=").append(poiname);
        }
        stringBuffer.append("&lat=").append(lat)
                .append("&lon=").append(lon)
                .append("&dev=").append(dev)
                ;
//        .append("&style=").append(style)
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuffer.toString()));
        intent.setPackage("com.autonavi.minimap");
        context.startActivity(intent);
    }

    /**
     * 根据包名检测某个APP是否安装
     * <h3>Version</h3> 1.0
     * <h3>CreateTime</h3> 2016/6/27,13:02
     * <h3>UpdateTime</h3> 2016/6/27,13:02
     * <h3>CreateAuthor</h3>
     * <h3>UpdateAuthor</h3>
     * <h3>UpdateInfo</h3> (此处输入修改内容,若无修改可不写.)
     *
     * @param packageName 包名
     * @return true 安装 false 没有安装
     */
    public static boolean isInstallByRead(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    public interface OnClickListener{
        void onClickListener(String comind);
    }
}

