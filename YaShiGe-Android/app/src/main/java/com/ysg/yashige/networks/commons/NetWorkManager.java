package com.ysg.yashige.networks.commons;

import android.app.Activity;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.ysg.yashige.base.C;
import com.ysg.yashige.networks.interfaces.OnErrorResponse;
import com.ysg.yashige.networks.interfaces.OnSuccessResponse;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by weitong on 16/7/4.
 */
public class NetWorkManager {
    private static OkHttpClient mOkHttpClient = null;

    public static void init() {
        mOkHttpClient = new OkHttpClient();
    }

    private static void success(final Activity activity, final OnSuccessResponse successResponse, final String result) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (successResponse != null)
                    successResponse.onSuccess(result);
            }
        });
    }

    private static void error(final Activity activity, final OnErrorResponse errorResponse, final String error) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (errorResponse != null)
                    errorResponse.onError(error);
            }
        });
    }

    public static void requestTime() {
        final long now = System.currentTimeMillis();
        if (mOkHttpClient == null) {
            init();
        }
        final Request request = new Request.Builder().url(C.network.time_url).get().build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException error) {
                Log.d("onFailure -> ", "请求时间戳errorMsg: " + error.getMessage());
                C.app.msg = "请求时间戳失败";
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String result = response.body().string();
                try {
                    Log.d("onResponse ->", "时间戳:" + result);
                    JSONObject jsonObject = JSONObject.parseObject(result);
                    C.app.time_offset = now - jsonObject.getJSONObject("data").getLong("time");
                } catch (Exception ex) {
                    Log.d("onError ->", "时间戳解析错误");
                    ex.printStackTrace();
                    C.app.msg = "时间戳解析错误";
                }
            }
        });
    }

    public static void requestUrl(String url, OnSucceed succeed) {
        final long now = System.currentTimeMillis();
        if (mOkHttpClient == null) {
            init();
        }
        final Request request = new Request.Builder().url(url).get().build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException error) {
                Log.d("onFailure -> ", "请求xml错误: " + error.getMessage());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String result = response.body().string();
                if (succeed != null) {
                    succeed.succeed(result);
                }
            }
        });
    }

    public interface OnSucceed {
        void succeed(String data);
    }
}
