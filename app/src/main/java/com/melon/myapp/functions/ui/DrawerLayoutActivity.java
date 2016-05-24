package com.melon.myapp.functions.ui;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;

import java.util.ArrayList;

public class DrawerLayoutActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private ListView mDrawerList;
    private TextView tv_content;
    private ArrayList<String> myArrayList;
    private DrawerLayout drawerLayout;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        tv_content = (TextView) findViewById(R.id.tv_content);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerList.setOnItemClickListener(this);
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
        drawerLayout.closeDrawer(mDrawerList);
    }
}
