package com.melon.myapp.functions.ui;

import android.renderscript.Script;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.melon.myapp.BaseFragment;
import com.melon.mylibrary.util.CommonUtil;

/**
 * Created by melon on 2017/9/15.
 * Email 530274554@qq.com
 */

public class TextFragment extends BaseFragment {
    @Override
    protected void initData() {

    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        TextView textView = new TextView(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);

        String content = getArguments().getString("content");
        if(!CommonUtil.isEmpty(content))
            textView.setText(content +"    默认文本");
        return textView;
    }

    @Override
    public void onClick(View v) {

    }
}
