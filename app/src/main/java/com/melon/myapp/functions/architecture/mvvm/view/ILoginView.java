package com.melon.myapp.functions.architecture.mvvm.view;
/**
 * V层   负责展示层
 * @author melon.wang
 * @date 2019/3/3 10:52
 * Email 530274554@qq.com
 */
public interface ILoginView {
    /**
     * 用户界面操作
     */
    String getUserName();
    String getPwd();

    /**
     * 界面展示
     */
    void showProgressDialog();
    void dismissProgressDialog();
    void showLoginSuccessTip(String tips);
    void showLoginFailedTip();
    void showInputErrorTip();
}
