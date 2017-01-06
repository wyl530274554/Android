package com.melon.myapp.functions.ui;

import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;
import com.melon.mylibrary.util.ToastUtil;

import java.lang.reflect.Method;

public class AppActionBarActivity extends BaseActivity {

    @Override
    protected void initView() {
        setContentView(R.layout.activity_action_bar);

        init();
    }

    @Override
    protected void initData() {

    }

    private void init() {
        setTitle("ActionBar");
        ActionBar actionBar = getSupportActionBar();
        //显示logo
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_app_actionbar, menu);
        //返回true才会显示overflow按钮
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ToastUtil.showShortToast(getApplicationContext(), item.getTitle() + "");
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }

    @Override
    public void onClick(View v) {

    }
}
