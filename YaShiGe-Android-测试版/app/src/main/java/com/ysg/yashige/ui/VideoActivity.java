package com.ysg.yashige.ui;

import android.os.Bundle;

import com.ysg.yashige.R;
import com.ysg.yashige.base.BaseActivity;

import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class VideoActivity extends BaseActivity {

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        //网络视频
        String videoUrl = getIntent().getStringExtra("videoUrl") ;

        JCVideoPlayerStandard jcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.videoplayer);
        jcVideoPlayerStandard.setUp(videoUrl
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
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
    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

}
