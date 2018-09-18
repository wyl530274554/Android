package com.melon.myapp.functions.system;

import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.widget.TextView;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;

import butterknife.BindView;

/**
 * 各种存储路径
 *
 * @author melon.wang
 * @date 2018/9/18
 */
public class FileStoragePathActivity extends BaseActivity {
    @BindView(R.id.tv_content)
    TextView tvContent;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_file_path);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initData() {
        StringBuilder sb = new StringBuilder();
        sb.append("Context.getFilesDir()").append("\n").append(getFilesDir());
        sb.append("\n\n");
        sb.append("Context.getCacheDir()").append("\n").append(getCacheDir());
        sb.append("\n\n");
        sb.append("Context.getExternalFilesDir()").append("\n").append(getExternalFilesDir(""));
        sb.append("\n\n");
        sb.append("Context.getExternalCacheDir()").append("\n").append(getExternalCacheDir());
        sb.append("\n\n");
        sb.append("Environment.getExternalStorageDirectory()").append("\n").append(Environment.getExternalStorageDirectory());
        sb.append("\n\n");
        sb.append("Environment.getExternalStoragePublicDirectory()").append("\n").append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC));
        sb.append("\n\n");
        sb.append("Environment.getDataDirectory()").append("\n").append(Environment.getDataDirectory());
        sb.append("\n\n");
        sb.append("Environment.getDownloadCacheDirectory()").append("\n").append(Environment.getDownloadCacheDirectory());
        sb.append("\n\n");

        tvContent.setText(sb.toString());
    }
}
