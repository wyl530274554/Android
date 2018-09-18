package com.melon.myapp.functions.camera;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Toast;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.BuildConfig;
import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.LogUtils;
import com.melon.mylibrary.util.ToastUtil;

import java.io.File;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * zBar扫描速率要高于zxing
 * 它有两个so文件，比较大。libiconv.so(870KB) libzbarjni.so(98KB) ，三个平台(arm/x86/arm-v7)加起来就大了。
 *
 * @author melon.wang
 * @date 2018/9/18
 */
public class ZBarActivity extends BaseActivity implements ZBarScannerView.ResultHandler {
    private ZBarScannerView mScannerView;
    private static final int REQUEST_CODE = 100;

    @Override
    protected void initView() {
        mScannerView = new ZBarScannerView(this);
//        setContentView(R.layout.activity_zxing);
        setContentView(mScannerView);
    }

    @Override
    protected void initData() {
//        ZXingLibrary.initDisplayOpinion(this);
        //注册广播接收器
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(downloadReceiver, filter);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Register ourselves as a handler for scan results.
        mScannerView.setResultHandler(this);
        // Start camera on resume
        mScannerView.startCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(downloadReceiver);
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    //zbar compile 'me.dm7.barcodescanner:zbar:1.9.8'
    private void scan3() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
//        if (result != null) {
//            if (result.getContents() == null) {
//                ToastUtil.toast(getApplicationContext(), "Cancelled");
//            } else {
//                ToastUtil.toast(getApplicationContext(), "Scanned: " + result.getContents());
//            }
//        } else {
//            super.onActivityResult(requestCode, resultCode, data);
//        }


//        if (requestCode == REQUEST_CODE) {
//            //处理扫描结果（在界面上显示）
//            if (null != data) {
//                Bundle bundle = data.getExtras();
//                if (bundle == null) {
//                    return;
//                }
//                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
//                    String result = bundle.getString(CodeUtils.RESULT_STRING);
//                    ToastUtil.toast(getApplicationContext(), "result: " + result);
//                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
//                    ToastUtil.toast(getApplicationContext(), "解析二维码失败: ");
//                }
//            }
//        }
    }

    @Override
    public void handleResult(Result result) {
        // Do something with the result here
        String content = result.getContents();
        String formatType = result.getBarcodeFormat().getName();
        ToastUtil.toast(getApplicationContext(), content);
        ToastUtil.toast(getApplicationContext(), formatType);

        //TODO 下载APK
        if (!CommonUtil.isEmpty(content) && content.endsWith(".apk")) {
            mScannerView.stopCamera();
            mScannerView.stopCameraPreview();
            mScannerView.setVisibility(View.GONE);
            downloadApk = CommonUtil.downloadFile(getApplicationContext(), content);
            ToastUtil.showLongToast(this, "Apk下载中...");
            LogUtils.e("downloadApk: " + downloadApk);
            return;
        }

        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);
        finish();
    }

    /**
     * APK存储路径
     */
    private String downloadApk;

    /**
     * 广播接受器, 下载完成监听器
     */
    BroadcastReceiver downloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                //下载完成了
                //获取当前完成任务的ID
                long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                Toast.makeText(ZBarActivity.this, "下载完成了", Toast.LENGTH_SHORT).show();

                //自动安装应用
                installApk();
            }
        }
    };

    /**
     * 安装apk
     */
    private void installApk() {
        try {
            File apkFile = new File(downloadApk);
            if (!apkFile.exists()) {
                ToastUtil.toast(this, "文件不存在");
                return;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW);

            //判断是否是AndroidN以及更高的版本
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", apkFile);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }

            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
