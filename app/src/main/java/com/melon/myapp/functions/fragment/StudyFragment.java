package com.melon.myapp.functions.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.melon.myapp.AndroidUiMainActivity;
import com.melon.myapp.NotificationActivity;
import com.melon.myapp.R;
import com.melon.mylibrary.util.CommonUtil;

/**
 * 学习记录
 */
public class StudyFragment extends Fragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_study,container, false);
        String content ="Android UI";
        try{
            content = getArguments().getString("content", "Android UI");
        }catch (Exception e){}
        Button btAndroid = (Button) view.findViewById(R.id.bt_study_fragment);
        btAndroid.setText(content);

        view.findViewById(R.id.bt_study_fragment_notify).setOnClickListener(this);
        btAndroid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtil.enterActivity(getContext(), AndroidUiMainActivity.class);
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_study_fragment_notify:
                CommonUtil.enterActivity(getContext(), NotificationActivity.class);
                break;
        }
    }
}