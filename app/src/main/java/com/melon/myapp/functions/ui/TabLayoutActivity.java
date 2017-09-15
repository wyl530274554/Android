package com.melon.myapp.functions.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;

import java.util.ArrayList;
import java.util.List;

public class TabLayoutActivity extends BaseActivity {
    private String[] titles = {"热门推荐", "热门收藏", "本月热榜"};
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<View> mViewList = new ArrayList<>();//页卡视图集合

    @Override
    protected void initView() {
        setContentView(R.layout.activity_tab_layout);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        MyAdapter mAdapter = new MyAdapter();
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initData() {
        for (int i = 0; i < titles.length; i++) {
            TextView textView = new TextView(getApplicationContext());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.BLACK);
            textView.setText(titles[i]);
            mViewList.add(textView);
        }
    }

    @Override
    public void onClick(View v) {

    }

    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));//添加页卡
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));//删除页卡
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
