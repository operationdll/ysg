package com.ysg.yashige.base;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.security.MessageDigest;
import java.util.TreeMap;

/**
 * Created by thomas on 2017/1/17.
 */

public class C {
    public static final class msg {
    }

    public static String SHARE_NAME = "sp";
    public static String Key_Notification = "share_notification";
    public static final class app {
        public static long time_offset = -1;
        public static String msg = null;
        public static String sign = null;
    }

    public static final class network {
//        public static final String home_url = "http://118.190.164.26:8080";
         public static final String home_url = "http://webview.easyiservice.com:8080/";
//         public static final String home_url ="http://47.52.220.96:80";
        public static final String do_main = "https://service.easyiservice.com/";
        public static final String time_url = do_main + "system/getTime";
        public static final String secret_key = "CtUyV$8MGoK8u5L*P0Q50T/b8S9iclS*LQqo";
    }

    public static final class key {
        public static final String msg = "msg";
        public static final String position = "position";
        public static final String id = "id";
        public static final String item = "item";
        public static final String user = "user";
        public static final String source = "source";
    }

    public static final class code {

        public static final class request {
        }

        public static final class result {
        }
    }

    public static final class handler {
        public static final int code = 3;
    }

    public static final class other {
    }

    public static final class fun {
        public static String encrypt(TreeMap<String, Object> paramMap) {
            StringBuffer sign_buffer = new StringBuffer();
            for (String key : paramMap.keySet()) {
                String val = paramMap.get(key) == null ? "" : paramMap.get(key).toString();
                sign_buffer.append(key).append(val);
            }
            sign_buffer.append(C.network.secret_key);
            String sign = C.fun.getMD5(sign_buffer.toString());
            Log.e("#####", "sign = " + sign + "sign_buffer = " + sign_buffer.toString());
            return sign;
        }
        /**
         * 关闭键盘
         *
         * @param context     上下文
         * @param windowToken view.getWindowToken()
         */
        public static void closeKeyBoard(Context context, IBinder windowToken) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            boolean isOpen = inputMethodManager.isActive();//isOpen若返回true，则表示输入法打开
            if (isOpen) {
                inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
            }
        }

        /**
         * 开始一个按钮的倒计时
         *
         * @param button                    控件(TextView类型)
         * @param millisInFuture            总时长
         * @param countDownInterval         间隔
         * @param normalBackGroundResources 正常的控件背景资源id
         * @param normalTextColorResources  正常字体颜色资源id
         * @param unableBackGroundResources 不能点击时背景资源id
         * @param unableTextColorResources  不能点击是字体颜色资源id
         * @param unablePostfix             倒计时后缀
         */
        public static void startCountDown(final TextView button, long millisInFuture, long countDownInterval,
                                          final int normalBackGroundResources, final int normalTextColorResources,
                                          final int unableBackGroundResources, final int unableTextColorResources,
                                          final String unablePostfix, final String overText) {
            final String normalText = button.getText().toString();
            final int normalTextColor = ContextCompat.getColor(button.getContext(), normalTextColorResources);
            final int unableTextColor = ContextCompat.getColor(button.getContext(), unableTextColorResources);
            button.setClickable(false);
            button.setBackgroundResource(unableBackGroundResources);
            button.setTextColor(unableTextColor);
            new CountDownTimer(millisInFuture, countDownInterval) {
                @Override
                public void onTick(long millisUntilFinished) {
                    button.setText((millisUntilFinished / 1000) + unablePostfix);
                }

                @Override
                public void onFinish() {
                    button.setClickable(true);
                    button.setBackgroundResource(normalBackGroundResources);
                    button.setTextColor(normalTextColor);
                    button.setText(overText == null ? normalText : overText);
                }
            }.start();
        }

        /**
         * 开始一个按钮的倒计时
         *
         * @param button                    控件(TextView类型)
         * @param normalBackGroundResources 正常的控件背景资源id
         * @param normalTextColorResources  正常字体颜色资源id
         * @param unableBackGroundResources 不能点击时背景资源id
         * @param unableTextColorResources  不能点击是字体颜色资源id
         */
        public static void startCountDown(final TextView button,
                                          final int normalBackGroundResources, final int normalTextColorResources,
                                          final int unableBackGroundResources, final int unableTextColorResources) {
            startCountDown(button, 60000, 1000,
                    normalBackGroundResources, normalTextColorResources,
                    unableBackGroundResources, unableTextColorResources, "s后重发", "重新获取");
        }

        /**
         * 生成字符串对应MD5
         *
         * @param string 待生成MD5字符串
         * @return MD5串
         */
        public static String getMD5(String string) {
            char[] b = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            try {
                MessageDigest var2 = MessageDigest.getInstance("MD5");
                var2.update(string.getBytes("UTF-8"));
                byte[] var3 = var2.digest();
                int var4 = var3.length;
                char[] var5 = new char[var4 * 2];
                int var6 = 0;

                for (int var7 = 0; var7 < var4; ++var7) {
                    var5[var6++] = b[var3[var7] >>> 4 & 15];
                    var5[var6++] = b[var3[var7] & 15];
                }

                return new String(var5);
            } catch (Exception var8) {
                return null;
            }
        }
    }
}
