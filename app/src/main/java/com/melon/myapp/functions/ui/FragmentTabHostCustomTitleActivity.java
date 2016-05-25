package com.melon.myapp.functions.ui;

import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.melon.myapp.BaseFragmentActivity;
import com.melon.myapp.R;
import com.melon.myapp.functions.fragment.CommonFragment;

public class FragmentTabHostCustomTitleActivity extends BaseFragmentActivity {
    private String[] titles = {"新闻", "音乐", "人生"};
    private FragmentTabHost mTabHost = null;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_fragmenttabhost);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
    }

    @Override
    protected void initData() {
        for (int i = 0; i < titles.length; i++) {
            mTabHost.addTab(mTabHost.newTabSpec(i+"").setIndicator(titles[i]), CommonFragment.class, putContent(titles[i]));
        }
    }

    private Bundle putContent(String content) {
        Bundle bundle = new Bundle();
        bundle.putString("content", content);
        return bundle;
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
