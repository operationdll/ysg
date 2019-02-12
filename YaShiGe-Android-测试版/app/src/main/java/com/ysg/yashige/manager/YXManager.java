package com.ysg.yashige.manager;

/**
 * Created by apple on 2017/6/28.
 */

public class YXManager {

    private static YXManager instance = null;
    public static YXManager getInstance() {
        if (instance == null) {
            instance = new YXManager();
        }
        return instance;
    }
}

