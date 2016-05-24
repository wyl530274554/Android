package com.melon.myapp.functions.ui;

import android.view.View;
import android.widget.Button;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;
import com.melon.mylibrary.view.FlowLayout;

public class FlowLayoutActivity extends BaseActivity {

    private FlowLayout flow_layout;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_flow_layout);
        flow_layout = (FlowLayout) findViewById(R.id.flow_layout);
    }

    @Override
    protected void initData() {
        //测试数据
        for (int i = 0; i < 14; i++) {
            Button btn = new Button(this);

            String str = "";
            for (int j = 0; j < i; j++) {
                str += i;
            }

            btn.setText("Button: " + str);
            btn.setPadding(30,10,30,10);
            flow_layout.addView(btn);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
