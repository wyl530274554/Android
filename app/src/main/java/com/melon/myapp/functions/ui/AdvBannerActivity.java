package com.melon.myapp.functions.ui;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.melon.myapp.ApiManager;
import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;
import com.melon.myapp.bean.Image;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class AdvBannerActivity extends BaseActivity {
    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    private List<Image> mImages = new ArrayList<>();
    private ConvenientBanner convenientBanner;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_adv_banner);
    }

    @Override
    protected void initData() {
//        init();
        init2();
    }

    private void init2() {
        OkHttpUtils.get().url(ApiManager.API_GET_ALL_IMAGE).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                mImages = new Gson().fromJson(response, new TypeToken<List<Image>>() {
                }.getType());

                convenientBanner = findViewById(R.id.convenientBanner);
                convenientBanner.setPages(
                        new CBViewHolderCreator<LocalImageHolderView2>() {
                            @Override
                            public LocalImageHolderView2 createHolder() {
                                return new LocalImageHolderView2();
                            }
                        }, mImages)
                        //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                        .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                        //设置指示器的方向
                        .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
            }
        });
    }

    private void init() {
        localImages.add(R.mipmap.ic_1);
        localImages.add(R.mipmap.ic_2);
        localImages.add(R.mipmap.ic_3);

        convenientBanner = findViewById(R.id.convenientBanner);
        convenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
    }

    public class LocalImageHolderView implements Holder<Integer> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, Integer data) {
            imageView.setImageResource(data);
        }
    }

    public class LocalImageHolderView2 implements Holder<Image> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, Image data) {
            mImageLoader.displayImage(data.getUrl(),imageView);
        }

    }

    // 开始自动翻页
    @Override
    protected void onResume() {
        super.onResume();
        //开始自动翻页
//        convenientBanner.startTurning(5000);
    }

    // 停止自动翻页
    @Override
    protected void onPause() {
        super.onPause();
        //停止翻页
        convenientBanner.stopTurning();
    }
}
