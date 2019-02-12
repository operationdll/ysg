package com.ysg.yashige.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by apple on 2017/9/12.
 */

public class CheckVersionUtil {
    /**
     * @param context
     * @param versionServer
     * @return if version1 > version2, return 1, if equal, return 0, else return
     * -1
     */
    public static boolean isUpVersion(Context context, String versionServer) {
        PackageManager pm = context.getApplicationContext().getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getApplicationContext().getPackageName(), 0);
            String versionLocal = pi.versionName;
            String version1 = versionServer;
            String version2 = versionLocal;
            if (version1 == null || version1.length() == 0 || version2 == null || version2.length() == 0)
                throw new IllegalArgumentException("Invalid parameter!");

            int index1 = 0;
            int index2 = 0;
            while (index1 < version1.length() && index2 < version2.length()) {
                int[] number1 = getValue(version1, index1);
                int[] number2 = getValue(version2, index2);

                if (number1[0] < number2[0]) {
                    return false;
                } else if (number1[0] > number2[0]) {
                    return true;
                } else {
                    index1 = number1[1] + 1;
                    index2 = number2[1] + 1;
                }
            }
            if (index1 == version1.length() && index2 == version2.length())
                return false;
            if (index1 < version1.length()) {
                return true;

            } else {
                return false;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param version
     * @param index   the starting point
     * @return the number between two dots, and the index of the dot
     */
    public static int[] getValue(String version, int index) {
        int[] value_index = new int[2];
        StringBuilder sb = new StringBuilder();
        while (index < version.length() && version.charAt(index) != '.') {
            sb.append(version.charAt(index));
            index++;
        }
        value_index[0] = Integer.parseInt(sb.toString());
        value_index[1] = index;

        return value_index;
    }
}
