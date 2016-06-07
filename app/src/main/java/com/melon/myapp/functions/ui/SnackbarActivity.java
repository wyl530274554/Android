package com.melon.myapp.functions.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.melon.myapp.R;

public class SnackbarActivity extends Activity {
    CoordinatorLayout container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snackbar);

        container = (CoordinatorLayout) findViewById(R.id.container);

        findViewById(R.id.btnFloatingAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    public void createSnackbar(View v) {
        Snackbar.make(container, "SnackbarTest",Snackbar.LENGTH_SHORT).setAction("Action", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(container,"ActionClick",Snackbar.LENGTH_SHORT).show();
            }
        }).show();
    }
}
