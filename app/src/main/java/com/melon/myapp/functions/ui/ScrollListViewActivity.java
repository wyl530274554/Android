package com.melon.myapp.functions.ui;

import android.view.View;
import android.widget.ArrayAdapter;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;
import com.melon.mylibrary.view.MyListView;

public class ScrollListViewActivity extends BaseActivity {

    private MyListView mListView;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_my_list_view);
        mListView = (MyListView) findViewById(R.id.my_list_view);
    }

    @Override
    protected void initData() {
        String[] names = {"1", "2", "3"};
        ArrayAdapter<String> mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, names);
        mListView.setAdapter(mAdapter);
    }
}
