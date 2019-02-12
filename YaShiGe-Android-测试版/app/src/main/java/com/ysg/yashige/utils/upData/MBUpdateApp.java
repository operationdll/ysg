package com.ysg.yashige.utils.upData;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;

import com.ysg.yashige.R;
import com.ysg.yashige.utils.upData.DownLoadFile.MBDownLoadManager;

import java.io.File;

/**
 * upLoadApp
 * Created by apple on 2016/12/26.
 */
public class MBUpdateApp {

    private SyncNetManager.onSynFile fileRsp;

    public MBUpdateApp(Context context, String url, SyncNetManager.onSynFile fileRsp){
        this.fileRsp = fileRsp;
        checkVersion(url,context);
    }

    private void checkVersion(final String url ,final Context context){
        downLoadApk(context,url, context.getResources().getString(R.string.app_name), "下载中", System.currentTimeMillis() + "YSG.pdf");
//        downLoadApk(context,url, context.getResources().getString(R.string.app_name), "下载中", System.currentTimeMillis()+ "YSG.pdf");
    }

    private String getVersionName(Context context){
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = packInfo.versionName;
        return version;
    }

    public static void installApk(Context context, String saveName){
        Uri apkUri = null;
        File file= new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
                saveName);
        //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (!file.getParentFile().exists())file.getParentFile().mkdirs();
        if (Build.VERSION.SDK_INT >= 24){
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
            apkUri =
                    FileProvider.getUriForFile(context, "me.ysg.fileprovider", file);
        }else {
            if (!file.exists()) {
                return;
            }
            apkUri = Uri.parse("file://" + file.toString());
        }
        // 通过Intent安装APK文件
        i.setDataAndType(apkUri,"application/vnd.android.package-archive");
        context.startActivity(i);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private void downLoadApk(Context context, String url, String title, String msg, final String saveName){
        new MBDownLoadManager(context, url, title, msg, saveName, new MBDownLoadManager.onDownloadListener() {
            @Override
            public void getDownLoadComplete(Context context, long id) {
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("saveName",saveName);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        }).startDownLoad();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            fileRsp.onSyncDownLoadFile(msg.getData().getString("saveName"));
            super.handleMessage(msg);
        }
    };

}
