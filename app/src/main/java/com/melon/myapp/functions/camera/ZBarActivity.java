package com.melon.myapp.functions.camera;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;
import com.melon.mylibrary.util.ToastUtil;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import butterknife.OnClick;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * zbar扫描速率要高于zxing
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
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

//    @OnClick({R.id.button, R.id.button1, R.id.button2, R.id.button3})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
//                scan();
//                scan2();
                scan3();
                break;
            case R.id.button1:
                break;
            case R.id.button2:
                break;
            case R.id.button3:
                break;
        }
    }

    //zbar compile 'me.dm7.barcodescanner:zbar:1.9.8'
    private void scan3() {

    }

    //ZXing Android Embedded
    private void scan2() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }

    //yipianfengye/android-zxingLibrary


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

        ToastUtil.toast(getApplicationContext(), result.getContents());
        ToastUtil.toast(getApplicationContext(), result.getBarcodeFormat().getName());
        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);
        finish();
    }
}
