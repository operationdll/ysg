package com.ysg.yashige.utils;

import android.text.SpannableString;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by admin on 2016/4/20.
 */
public class ValueUtils {

    /**
     * @param value        需要设置的值
     * @param defaultValue 当值为空时默认显示的值
     * @param tView
     */
    public static void setValue(String value, String defaultValue, TextView tView) {
        if (EmptyUtils.isEmpty(defaultValue)) {
            defaultValue = " ";
        }
        if (EmptyUtils.isEmpty(value)) {
            value = defaultValue;
        }
        tView.setText(value);
    }

    /**
     * @param value     需要设置的值
     * @param defaultId 当值为空时默认显示的值
     * @param tView
     */
    public static void setValue(String value, int defaultId, TextView tView) {
        if (EmptyUtils.isEmpty(value))
            tView.setText(defaultId);
        tView.setText(value);
    }


    /**
     * @param value        需要设置的值
     * @param defaultValue 当值为空时默认显示的值
     * @param eText
     */
    public static void setValue(String value, String defaultValue, EditText eText) {
        if (EmptyUtils.isEmpty(defaultValue))
            defaultValue = " ";
        if (EmptyUtils.isEmpty(value))
            value = defaultValue;
        eText.setText(value);
        eText.setSelection(value.length());
    }

    /**
     * 获得控件值
     *
     * @param editText
     *
     * @return
     */
    public static String getValue(EditText editText) {
        if (editText != null && editText.getText() != null && !EmptyUtils.isEmpty(editText.getText().toString().trim())) {
            return editText.getText().toString().trim();
        } else {
            return "";
        }
    }

    /**
     * 获得控件值
     *
     * @param textView
     *
     * @return
     */
    public static String getValue(TextView textView) {
        if (textView != null && textView.getText() != null && !EmptyUtils.isEmpty(textView.getText().toString().trim())) {
            return textView.getText().toString().trim();
        } else {
            return "";
        }
    }

    /**
     * 获得设置关键字颜色后的Span
     *
     * @param color   颜色
     * @param content 内容
     * @param keyword 关键字
     *
     * @return
     */
    public static SpannableString getSpannableKeyword(int color, String content, String keyword) {
        SpannableString span = new SpannableString(content);
        if (content.contains(keyword)) {
//            int index = content.indexOf(keyword);
//            span.setSpan(new ForegroundColorSpan(color), index, keyword.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return span;
    }

    /**
     * 处理时间 处理市以年月日 还是 小时前 还是分钟前显示
     *
     * @param value 时间值
     *
     * @return
     */
    public static String processTime(String value) {
        long releaseTime = Long.parseLong(value);
        long currentTime = System.currentTimeMillis();
        long lessTime = (currentTime - releaseTime) / 1000;
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int lessHour = (int) (lessTime / (60 * 60));
        if (lessHour > currentHour) {//今天以前
            return new SimpleDateFormat("yyyy-MM-dd").format(currentTime / 1000);
        } else if (lessHour > 0) {//今天 一小时包括一小时以前
            return lessHour + "小时前";
        } else {//分钟
            long lessM = lessTime / 60;
            if (lessM < 1) {
                return "刚刚";
            } else {
                return lessM + "分钟前";
            }
        }
    }

    /**
     * 处理时间 处理市以年月日 还是 小时前 还是分钟前显示
     *
     * @param value 时间值
     *
     * @return
     */
    public static String processTime(long value) {
        long releaseTime = value;
        long currentTime = System.currentTimeMillis();
        int less;
        if ((value + "").length() < 11) {
            less = 1;
        } else {
            less = 1000;
        }
        long lessTime = (currentTime - releaseTime) / less;//当前时间与发布时间差值
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);//当前小时
        int lessHour = (int) (lessTime / (60 * 60));//时间小时差
        if (lessTime > 0) {//当前时间以前
            if (lessHour >= currentHour) {//今天以前
                return new SimpleDateFormat("yyyy-MM-dd").format(releaseTime);
            } else if (currentHour - lessHour > 0 && lessHour > 0) {//今天内且一小时前
                return lessHour + "小时前";
            } else {//分钟
                long lessM = lessTime / 60;//时间分钟差
                long minute = lessM - lessHour * 60;//一小时内分分钟差
                if (minute < 1) {
                    return "刚刚";
                } else {
                    return minute + "分钟前";
                }
            }
        } else {//当前时间之后
            return new SimpleDateFormat("yyyy-MM-dd").format(releaseTime);
        }
    }

    /**
     * 返回格式化时间
     *
     * @param value 毫秒值
     *
     * @return
     */
    public static String getFormantTime(String value) {
        long time = Long.parseLong(value);
        return new SimpleDateFormat("yyyy-MM-dd").format(time);
    }

}
