package com.ysg.yashige.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.umeng.analytics.MobclickAgent;

public abstract class BaseActivity extends AppCompatActivity implements Handler.Callback, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(savedInstanceState);
        initData();
        initControl();
        initListener();
        MyApplication.getInstance().addActivity(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE|WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initData();

    protected abstract void initControl();

    protected abstract void initListener();

    protected void initNetWorks() {

    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    protected Handler mHandler = new Handler(this);

    public void sendMsg(Object compressPath, int what, Bundle bundle) {
        Message sendMsg = Message.obtain();
        sendMsg.what = what;
        sendMsg.obj = compressPath;
        sendMsg.setData(bundle);
        mHandler.sendMessage(sendMsg);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.setBaseActivity(this);
        MobclickAgent.onPageStart(this.getClass().getSimpleName()); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);          //统计时长
    }

    @Override
    protected void onPause() {
        super.onPause();
        // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }
}
