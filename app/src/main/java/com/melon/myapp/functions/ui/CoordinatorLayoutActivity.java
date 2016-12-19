package com.melon.myapp.functions.ui;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;
import com.melon.myapp.adapter.MainViewPagerAdapter;
import com.melon.myapp.functions.fragment.BrowserFragment;
import com.melon.myapp.functions.fragment.StudyFragment;
import com.melon.myapp.functions.fragment.WebsiteGuideFragment;
import com.melon.myapp.functions.h5.HtmlActivity;
import com.melon.mylibrary.util.LogUtils;
import com.melon.mylibrary.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;


public class CoordinatorLayoutActivity extends BaseActivity {
    @Override
    protected void initView() {
        setContentView(R.layout.activity_coordinator_layout);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolBar);
        mToolbar.setTitleTextColor(Color.WHITE);//设置ToolBar的title颜色

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

    }

    @Override
    public void onClick(View v) {

    }

}
