package com.melon.myapp.functions.screen;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;

/**
 * Created by melon on 2015/6/22.
 */
public class PhoneDensityActivity extends BaseActivity {
    private TextView tvContent;

    @Override
    protected void initData() {
        showDesity();
    }

    private void showDesity() {
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = getWindowManager().getDefaultDisplay();
        display.getMetrics(metrics);

        String densityType = "未知分辨率";
        switch (metrics.densityDpi){
            case 120:
                densityType = "Ldpi";
                break;
            case 160:
                densityType = "Mdpi";
                break;
            case 240:
                densityType = "Hdpi";
                break;
            case 320:
                densityType = "Xhdpi";
                break;
            case 480:
                densityType = "XXhdpi";
                break;
            case 640:
                densityType = "XXXhdpi";
                break;
        }

        tvContent.setText("density: " + metrics.density + "\ndensityDpi: " + metrics.densityDpi
                        + "\nheightPixels: " + metrics.heightPixels
                        + "\nwidthPixels: " + metrics.widthPixels
                        + "\nscaledDensity: " + metrics.scaledDensity
                        + "\nxdpi: " + metrics.xdpi
                        + "\nydpi: " + metrics.ydpi
                        + "\n\n分辨率类型："+densityType
        );
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_phone_density);

        tvContent = (TextView) findViewById(R.id.tv_phone_density_content);
    }

    @Override
    public void onClick(View v) {

    }
}
