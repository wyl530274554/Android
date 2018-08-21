package com.melon.myapp.functions.ui;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;
import com.melon.mylibrary.util.CommonUtil;

/**
 * 标签导航
 */
public class NavigationActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private String[] items = {"标准FragmentTabHost","自定义标题FragmentTabHost","TabLayout + ViewPager","TabLayout + ViewPager + Fragment"};
    private Class[] clazz = {FragmentTabhostActivity.class,FragmentTabHostCustomTitleActivity.class,TabLayoutActivity.class, TabLayoutFragmentActivity.class};
    @Override
    protected void initView() {
        setContentView(R.layout.activity_navigation);
        listView = (ListView) findViewById(R.id.lv_navigation);
        listView.setOnItemClickListener(this);
    }
    @Override
    protected void initData() {
        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,items);
        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CommonUtil.enterActivity(this,clazz[position]);
    }
}
