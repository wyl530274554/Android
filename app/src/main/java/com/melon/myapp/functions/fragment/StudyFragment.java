package com.melon.myapp.functions.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sdkdemo.SdkMelon;
import com.melon.myapp.BaseFragment;
import com.melon.myapp.CommonFragmentActivity;
import com.melon.myapp.R;
import com.melon.myapp.functions.activity.RetrofitActivity;
import com.melon.myapp.functions.architecture.mvvm.view.MvvmActivity;
import com.melon.myapp.functions.beacon.ShowBeaconsActivity;
import com.melon.myapp.functions.camera.ZBarActivity;
import com.melon.myapp.functions.camera.ZxingActivity;
import com.melon.myapp.functions.screen.PhoneDensityActivity;
import com.melon.myapp.functions.sensor.ShakeOneShakeActivity;
import com.melon.myapp.functions.system.FileStoragePathActivity;
import com.melon.myapp.functions.system.MaxMemoryActivity;
import com.melon.myapp.functions.ui.AddToCartAnim2Activity;
import com.melon.myapp.functions.ui.AddToCartAnimActivity;
import com.melon.myapp.functions.ui.AdvBannerActivity;
import com.melon.myapp.functions.ui.AnimationActivity;
import com.melon.myapp.functions.ui.AppActionBarActivity;
import com.melon.myapp.functions.ui.CardViewActivity;
import com.melon.myapp.functions.ui.CollapsingToolbarLayoutActivity;
import com.melon.myapp.functions.ui.ConstraintLayoutActivity;
import com.melon.myapp.functions.ui.CoordinatorLayoutActivity;
import com.melon.myapp.functions.ui.CustomerViewActivity;
import com.melon.myapp.functions.ui.DrawerLayoutActivity;
import com.melon.myapp.functions.ui.FlowLayoutActivity;
import com.melon.myapp.functions.ui.FullScreenImageActivity;
import com.melon.myapp.functions.ui.ImageViewPressActivity;
import com.melon.myapp.functions.ui.LikeActivity;
import com.melon.myapp.functions.ui.MaterialDesignActivity;
import com.melon.myapp.functions.ui.NavigationActivity;
import com.melon.myapp.functions.ui.NavigationViewActivity;
import com.melon.myapp.functions.ui.ProgressActivity;
import com.melon.myapp.functions.ui.PullRefreshActivity;
import com.melon.myapp.functions.ui.RecycleViewActivity;
import com.melon.myapp.functions.ui.RecyclerViewShowWayActivity;
import com.melon.myapp.functions.ui.ScaleImageViewActivity;
import com.melon.myapp.functions.ui.ScrollListViewActivity;
import com.melon.myapp.functions.ui.SnackbarActivity;
import com.melon.myapp.functions.ui.StatusBarActivity;
import com.melon.myapp.functions.ui.TextSwitcherActivity;
import com.melon.myapp.functions.ui.ToolbarActivity;
import com.melon.myapp.functions.ui.TourGuideActivity;
import com.melon.myapp.functions.ui.ViewFlipperActivity;
import com.melon.myapp.functions.ui.WxProgressBarActivity;
import com.melon.myapp.functions.ui.WxWebProgressActivity;
import com.melon.myapp.functions.ui.XRecyclerViewActivity;
import com.melon.myapp.functions.wifi.ShowWifiInfoActivity;
import com.melon.myapp.third.expandablegridrecycleview.Item;
import com.melon.myapp.third.expandablegridrecycleview.ItemClickListener;
import com.melon.myapp.third.expandablegridrecycleview.Section;
import com.melon.myapp.third.expandablegridrecycleview.SectionedExpandableLayoutHelper;
import com.melon.mylibrary.util.CommonUtil;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 学习记录
 */
public class StudyFragment extends BaseFragment implements ItemClickListener {
    @BindView(R.id.recycler_view_study)
    RecyclerView mRecyclerView;

    Context mContext;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_study, container, false);
    }

    @Override
    protected void initData() {
        mContext = getContext();

        SectionedExpandableLayoutHelper sectionedExpandableLayoutHelper = new SectionedExpandableLayoutHelper(getContext(), mRecyclerView, this, 3);

        //数据库
        ArrayList<Item> arrayList = new ArrayList<>();
        arrayList.add(new Item("greenDao", 0));
        sectionedExpandableLayoutHelper.addSection("数据库", arrayList);

        //硬件信息
        arrayList = new ArrayList<>();
        arrayList.add(new Item("Wifi列表", 1));
        arrayList.add(new Item("摇一摇", 2));
        arrayList.add(new Item("Beacon", 3));
        arrayList.add(new Item("屏幕信息", 4));
        arrayList.add(new Item("最大内存", 5));
        arrayList.add(new Item("扫一扫Zbar", 35));
        arrayList.add(new Item("扫一扫Zxing", 36));
        arrayList.add(new Item("File位置", 43));
        sectionedExpandableLayoutHelper.addSection("硬件", arrayList);

        //UI界面
        arrayList = new ArrayList<>();
        arrayList.add(new Item("进度条", 6));
        arrayList.add(new Item("导航", 7));
        arrayList.add(new Item("侧滑", 8));
        arrayList.add(new Item("自动换行", 9));
        arrayList.add(new Item("ActionBar", 10));
        arrayList.add(new Item("沉浸式状态栏", 11));
        arrayList.add(new Item("下拉刷新", 12));
        arrayList.add(new Item("Toolbar", 13));
        arrayList.add(new Item("RecycleView", 14));
        arrayList.add(new Item("动画", 15));
        arrayList.add(new Item("NavigationView", 16));
        arrayList.add(new Item("CardView", 17));
        arrayList.add(new Item("SnackBar\nFloatingActBtn", 18));
        arrayList.add(new Item("广告轮播", 19));
        arrayList.add(new Item("ViewFlipper", 20));
        arrayList.add(new Item("Twitter登录", 21));
        arrayList.add(new Item("Like", 22));
        arrayList.add(new Item("自定义下拉刷新", 23));
        arrayList.add(new Item("微信进度", 24));
        arrayList.add(new Item("TextSwither", 25));
        arrayList.add(new Item("Coordinator\nAppBar", 26));
        arrayList.add(new Item("CollapsingToolbar", 27));
        arrayList.add(new Item("图片按下状态", 28));
        arrayList.add(new Item("沉浸式图片", 29));
        arrayList.add(new Item("添加购物车动画", 30));
        arrayList.add(new Item("贝塞尔曲线添加购物车", 31));
        arrayList.add(new Item("XRecyclerView", 32));
        arrayList.add(new Item("列表网络切换", 33));
        arrayList.add(new Item("微信进度2", 34));
        arrayList.add(new Item("TourGuide向导", 37));
        arrayList.add(new Item("自定义View", 38));
        arrayList.add(new Item("Material Design", 39));
        arrayList.add(new Item("缩放ImageView", 40));
        arrayList.add(new Item("ConstraintLayout约束", 42));
        sectionedExpandableLayoutHelper.addSection("Ui界面", arrayList);

        //网络
        arrayList = new ArrayList<>();
        arrayList.add(new Item("Retrofit", 45));
        sectionedExpandableLayoutHelper.addSection("网络", arrayList);
        //架构
        arrayList = new ArrayList<>();
        arrayList.add(new Item("MVVM", 44));
        sectionedExpandableLayoutHelper.addSection("架构", arrayList);

        //底层
        arrayList = new ArrayList<>();
        arrayList.add(new Item("调用ARR文件", 41));
        sectionedExpandableLayoutHelper.addSection("底层", arrayList);

        sectionedExpandableLayoutHelper.notifyDataSetChanged();
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void itemClicked(Item item) {
        switch (item.getId()) {
            case 0:
                //greenDao
                CommonUtil.enterFragment(mContext, CommonFragmentActivity.class, CommonFragmentActivity.FRAGMENT_GREEN_DAO);
                break;
            case 1:
                // wifi列表
                CommonUtil.enterActivity(mContext, ShowWifiInfoActivity.class);
                break;
            case 2:
                // 摇一摇
                CommonUtil.enterActivity(mContext, ShakeOneShakeActivity.class);
                break;
            case 3:
                //beacon
                CommonUtil.enterActivity(mContext, ShowBeaconsActivity.class);
                break;
            case 4:
                //屏幕分辨率
                CommonUtil.enterActivity(mContext, PhoneDensityActivity.class);
                break;
            case 5:
                //最大内存
                CommonUtil.enterActivity(mContext, MaxMemoryActivity.class);
                break;
            case 6:
                //进度条
                CommonUtil.enterActivity(mContext, ProgressActivity.class);
                break;
            case 7:
                //导航
                CommonUtil.enterActivity(mContext, NavigationActivity.class);
                break;
            case 8:
                //侧滑
                CommonUtil.enterActivity(mContext, DrawerLayoutActivity.class);
                break;
            case 9:
                //自动换行
                CommonUtil.enterActivity(mContext, FlowLayoutActivity.class);
                break;
            case 10:
                //ActionBar
                CommonUtil.enterActivity(mContext, AppActionBarActivity.class);
                break;
            case 11:
                //沉浸式状态栏
                CommonUtil.enterActivity(mContext, StatusBarActivity.class);
                break;
            case 12:
                //下拉刷新
                CommonUtil.enterActivity(mContext, PullRefreshActivity.class);
                break;
            case 13:
                //Toolbar
                CommonUtil.enterActivity(mContext, ToolbarActivity.class);
                break;
            case 14:
                //RecycleView
                CommonUtil.enterActivity(mContext, RecycleViewActivity.class);
                break;
            case 15:
                //动画
                CommonUtil.enterActivity(mContext, AnimationActivity.class);
                break;
            case 16:
                //NavigationView
                CommonUtil.enterActivity(mContext, NavigationViewActivity.class);
                break;
            case 17:
                //CardView
                CommonUtil.enterActivity(mContext, CardViewActivity.class);
                break;
            case 18:
                //Snackbar
                CommonUtil.enterActivity(mContext, SnackbarActivity.class);
                break;
            case 19:
                //广告Banner
                CommonUtil.enterActivity(mContext, AdvBannerActivity.class);
                break;
            case 20:
                //ViewFlipper
                CommonUtil.enterActivity(mContext, ViewFlipperActivity.class);
                break;
            case 21:
                //Twitter登录
                break;
            case 22:
                //Like
                CommonUtil.enterActivity(mContext, LikeActivity.class);
                break;
            case 23:
                //自定义下拉刷新
                CommonUtil.enterActivity(mContext, ScrollListViewActivity.class);
                break;
            case 24:
                //微信进度
                CommonUtil.enterActivity(mContext, WxProgressBarActivity.class);
                break;
            case 25:
                //TextSwitcher
                CommonUtil.enterActivity(mContext, TextSwitcherActivity.class);
                break;
            case 26:
                //Coordinator AppBar
                CommonUtil.enterActivity(mContext, CoordinatorLayoutActivity.class);
                break;
            case 27:
                //CollapsingToolbarLayout标题折叠
                CommonUtil.enterActivity(mContext, CollapsingToolbarLayoutActivity.class);
                break;
            case 28:
                //图片按下
                CommonUtil.enterActivity(mContext, ImageViewPressActivity.class);
                break;
            case 29:
                //沉浸式图片
                CommonUtil.enterActivity(mContext, FullScreenImageActivity.class);
                break;
            case 30:
                //添加购物车动画
                CommonUtil.enterActivity(mContext, AddToCartAnimActivity.class);
                break;
            case 31:
                //贝塞尔曲线添加购物车
                CommonUtil.enterActivity(mContext, AddToCartAnim2Activity.class);
                break;
            case 32:
                //XRecyclerView
                CommonUtil.enterActivity(mContext, XRecyclerViewActivity.class);
                break;
            case 33:
                //列表网络切换
                CommonUtil.enterActivity(mContext, RecyclerViewShowWayActivity.class);
                break;
            case 34:
                //微信进度2
                CommonUtil.enterActivity(mContext, WxWebProgressActivity.class);
                break;
            case 35:
                //zbar
                CommonUtil.enterActivity(mContext, ZBarActivity.class);
                break;
            case 36:
                //zxing
                CommonUtil.enterActivity(mContext, ZxingActivity.class);
                break;
            case 37:
                //TourGuide
                CommonUtil.enterActivity(mContext, TourGuideActivity.class);
                break;
            case 38:
                //自定义View
                CommonUtil.enterActivity(mContext, CustomerViewActivity.class);
                break;
            case 39:
                //Material Design
                CommonUtil.enterActivity(mContext, MaterialDesignActivity.class);
                break;
            case 40:
                //缩放ImageView
                CommonUtil.enterActivity(mContext, ScaleImageViewActivity.class);
                break;
            case 41:
                //调用ARR SDK
                startAar();
                break;
            case 42:
                //约束布局
                CommonUtil.enterActivity(mContext, ConstraintLayoutActivity.class);
                break;
            case 43:
                //File存储的路径
                CommonUtil.enterActivity(mContext, FileStoragePathActivity.class);
                break;
            case 44:
                //MVVM
                CommonUtil.enterActivity(mContext, MvvmActivity.class);
                break;
            case 45:
                //网络
                CommonUtil.enterActivity(mContext, RetrofitActivity.class);
                break;
            default:
        }
    }

    private void startAar() {
        SdkMelon.getInstance().init();
        SdkMelon.getInstance().enterLogin(getActivity());
    }

    @Override
    public void itemClicked(Section section) {
        Toast.makeText(getContext(), section.getName(), Toast.LENGTH_SHORT).show();
    }
}