package com.melon.myapp.functions.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.melon.myapp.Constants;
import com.melon.myapp.R;
import com.melon.myapp.functions.h5.HtmlActivity;
import com.melon.mylibrary.util.CommonUtil;

/**
 * 浏览器
 */
public class BrowserFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        Context context = new ContextThemeWrapper(getActivity(), R.style.AppBaseTheme);
//        LayoutInflater localInflater = inflater.cloneInContext(context);

        View view = inflater.inflate(R.layout.fragment_browser, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        final EditText et_browser_search = (EditText) view.findViewById(R.id.et_browser_search);
        Button bt_browser_search = (Button) view.findViewById(R.id.bt_browser_search);
        bt_browser_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //搜索
                String key = et_browser_search.getText().toString().trim();

                if (!CommonUtil.isEmpty(key)) {
                    Intent intent = new Intent(getActivity(), HtmlActivity.class);
                    intent.putExtra("url", Constants.URL_BAIDU + key);
                    startActivity(intent);
                }

            }
        });
        bt_browser_search.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                et_browser_search.setText("");
                return true;
            }
        });
    }


}