package com.stan.androidgroupproject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;

import static com.stan.androidgroupproject.FoodDatabaseHelper.KEY_CALORIES;
import static com.stan.androidgroupproject.FoodDatabaseHelper.KEY_CARBS;
import static com.stan.androidgroupproject.FoodDatabaseHelper.KEY_DATE;
import static com.stan.androidgroupproject.FoodDatabaseHelper.KEY_FAT;
import static com.stan.androidgroupproject.FoodDatabaseHelper.KEY_NAME;
import static com.stan.androidgroupproject.FoodDatabaseHelper.KEY_PROTEIN;
import static com.stan.androidgroupproject.FoodDatabaseHelper.KEY_TIME;
import static com.stan.androidgroupproject.FoodDatabaseHelper.TABLE_NAME;


public class FoodInfoForm extends Activity {

    private EditText foodName;
    private EditText cals;
    private EditText fat;
    private EditText carbs;
    private EditText protein;
    private ContentValues values;
    private String nameString;
    private int calInt;
    private int proInt;
    private int fatInt;
    private int carbInt;
    private Button submit;
    private FoodDatabaseHelper foodHelper;
    private SQLiteDatabase db;
    private Long currentTime;
    private Button back;
    private String timeString;
    private String dateString;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_info_form);
        Log.i("FoodInfoForm", "ONCREATE IS CALLED");

        currentTime = System.currentTimeMillis();
        SimpleDateFormat time = new SimpleDateFormat("h:mm a");
        SimpleDateFormat day = new SimpleDateFormat("EEE, MMM d, yyyy");

         timeString = time.format(currentTime);
         dateString = day.format(currentTime);


        back = findViewById(R.id.goingBack);
        foodName = findViewById(R.id.editName);
        cals = findViewById(R.id.editCals);
        protein = findViewById(R.id.editProtein);
        carbs = findViewById(R.id.editCarbs);
        fat = findViewById(R.id.editFat);
        values = new ContentValues();
        submit = findViewById(R.id.submit);
        foodHelper = new FoodDatabaseHelper(this);
        db = foodHelper.getWritableDatabase(); //opens database

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // nameString.add(foodName.getText().toString());
                nameString = foodName.getText().toString();
                calInt = Integer.parseInt(cals.getText().toString());
                proInt = Integer.parseInt(protein.getText().toString());
                carbInt = Integer.parseInt(carbs.getText().toString());
                fatInt = Integer.parseInt(fat.getText().toString());


                Log.i("FoodInfoForm", "Does the String exist!?... " + foodName.getText().toString());
                Log.i("FoodInfoForm", "Does the int exist!? " + cals.getText().toString());

                values.put(KEY_NAME, nameString);
                values.put(KEY_CALORIES, calInt);
                values.put(KEY_FAT, fatInt);
                values.put(KEY_CARBS, carbInt);
                values.put(KEY_PROTEIN, proInt);
                values.put(KEY_DATE, dateString);
                values.put(KEY_TIME, timeString);
                values.put(KEY_DATE, dateString);
                db.insert(TABLE_NAME, null, values);


                Intent resultIntent = new Intent(  );
                resultIntent.putExtra("Success", "Your food has been added!");
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodInfoForm.this, NutriMain.class);
                startActivity(intent);
            }
        });



    }
}
