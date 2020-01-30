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
                showSearchContent(content);
            }
        });
        mHomeViewModel.getUrl().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String url) {
                enterWeb(url);
            }
        });
    }

    private void showSearchContent(@Nullable String content) {
        etHomeSearch.setText(content);
    }

    private void enterWeb(String url) {
        CommonUtil.enterActivity(getActivity(), WebActivity.class, "url", url);
    }

    @OnClick(R.id.iv_home_del)
    void onClick(View view) {
        mHomeViewModel.setText("");
    }

    @OnEditorAction(R.id.et_home_search)
    boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            mHomeViewModel.setUrl(etHomeSearch.getText().toString().trim());
            return true;
        }
        return false;
    }
}