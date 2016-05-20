package com.melon.myapp;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.melon.myapp.functions.beacon.ShowBeaconsActivity;
import com.melon.myapp.functions.screen.PhoneDensityActivity;
import com.melon.myapp.functions.sensor.ShakeOneShakeActivity;
import com.melon.myapp.functions.ui.ProgressActivity;
import com.melon.myapp.functions.wifi.ShowWifiInfoActivity;
import com.melon.myapp.util.ToastUtil;
import com.melon.myapp.util.ViewHolder;
import com.melon.mylibrary.CommonUtil;

public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private String[] items = new String[]{"查看Wifi列表", "摇一摇", "Beacon", "屏幕分辨率","进度条"};
    private SwipeRefreshLayout srl_main;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);

        srl_main = (SwipeRefreshLayout) findViewById(R.id.srl_main);
        srl_main.setOnRefreshListener(this);
        srl_main.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light);

        GridView gridView = (GridView) findViewById(R.id.gv_main);
        MyAdapter mAdapter = new MyAdapter();
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        // wifi列表
                        CommonUtil.enterActivity(mContext,ShowWifiInfoActivity.class);
                        break;
                    case 1:
                        // 摇一摇
                        CommonUtil.enterActivity(mContext,ShakeOneShakeActivity.class);
                        break;
                    case 2:
                        //beacon
                        CommonUtil.enterActivity(mContext,ShowBeaconsActivity.class);
                        break;
                    case 3:
                        //屏幕分辨率
                        CommonUtil.enterActivity(mContext,PhoneDensityActivity.class);
                        break;
                    case 4:
                        //屏幕分辨率
                        CommonUtil.enterActivity(mContext,ProgressActivity.class);
                        break;
                }
            }

        });
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                srl_main.setRefreshing(false);
                ToastUtil.showShortToast(getApplicationContext(),"刷新完毕");
            }
        }, 2000);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_main, parent, false);
            }

            TextView tvName = ViewHolder.get(convertView, R.id.tv_item_main_name);
            tvName.setText(items[position]);
            return convertView;
        }
    }
}
