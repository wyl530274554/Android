package com.melon.myapp;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;
import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.SpUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    private Switch switch_setting_img;
    @BindView(R.id.tv_setting_server_host)
    public TextView tv_setting_server_host;

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
    protected void onResume() {
        super.onResume();
        showHost();
    }

    private void showHost() {
        String apiHost = ApiManager.getApiHost();
        tv_setting_server_host.setText(apiHost);
    }

    @OnClick({R.id.rl_setting_server_host})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_setting_server_host:
                //TODO 修改ip和端口
                CommonUtil.enterFragment(this,CommonFragmentActivity.class, CommonFragmentActivity.FRAGMENT_ALTER_HOST);
                break;
        }
    }
}
