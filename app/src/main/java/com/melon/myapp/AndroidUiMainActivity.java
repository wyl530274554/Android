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
import com.melon.myapp.functions.system.MaxMemoryActivity;
import com.melon.myapp.functions.ui.AdvBannerActivity;
import com.melon.myapp.functions.ui.AnimationActivity;
import com.melon.myapp.functions.ui.AppActionBarActivity;
import com.melon.myapp.functions.ui.CardViewActivity;
import com.melon.myapp.functions.ui.CollapsingToolbarLayoutActivity;
import com.melon.myapp.functions.ui.CoordinatorLayoutActivity;
import com.melon.myapp.functions.ui.DrawerLayoutActivity;
import com.melon.myapp.functions.ui.FlowLayoutActivity;
import com.melon.myapp.functions.ui.FullScreenImageActivity;
import com.melon.myapp.functions.ui.ImageViewPressActivity;
import com.melon.myapp.functions.ui.LikeActivity;
import com.melon.myapp.functions.ui.NavigationActivity;
import com.melon.myapp.functions.ui.NavigationViewActivity;
import com.melon.myapp.functions.ui.ProgressActivity;
import com.melon.myapp.functions.ui.PullRefreshActivity;
import com.melon.myapp.functions.ui.RecycleViewActivity;
import com.melon.myapp.functions.ui.ScrollListViewActivity;
import com.melon.myapp.functions.ui.SnackbarActivity;
import com.melon.myapp.functions.ui.StatusBarActivity;
import com.melon.myapp.functions.ui.TextSwitcherActivity;
import com.melon.myapp.functions.ui.ToolbarActivity;
import com.melon.myapp.functions.ui.ViewFlipperActivity;
import com.melon.myapp.functions.ui.WxProgressBarActivity;
import com.melon.myapp.functions.wifi.ShowWifiInfoActivity;
import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.ViewHolder;

public class AndroidUiMainActivity extends BaseActivity implements OnItemClickListener {
    private String[] items = new String[]{
            "查看Wifi列表", "摇一摇", "Beacon",
            "屏幕分辨率", "进度条", "导航",
            "侧滑", "自动换行", "ActionBar",
            "沉浸式状态栏", "下拉刷新","Toolbar",
            "RecycleView","动画","NavigationView",
            "CardView","Snackbar\nFloatingActBtn","广告轮播", //6
            "ViewFlipper", "Twitter登录", "Like",//7
            "自定义下拉刷新","微信进度","TextSwither",//8
            "最大内存","Coordinator\nAppBar", "CollapsingToolbarLayout",//9
            "图片按下状态","沉浸式图片"
    };

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main_ui);
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
                break;
            case 13:
                //动画
                CommonUtil.enterActivity(mContext, AnimationActivity.class);
                break;
            case 14:
                //NavigationView
                CommonUtil.enterActivity(mContext, NavigationViewActivity.class);
                break;
            case 15:
                //CardView
                CommonUtil.enterActivity(mContext, CardViewActivity.class);
                break;
            case 16:
                //Snackbar
                CommonUtil.enterActivity(mContext, SnackbarActivity.class);
                break;
            case 25:
                //Main
                CommonUtil.enterActivity(mContext, CoordinatorLayoutActivity.class);
                break;
            case 18:
                //ViewFlipper
                CommonUtil.enterActivity(mContext, ViewFlipperActivity.class);
                break;
            case 19:
                //Twitter登录
                break;
            case 20:
                //Like
                CommonUtil.enterActivity(mContext, LikeActivity.class);
                break;
            case 21:
                //自定义下拉刷新
                CommonUtil.enterActivity(mContext, ScrollListViewActivity.class);
                break;
            case 22:
                //微信进度
                CommonUtil.enterActivity(mContext, WxProgressBarActivity.class);
                break;
            case 23:
                //TextSwitcher
                CommonUtil.enterActivity(mContext, TextSwitcherActivity.class);
                break;
            case 24:
                //最大内存
                CommonUtil.enterActivity(mContext, MaxMemoryActivity.class);
                break;
            case 17:
                //广告Banner
                CommonUtil.enterActivity(mContext, AdvBannerActivity.class);
                break;
            case 26:
                //CollapsingToolbarLayout标题折叠
                CommonUtil.enterActivity(mContext, CollapsingToolbarLayoutActivity.class);
                break;
            case 27:
                //图片按下
                CommonUtil.enterActivity(mContext, ImageViewPressActivity.class);
                break;
            case 28:
                //沉浸式图片
                CommonUtil.enterActivity(mContext, FullScreenImageActivity.class);
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
