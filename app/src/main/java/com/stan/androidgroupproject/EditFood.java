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
import java.util.ArrayList;



public class EditFood extends Activity {

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
    private SimpleDateFormat sdf;
    private String dateString;
    private long time;
    private Button back;
    private String passedFood;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food);
        Log.i("FoodInfoForm", "ONCREATE IS CALLED");

        sdf = new SimpleDateFormat("MMM dd yyyy\nhh-mm-ss a");

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
                nameString = foodName.getText().toString();
                calInt = Integer.parseInt(cals.getText().toString());
                proInt = Integer.parseInt(protein.getText().toString());
                carbInt = Integer.parseInt(carbs.getText().toString());
                fatInt = Integer.parseInt(fat.getText().toString());
                time = System.currentTimeMillis();
                dateString = sdf.format(time);
                Log.i("FoodInfoForm", "Does the String exist!?... " + foodName.getText().toString());
                Log.i("FoodInfoForm", "Does the int exist!? " + cals.getText().toString());



                Intent getStuff = getIntent();
                passedFood = getStuff.getStringExtra("Food");
                Log.i("EditFood", "THe string to delete is " + passedFood);
                Log.i("EditFood", "THe string to update is  " + nameString);
                Intent intent = new Intent(EditFood.this, FoodChat.class);
                intent.putExtra("newFood", nameString);
                intent.putExtra("newCal", calInt);
                intent.putExtra("newFat", fatInt);
                intent.putExtra("newCarb", carbInt);
                intent.putExtra("newPro", proInt);
                intent.putExtra("deleteString",passedFood);
                setResult(10, intent);
                finish();
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditFood.this, FoodChat.class);
                startActivity(intent);
            }
        });



    }
}
