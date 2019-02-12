package com.ysg.yashige.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

/**
 * 解决webView键盘遮挡问题的类
 * Created by zqy on 2016/11/14.
 */
public class KeyBoardListener {
    private Activity activity;
    private View mChildOfContent;
    private int usableHeightPrevious;
    private FrameLayout.LayoutParams frameLayoutParams;

    private static KeyBoardListener keyBoardListener;


    public static KeyBoardListener getInstance(Activity activity) {

        keyBoardListener=new KeyBoardListener(activity);
        return keyBoardListener;
    }

    public KeyBoardListener(Activity activity) {
        super();
        this.activity = activity;
    }

    public void init() {
        FrameLayout content = (FrameLayout) activity
                .findViewById(android.R.id.content);
        mChildOfContent = content.getChildAt(0);
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        possiblyResizeChildOfContent();
                    }
                });
        frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent
                .getLayoutParams();
    }
    private int getNavigationBarHeight() {
        Resources resources = this.activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }
    public boolean isOffset() {
        return getDecorViewHeight() > getScreenHeight();
    }


    public int getDecorViewHeight(){
        return (this.activity).getWindow().getDecorView().getHeight();
    }

    public int getScreenHeight(){
        DisplayMetrics dm = new DisplayMetrics();
        (this.activity).getWindowManager().getDefaultDisplay().getMetrics(dm);//当前activity
        return dm.heightPixels;
    }

    private void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = mChildOfContent.getRootView()
                    .getHeight();
            if(isOffset()) {
                usableHeightSansKeyboard = usableHeightSansKeyboard - getNavigationBarHeight();
            }
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard / 4)) {
// keyboard probably just became visible
                frameLayoutParams.height = usableHeightSansKeyboard
                        - heightDifference;
            } else {
// keyboard probably just became hidden
                frameLayoutParams.height = usableHeightSansKeyboard;
            }
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }


    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);
    }

}
