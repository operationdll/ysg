package com.ysg.yashige.networks.RFJava;

import com.ysg.yashige.networks.RFJava.bean.GuidBean;
import com.ysg.yashige.networks.RFJava.bean.ServiceTime;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by apple on 2017/6/28.
 */

public interface YXService {

    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_CONTROL_NETWORK)
    @GET("system/getTime")
    Observable<ServiceTime> systemGetTime(@QueryMap Map<String, String> params);

    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_CONTROL_NETWORK)
    @GET("appStartPic/getEffectiveAppStartPic")
    Observable<GuidBean> appStartPicGetEffectiveAppStartPic(@QueryMap Map<String, String> params);

}
