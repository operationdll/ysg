package com.ysg.yashige.networks.dialog;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ysg.yashige.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NetDialogFragment extends DialogFragment {
    @BindView(R.id.loading_tv)
    TextView loadingTv;

    private int dot_num = 0;
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {
                    if (loadingTv != null) {
                        switch (dot_num) {
                            case 0:
                                loadingTv.setText("正在加载");
                                dot_num = 1;
                                break;
                            case 1:
                                loadingTv.setText("正在加载.");
                                dot_num = 2;
                                break;
                            case 2:
                                loadingTv.setText("正在加载..");
                                dot_num = 3;
                                break;
                            case 3:
                            default:
                                loadingTv.setText("正在加载...");
                                dot_num = 0;
                                break;
                        }
                    }
                }
            });
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getDialog() == null) {
            return null;
        }
        timer.schedule(task, 0, 350);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.fragment_wt_dialog, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels, dm.heightPixels);
    }

    @Override
    public void onDestroyView() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (task != null) {
            task.cancel();
            task = null;
        }
        NetDialogUtil.getInstance().isRunning = false;
        super.onDestroyView();
    }
}
