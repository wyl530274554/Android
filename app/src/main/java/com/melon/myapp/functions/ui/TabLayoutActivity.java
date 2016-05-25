package com.melon.myapp.functions.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.melon.myapp.BaseFragmentActivity;
import com.melon.myapp.R;
import com.melon.myapp.functions.fragment.CommonFragment;

public class TabLayoutActivity extends BaseFragmentActivity {
    private String[] titles = {"热门推荐", "热门收藏", "本月热榜"};
    private Fragment[] fragments = new Fragment[titles.length];
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_tab_layout);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        MyAdapter mAdapter = new MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initData() {
        for (int i = 0; i < titles.length; i++) {
            CommonFragment commonFragment = new CommonFragment();
            Bundle bunlde = new Bundle();
            bunlde.putString("content", titles[i]);
            commonFragment.setArguments(bunlde);
            fragments[i] = commonFragment;
        }
    }

    @Override
    public void onClick(View v) {

    }

    private class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {

            return fragments[position];
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
