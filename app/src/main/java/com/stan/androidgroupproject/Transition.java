package com.stan.androidgroupproject;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by jerma on 2017-12-27.
 */

public class Transition extends Activity{

    private FoodFragment ff = new FoodFragment();
    //private FoodFragmentList ffl = new FoodFragmentList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_empty_frame_layout);
        ff.setArguments(getIntent().getExtras());
        //ffl.setArguments(getIntent().getExtras());
        //LOAD THE FRAGMENT (THAT IS DISPLAYING EMPTYLAYOUT)INTO THE FRAMELAYOUT
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, ff);
        //ft.replace(R.id.frameLayout, ffl);
        ft.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Transition", "THIS HAS BEEN DESTROYED");
    }
}


