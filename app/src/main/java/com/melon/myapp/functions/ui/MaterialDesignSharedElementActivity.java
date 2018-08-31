package com.melon.myapp.functions.ui;

import android.view.Window;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;

/**
 * 共享元素
 * @author melon.wang
 * @date 2018/8/31
 */
public class MaterialDesignSharedElementActivity extends BaseActivity {
    @Override
    protected void initView() {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_share_element);
    }

    @Override
    protected void initData() {

    }
}
