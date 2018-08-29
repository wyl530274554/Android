package com.melon.myapp;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.SpUtil;

import butterknife.BindView;
import butterknife.OnClick;
/**
 * 设置
 * @author melon.wang
 * @date 2018/8/23
 */
public class SettingActivity extends BaseActivity {

    private Switch switchSettingImg;
    @BindView(R.id.tv_setting_server_host)
    public TextView tvSettingServerHost;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_setting);

        switchSettingImg = findViewById(R.id.switch_setting_img);
        switchSettingImg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SpUtil.setBoolean(getApplicationContext(), "isSmartWebNoImgOpen", isChecked);
            }
        });
    }

    @Override
    protected void initData() {
        boolean isWebNoImgOpen = SpUtil.getBoolean(getApplicationContext(), "isSmartWebNoImgOpen");
        switchSettingImg.setChecked(isWebNoImgOpen);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showHost();
    }

    private void showHost() {
        String apiHost = ApiManager.getApiHost();
        tvSettingServerHost.setText(apiHost);
    }

    @OnClick({R.id.rl_setting_server_host})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_setting_server_host:
                // 修改ip和端口
                CommonUtil.enterFragment(this, CommonFragmentActivity.class, CommonFragmentActivity.FRAGMENT_ALTER_HOST);
                break;
            default:
        }
    }
}
