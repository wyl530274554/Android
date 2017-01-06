package com.melon.myapp.functions.ui;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;

import java.util.Random;

public class TextSwitcherActivity extends BaseActivity implements ViewSwitcher.ViewFactory {

    @Override
    protected void initView() {
        setContentView(R.layout.activity_text_switcher);

        final TextSwitcher textSwitcher = (TextSwitcher) findViewById(R.id.textSwitcher);

        textSwitcher.setFactory(this);

        Animation in = AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.slide_out_top);
        textSwitcher.setInAnimation(in);
        textSwitcher.setOutAnimation(out);

        Button btnChange = (Button) this.findViewById(R.id.btnChange);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textSwitcher.setText(String.valueOf(new Random().nextInt()));
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public View makeView() {
        TextView textView = new TextView(this);
        textView.setTextSize(36);
        return textView;
    }

    @Override
    public void onClick(View v) {

    }
}
