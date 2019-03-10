package com.melon.myapp.functions.architecture.mvvm.view;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;
import com.melon.myapp.databinding.ActivityMvvmBinding;
import com.melon.myapp.functions.architecture.mvvm.model.User;
import com.melon.myapp.functions.architecture.mvvm.viewmodel.LoginViewModel;
import com.melon.mylibrary.util.ToastUtil;

/**
 * MVVM模式
 * Activity充当View角色，负责展示、接收用户操作，转发给ViewModel
 *
 * @author melon.wang
 * @date 2019/3/2 17:20
 * Email 530274554@qq.com
 */
public class MvvmActivity extends BaseActivity implements ILoginView {

    private ActivityMvvmBinding viewDataBinding;
    private LoginViewModel userViewModel;

    @Override
    protected void initView() {
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm);
        userViewModel = new LoginViewModel(this);

    }

    @Override
    protected void initData() {

    }

    public void onLoginClick(View view) {
        userViewModel.login();

        //模拟输入监测、View和Model自动绑定
        viewDataBinding.setUser(new User(getUserName(),getPwd()));
    }

    @Override
    public String getUserName() {
        return viewDataBinding.et1.getText().toString();
    }

    @Override
    public String getPwd() {
        return viewDataBinding.et2.getText().toString();
    }

    @Override
    public void showProgressDialog() {
        viewDataBinding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgressDialog() {
        viewDataBinding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoginSuccessTip(String tips) {
        ToastUtil.toast(this, tips);
    }

    @Override
    public void showLoginFailedTip() {
        ToastUtil.toast(this, "登录失败");
    }

    @Override
    public void showInputErrorTip() {
        ToastUtil.toast(this, "请输入用户或密码");
    }
}
