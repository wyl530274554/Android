package com.melon.myapp.functions.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.melon.myapp.ApiManager;
import com.melon.myapp.BaseFragment;
import com.melon.myapp.R;
import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.SpUtil;
import com.melon.mylibrary.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 修改服务器ip和端口号
 * Created by melon on 2017/10/24.
 * Email 530274554@qq.com
 */

public class HostAlterFragment extends BaseFragment {
    @BindView(R.id.et_alter_host_ip)
    EditText et_alter_host_ip;
    @BindView(R.id.et_alter_host_port)
    EditText et_alter_host_port;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_host_alter,container,false);
    }

    @Override
    protected void initData() {
        et_alter_host_ip.setText(ApiManager.getApiIp());
        et_alter_host_port.setText(ApiManager.getApiPort());
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.bt_alter_host_ok})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_alter_host_ok:
                String ip = et_alter_host_ip.getText().toString().trim();
                String port = et_alter_host_port.getText().toString().trim();

                if(CommonUtil.isEmpty(ip) || CommonUtil.isEmpty(port)) {
                    ToastUtil.toast(getContext(), "请输入内容");
                    return;
                }

                String apiHost = ip+":"+port;
                SpUtil.setString(getContext(),"api_host", apiHost);
                getActivity().finish();
                System.exit(0);
                break;
        }
    }
}
