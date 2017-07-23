package com.example.kremik.moododroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class FixedTabsActivity extends AppCompatActivity {

    private static final String TAG = "FixedTabsActivity";

    private TabsPagerAdapter mAdapter;
    private FloatingActionButton mFab;
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixed_tabs);
        Log.d(TAG, "onCreate: Starting.");

        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        setupViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mFab.show();
                } else {
                    mFab.hide();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mFab = (FloatingActionButton) findViewById(R.id.fab_add);
        mFab.setOnClickListener((View v) -> {
            mFab.setElevation(getResources().getDimension(R.dimen.fab_focused_elevation));
            Intent intent = new Intent(this, AddMoodActivity.class);
            startActivity(intent);
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        TabsPagerAdapter adapter = new TabsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new WeekMoodFragment(), "Week");
        adapter.addFragment(new MoodChartFragment(), "Chart");
        adapter.addFragment(new MoodHistoryFragment(), "History");
        viewPager.setAdapter(adapter);
    }
}
