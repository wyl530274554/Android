package com.melon.app.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.melon.app.R;
import com.melon.app.ui.view.MelonWebView;
import com.melon.mylibrary.util.SpUtil;

import java.util.ArrayList;

public class WebActivity extends AppCompatActivity {

    private MelonWebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        mWebView = findViewById(R.id.wv_web);
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle("搜索");
        actionBar.setDisplayHomeAsUpEnabled(true);

        String mUrl = getIntent().getStringExtra("url");
        mWebView.loadUrl(mUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_collect:
                collectWeb();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void collectWeb() {
        String url = mWebView.getCurrentUrl();
        if (TextUtils.isEmpty(url)) return;
        String collects = SpUtil.getString(getApplicationContext(), "webCollect");
        Gson gson = new Gson();
        ArrayList<String> items = gson.fromJson(collects, new TypeToken<ArrayList<String>>() {
        }.getType());

        if (items == null) items = new ArrayList<>();
        items.add(url);
        SpUtil.setString(getApplicationContext(), "webCollect", gson.toJson(items));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
