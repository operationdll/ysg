package com.ysg.yashige.networks.commons;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import static com.ysg.yashige.networks.commons.NetConfig.kNetWork_MethodType_GET;
import static com.ysg.yashige.networks.commons.NetConfig.kNetWork_MethodType_POST;

/**
 * Created by weitong on 16/7/4.
 */
public class BaseRequest {
    private int methodType;
    private String methodName;
    private TreeMap<String, Object> paramMap;

    public BaseRequest() {
        init(kNetWork_MethodType_POST);
    }

    public BaseRequest(int method_type) {
        init(method_type);
    }

    private void init(int method_type) {
        methodName = "";
        this.methodType = method_type == kNetWork_MethodType_POST || method_type == kNetWork_MethodType_GET ? method_type : kNetWork_MethodType_POST;
        this.paramMap = new TreeMap<>(new Comparator<String>() {
            public int compare(String obj1, String obj2) {
                // 升序排序
                return obj1.compareTo(obj2);
            }
        });
    }

    public void setValue(String key, Object val) {
        if (key == null || val == null || TextUtils.isEmpty(key)) {
            return;
        }
        this.paramMap.put(key, val);
    }

    public void setValues(String params) {
        if (TextUtils.isEmpty(params)) {
            return;
        }
        paramMap.clear();
        try {
            paramMap.putAll((Map<? extends String, ?>) JSON.parse(params));
        } catch (Exception e) {
            paramMap.clear();
            e.printStackTrace();
        }
    }

    public int getMethodType() {
        return methodType;
    }

    public void setMethodType(int methodType) {
        this.methodType = methodType;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public TreeMap<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(TreeMap<String, Object> paramMap) {
        this.paramMap.clear();
        this.paramMap.putAll(paramMap);
    }
}
