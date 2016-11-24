package com.melon.myapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View.OnClickListener;
import android.view.WindowManager;

public abstract class BaseActivity extends AppCompatActivity implements OnClickListener {
	public static Handler mHandler = new Handler();
	Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getApplicationContext();
		initView();
		initData();

		//透明状态栏
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		//透明导航栏
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
	}

	protected abstract void initView();
	protected abstract void initData();
}
