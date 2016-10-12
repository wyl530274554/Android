package com.melon.myapp.functions.system;

import android.view.View;
import android.widget.TextView;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;

public class MaxMemoryActivity extends BaseActivity {

    private TextView tv_max_memory;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_max_memory);

        tv_max_memory = (TextView) findViewById(R.id.tv_max_memory);
    }

    @Override
    protected void initData() {
        //应用程序最大可用内存
        int maxMemory = ((int) Runtime.getRuntime().maxMemory())/1024/1024;
        //应用程序已获得内存
        long totalMemory = ((int) Runtime.getRuntime().totalMemory())/1024/1024;
        //应用程序已获得内存中未使用内存
        long freeMemory = ((int) Runtime.getRuntime().freeMemory())/1024/1024;
        tv_max_memory.setText("maxMemory="+maxMemory+"M, totalMemory="+totalMemory+"M, freeMemory="+freeMemory+"M");
    }

    @Override
    public void onClick(View v) {

    }
}
