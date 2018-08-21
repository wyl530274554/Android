package com.melon.myapp.functions.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;
import com.melon.myapp.adapter.MyXRecyclerViewAdapter;

public class XRecyclerViewActivity extends BaseActivity {

    @Override
    protected void initView() {
        setContentView(R.layout.activity_xrecycler_view);

        final XRecyclerView mRecyclerView = (XRecyclerView) findViewById(R.id.xRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new MyXRecyclerViewAdapter());

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.refreshComplete();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.loadMoreComplete();

                        mRecyclerView.setLoadingMoreEnabled(false);
                    }
                }, 2000);
            }
        });
    }

    @Override
    protected void initData() {

    }


}
