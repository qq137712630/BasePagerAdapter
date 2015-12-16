package com.nshmura.recyclertablayout.demo.basic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.nshmura.recyclertablayout.RecyclerTabLayout;
import com.nshmura.recyclertablayout.demo.ColorItem;
import com.nshmura.recyclertablayout.demo.Demo;
import com.nshmura.recyclertablayout.demo.DemoColorPagerAdapter;
import com.nshmura.recyclertablayout.demo.R;
import com.nshmura.recyclertablayout.demo.utils.DemoData;
import com.test.basepageradapterlibrary.basepager.BasePagerAdapter;
import com.test.basepageradapterlibrary.basepager.BasePagerAdapterHelper;
import com.test.basepageradapterlibrary.basepager.QuickPagerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DemoBasicActivity extends AppCompatActivity {

    protected static final String KEY_DEMO = "demo";

    protected RecyclerTabLayout mRecyclerTabLayout;

    public static void startActivity(Context context, Demo demo) {
        Intent intent = new Intent(context, DemoBasicActivity.class);
        intent.putExtra(KEY_DEMO, demo.name());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_basic);

        Demo demo = Demo.valueOf(getIntent().getStringExtra(KEY_DEMO));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(demo.titleResId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<ColorItem> items = DemoData.loadDemoColorItems(this);
        Collections.sort(items, new Comparator<ColorItem>() {
            @Override
            public int compare(ColorItem lhs, ColorItem rhs) {
                return lhs.name.compareTo(rhs.name);
            }
        });

        DemoColorPagerAdapter adapter = new DemoColorPagerAdapter();
        adapter.addAll(items);

        List<String> strs = new ArrayList<>();
        List<Integer> layouts = new ArrayList<>();
        for (ColorItem mColorItem : items) {
            strs.add(mColorItem.name);
        }
        layouts.add(R.layout.layout_page);
        BasePagerAdapter baseAdapter = new BasePagerAdapter<ColorItem>(items, strs, layouts) {

            @Override
            protected void convert(View view, ColorItem item, int position) {

                TextView textView = (TextView) view.findViewById(R.id.title);
                textView.setText("Page: " + item.hex);
            }
        };

        QuickPagerAdapter mQuickPagerAdapter = new QuickPagerAdapter<ColorItem>(items, strs, layouts) {

            @Override
            protected void convert(BasePagerAdapterHelper helper, ColorItem item, int position) {

                TextView textView = helper.getTextView(R.id.title);
                textView.setText("Page: " + item.hex);
            }
        };

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
//        viewPager.setAdapter(baseAdapter);
        viewPager.setAdapter(mQuickPagerAdapter);
//        viewPager.setAdapter(adapter);

        mRecyclerTabLayout = (RecyclerTabLayout)
                findViewById(R.id.recycler_tab_layout);
        mRecyclerTabLayout.setUpWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}