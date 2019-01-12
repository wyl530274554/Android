package com.melon.myapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melon.mylibrary.util.LogUtils;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    public Handler mHandler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.e("onCreateView fragment: "+this.getClass().getSimpleName());
        View view = createView(inflater, container);
        ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    protected abstract View createView(LayoutInflater inflater, ViewGroup container);

    protected abstract void initData();

    protected abstract void initView();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.e("onDestroyView fragment: "+this.getClass().getSimpleName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e("onDestroy fragment: "+this.getClass().getSimpleName());
    }
}
