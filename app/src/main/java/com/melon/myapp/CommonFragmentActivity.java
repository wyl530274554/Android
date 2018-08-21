package com.melon.myapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.melon.myapp.functions.fragment.GreenDaoFragment;
import com.melon.myapp.functions.fragment.HostAlterFragment;
import com.melon.mylibrary.util.ToastUtil;

/**
 * 单个fragment页面 可以统一走这里
 * Created by admin on 2017/3/28.
 * Email 530274554@qq.com
 */

public class CommonFragmentActivity extends BaseActivity {
    public static final int FRAGMENT_GREEN_DAO = 1;
    public static final int FRAGMENT_ALTER_HOST = 2;

    private FragmentManager fragmentManager;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_common_fragment);
        fragmentManager = getSupportFragmentManager();
        int target = getIntent().getIntExtra("target", -1);

        if (target < 0) {
            ToastUtil.toast(this, "目标Fragment不存在");
            finish();
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = null;
        switch (target) {
            case FRAGMENT_GREEN_DAO:
                fragment = new GreenDaoFragment();
                break;
            case FRAGMENT_ALTER_HOST:
                fragment = new HostAlterFragment();
                break;
        }
        transaction.add(R.id.fl_common_fragment, fragment);
        transaction.commit();
    }

    @Override
    protected void initData() {

    }
}
