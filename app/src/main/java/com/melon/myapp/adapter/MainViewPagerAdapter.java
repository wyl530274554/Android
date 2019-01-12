package com.melon.myapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.melon.myapp.functions.fragment.BrowserFragment;
import com.melon.myapp.functions.fragment.NoteFragment;
import com.melon.myapp.functions.fragment.NotificationFragment;
import com.melon.myapp.functions.fragment.PasswordFragment;
import com.melon.myapp.functions.fragment.StudyFragment;
import com.melon.myapp.functions.fragment.WebsiteGuideFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/12/19.
 * Email 530274554@qq.com
 * 注：如果用FragmentPagerAdapter则容易发生内存泄漏问题
 */

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {
    //每个Fragment对应的title的集合
    private List<String> mFragmentsTitles = new ArrayList<>();

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * @param fragmentTitle Fragment的标题，即TabLayout中对应Tab的标题
     */
    public void addFragment(String fragmentTitle) {
        mFragmentsTitles.add(fragmentTitle);
    }

    @Override
    public Fragment getItem(int position) {
        //得到对应position的Fragment 不用list来保存fragment集，防止内存泄漏
        switch (position) {
            case 0:
                return new BrowserFragment();
            case 1:
                return new PasswordFragment();
            case 2:
                return new NoteFragment();
            case 3:
                return WebsiteGuideFragment.newInstance();
            case 4:
                return new StudyFragment();
            case 5:
                return new NotificationFragment();
            default:
        }
        return null;
    }

    @Override
    public int getCount() {
        //返回Fragment的数量
        return 6;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //得到对应position的Fragment的title
        return mFragmentsTitles.get(position);
    }
}
