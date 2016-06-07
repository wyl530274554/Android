package com.melon.myapp;

import android.graphics.Color;
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
import com.melon.myapp.functions.thirdLogin.TwitterLoginActivity;
import com.melon.myapp.functions.ui.AppActionBarActivity;
import com.melon.myapp.functions.ui.DrawerLayoutActivity;
import com.melon.myapp.functions.ui.FlowLayoutActivity;
import com.melon.myapp.functions.ui.LikeActivity;
import com.melon.myapp.functions.ui.NavigationActivity;
import com.melon.myapp.functions.ui.NavigationViewActivity;
import com.melon.myapp.functions.ui.ProgressActivity;
import com.melon.myapp.functions.ui.PullRefreshActivity;
import com.melon.myapp.functions.ui.RecycleViewActivity;
import com.melon.myapp.functions.ui.StatusBarActivity;
import com.melon.myapp.functions.ui.ToolbarActivity;
import com.melon.myapp.functions.wifi.ShowWifiInfoActivity;
import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.ToastUtil;
import com.melon.mylibrary.util.ViewHolder;

public class MainActivity extends BaseActivity implements OnItemClickListener {
    private String[] items = new String[]{"查看Wifi列表", "摇一摇", "Beacon",
            "屏幕分辨率", "进度条", "导航",
            "侧滑", "自动换行", "ActionBar",
            "沉浸式状态栏", "下拉刷新","Toolbar",
            "RecycleView","动画","NavigationView",
            "CardView","Snackbar","FloatingActionButton",
            "AppBarLayout","ViewFlipper","ViewFlipperScrollView",
            "Twitter登录","Like"
    };

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        GridView gridView = (GridView) findViewById(R.id.gv_main);
        MyAdapter mAdapter = new MyAdapter();
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                // wifi列表
                CommonUtil.enterActivity(mContext, ShowWifiInfoActivity.class);
                break;
            case 1:
                // 摇一摇
                CommonUtil.enterActivity(mContext, ShakeOneShakeActivity.class);
                break;
            case 2:
                //beacon
                CommonUtil.enterActivity(mContext, ShowBeaconsActivity.class);
                break;
            case 3:
                //屏幕分辨率
                CommonUtil.enterActivity(mContext, PhoneDensityActivity.class);
                break;
            case 4:
                //屏幕分辨率
                CommonUtil.enterActivity(mContext, ProgressActivity.class);
                break;
            case 5:
                //导航
                CommonUtil.enterActivity(mContext, NavigationActivity.class);
                break;
            case 6:
                //侧滑
                CommonUtil.enterActivity(mContext, DrawerLayoutActivity.class);
                break;
            case 7:
                //自动换行
                CommonUtil.enterActivity(mContext, FlowLayoutActivity.class);
                break;
            case 8:
                //ActionBar
                CommonUtil.enterActivity(mContext, AppActionBarActivity.class);
                break;
            case 9:
                //沉浸式状态栏
                CommonUtil.enterActivity(mContext, StatusBarActivity.class);
                break;
            case 10:
                //下拉刷新
                CommonUtil.enterActivity(mContext, PullRefreshActivity.class);
                break;
            case 11:
                //Toolbar
                CommonUtil.enterActivity(mContext, ToolbarActivity.class);
                break;
            case 12:
                //RecycleView
                CommonUtil.enterActivity(mContext, RecycleViewActivity.class);
            case 14:
                //NavigationView
                CommonUtil.enterActivity(mContext, NavigationViewActivity.class);
                break;
            case 21:
                //Twitter登录
                CommonUtil.enterActivity(mContext, TwitterLoginActivity.class);
                break;
            case 22:
                //Like
                CommonUtil.enterActivity(mContext, LikeActivity.class);
                break;
        }
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
//            if (position == 5)
//                tvName.setTextColor(Color.RED);
            return convertView;
        }
    }
}
