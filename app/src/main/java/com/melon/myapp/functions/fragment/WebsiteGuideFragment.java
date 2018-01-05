package com.melon.myapp.functions.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melon.myapp.R;
import com.melon.myapp.adapter.WebSiteAdapter;
import com.melon.myapp.bean.Website;

import java.util.ArrayList;
import java.util.List;

/**
 * 网址导航
 */
public class WebsiteGuideFragment extends Fragment {
    private static WebsiteGuideFragment instance = null;
    private List<Website> mWebsites = new ArrayList<>();

    public static WebsiteGuideFragment newInstance() {
        if (instance == null) {
            instance = new WebsiteGuideFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_one, container, false);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(new WebSiteAdapter(getActivity(), mWebsites));

        initData();
        return view;
    }

    private void initData() {
        if (mWebsites.size() > 0) return;
        mWebsites.add(new Website("http://m.news.baidu.com/news?fr=mohome", R.drawable.ic_baidu_news));
        mWebsites.add(new Website("https://m.taobao.com", R.drawable.ic_taobao));
        mWebsites.add(new Website("https://www.jd.com", R.drawable.ic_jd));
        mWebsites.add(new Website("https://www.baidu.com/s?wd=天气", R.drawable.ic_weather));
    }
}
