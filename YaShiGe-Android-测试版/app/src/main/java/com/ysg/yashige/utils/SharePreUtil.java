package com.ysg.yashige.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ysg.yashige.base.C;
import com.ysg.yashige.base.MyApplication;

public class SharePreUtil {
    private SharePreUtil() {
        init(MyApplication.getContext());
    }

    private static SharedPreferences mPres;
    private static SharePreUtil mInstance;

    public void init(Context context) {
        mPres = context.getSharedPreferences(C.SHARE_NAME,
                Context.MODE_PRIVATE);
    }

    public void clear() {
        SharedPreferences.Editor edit = mPres.edit();
        edit.clear();
        edit.apply();
    };

    public static SharePreUtil getInstance() {
        if (mInstance == null) {
            mInstance = new SharePreUtil();
        }
        return mInstance;
    }

    public String getString(String key) {
        return mPres.getString(key, "");
    }

    public int getInt(String key) {
        return mPres.getInt(key, 0);
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor edit = mPres.edit();
        edit.putLong(key, value);
        edit.apply();
    }

    public long getLong(String key) {
        return mPres.getLong(key, -1);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor edit = mPres.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor edit = mPres.edit();
        edit.putInt(key, value);
        edit.apply();
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor edit = mPres.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    public boolean getBoolean(String key, boolean value) {
        if (mPres.contains(key)){
            return mPres.getBoolean(key, value);
        }else {
            return false;
        }

    }

    public boolean containFirst(String key) {
        return mPres.contains(key);
    }

    public boolean getBoolean(String key) {
        return mPres.getBoolean(key, false);
    }
}

