package com.stan.androidgroupproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Stan on 18/12/2017
 * Main Activity For Automobile app
 */

public class Automobile extends AppCompatActivity {

    private static final String ACTIVITY_NAME = "Automobile";

    PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), Automobile.this);
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automobile);

        Log.i(ACTIVITY_NAME, "In onCreate");

        Toolbar toolbar = findViewById(R.id.automobile_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Get the ViewPager and set its PagerAdapter so that it can display items
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(pagerAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);


        // Iterate over all the tabs and set the custom view
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            assert tab != null;
            tab.setCustomView(pagerAdapter.getTabView(i));
        }


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), RefuelActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_up, R.anim.stay);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewPager.setAdapter(pagerAdapter);

        Log.i(ACTIVITY_NAME, "In onResume");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause");
    }

    /**
     * A Simple Adapter class for attaching fragment to the ViewPager
     */

    class PagerAdapter extends FragmentPagerAdapter {

        String[] tabTitle = new String[]{"History","Monthly"};
        Context mContext;

        PagerAdapter(FragmentManager fm, Context context) {
            super(fm);

            this.mContext = context;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new HistoryFragment().newInstance();
                case 1:
                    return new MonthlyFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return tabTitle.length;
        }

        public CharSequence getPageTitle(int position) {
            return tabTitle[position];
        }

        View getTabView(int position) {
            View tab = LayoutInflater.from(Automobile.this).inflate(R.layout.tab_layout, null);
            TextView textView = tab.findViewById(R.id.tab_text);
            textView.setText(tabTitle[position]);

            return tab;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.automobile_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_automobile_help:
                openHelpDialog();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openHelpDialog() {
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.automobile_help_menu, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.show();

    }


}
