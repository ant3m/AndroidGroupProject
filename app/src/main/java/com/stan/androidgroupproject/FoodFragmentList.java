package com.stan.androidgroupproject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


import static com.stan.androidgroupproject.FoodDatabaseHelper.KEY_CALORIES;
import static com.stan.androidgroupproject.FoodDatabaseHelper.KEY_CARBS;
import static com.stan.androidgroupproject.FoodDatabaseHelper.KEY_FAT;
import static com.stan.androidgroupproject.FoodDatabaseHelper.KEY_ID;
import static com.stan.androidgroupproject.FoodDatabaseHelper.KEY_NAME;
import static com.stan.androidgroupproject.FoodDatabaseHelper.KEY_PROTEIN;
import static com.stan.androidgroupproject.FoodDatabaseHelper.TABLE_NAME;

/**
 * Created by jerma on 2017-12-28.
 */

public class FoodFragmentList extends Fragment {


    private Activity parent;
    private ListView lv;
    private FoodDatabaseHelper foodHelper;
    private SQLiteDatabase db;
    private Cursor curse;
    private ArrayList<String> nameList;
    private ArrayList<Integer> calList;
    private ArrayList<Integer> proList;
    private ArrayList<Integer> carbList;
    private ArrayList<Integer> fatList;
    private FoodChat adapter;
    private TextView food;
    private Bundle info;
    private Button back;
    View view;
    FoodChat fc = new FoodChat();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("ONCREATE?", "FRAGMENT ON CREATE CALLED");


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //Transition is the activity
        parent = activity;
        Log.i("WHICH ACTIVITY?", "THE ACTIVITY IS: " + parent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //opens up the fragment layout and attatch reference to layout you want to load with this fragment
        //this currently inflates the layout with the 2 text views and the button
        view = inflater.inflate(R.layout.activity_food_chat, container, false);
        back = view.findViewById(R.id.back2Main);
        info = new Bundle();
        //adapter = new FoodAdapter(fc, 0);
        nameList = new ArrayList<>();
        calList = new ArrayList<>();
        proList = new ArrayList<>();
        carbList = new ArrayList<>();
        fatList = new ArrayList<>();
        lv = view.findViewById(R.id.foodList);
        foodHelper = new FoodDatabaseHelper(parent); //UMMM I DUNNO ABOUT THIS LUL
        db = foodHelper.getWritableDatabase(); //opens database
        curse = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME, KEY_CALORIES, KEY_CARBS, KEY_FAT, KEY_PROTEIN}, null, null, null, null, null);

        //grab items from database and add them to the arraylist
        while (curse.moveToNext()) {
            String name = curse.getString(curse.getColumnIndexOrThrow(KEY_NAME));
            int cals = curse.getInt(curse.getColumnIndexOrThrow(KEY_CALORIES));
            int carbs = curse.getInt(curse.getColumnIndexOrThrow(KEY_CARBS));
            int pro = curse.getInt(curse.getColumnIndexOrThrow(KEY_PROTEIN));
            int fat = curse.getInt(curse.getColumnIndexOrThrow(KEY_FAT));
            Log.i("FoodChat", "which string" + name);
            Log.i("FoodChat", "which int" + cals);
            nameList.add(name);
            calList.add(cals);
            carbList.add(carbs);
            proList.add(pro);
            fatList.add(fat);
        }
       // lv.setAdapter(adapter);
        //adapter.notifyDataSetChanged();

            return view;
        }

    private class FoodAdapter extends ArrayAdapter<String> {

        public FoodAdapter(Context ctx, int resource){
            super(ctx, resource);
        }

        public int getCount(){
            return nameList.size();
        }

        public String getItem(int position){
            return nameList.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = fc.getLayoutInflater();
            View result;

            //food item comes from food item resource layout
            result = inflater.inflate(R.layout.food_item, null);
            food = result.findViewById(R.id.foodText);
            food.setText(getItem(position));//THIS IS BREAKING IT
            return result;
        }

    }

    public void onDestroy(){
        super.onDestroy();
        foodHelper.close();
        Log.i("FoodChat", "In onDestroy()");
    }

    //DOUBLE CHECK
    public long getItemID(int position){
        curse.moveToPosition(position);
        return curse.getPosition();
    }
    }



