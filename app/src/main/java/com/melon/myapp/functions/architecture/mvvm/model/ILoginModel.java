package com.melon.myapp.functions.architecture.mvvm.model;
/**
 * M层   数据层  网络层
 * @author melon.wang
 * @date 2019/3/3 11:16
 * Email 530274554@qq.com
 */
public interface ILoginModel {
    void netLogin(String name, String pwd, NetListener listener);
}
