package com.melon.myapp.functions.wifi;

import java.util.List;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;
import com.melon.mylibrary.util.NetUtil;

//wifi信息列表
public class ShowWifiInfoActivity extends BaseActivity {

	private TextView tvBssid;
	private WifiManager wm;

	@Override
	protected void initView() {
		setContentView(R.layout.activity_show_wifi_info);
		tvBssid = (TextView) findViewById(R.id.tv_wifi_bssid);
	}

	@Override
	protected void initData() {
		setTitle("Wifi列表");
		wm = (WifiManager) getSystemService(WIFI_SERVICE);

		boolean isWifiEnabled = NetUtil.isWifiEnable(this);
		if(isWifiEnabled){
			show();
		}else{
			tvBssid.setText("当前WiFi功能未启用");
		}
	}

	private void show() {
		try {
			List<ScanResult> scanResults = wm.getScanResults();

			NetUtil.sort(scanResults);

			StringBuilder sb = new StringBuilder();
			for (ScanResult scanResult : scanResults) {
				sb.append(scanResult.SSID + "\n" + scanResult.BSSID + "    信号：" + scanResult.level + "\n加密：" + scanResult.capabilities + "\n\n");
			}

			String result = sb.toString();
			if(TextUtils.isEmpty(result)){
				tvBssid.setText("未扫描到WiFi信号");
			}else{
				tvBssid.setText(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {

	}

}
