package com.melon.myapp.functions.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.melon.myapp.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentOne extends Fragment {
    private static FragmentOne instance = null;

    public static FragmentOne newInstance() {
        if (instance == null) {
            instance = new FragmentOne();
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
        List<Integer> datas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            datas.add(i);
        }
        mRecyclerView.setAdapter(new MyRecyclerViewAdapter(getActivity(), datas));
        return view;
    }




    public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private List<Integer> datas;
        private Context context;
        private List<Integer> lists;

        public MyRecyclerViewAdapter(Context context, List<Integer> datas) {
            this.datas = datas;
            this.context = context;
            getRandomHeights(datas);
        }

        private void getRandomHeights(List<Integer> datas) {
            lists = new ArrayList<>();
            for (int i = 0; i < datas.size(); i++) {
                lists.add((int) (200 + Math.random() * 400));
            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_item_fragment_one, parent, false);
            MyViewHolder viewHolder = new MyViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            params.height = lists.get(position);//把随机的高度赋予item布局
            holder.itemView.setLayoutParams(params);
            holder.mTextView.setText(position+"");
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        public MyViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.item_tv);
        }
    }
}
