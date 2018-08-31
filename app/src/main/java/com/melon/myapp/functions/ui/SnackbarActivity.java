package com.melon.myapp.functions.ui;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;

public class SnackbarActivity extends BaseActivity {
    CoordinatorLayout container;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_snackbar);

        container = (CoordinatorLayout) findViewById(R.id.container);

        findViewById(R.id.btnFloatingAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    protected void initData() {

    }

    public void createSnackbar(View v) {
        Snackbar.make(container, "SnackbarTest SnackbarTest SnackbarTest SnackbarTest SnackbarTest SnackbarTest", Snackbar.LENGTH_INDEFINITE).setAction("Action", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(container, "ActionClickActionClickActionClickActionClickActionClickActionClickActionClickActionClickActionClick", Snackbar.LENGTH_LONG).show();
            }
        }).show();
    }

}
