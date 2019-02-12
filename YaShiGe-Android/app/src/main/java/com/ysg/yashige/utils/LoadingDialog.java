package com.ysg.yashige.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ysg.yashige.R;

/**
 * 作者：gsm
 * 描述：加载中弹窗
 * 时间：2016/7/27.
 */
public class LoadingDialog extends ProgressDialog {
    private TextView tvHint;
    private ImageView ivLoading;
    private View rootView;
    private Animation mAnimation;
    private LinearLayout dialogLoadingLL;

    private static LoadingDialog loadingDialog;
    private static Context mContext;

    public static LoadingDialog getInstance(Context context){
        if(loadingDialog == null || mContext != context){
            if(loadingDialog != null)
                loadingDialog.dismiss();
            loadingDialog = new LoadingDialog(context);
            mContext = context;
        }
        return loadingDialog;
    }

    public LoadingDialog(Context context) {
        this(context, 0);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, R.style.center_dialog_style);
        setCanceledOnTouchOutside(false);
        rootView = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        tvHint = (TextView) rootView.findViewById(R.id.tv_loading_dialog_hint);
        ivLoading = (ImageView) rootView.findViewById(R.id.iv_loading_dialog);
        mAnimation = AnimationUtils.loadAnimation(context, R.anim.loading_animation);
        dialogLoadingLL = (LinearLayout) rootView.findViewById(R.id.ll_loading_dialog);
    }

    /**
     * 设置加载中的显示文字
     *
     * @param hintInfo
     */
    public void setHint(String hintInfo) {
        ValueUtils.setValue(hintInfo, R.string.is_loading, tvHint);
    }

    public void showDialog() {
        if (!isShowing()) {
            show();
            ivLoading.startAnimation(mAnimation);
            setContentView(rootView);
        }
    }

    public void hideDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            ivLoading.clearAnimation();
            dismiss();
        }
    }

}
