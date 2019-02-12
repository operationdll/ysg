package com.ysg.yashige.utils;

import android.text.TextUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 作者：gsm
 * 描述：判断值是否为空的工具类
 * 时间：2016/4/20.
 */
public class EmptyUtils {

    public static boolean isEmpty(String str) {
        return TextUtils.isEmpty(str) || str.trim().length() == 0;
    }

    public static boolean isEmpty(String[] strs) {
        return (strs == null || strs.length <= 0);
    }

    public static boolean isEmpty(int[] ints) {
        return (ints == null || ints.length <= 0);
    }

    public static boolean isEmpty(boolean[] booleans) {
        return (booleans == null || booleans.length <= 0);
    }

    public static boolean isEmpty(float[] floats) {
        return (floats == null || floats.length <= 0);
    }

    public static boolean isEmpty(double[] doubles) {
        return (doubles == null || doubles.length <= 0);
    }

    public static boolean isEmpty(Map map) {
        return (map == null || map.size() <= 0);
    }

    public static boolean isEmpty(Set set) {
        return (set == null || set.size() <= 0);
    }

    public static boolean isEmpty(List list) {
        return (list == null || list.size() <= 0);
    }


}
