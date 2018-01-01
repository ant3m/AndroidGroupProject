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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class Automobile extends AppCompatActivity {

    private static final String ACTIVITY_NAME = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automobile);

        Toolbar toolbar = findViewById(R.id.automobile_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Get the ViewPager and set its PagerAdapter so that it can display items
        ViewPager viewPager = findViewById(R.id.view_pager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), Automobile.this);
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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), RefuelActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_up, R.anim.stay);
                finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
//            Log.i(ACTIVITY_NAME, "Returned to startActivity.onActivityResult");
//
//        }
//    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    /**
     * A Simple Adapter class for attaching fragment to the ViewPager
     */

    class PagerAdapter extends FragmentPagerAdapter {

        String tabTitle[] = new String[]{"History", "Monthly"};
        Context mContext;

        PagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.mContext = context;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new HistoryFragment();
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

        public View getTabView(int position) {
            View tab = LayoutInflater.from(Automobile.this).inflate(R.layout.tab_layout, null);
            TextView textView = tab.findViewById(R.id.tab_text);
            textView.setText(tabTitle[position]);

            return tab;
        }
    }


}
