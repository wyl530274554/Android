package com.melon.myapp.functions.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.melon.myapp.R;

public class CommonFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common,container, false);
        String content = getArguments().getString("content", "");
        TextView tv_common_fragment = (TextView) view.findViewById(R.id.tv_common_fragment);
        tv_common_fragment.setText(content);
        return view;
    }


}