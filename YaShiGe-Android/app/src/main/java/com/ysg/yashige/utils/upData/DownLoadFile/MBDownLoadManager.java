package com.ysg.yashige.utils.upData.DownLoadFile;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Settings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * downLoadManager
 * Created by apple on 2016/12/26.
 */
public class MBDownLoadManager {

    DownloadManager downloadManager;
    private DownLoadCompleteReceiver    receiver;
    private MBDownLoadRequest           request;
    private long                        id;
    private List data;
    private Context mContext;
    private onDownloadListener          listener;

    public static interface onDownloadListener {
        void getDownLoadComplete(Context context, long id);
    }

    public MBDownLoadManager(Context context, String url, String title, String msg, String saveName, onDownloadListener listener){
        this.listener = listener;
        this.mContext = context;
        downloadManager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        request = new MBDownLoadRequest(context,url,title,msg,saveName);
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        filter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        receiver = new DownLoadCompleteReceiver();
        context.registerReceiver(receiver, filter);
    }

    private class DownLoadCompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)){
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                listener.getDownLoadComplete(context,id);
            }else if(intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)){
//                Toast.makeText(context, "别瞎点！！！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void startDownLoad(){

        final String packageName = mContext.getPackageName();
        int state = mContext.getPackageManager().getApplicationEnabledSetting(packageName);
        //检测下载管理器是否被禁用
        if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {
            try {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + packageName));
                mContext.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                mContext.startActivity(intent);
            }
        }else {
            id = downloadManager.enqueue(request);
        }
    }

    public void cancleDownLoad(){
        downloadManager.remove(id);
    }

    private void queryDownTask(DownloadManager downManager, int status) {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterByStatus(status);
        Cursor cursor= downManager.query(query);

        while(cursor.moveToNext()){
            String downId= cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE));
            String address = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
            String size= cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
            String sizeTotal = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
            Map<String, String> map = new HashMap<String, String>();
            map.put("downid", downId);
            map.put("title", title);
            map.put("address", address);
            map.put("status", sizeTotal+":"+size);
            this.data.add(map);
        }
        cursor.close();
    }

}
