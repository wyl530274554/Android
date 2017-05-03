package com.melon.myapp;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;
import com.melon.mylibrary.util.SpUtil;

public class SettingActivity extends BaseActivity {

    private Switch switch_setting_img;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_setting);

        switch_setting_img = (Switch) findViewById(R.id.switch_setting_img);
        switch_setting_img.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SpUtil.setBoolean(getApplicationContext(),"isSmartWebNoImgOpen",isChecked);
            }
        });
    }

    @Override
    protected void initData() {
        boolean isWebNoImgOpen = SpUtil.getBoolean(getApplicationContext(), "isSmartWebNoImgOpen");
        switch_setting_img.setChecked(isWebNoImgOpen);
    }

    @Override
    public void onClick(View v) {

    }
}
