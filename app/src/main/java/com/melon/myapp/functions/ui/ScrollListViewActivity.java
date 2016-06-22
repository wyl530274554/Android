package com.melon.myapp.functions.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.melon.myapp.R;
import com.melon.mylibrary.view.MyListView;

public class ScrollListViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list_view);

        MyListView mListView= (MyListView) findViewById(R.id.my_list_view);
        String[] names = {"1","2","3"};
        ArrayAdapter<String> mAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,names);
        mListView.setAdapter(mAdapter);
    }
}
