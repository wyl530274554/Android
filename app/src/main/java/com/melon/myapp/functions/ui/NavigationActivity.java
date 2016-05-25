package com.melon.myapp.functions.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;

import com.melon.myapp.BaseFragmentActivity;
import com.melon.myapp.R;
import com.melon.myapp.functions.fragment.CommonFragment;

public class NavigationActivity extends BaseFragmentActivity {
    private FragmentTabHost mTabHost = null;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_navigation);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
    }

    @Override
    protected void initData() {
        mTabHost.addTab(mTabHost.newTabSpec("0").setIndicator("新闻"), CommonFragment.class, putContent("新闻"));
        mTabHost.addTab(mTabHost.newTabSpec("1").setIndicator("音乐"), CommonFragment.class, putContent("音乐"));
        mTabHost.addTab(mTabHost.newTabSpec("2").setIndicator("人生"), CommonFragment.class, putContent("人生"));
    }

    private Bundle putContent(String content){
        Bundle bundle = new Bundle();
        bundle.putString("content",content);
        return  bundle;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTabHost = null;
    }
}
