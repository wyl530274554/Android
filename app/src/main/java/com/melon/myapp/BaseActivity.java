package com.melon.myapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View.OnClickListener;
import android.view.WindowManager;

public abstract class BaseActivity extends Activity implements OnClickListener {
	public static Handler mHandler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏
		initView();
		initData();
	}

	protected abstract void initView();
	protected abstract void initData();
}
