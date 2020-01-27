package com.melon.app.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.melon.app.R;
import com.melon.app.ui.web.WebActivity;
import com.melon.mylibrary.util.Constants;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final EditText etSearch = root.findViewById(R.id.et_home_search);
        final ImageView ivDel = root.findViewById(R.id.iv_home_del);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                etSearch.setText(s);
            }
        });

        ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeViewModel.setText("");
            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String content = etSearch.getText().toString().trim();
                    if (!TextUtils.isEmpty(content)) {
                        enterWeb(content);
                    }
                    return true;
                }
                return false;
            }
        });
        return root;
    }

    private void enterWeb(String content) {
        if(!content.startsWith(Constants.NET_PROTOCOL_HTTP)) {
            content = Constants.URL_BAI_DU + content;
        }
        Intent intent = new Intent(getActivity(), WebActivity.class);
        intent.putExtra("url", content);
        startActivity(intent);
    }
}