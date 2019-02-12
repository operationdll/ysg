package com.ysg.yashige.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ysg.yashige.R;
import com.ysg.yashige.base.BaseActivity;
import com.ysg.yashige.base.C;
import com.ysg.yashige.networks.RFJava.RetrofitManager;
import com.ysg.yashige.networks.RFJava.bean.GuidBean;
import com.ysg.yashige.networks.RFJava.bean.ServiceTime;
import com.ysg.yashige.utils.JumpItent;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class LaunchActivity extends BaseActivity {

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.text)
    TextView text;
    private Handler handler = new Handler();
    private Boolean isHander = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        ButterKnife.bind(this);
        text.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        handler.postDelayed(new Thread() {
            @Override
            public void run() {
                super.run();
                request();
            }
        }, 2000);
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
        isHander = false;
        JumpItent.jump(LaunchActivity.this, MainActivity.class, true);
        overridePendingTransition(R.anim.screen_alpha_in, R.anim.screen_alpha);
    }

    protected void request() {
        RetrofitManager.builder().systemGetTime(new HashMap<>())
                .flatMap(new Func1<ServiceTime, Observable<GuidBean>>() {
                    @Override
                    public Observable<GuidBean> call(ServiceTime serviceTime) {
                        String sign = C.fun.getMD5("groupid1"+"time" + serviceTime.getData().getTime() + C.network.secret_key);
                        Map map = new HashMap();
                        map.put("time", serviceTime.getData().getTime());
                        map.put("groupid", "1");
                        map.put("sign", sign);
                        return RetrofitManager.builder().appStartPicGetEffectiveAppStartPic(map);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(guidBean -> {
                    Glide.with(this)
                            .load(guidBean.getData().getList().getPic())
                            .placeholder(R.drawable.launch)
                            .into(image);
                    text.setVisibility(View.VISIBLE);
                    image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            isHander = false;
                            String url = guidBean.getData().getList().getLink();
                            Intent intent = new Intent(LaunchActivity.this, WebViewActivity.class);
                            intent.putExtra("adurl", url);
                            intent.putExtra("adName", "ad");
                            startActivity(intent);
                        }
                    });
                    handler.postDelayed(new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            if (isHander) {
                                JumpItent.jump(LaunchActivity.this, MainActivity.class, true);
                                overridePendingTransition(R.anim.screen_alpha_in, R.anim.screen_alpha);
                            }
                        }
                    }, 3000);
                }, throwable -> {
                    JumpItent.jump(LaunchActivity.this, MainActivity.class, true);
                    overridePendingTransition(R.anim.screen_alpha_in, R.anim.screen_alpha);
                });
    }


}
