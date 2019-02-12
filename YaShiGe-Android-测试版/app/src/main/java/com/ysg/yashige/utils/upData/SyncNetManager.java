package com.ysg.yashige.utils.upData;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.ysg.yashige.base.MyApplication;


/**
 * Created by apple on 2016/12/28.
 */
public class SyncNetManager implements onDownloadListener {

    public Context context;
    private static SyncNetManager instance;
    private Integer gengXinSize;
    private boolean flag = false;
    private onSynFile fileRsp;

    @Override
    public void getDownLoadNotice(String url) {

    }


    public interface onSyncData{
        void onSyncDataResponse(boolean code);
    }

    public interface onSynFile{
        void onSyncDownLoadFile(String saveName);
    }

    public static SyncNetManager getInstance() {
        if (instance == null) {
            instance = new SyncNetManager();
            instance.context = MyApplication.getInstance().getBaseContext();
        }
        return instance;
    }

    public void onStart(final String pwd , final onSyncData rsp, onSynFile fileRsp){
        boolean netWorkState = checkNetworkState(context);
        if (!netWorkState){
            rsp.onSyncDataResponse(false);
            return;
        }
        this.fileRsp = fileRsp;
        Log.e("开始同步数据","onStart");
    }


    /**
     * 检测是否有网络连接
     * @param context
     */
    public static boolean checkNetworkState(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
            //1.判断是否有网络连接
            if (networkInfo == null) {
                return false;

            }
            return networkInfo.isConnected();
        } else {
            return false;
        }
    }
}
