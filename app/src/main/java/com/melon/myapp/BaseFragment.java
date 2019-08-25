package com.melon.myapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melon.mylibrary.util.LogUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment{
    public Handler mHandler = new Handler();
    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.e("onCreateView fragment: "+this.getClass().getSimpleName());
        View view = createView(inflater, container);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    public Context getContext(){
        return MyApplication.getInstance();
    }

    protected abstract View createView(LayoutInflater inflater, ViewGroup container);

    protected abstract void initData();

    protected abstract void initView();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.e("onDestroyView fragment: "+this.getClass().getSimpleName());
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e("onDestroy fragment: "+this.getClass().getSimpleName());
    }
}
