package com.melon.myapp.functions.architecture.mvvm.viewmodel;

import com.melon.myapp.functions.architecture.mvvm.model.ILoginModel;
import com.melon.myapp.functions.architecture.mvvm.model.LoginModel;
import com.melon.myapp.functions.architecture.mvvm.model.NetListener;
import com.melon.myapp.functions.architecture.mvvm.view.ILoginView;
import com.melon.mylibrary.util.CommonUtil;

/**
 * VM 负责业务逻辑    连接View和Model
 *
 * @author melon.wang
 * @date 2019/3/2 20:00
 * Email 530274554@qq.com
 */
public class LoginViewModel {
    ILoginView loginView;
    ILoginModel loginModel;

    public LoginViewModel(ILoginView loginView) {
        this.loginView = loginView;
        loginModel = new LoginModel();
    }

    public void login() {
        String name = loginView.getUserName();
        String pwd = loginView.getPwd();

        if (CommonUtil.isEmpty(name) || CommonUtil.isEmpty(pwd)) {
            loginView.showInputErrorTip();
            return;
        }

        loginModel.netLogin(name, pwd, new NetListener() {

            @Override
            public void onSucc(String tips) {
                loginView.showLoginSuccessTip(tips);
            }

            @Override
            public void onFail() {
                loginView.showLoginFailedTip();
            }

            @Override
            public void onAfter() {
                loginView.dismissProgressDialog();
            }

            @Override
            public void onBefore() {
                loginView.showProgressDialog();
            }
        });
    }
}
