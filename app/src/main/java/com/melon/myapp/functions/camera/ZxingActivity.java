package com.melon.myapp.functions.camera;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;
import com.melon.mylibrary.util.ImageUtil;
import com.melon.mylibrary.util.ToastUtil;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import butterknife.OnClick;

/**
 * Created by melon on 2017/9/21.
 * Email 530274554@qq.com
 */

public class ZxingActivity extends BaseActivity {

    private static final int REQUEST_CODE = 100;
    private static final int REQUEST_IMAGE = 101;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_zxing);
    }

    @Override
    protected void initData() {
        ZXingLibrary.initDisplayOpinion(this);
    }

    private void scan() {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @OnClick({R.id.button, R.id.button1, R.id.button2, R.id.button3})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                scan();
                break;
            case R.id.button1:
                parseImage();
                break;
            case R.id.button2:
                break;
            case R.id.button3:
                break;
        }
    }

    private void parseImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    ToastUtil.toast(getApplicationContext(), "result: " + result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtil.toast(getApplicationContext(), "解析二维码失败: ");
                }
            }
        }

        if (requestCode == REQUEST_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                ContentResolver cr = getContentResolver();
                try {
                    Bitmap mBitmap = MediaStore.Images.Media.getBitmap(cr, uri);//显得到bitmap图片
                    String path = uri.getPath();
                    CodeUtils.analyzeBitmap(ImageUtil.getImageAbsolutePath(ZxingActivity.this, uri), new CodeUtils.AnalyzeCallback() {
                        @Override
                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                            ToastUtil.toast(getApplicationContext(), "result: " + result);
                        }

                        @Override
                        public void onAnalyzeFailed() {
                            ToastUtil.toast(getApplicationContext(), "解析二维码失败: ");
                        }
                    });

                    if (mBitmap != null) {
                        mBitmap.recycle();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
