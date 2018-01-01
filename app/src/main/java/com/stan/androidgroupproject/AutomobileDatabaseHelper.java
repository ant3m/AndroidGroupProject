package com.stan.androidgroupproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stan on 30/12/2017.
 */

public class AutomobileDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Automobile.db";
    private static final int VERSION_NUM = 3;
    private static final String TABLE_NAME = "History";
    private static final String KEY_ID = "_id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_HISTORY_PRICE = "history_price";
    private static final String COLUMN_HISTORY_TRIP = "trip";
    private static final String COLUMN_HISTORY_LITER = "liter";


    private List<HistoryModel> historyModels = new ArrayList<>();


    public AutomobileDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUM);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                COLUMN_DATE + " DATETIME NOT NULL , " +
                COLUMN_HISTORY_PRICE + " INTEGER NOT NULL , " +
                COLUMN_HISTORY_LITER + " INTEGER NOT NULL , " +
                COLUMN_HISTORY_TRIP + " INTEGER NOT NULL);"
        );

        Log.i("AutoDatabaseHelper", "Table Created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

        Log.i("AutoDatabaseHelper", "Calling onUpgrade, old version was " + oldVersion + " new version is " + newVersion);
    }

    void saveNewRefuel(SQLiteDatabase database, String date, int price, int liter, int distance) {

//        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
//        contentValues.put(KEY_ID, historyModel.getId());
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_HISTORY_PRICE, price);
        contentValues.put(COLUMN_HISTORY_LITER, liter);
        contentValues.put(COLUMN_HISTORY_TRIP, distance);
        database.insert(TABLE_NAME, null, contentValues);

        Log.i("AutoDatabaseHelper", "One row inserted");

//        database.close();

    }


    List<HistoryModel> getHistoryList() {

        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Log.i("AutoDatabaseHelper", "Getting History List");

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        HistoryModel historyModel;

        while (cursor.moveToNext()) {
            historyModel = new HistoryModel();
            historyModel.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            historyModel.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
            historyModel.setPrice(cursor.getInt(cursor.getColumnIndex(COLUMN_HISTORY_PRICE)));
            historyModel.setDistance(cursor.getInt(cursor.getColumnIndex(COLUMN_HISTORY_TRIP)));
            historyModel.setLiter(cursor.getInt(cursor.getColumnIndex(COLUMN_HISTORY_LITER)));

            historyModels.add(historyModel);
        }

        for (HistoryModel model : historyModels) {
            Log.i("AutoDatabaseHelper", "" + model.getPrice());
        }

        return historyModels;
    }

}






















