package com.ysg.yashige.utils.upData.DownLoadFile;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

/**
 * Created by apple on 2016/12/26.
 */
public class MBDownLoadRequest extends DownloadManager.Request{

    /**
     * @param uri the HTTP or HTTPS URI to download.
     */
    public MBDownLoadRequest(Uri uri) {
        super(uri);

    }

    public MBDownLoadRequest(Context context, String url, String title, String msg, String saveName){
        super(Uri.parse(url));
        this.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        this.setTitle(title);
        this.setDescription(msg);
        this.setAllowedOverRoaming(true);
        this.allowScanningByMediaScanner() ;
        this.setVisibleInDownloadsUi( true ) ;
        this.setAllowedNetworkTypes( DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI ) ;
        this.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, saveName);
    }

}
