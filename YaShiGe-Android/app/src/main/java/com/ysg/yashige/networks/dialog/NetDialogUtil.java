package com.ysg.yashige.networks.dialog;

import android.app.Activity;

import com.ysg.yashige.networks.interfaces.NetDialog;


/**
 * Created by weitong on 16/7/29.
 */
public class NetDialogUtil implements NetDialog {

    private static NetDialogUtil instance;
    private NetDialogFragment dialogFragment;
    public boolean isRunning;

    public static NetDialogUtil getInstance() {
        if (instance == null){
            instance = new NetDialogUtil();
        }
        return instance;
    }

    private NetDialogUtil() {
        isRunning = false;
    }

    @Override
    public void show(Activity activity) {
        if (!isRunning){
            isRunning = true;
            android.app.FragmentManager fragmentManager = activity.getFragmentManager();
            dialogFragment = new NetDialogFragment();
            dialogFragment.show(fragmentManager, "NetDialogFragment");
        }
    }

    @Override
    public void hide() {
        if (isRunning){
            isRunning = false;
            dialogFragment.dismiss();
        }
    }
}
