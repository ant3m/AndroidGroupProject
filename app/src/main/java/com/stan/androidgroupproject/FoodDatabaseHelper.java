package com.stan.androidgroupproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jerma on 2017-12-27.
 */

public class FoodDatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "Food.db";
    public static final String TABLE_NAME = "Food";
    public static final String KEY_ID = "id"; //column name
    public static final String KEY_NAME = "Name"; //column name
    public static final String KEY_CALORIES = "Calories"; //column name
    public static final String KEY_PROTEIN = "Protein"; //column name
    public static final String KEY_CARBS = "Carbs"; //column name
    public static final String KEY_FAT = "Fat"; //column name
    public static final String KEY_DATE = "Date"; //column name
    public static final String KEY_TIME = "Time"; //column name
    public static final int VERSION_NUM = 6;
    private SQLiteDatabase db;

    public FoodDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
        db = this.getWritableDatabase();
    }

    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("CREATE TABLE " + TABLE_NAME + "( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME + " TEXT)");

       // db.execSQL("CREATE TABLE " + TABLE_NAME + "( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME + " TEXT,"
           //            + KEY_CALORIES + " INTEGER)");
        //The full database
        db.execSQL("CREATE TABLE " + TABLE_NAME + "( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME + " TEXT,"
               + KEY_CALORIES + " TEXT," + KEY_PROTEIN + " TEXT," + KEY_CARBS + " TEXT," + KEY_FAT + " TEXT," + KEY_DATE + " TEXT,"
                + KEY_TIME + " TEXT)");
        Log.i("FoodDatabaseHelper", "Calling onCreate");

    }

    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

        Log.i("FoodDatabaseHelper", "Calling onUpgrade, oldVersion= " + oldVer + " newVersion= " + newVer);


    }
}

