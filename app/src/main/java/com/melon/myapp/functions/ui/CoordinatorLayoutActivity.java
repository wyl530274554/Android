package com.melon.myapp.functions.ui;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;
import com.melon.myapp.adapter.MainViewPagerAdapter;
import com.melon.myapp.functions.fragment.BrowserFragment;
import com.melon.myapp.functions.fragment.StudyFragment;
import com.melon.myapp.functions.fragment.WebsiteGuideFragment;
import com.melon.myapp.functions.h5.HtmlActivity;
import com.melon.mylibrary.util.LogUtils;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;


public class CoordinatorLayoutActivity extends BaseActivity {
    ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_coordinator_layout);

        mToolbar = (Toolbar) findViewById(R.id.toolBar);
        mToolbar.setTitleTextColor(Color.WHITE);//设置ToolBar的title颜色
//        mToolbar.setNavigationIcon(R.drawable.ic_menu_selectall_holo_light);//设置导航栏图标

        //Toolbar高度+状态栏高度(不设置，图标会变形)
        mToolbar.getLayoutParams().height = getAppBarHeight() + 50;
        setSupportActionBar(mToolbar);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);
        MainViewPagerAdapter viewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new BrowserFragment(), "主页");
        viewPagerAdapter.addFragment(WebsiteGuideFragment.newInstance(), "网址导航");//添加Fragment
        viewPagerAdapter.addFragment(new StudyFragment(), "学习记录");
        mViewPager.setAdapter(viewPagerAdapter);//设置适配器

        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText("主页"));//给TabLayout添加Tab
        mTabLayout.addTab(mTabLayout.newTab().setText("网址导航"));//给TabLayout添加Tab
        mTabLayout.addTab(mTabLayout.newTab().setText("学习记录"));
        mTabLayout.setupWithViewPager(mViewPager);//给TabLayout设置关联ViewPager，如果设置了ViewPager，那么ViewPagerAdapter中的getPageTitle()方法返回的就是Tab上的标题

        initDrawLayout();
    }

    private void initDrawLayout() {
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
                LogUtils.e("slideOffset: "+slideOffset);
//                silde(drawerView, slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                mDrawerToggle.onDrawerOpened(drawerView);//开关状态改为opened
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                mDrawerToggle.onDrawerClosed(drawerView);//开关状态改为closed
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                mDrawerToggle.onDrawerStateChanged(newState);
            }
        });
        mDrawerToggle = new ActionBarDrawerToggle(CoordinatorLayoutActivity.this, mDrawerLayout, mToolbar, R.string.text_open, R.string.text_close);
    }

    private void silde(View drawerView, float slideOffset) {
        View mContent = mDrawerLayout.getChildAt(0);
        View mMenu = drawerView;
        float scale = 1 - slideOffset;
        float rightScale = 0.8f + scale * 0.2f;

        if (drawerView.getTag().equals("LEFT")) {
            ViewHelper.setTranslationX(mContent, mMenu.getMeasuredWidth() * (1 - scale));
            mContent.invalidate();
        } else {
            ViewHelper.setTranslationX(mContent,  -mMenu.getMeasuredWidth() * slideOffset);
            ViewHelper.setPivotX(mContent, mContent.getMeasuredWidth());
            ViewHelper.setPivotY(mContent, mContent.getMeasuredHeight() / 2);
            mContent.invalidate();
            ViewHelper.setScaleX(mContent, rightScale);
            ViewHelper.setScaleY(mContent, rightScale);
        }

//                if (drawerView.getTag().equals("LEFT")) {
//                    float leftScale = 1 - 0.3f * scale;
//                    ViewHelper.setScaleX(mMenu, leftScale);
//                    ViewHelper.setScaleY(mMenu, leftScale);
//                    ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
//                    ViewHelper.setTranslationX(mContent,
//                            mMenu.getMeasuredWidth() * (1 - scale));
//                    ViewHelper.setPivotX(mContent, 0);
//                    ViewHelper.setPivotY(mContent,
//                            mContent.getMeasuredHeight() / 2);
//                    mContent.invalidate();
//                    ViewHelper.setScaleX(mContent, rightScale);
//                    ViewHelper.setScaleY(mContent, rightScale);
//                } else {
//                    ViewHelper.setTranslationX(mContent,
//                            -mMenu.getMeasuredWidth() * slideOffset);
//                    ViewHelper.setPivotX(mContent, mContent.getMeasuredWidth());
//                    ViewHelper.setPivotY(mContent,
//                            mContent.getMeasuredHeight() / 2);
//                    mContent.invalidate();
//                    ViewHelper.setScaleX(mContent, rightScale);
//                    ViewHelper.setScaleY(mContent, rightScale);
//                }
    }

    /**
     * activity创建完成后
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();//该方法会自动和actionBar关联, 将开关的图片显示在了action上，如果不设置，也可以有抽屉的效果，不过是默认的图标
    }

    private int getAppBarHeight() {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }

        return actionBarHeight;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu_coordinator, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSubmitButtonEnabled(true);

//        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(new ComponentName(this, HtmlActivity.class));
        searchView.setSearchableInfo(searchableInfo);
        return true;
    }

    @Override
    protected void initData() {
        List<String> myArrayList = new ArrayList<>();
        myArrayList.add("首页");
        myArrayList.add("发现");
        myArrayList.add("购物车");
        myArrayList.add("关于");
        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myArrayList);
        mDrawerList.setAdapter(myArrayAdapter);
    }

    @Override
    public void onClick(View v) {

    }

}
