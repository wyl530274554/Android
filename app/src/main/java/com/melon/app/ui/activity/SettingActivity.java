package com.melon.app.ui.activity;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.melon.app.ApiManager;
import com.melon.app.CommonFragmentActivity;
import com.melon.app.R;
import com.melon.mylibrary.BaseActivity;
import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.SpUtil;
import com.melon.mylibrary.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置
 *
 * @author melon.wang
 * @date 2018/8/23
 */
public class SettingActivity extends BaseActivity {

    private Switch switchSettingImg;
    @BindView(R.id.tv_setting_server_host)
    public TextView tvSettingServerHost;
    @BindView(R.id.tv_setting_server_local_ip)
    public TextView tvSettingServerLocalIp;
    @BindView(R.id.tv_setting_version)
    public TextView tvSettingVersion;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_setting);

        tvSettingVersion = findViewById(R.id.tv_setting_version);
        tvSettingServerLocalIp = findViewById(R.id.tv_setting_server_local_ip);
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

        //获取远程服务器局域网IP
        getServerLocalIp();

        //版本号
        tvSettingVersion.setText(CommonUtil.getVersion(getApplicationContext()));
    }

    private void getServerLocalIp() {
        //TODO 换成自己的网络封装
        OkHttpUtils.get().url(ApiManager.API_GET_LOCAL_IP).build().execute(new StringCallback() {

            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                ToastUtil.toast(getApplicationContext(),"onError: "+e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                if (!CommonUtil.isEmpty(response)) {
                    tvSettingServerLocalIp.setText(response);
                }
            }
        });
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
