package com.melon.myapp.functions.ui;

import android.view.View;
import android.widget.EditText;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;

import butterknife.OnClick;

/**
 * @deprecated 无用了，以WebActivity中的为准
 */
public class WxProgressBarActivity extends BaseActivity {
    private EditText etUrl;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_wx_progress_bar);
    }


    @Override
    protected void initData() {
    }

    @OnClick(R.id.bt_wx_progress_bar)
    public void onClick(View v) {
    }
}
