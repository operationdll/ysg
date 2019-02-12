package com.ysg.yashige.utils.upData;

/**
 * Created by apple on 2016/12/28.
 */

/** 自定义一个接口，在需要下载文件类中实现此接口 */
public interface onDownloadListener {
    void getDownLoadNotice(String url);
}