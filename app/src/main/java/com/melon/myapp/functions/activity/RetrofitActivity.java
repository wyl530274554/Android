package com.melon.myapp.functions.activity;

import android.widget.TextView;

import com.melon.myapp.ApiManager;
import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;
import com.melon.myapp.other.User;
import com.melon.myapp.other.UserService;
import com.melon.mylibrary.util.LogUtils;

import java.io.IOException;

import butterknife.BindView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit请求
 *
 * @author melon.wang
 * @date 2019/3/10 12:02
 * Email 530274554@qq.com
 */
public class RetrofitActivity extends BaseActivity {

    @BindView(R.id.textView4)
    TextView textView4;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_retrofit);
    }

    @Override
    protected void initData() {
        getUserInfoByRetrofit();
    }

    /**
     * 使用Gson解析框架
     */
    private void getUserInfoByRetrofit() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiManager.API_BASE).addConverterFactory(GsonConverterFactory.create()).build();
        retrofit.create(UserService.class).getUserInfo2().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                textView4.setText(response.body().toString());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    /**
     * 无解析json框架
     */
    private void getUserInfoByRetrofit2() {
        new Retrofit.Builder().baseUrl(ApiManager.API_BASE).build().create(UserService.class).getUserInfo().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody body = response.body();
                try {
                    LogUtils.e("body: " + body.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                LogUtils.e("error: " + t.getMessage());
            }
        });
    }
}
