package com.melon.app.ui.home;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.melon.app.R;
import com.melon.app.ui.activity.WebActivity;
import com.melon.mylibrary.BaseFragment;
import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.Constants;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.et_home_search)
    EditText etHomeSearch;
    @BindView(R.id.iv_home_del)
    ImageView ivHomeDel;

    private HomeViewModel mHomeViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {
        mHomeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        mHomeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String content) {
                etHomeSearch.setText(content);
            }
        });
    }

    @OnClick(R.id.iv_home_del)
    void onClick(View view) {
        mHomeViewModel.setText("");
    }

    @OnEditorAction(R.id.et_home_search)
    boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String content = textView.getText().toString().trim();
            CommonUtil.enterActivity(getActivity(), WebActivity.class, "url", Constants.URL_BAI_DU + content);
            return true;
        }
        return false;
    }
}