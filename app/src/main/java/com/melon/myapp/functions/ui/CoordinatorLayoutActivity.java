package com.melon.myapp.functions.ui;

import android.app.SearchManager;
import android.app.SearchableInfo;
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
import com.melon.myapp.functions.fragment.BrowserFragment;
import com.melon.myapp.functions.fragment.StudyFragment;
import com.melon.myapp.functions.fragment.WebsiteGuideFragment;

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
        MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
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

        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
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


class MyViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragments = new ArrayList<>();//添加的Fragment的集合
    private final List<String> mFragmentsTitles = new ArrayList<>();//每个Fragment对应的title的集合

    public MyViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * @param fragment      添加Fragment
     * @param fragmentTitle Fragment的标题，即TabLayout中对应Tab的标题
     */
    public void addFragment(Fragment fragment, String fragmentTitle) {
        mFragments.add(fragment);
        mFragmentsTitles.add(fragmentTitle);
    }

    @Override
    public Fragment getItem(int position) {
        //得到对应position的Fragment
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        //返回Fragment的数量
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //得到对应position的Fragment的title
        return mFragmentsTitles.get(position);
    }

}
