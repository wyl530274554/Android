package com.melon.myapp;

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
import com.melon.myapp.functions.wifi.ShowWifiInfoActivity;
import com.melon.myapp.util.ViewHolder;
import com.melon.mylibrary.CommonUtil;

public class MainActivity extends BaseActivity {
    private String[] items = new String[]{"查看Wifi列表", "摇一摇", "Beacon", "屏幕分辨率"};

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        GridView gridView = (GridView) findViewById(R.id.gv_main);
        MyAdapter mAdapter = new MyAdapter();
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        // wifi列表
                        CommonUtil.enterActivity(getApplicationContext(),ShowWifiInfoActivity.class);
                        break;
                    case 1:
                        // 摇一摇
                        CommonUtil.enterActivity(getApplicationContext(),ShakeOneShakeActivity.class);
                        break;
                    case 2:
                        //beacon
                        CommonUtil.enterActivity(getApplicationContext(),ShowBeaconsActivity.class);
                        break;
                    case 3:
                        //屏幕分辨率
                        CommonUtil.enterActivity(getApplicationContext(),PhoneDensityActivity.class);
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
