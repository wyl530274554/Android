package com.melon.myapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.melon.myapp.adapter.MainViewPagerAdapter;
import com.melon.myapp.functions.camera.ZBarActivity;
import com.melon.myapp.functions.fragment.BrowserFragment;
import com.melon.myapp.functions.fragment.NoteFragment;
import com.melon.myapp.functions.fragment.NotificationFragment;
import com.melon.myapp.functions.fragment.PasswordFragment;
import com.melon.myapp.functions.fragment.StudyFragment;
import com.melon.myapp.functions.fragment.WebsiteGuideFragment;
import com.melon.myapp.functions.h5.WebActivity;
import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.ToastUtil;
import com.nineoldandroids.view.ViewHelper;

/**
 * 主界面
 *
 * @author melon.wang
 * @date 2018/8/21
 */
public class MainActivity extends BaseActivity {
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        CommonUtil.setTransparentStateBar(this);

        mToolbar = findViewById(R.id.toolBar);
        //设置ToolBar的title颜色
        mToolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(mToolbar);

        ViewPager mViewPager = findViewById(R.id.viewpager);
        MainViewPagerAdapter viewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new BrowserFragment(), "主页");
        viewPagerAdapter.addFragment(new PasswordFragment(), "密码本");
        viewPagerAdapter.addFragment(new NoteFragment(), "记事本");
        viewPagerAdapter.addFragment(WebsiteGuideFragment.newInstance(), "网址导航");
        viewPagerAdapter.addFragment(new StudyFragment(), "学习记录");
        viewPagerAdapter.addFragment(new NotificationFragment(), "通知");
        //设置适配器
        mViewPager.setAdapter(viewPagerAdapter);

        TabLayout mTabLayout = findViewById(R.id.tabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText("主页"));
        mTabLayout.addTab(mTabLayout.newTab().setText("密码本"));
        mTabLayout.addTab(mTabLayout.newTab().setText("记事本"));
        mTabLayout.addTab(mTabLayout.newTab().setText("网址导航"));
        mTabLayout.addTab(mTabLayout.newTab().setText("学习记录"));
        mTabLayout.addTab(mTabLayout.newTab().setText("通知"));
        //给TabLayout设置关联ViewPager，如果设置了ViewPager，那么ViewPagerAdapter中的getPageTitle()方法返回的就是Tab上的标题
        mTabLayout.setupWithViewPager(mViewPager);

        initDrawLayout();
    }

    private void initDrawLayout() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                mDrawerToggle.onDrawerSlide(drawerView, slideOffset);

                slide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                //开关状态改为opened
                mDrawerToggle.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                //开关状态改为closed
                mDrawerToggle.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                mDrawerToggle.onDrawerStateChanged(newState);
            }
        });
        mDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, mToolbar, R.string.text_open, R.string.text_close);

        //Navigation View
        NavigationView navigationViewMain = findViewById(R.id.navigation_view_main);
        View headerView = navigationViewMain.getHeaderView(0);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.toast(getApplicationContext(), "HeaderView");
            }
        });

        navigationViewMain.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //在这里处理item的点击事件
                switch (item.getItemId()) {
                    case R.id.menu_navigation_home:
                        break;
                    case R.id.menu_navigation_setting:
                        CommonUtil.enterActivity(getApplicationContext(), SettingActivity.class);
                        break;
                    case R.id.menu_navigation_about:
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showAboutDialog();
                            }
                        }, 300);
                        break;
                    default:
                }

                mDrawerLayout.closeDrawers();
                return true;
            }
        });

    }

    private void showAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("提示");
        builder.setMessage("一切解释权归本人所有...\n电话: 18321152272");
        builder.setPositiveButton("确定", null);
        Dialog dialog = builder.create();
        dialog.show();
    }

    private void slide(View drawerView, float slideOffset) {
        View mContent = mDrawerLayout.getChildAt(0);
        float scale = 1 - slideOffset;
        float rightScale = 0.8f + scale * 0.2f;

        if (getString(R.string.tag_left).equals(drawerView.getTag())) {
            ViewHelper.setTranslationX(mContent, drawerView.getMeasuredWidth() * (1 - scale));
            mContent.invalidate();
        } else {
            ViewHelper.setTranslationX(mContent, -drawerView.getMeasuredWidth() * slideOffset);
            ViewHelper.setPivotX(mContent, mContent.getMeasuredWidth());
            ViewHelper.setPivotY(mContent, mContent.getMeasuredHeight() / 2);
            mContent.invalidate();
            ViewHelper.setScaleX(mContent, rightScale);
            ViewHelper.setScaleY(mContent, rightScale);
        }
    }

    /**
     * activity创建完成后
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();//该方法会自动和actionBar关联, 将开关的图片显示在了action上，如果不设置，也可以有抽屉的效果，不过是默认的图标
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu_coordinator, menu);


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSubmitButtonEnabled(true);

        SearchableInfo searchableInfo = null;
        if (searchManager != null) {
            searchableInfo = searchManager.getSearchableInfo(new ComponentName(this, WebActivity.class));
        }
        searchView.setSearchableInfo(searchableInfo);

        //扫一扫
        menu.findItem(R.id.scan).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                CommonUtil.enterActivity(mContext, ZBarActivity.class);
                return false;
            }
        });
        return true;
    }

    @Override
    protected void initData() {
        setSlideRight(false);
    }
}
