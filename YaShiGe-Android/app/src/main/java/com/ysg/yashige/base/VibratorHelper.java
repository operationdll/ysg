package com.ysg.yashige.base;

import android.app.Activity;
import android.app.Service;
import android.os.Vibrator;

/**
 * Created by apple on 2017/6/25.
 */
public class VibratorHelper {
    public static void Vibrate(final Activity activity, long milliseconds) {
        Vibrator vibrator = (Vibrator) activity
                .getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.vibrate(milliseconds);
    }

    public static void Vibrate(final Activity activity, long[] pattern,
                               boolean isRepeat) {
        Vibrator vibrator = (Vibrator) activity
                .getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.vibrate(pattern, isRepeat ? 1 : -1);
    }
}