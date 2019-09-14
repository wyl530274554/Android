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
 *
 * 1.采用FragmentStatePagerAdapter
 * FragmentStatePagerAdapter和FragmentPagerAdapter的主要区别是：
 * FragmentStatePagerAdapter会及时回收fragment
 * 而FragmentPagerAdapter会把fragment一直放在内存当中
 * 那么在当前需求下肯定是要用FragmentStatePagerAdapter比较合适
 * 2.FragmentStatePagerAdapter设置contentVp.setOffscreenPageLimit(1);
 * 这个设置是防止viewpager缓存过多的fragment但是不用设置成0
 * 因为设置0默认最小值1
 * 3.懒加载的问题上一篇说过了：
 * https://blog.csdn.net/random_7474/article/details/81205513
 * 4.需要特别注意的一点，虽然很小的细节但是坑了我足有两天···
 * 我们通常喜欢把所有的Fragment new出来然后放在一个list里面
 * 在adapter的getItem(int position)的时候在用list.get(position)来返回fragment
 * 在这个需求下面是不行的，会导致fragment无法回收一直占用内存，我现在的方案是：
 * 在adapter的getItem(int position)的时候直接return NewsContentFragment.getInstance()
 * 也就是直接new
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
                return new StudyFragment();
//            case 3:
//                return WebsiteGuideFragment.newInstance();
//            case 4:
//                return new StudyFragment();
//            case 5:
//                return new NotificationFragment();
            default:
        }
        return null;
    }

    @Override
    public int getCount() {
        //返回Fragment的数量
        return mFragmentsTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //得到对应position的Fragment的title
        return mFragmentsTitles.get(position);
    }
}
