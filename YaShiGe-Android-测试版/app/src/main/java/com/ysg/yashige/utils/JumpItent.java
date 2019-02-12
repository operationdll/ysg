package com.ysg.yashige.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.io.Serializable;

/**
 * 封装的Itent页面跳转工具类
 * Created by Flavin on 2016/4/5 0004.
 */
public class JumpItent {
    public final static String REQUEST_CODE = "REQUEST_CODE";

    public static void jump(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
    }

    public static void jump(Activity activity, Class<?> cls, String key, String string) {
        Intent intent = new Intent(activity, cls);
        intent.putExtra(key,string);
        activity.startActivity(intent);
    }

    public static void jump(Activity activity, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(activity, cls);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }
    public static void jump(Activity activity, Class<?> cls, String key, Serializable value) {
        Intent intent = new Intent(activity, cls);
        Bundle bundle = new Bundle();
        bundle.putSerializable(key,value);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }
    public static void jump(Activity activity, Class<?> cls, boolean isFinish) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
        if (isFinish) {
            activity.finish();
        }
    }

    public static void jump(Activity activity, Class<?> cls, int requestCode) {
        Intent intent = new Intent(activity, cls);
        intent.putExtra(REQUEST_CODE, requestCode);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void jump(Activity activity, Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(activity, cls);
        if (bundle != null) {
            bundle.putInt(REQUEST_CODE, requestCode);
            intent.putExtras(bundle);
        }
        activity.startActivityForResult(intent, requestCode);
    }

    public static void jump(Activity activity, Class<?> cls, boolean isFinish, Bundle bundle) {
        Intent intent = new Intent(activity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
        if (isFinish) {
            activity.finish();
        }
    }

    public static void jump(Activity activity, Class<?> cls, boolean isFinish, int requestCode) {
        Intent intent = new Intent(activity, cls);
        intent.putExtra(REQUEST_CODE, requestCode);
        activity.startActivityForResult(intent, requestCode);
        if (isFinish) {
            activity.finish();
        }
    }

    public static void jump(Activity activity, Class<?> cls, boolean isFinish, Bundle bundle, int requestCode) {
        Intent intent = new Intent(activity, cls);
        if (bundle != null) {
            bundle.putInt(REQUEST_CODE, requestCode);
            intent.putExtras(bundle);
        }
        activity.startActivityForResult(intent, requestCode);
        if (isFinish) {
            activity.finish();
        }
    }

    public static void jump(Activity activity, String action) {
        Intent intent = new Intent(action);
        activity.startActivity(intent);
    }

    public static void jump(Activity activity, String action, Bundle bundle) {
        Intent intent = new Intent(action);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public static void jump(Activity activity, String action, int requestCode) {
        Intent intent = new Intent(action);
        intent.putExtra(REQUEST_CODE, requestCode);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void jump(Activity activity, String action, Bundle bundle, int requestCode) {
        Intent intent = new Intent(action);
        bundle.putInt(REQUEST_CODE, requestCode);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void jump(Activity activity, String action, Uri uri) {
        Intent intent = new Intent(action, uri);
        activity.startActivity(intent);
    }

    public static void jump(Activity activity, String action, Uri uri, int requestCode) {
        Intent intent = new Intent(action, uri);
        intent.putExtra(REQUEST_CODE, requestCode);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void backActivity(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }
    public static void backActivity(Activity activity, Class<?> cls,Bundle bundle) {
        Intent intent = new Intent(activity, cls);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        for (int i = 0; i < MboxApplication.instance.activitys.size() ; i++) {
//            Activity activity1 = MboxApplication.instance.activitys.get(i);
//            boolean isCls = activity1 instanceof ChooseCourseActivity || activity1 instanceof MBHomeBaseActivity;
//            if (isCls){
//                return;
//            }
//            MboxApplication.instance.activitys.remove(activity);
//        }
        activity.startActivity(intent);
    }
}
