package com.melon.myapp.functions.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.melon.myapp.R;
import com.melon.myapp.bean.Website;
import com.melon.myapp.functions.h5.HtmlActivity;
import com.melon.mylibrary.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        mRecyclerView.setAdapter(new MyRecyclerViewAdapter(getActivity(), mWebsites));

        initData();
        return view;
    }

    private void initData() {
        if (mWebsites.size() > 0) return;
        mWebsites.add(new Website("http://m.news.baidu.com/news?fr=mohome&ssid=0&from=&uid=&pu=sz%40320_1004%2Cta%40iphone_2_6.0_11_8.2&bd_page_type=1#/?_k=g2h3wk", R.drawable.ic_baidu_news));
        mWebsites.add(new Website("http://3g.163.com/touch/news/subchannel/all", R.drawable.ic_163_news));
        mWebsites.add(new Website("https://m.taobao.com", R.drawable.ic_taobao));
        mWebsites.add(new Website("https://www.jd.com", R.drawable.ic_jd));
    }


    public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private Context context;
        private List<Website> websites;

        public MyRecyclerViewAdapter(Context context, List<Website> websites) {
            this.context = context;
            this.websites = websites;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_item_fragment_one, parent, false);
            MyViewHolder viewHolder = new MyViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final Website website = mWebsites.get(position);

            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            params.height = CommonUtil.getPicHeight(context, website.getImg()) + CommonUtil.dip2px(context, new Random().nextInt(50) + 100);
            holder.itemView.setLayoutParams(params);
//            LogUtils.e("params.height: " + params.height);
            holder.mImageView.setImageResource(website.getImg());

            holder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), HtmlActivity.class);
                    intent.putExtra("url", website.getUrl());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return websites.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageButton mImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageButton) itemView.findViewById(R.id.iv_item_fragment_one_img);
        }
    }
}
