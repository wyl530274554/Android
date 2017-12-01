package com.melon.myapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        View view = createView(inflater, container);
        ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    protected abstract View createView(LayoutInflater inflater, ViewGroup container);

    protected abstract void initData();

    protected abstract void initView();
}
