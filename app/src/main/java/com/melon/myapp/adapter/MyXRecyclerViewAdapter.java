package com.melon.myapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.melon.myapp.R;

/**
 * Created by melon on 2017/6/26.
 * Email 530274554@qq.com
 */

public class MyXRecyclerViewAdapter extends RecyclerView.Adapter<MyXRecyclerViewAdapter.ViewHolder> {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mContent;

        public ViewHolder(View view) {
            super(view);
            mContent = (TextView) view.findViewById(R.id.tv_note_content);
        }
    }
}
