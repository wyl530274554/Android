package com.melon.myapp.functions.ui;

import android.content.Context;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.BaseFragmentActivity;
import com.melon.myapp.R;
import com.melon.myapp.functions.fragment.CommonFragment;

public class TabLayoutFragmentActivity extends BaseFragmentActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    private String[] titles = {"热门推荐","热门收藏","今日头条"};

    protected void initView() {
        setContentView(R.layout.activity_tab_layout_fragment);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new CustomAdapter(getSupportFragmentManager(),getApplicationContext()));

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    protected void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    private class CustomAdapter extends FragmentPagerAdapter {

        public CustomAdapter(FragmentManager supportFragmentManager, Context applicationContext) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return createFragment(position);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    private Fragment createFragment(int position) {
        CommonFragment fragment = new CommonFragment();
        Bundle args = new Bundle();
        args.putString("content",titles[position]);
        fragment.setArguments(args);
        return fragment;
    }
}
