package com.ysg.yashige.ui;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ysg.yashige.R;
import com.ysg.yashige.base.BaseActivity;
import com.ysg.yashige.base.BasePagerAdapter;
import com.ysg.yashige.utils.Toastutils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeADActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.skip)
    TextView skipText;
    @BindView(R.id.use)
    TextView userText;
    private PagerAdapter adapter;
    private ArrayList<View> views = new ArrayList<>();
    private int[] images = new int[]{};

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_welcome_ad);
        ButterKnife.bind(this);
        skipText.setOnClickListener(this);
        userText.setOnClickListener(this);
        viewPager.setOnPageChangeListener(this);
    }


    @Override
    protected void initData() {
        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(images[i]);
            views.add(imageView);
        }
        adapter = new BasePagerAdapter(views);
        viewPager.setAdapter(adapter);
    }


    @Override
    protected void initControl() {

    }

    @Override
    protected void initListener() {

    }


    @Override
    public void onClick(View v) {
//        JumpItent.jump(this, HomeActivity.class);;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 2) {
            userText.setVisibility(View.VISIBLE);
        } else {
            userText.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ExitApp();      //调用双击退出函数
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
            finish();
        }
    }
}
