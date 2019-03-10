package com.melon.myapp.functions.architecture.mvvm.model;

/**
 * 提供数据给ViewModel
 *
 * @author melon.wang
 * @date 2019/3/2 18:40
 * Email 530274554@qq.com
 */
public class LoginModel implements ILoginModel {
    @Override
    public void netLogin(String name, String pwd, final NetListener listener) {
        listener.onBefore();
        //模拟登录
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onSucc("用户登录成功");
                listener.onAfter();
            }
        }, 5000);
    }
}
