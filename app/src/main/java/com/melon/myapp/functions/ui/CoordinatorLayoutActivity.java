package com.melon.myapp.functions.ui;

import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;
import com.melon.myapp.adapter.MyRecyclerViewAdapter;
import com.melon.myapp.bean.Website;

import java.util.ArrayList;
import java.util.List;

public class CoordinatorLayoutActivity extends BaseActivity {

    private RecyclerView coordinator_recycler_view;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_coordinator_layout);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolBar);
        mToolbar.setTitleTextColor(Color.WHITE);//设置ToolBar的titl颜色
        mToolbar.setTitle("CoordinatorAppBar");
        setSupportActionBar(mToolbar);

        coordinator_recycler_view = (RecyclerView) findViewById(R.id.coordinator_recycler_view);
        coordinator_recycler_view.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        coordinator_recycler_view.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initData() {
        List<Website> datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add(new Website("http://m.news.baidu.com/news?fr=mohome", R.drawable.ic_baidu_news));
        }
        coordinator_recycler_view.setAdapter(new MyRecyclerViewAdapter(getApplicationContext(), datas));
    }

    @Override
    public void onClick(View v) {

    }
}
