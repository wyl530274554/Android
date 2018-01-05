package com.melon.myapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.melon.myapp.R;
import com.melon.myapp.bean.Website;
import com.melon.myapp.functions.h5.HtmlActivity;
import com.melon.mylibrary.util.CommonUtil;

import java.util.List;
import java.util.Random;

/**
 * Created by admin on 2017/1/5.
 * Email 530274554@qq.com
 */

public class WebSiteAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private List<Website> websites;

    public WebSiteAdapter(Context context, List<Website> websites) {
        this.context = context;
        this.websites = websites;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_fragment_one, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Website website = websites.get(position);

        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = CommonUtil.getPicHeight(context, website.getImg()) + CommonUtil.dip2px(context, new Random().nextInt(50) + 70);
        holder.itemView.setLayoutParams(params);
//            LogUtils.e("params.height: " + params.height);
        holder.mImageView.setImageResource(website.getImg());

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HtmlActivity.class);
                intent.putExtra("url", website.getUrl());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
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
