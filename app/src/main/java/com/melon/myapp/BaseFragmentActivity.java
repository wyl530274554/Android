package com.melon.myapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View.OnClickListener;

import com.melon.myapp.util.SpUtil;

public abstract class BaseFragmentActivity extends FragmentActivity implements OnClickListener {
	protected SharedPreferences mConfigSp;
	protected Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mConfigSp = SpUtil.getSharePerference(this);
		mHandler = new Handler();
		initView();
		initData();

//		SystemHelper.setStateBarTint(this);
	}

	protected abstract void initView();

	protected abstract void initData();

}
