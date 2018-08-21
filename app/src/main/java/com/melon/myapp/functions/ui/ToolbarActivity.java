package com.melon.myapp.functions.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;
import com.melon.mylibrary.util.ToastUtil;

public class ToolbarActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_menu_selectall_holo_light);//设置导航栏图标
        toolbar.setLogo(R.mipmap.ic_launcher);//设置app logo
        toolbar.setTitle("Title");//设置主标题
        toolbar.setSubtitle("Subtitle");//设置子标题

        toolbar.inflateMenu(R.menu.base_toolbar_menu);//设置右上角的填充菜单
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ToastUtil.toast(getApplicationContext(),item.getTitle()+"");
                return true;
            }
        });

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

}
