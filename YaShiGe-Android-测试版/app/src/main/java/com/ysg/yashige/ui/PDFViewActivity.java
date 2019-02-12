package com.ysg.yashige.ui;


import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.ysg.yashige.R;
import com.ysg.yashige.base.BaseActivity;
import com.ysg.yashige.utils.LoadingDialog;
import com.ysg.yashige.utils.upData.MBUpdateApp;
import com.ysg.yashige.utils.upData.SyncNetManager;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class PDFViewActivity extends BaseActivity {

    @BindView(R.id.pdfView)
    PDFView pdfView;
    String url = "";

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pdfview);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("pdfUrl");
        PermissionGen.with(PDFViewActivity.this)
                .addRequestCode(100)
                .permissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 100)
    public void doSomething() {
        downloadPDF(url);
        LoadingDialog.getInstance(this).showDialog();
    }

    @PermissionFail(requestCode = 100)
    public void doFailSomething() {
        Toast.makeText(this, "权限申请失败", Toast.LENGTH_SHORT).show();
    }

    private void openPDF(Uri uri) {
        pdfView.fromUri(uri)
//                .pages() // all pages are displayed by default
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                // spacing between pages in dp. To define spacing color, set view background
                .spacing(0)
                .load();
    }

    private void downloadPDF(String urlpath) {

        new MBUpdateApp(this, urlpath, new SyncNetManager.onSynFile() {
            @Override
            public void onSyncDownLoadFile(String saveName) {
                Uri pdfurl = null;
                File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
                        saveName);
                pdfurl = Uri.parse("file://" + file.toString());
                LoadingDialog.getInstance(PDFViewActivity.this).hideDialog();
                openPDF(pdfurl);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initControl() {

    }

    @Override
    protected void initListener() {

    }


}
