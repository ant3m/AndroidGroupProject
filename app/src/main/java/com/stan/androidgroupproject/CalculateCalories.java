package com.stan.androidgroupproject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import static com.stan.androidgroupproject.FoodDatabaseHelper.KEY_CALORIES;
import static com.stan.androidgroupproject.FoodDatabaseHelper.KEY_CARBS;
import static com.stan.androidgroupproject.FoodDatabaseHelper.KEY_DATE;
import static com.stan.androidgroupproject.FoodDatabaseHelper.KEY_FAT;
import static com.stan.androidgroupproject.FoodDatabaseHelper.KEY_ID;
import static com.stan.androidgroupproject.FoodDatabaseHelper.KEY_NAME;
import static com.stan.androidgroupproject.FoodDatabaseHelper.KEY_PROTEIN;
import static com.stan.androidgroupproject.FoodDatabaseHelper.KEY_TIME;
import static com.stan.androidgroupproject.FoodDatabaseHelper.TABLE_NAME;

public class CalculateCalories extends Activity {

    private ListView lv;
    private FoodDatabaseHelper foodHelper;
    private SQLiteDatabase db;
    private Cursor curse;
    private ArrayList<String> nameList;
    private ArrayList<Integer> calList;
    private ArrayList<Integer> proList;
    private ArrayList<Integer> carbList;
    private ArrayList<Integer> fatList;
    private ArrayList<String> dateList;
    private ArrayList<String> timeList;
    private TextView totalAvgT;
    private TextView dailyAvgT;
    private TextView dailyMaxT;
    private Bundle info;
    private Button calCalc;
    private int totalAvg = 0;
    private String today;
    private int dailyAvg = 0;
    private ArrayList<Integer> avgCalsPerDay;
    private int dailyMax = 0;
    private ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_calories);

        progress = findViewById(R.id.progressBar);

        FoodQuery myAsync = new FoodQuery();
        myAsync.execute();
    }


    public void calcTotalAvgCals() {
        for (int i = 0; i < calList.size(); i++) {
            totalAvg += calList.get(i);
            Log.i("CalcCals", "here's whats inside " + calList.get(i));
        }
        totalAvg /= calList.size();
        Log.i("CalcCals", "Total avg cals is " + totalAvg);
    }

    public void calcDailyAvgCals() {
        ArrayList<Integer> list = new ArrayList<>();
        String rawQuery = "select " + KEY_CALORIES + " from " + TABLE_NAME + " where " + KEY_DATE + " = '" + today + "' ";
        Cursor cur = db.rawQuery(rawQuery, null);
        while (cur.moveToNext()) {
            int cals = cur.getInt(cur.getColumnIndexOrThrow(KEY_CALORIES));
            // String theTime = getString(cur.getColumnIndexOrThrow(KEY_DATE));
            list.add(cals);
            dailyAvg += cals;
            Log.i("DailyAvgCalc", "Here are the values for today(cals): " + cals);
            //   Log.i("DailyAvgCalc", "Here are the values for today(date): " + theTime);

        }
        dailyAvg /= list.size();
        Log.i("CalcCals", "Daily avg cals is " + dailyAvg);
    }

    public void calcDailyMaxCals() {
        ArrayList<Integer> list = new ArrayList<>();
        String rawQuery = "select " + KEY_CALORIES + " from " + TABLE_NAME + " where " + KEY_DATE + " = '" + today + "' ";
        Cursor cur = db.rawQuery(rawQuery, null);
        while (cur.moveToNext()) {
            int cals = cur.getInt(cur.getColumnIndexOrThrow(KEY_CALORIES));
            // String theTime = getString(cur.getColumnIndexOrThrow(KEY_DATE));
            list.add(cals);
            dailyMax += cals;
            Log.i("DailyAvgCalc", "Here are the values for today(cals): " + cals);
            //   Log.i("DailyAvgCalc", "Here are the values for today(date): " + theTime);
        }
        Log.i("CalcCals", "Daily max cals is " + dailyMax);
    }


    public void getList() {
        //grab items from database and add them to the arraylist
        while (curse.moveToNext()) {
            String name = curse.getString(curse.getColumnIndexOrThrow(KEY_NAME));
            String date = curse.getString(curse.getColumnIndexOrThrow(KEY_DATE));
            String time = curse.getString(curse.getColumnIndexOrThrow(KEY_TIME));
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
            dateList.add(date);
            timeList.add(time);

            Log.i("CalcCalories", "What is inside of this thing...." + date);
            Log.i("CalcCalories", "What is inside of this thing...." + time);
            Log.i("CalcCalories", "Today is...." + today);
            Log.i("CalcCalories", "What is inside of this thing...." + name);
            Log.i("CalcCalories", "What is inside of this thing...." + cals);


        }
    }

    public class FoodQuery extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {


            calCalc = findViewById(R.id.calCalcButton);

            dailyMaxT = findViewById(R.id.dailyMax);
            dailyAvgT = findViewById(R.id.dailyAvg);
            totalAvgT = findViewById(R.id.totalAvg);
            nameList = new ArrayList<>();
            calList = new ArrayList<>();
            proList = new ArrayList<>();
            carbList = new ArrayList<>();
            fatList = new ArrayList<>();
            timeList = new ArrayList<>();
            dateList = new ArrayList<>();
            avgCalsPerDay = new ArrayList<>();
            lv = findViewById(R.id.foodList);
            foodHelper = new FoodDatabaseHelper(CalculateCalories.this);
            db = foodHelper.getWritableDatabase(); //opens database
            curse = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME, KEY_CALORIES, KEY_CARBS, KEY_FAT, KEY_PROTEIN, KEY_DATE, KEY_TIME}, null, null, null, null, null);

            Long currentTime = System.currentTimeMillis();
            SimpleDateFormat internalTime = new SimpleDateFormat("h:mm a");
            SimpleDateFormat internalDay = new SimpleDateFormat("EEE, MMM d, yyyy");
            today = internalDay.format(currentTime);

            calCalc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CalculateCalories.this, NutriMain.class);
                    startActivity(intent);
                }
            });

            //getList();
            while (curse.moveToNext()) {
                String name = curse.getString(curse.getColumnIndexOrThrow(KEY_NAME));
                String date = curse.getString(curse.getColumnIndexOrThrow(KEY_DATE));
                String time = curse.getString(curse.getColumnIndexOrThrow(KEY_TIME));
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
                dateList.add(date);
                timeList.add(time);

                Log.i("CalcCalories", "What is inside of this thing...." + date);
                Log.i("CalcCalories", "What is inside of this thing...." + time);
                Log.i("CalcCalories", "Today is...." + today);
                Log.i("CalcCalories", "What is inside of this thing...." + name);
                Log.i("CalcCalories", "What is inside of this thing...." + cals);
            }


            //calcTotalAvgCals();
            for (int i = 0; i < calList.size(); i++) {
                totalAvg += calList.get(i);
                Log.i("CalcCals", "here's whats inside " + calList.get(i));

            }
            publishProgress(33);
            if(calList.size() == 0){
               Log.i("zero", "Can't divide by zero");
            }else{
                totalAvg /= calList.size();
            }
            Log.i("CalcCals", "Total avg cals is " + totalAvg);


            //  calcDailyAvgCals();
            ArrayList<Integer> list = new ArrayList<>();
            String rawQuery = "select " + KEY_CALORIES + " from " + TABLE_NAME + " where " + KEY_DATE + " = '" + today + "' ";
            Cursor cur = db.rawQuery(rawQuery, null);
            while (cur.moveToNext()) {
                int cals = cur.getInt(cur.getColumnIndexOrThrow(KEY_CALORIES));
                // String theTime = getString(cur.getColumnIndexOrThrow(KEY_DATE));
                list.add(cals);
                dailyAvg += cals;
                Log.i("DailyAvgCalc", "Here are the values for today(cals): " + cals);
                //   Log.i("DailyAvgCalc", "Here are the values for today(date): " + theTime);


            }
            publishProgress(66);
            if(list.size()== 0){
                Log.i("Zero","Watch out");
            }else{
                dailyAvg /= list.size();
            }
            Log.i("CalcCals", "Daily avg cals is " + dailyAvg);


            //calcDailyMaxCals();

            ArrayList<Integer> list2 = new ArrayList<>();
            String rawQueryy = "select " + KEY_CALORIES + " from " + TABLE_NAME + " where " + KEY_DATE + " = '" + today + "' ";
            Cursor curr = db.rawQuery(rawQueryy, null);
            while (curr.moveToNext()) {
                int calss = curr.getInt(curr.getColumnIndexOrThrow(KEY_CALORIES));
                // String theTime = getString(cur.getColumnIndexOrThrow(KEY_DATE));
                list2.add(calss);
                dailyMax += calss;
                Log.i("DailyAvgCalc", "Here are the values for today(cals): " + calss);
                //   Log.i("DailyAvgCalc", "Here are the values for today(date): " + theTime);

            }
            publishProgress(100);
            Log.i("CalcCals", "Daily max cals is " + dailyMax);


            return null;
        }


        public void onProgressUpdate(Integer... value) {
            progress.setVisibility(View.VISIBLE);
            progress.setProgress(value[0]);
        }

        public void onPostExecute(String s) {

            totalAvgT.setText("Total average calories are: " + totalAvg);
           // onProgressUpdate(40);
            dailyAvgT.setText("Daily average calories are " + dailyAvg);
            //onProgressUpdate(80);
            dailyMaxT.setText("Total daily calories are " + dailyMax);
           // onProgressUpdate(100);
            progress.setVisibility(View.INVISIBLE);

        }
    }
}










