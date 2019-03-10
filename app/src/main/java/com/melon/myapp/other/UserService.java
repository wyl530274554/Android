package com.melon.myapp.other;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * 获取ip地址
 * @author melon.wang
 * @date 2019/3/7 11:39
 * Email 530274554@qq.com
 */
public interface UserService {
    @GET("index/getUserInfo")
    Call<ResponseBody> getUserInfo();
    @GET("index/getUserInfo")
    Call<User> getUserInfo2();
}
