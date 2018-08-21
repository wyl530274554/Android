package com.melon.myapp.functions.ui;

import android.view.View;
import android.widget.Button;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;

import butterknife.BindView;
import butterknife.OnClick;
import tourguide.tourguide.Overlay;
import tourguide.tourguide.Pointer;
import tourguide.tourguide.ToolTip;
import tourguide.tourguide.TourGuide;

/**
 * Created by melon on 2017/9/25.
 * Email 530274554@qq.com
 */

public class TourGuideActivity extends BaseActivity {
    @BindView(R.id.button5)
    Button button5;
    private TourGuide mTourGuideHandler;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_tour_guide);
    }

    private void initGuide() {
        mTourGuideHandler = TourGuide.init(this).with(TourGuide.Technique.Click)
                .setPointer(new Pointer())
                .setToolTip(new ToolTip().setTitle("Welcome!").setDescription("Click on Get Started to begin..."))
                .setOverlay(new Overlay())
                .playOn(button5);
    }

    @Override
    protected void initData() {
        initGuide();
    }

    @OnClick({R.id.button5})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button5:
                mTourGuideHandler.cleanUp();
                break;
        }
    }
}
