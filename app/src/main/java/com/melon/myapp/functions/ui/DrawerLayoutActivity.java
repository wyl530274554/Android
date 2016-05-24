package com.melon.myapp.functions.ui;

import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;
import com.melon.mylibrary.util.ToastUtil;

import java.util.ArrayList;

public class DrawerLayoutActivity extends BaseActivity implements AdapterView.OnItemClickListener, DrawerLayout.DrawerListener {

    private ListView mDrawerList;
    private TextView tv_content;
    private ArrayList<String> myArrayList;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        tv_content = (TextView) findViewById(R.id.tv_content);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerList.setOnItemClickListener(this);
        mDrawerLayout.addDrawerListener(this);
    }

    @Override
    protected void initData() {
        myArrayList = new ArrayList<>();
        myArrayList.add("首页");
        myArrayList.add("发现");
        myArrayList.add("购物车");
        myArrayList.add("关于");
        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myArrayList);
        mDrawerList.setAdapter(myArrayAdapter);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String title = myArrayList.get(position);
        tv_content.setText(title);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {
        ToastUtil.showShortToast(this,"打开...");
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        ToastUtil.showShortToast(this,"关闭...");
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}
