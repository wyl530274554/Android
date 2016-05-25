package com.melon.myapp.functions.ui;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;
import com.melon.mylibrary.util.ToastUtil;

import java.util.Date;

public class PullRefreshActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tv_time;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_pull_refresh);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light);

        tv_time = (TextView) findViewById(R.id.tv_time);
    }

    @Override
    protected void initData() {
        showRefresh();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_time.setText("刷新时间："+new Date().toLocaleString());
                swipeRefreshLayout.setRefreshing(false);
                ToastUtil.showShortToast(getApplicationContext(), "刷新完毕");
            }
        }, 2000);
    }

    private void showRefresh() {
        //第一次显示下拉刷新
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        }, 200);
    }
}
