package com.ysg.yashige.base;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.common.inter.ITagManager;
import com.umeng.message.entity.UMessage;
import com.umeng.message.tag.TagManager;
import com.ysg.yashige.R;
import com.ysg.yashige.ui.NotificationWebViewActivity;
import com.ysg.yashige.utils.SharePreUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by thomas on 2017/1/17.
 */

public class MyApplication extends Application {
    private static Context context;
    private static MyApplication mApplication;
    private static BaseActivity baseActivity;
    private static MyApplication instance;
    public LinkedList<Activity> activitys = new LinkedList<>();
    private Handler handler;
    private PushAgent mPushAgent;
    public static int isHome = 1;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        mApplication = this;
        instance = this;

        mPushAgent = PushAgent.getInstance(this);
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.e("onSuccess", deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.e("onFailure", s + s1);
            }
        });
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                try {
                    JSONObject contentJson = new JSONObject(msg.custom);
                    Bundle bundle = new Bundle();
                    bundle.putString("url", contentJson.getString("value"));
                    bundle.putString("title", msg.title);
                    Intent intent = new Intent(context, NotificationWebViewActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
//                String data = msg.extra.get("data");
//                Intent intent=new Intent(context,MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("data",data);
//                context.startActivity(intent);
//                for (int i = 0; i <MyApplication.getInstance().activitys.size() ; i++) {
//                    Activity activity = MyApplication.getInstance().activitys.get(i);
//                    boolean isCls = activity instanceof MainActivity;
//                    if (isCls){
//                        MainActivity ac = (MainActivity) activity;
//                        ac.openVuePage();
//                        ac.flag = true;
//                        return;
//                    }
//                }
            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);
        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            /**
             * 自定义消息的回调方法
             * */
            @Override
            public void dealWithCustomMessage(final Context context, final UMessage msg) {

                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        // 对自定义消息的处理方式，点击或者忽略
                        boolean isClickOrDismissed = true;
                        if (isClickOrDismissed) {
                            //自定义消息的点击统计
                            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
                        } else {
                            //自定义消息的忽略统计
                            UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
                        }
                        Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
                    }
                });
            }

            /**
             * 自定义通知栏样式的回调方法
             * */
            @Override
            public Notification getNotification(Context context, UMessage msg) {
                Boolean result = SharePreUtil.getInstance().containFirst(C.Key_Notification);
                String strNotification = "[]";
                if (result) {
                    strNotification = SharePreUtil.getInstance().getString(C.Key_Notification);
                }
                List<String> list = JSON.parseArray(strNotification, String.class);
                list.add(msg.getRaw().toString());
                Log.e(C.Key_Notification, JSON.toJSONString(list));
                SharePreUtil.getInstance().putString(C.Key_Notification, JSON.toJSONString(list));

                switch (msg.builder_id) {
                    case 1:
                        Notification.Builder builder = new Notification.Builder(context);
                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(), R.layout.notification_view);
                        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
                        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
                        myNotificationView.setImageViewBitmap(R.id.notification_large_icon, getLargeIcon(context, msg));
                        myNotificationView.setImageViewResource(R.id.notification_small_icon, getSmallIconId(context, msg));
                        builder.setContent(myNotificationView)
                                .setSmallIcon(getSmallIconId(context, msg))
                                .setTicker(msg.ticker)
                                .setAutoCancel(true);

                        return builder.getNotification();
                    default:
                        //默认为0，若填写的builder_id并不存在，也使用默认。
                        return super.getNotification(context, msg);
                }
            }
        };
        mPushAgent.setMessageHandler(messageHandler);

    }

    public void setAliasTag(String ALIAS_TYPE, String alias, String[] tags) {

        mPushAgent.addAlias(alias, ALIAS_TYPE, new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean isSuccess, String message) {
                Log.e("", message);
            }
        });

        mPushAgent.getTagManager().add(new TagManager.TCallBack() {
            @Override
            public void onMessage(final boolean isSuccess, final ITagManager.Result result) {
                if (isSuccess) {
                    Log.e("", result.toString());
                }
            }
        }, tags);
    }

    public void removeMessageTag(String ALIAS_TYPE, String alias, String[] tags) {

        mPushAgent.removeAlias(alias, ALIAS_TYPE, new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean isSuccess, String message) {
                Log.e("", message);
            }
        });

        mPushAgent.getTagManager().delete(new TagManager.TCallBack() {
            @Override
            public void onMessage(final boolean isSuccess, final ITagManager.Result result) {
                if (isSuccess) {
                    Log.e("", result.toString());
                }
            }
        }, tags);
    }


    //拿到应用的上下文Context
    public static Context getContext() {
        return context;
    }

    /**
     * 添加activity到LinkedList集合
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activitys.add(0, activity);
    }

    public static MyApplication getInstance() {
        return instance;
    }

    /**
     * 获取 Application 对象
     */
    public static Application getApplication() {
        return mApplication;
    }

    public static BaseActivity getBaseActivity() {
        return baseActivity;
    }

    public static void setBaseActivity(BaseActivity base_activity) {
        baseActivity = base_activity;
    }
}
