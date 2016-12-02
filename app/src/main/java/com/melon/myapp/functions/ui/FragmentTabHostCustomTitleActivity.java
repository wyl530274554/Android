package com.melon.myapp.functions.ui;

import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.melon.myapp.BaseFragmentActivity;
import com.melon.myapp.R;
import com.melon.myapp.functions.fragment.StudyFragment;

public class FragmentTabHostCustomTitleActivity extends BaseFragmentActivity {
    private String[] titles = {"新闻", "音乐", "人生"};
    private int[] imgs = {R.drawable.ic_menu_copy_holo_light,R.drawable.ic_menu_cut_holo_light,R.drawable.ic_menu_edit};
    private FragmentTabHost mTabHost = null;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_fragmenttabhost);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
    }

    @Override
    protected void initData() {
        for (int i = 0; i < titles.length; i++) {
            mTabHost.addTab(mTabHost.newTabSpec(i+"").setIndicator(getTabTitleView(i)), StudyFragment.class, putContent(titles[i]));
            //设置Tab按钮的背景
            mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_item_title_bg);
        }
    }
    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabTitleView(int index){
        View view = View.inflate(this,R.layout.tab_item_title_view, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.iv_tab_item_title);
        imageView.setImageResource(imgs[index]);

        TextView textView = (TextView) view.findViewById(R.id.tv_tab_item_title);
        textView.setText(titles[index]);

        return view;
    }
    private Bundle putContent(String content) {
        Bundle bundle = new Bundle();
        bundle.putString("content", content);
        return bundle;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTabHost = null;
    }
}
