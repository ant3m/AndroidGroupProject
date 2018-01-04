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
 * Helper class for the Automobile database
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
    private static AutomobileDatabaseHelper automobileDatabaseInstance = null;
    private Context mContext;


    private List<HistoryModel> historyModels;

    /**
     * Singleton Pattern to prevent memory leaks.
     *
     * @param context
     * @return instance of AutomobileDatabaseHelper
     */

    public static AutomobileDatabaseHelper newInstance(Context context) {
        if (automobileDatabaseInstance == null) {
            automobileDatabaseInstance = new AutomobileDatabaseHelper(context.getApplicationContext());
        }
        return automobileDatabaseInstance;
    }


    private AutomobileDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUM);
        this.mContext = context;
    }

    /*
        Creates Table
     */

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

    /*
        Inserts data to the table
     */

    void saveNewRefuel(SQLiteDatabase database, String date, int price, int liter, int distance) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_HISTORY_PRICE, price);
        contentValues.put(COLUMN_HISTORY_LITER, liter);
        contentValues.put(COLUMN_HISTORY_TRIP, distance);
        database.insert(TABLE_NAME, null, contentValues);

        Log.i("AutoDatabaseHelper", "One row inserted");


    }

    /*
        Gets all records in the table
     */

    List<HistoryModel> getHistoryList() {

        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Log.i("AutoDatabaseHelper", "Getting History List");

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        historyModels = new ArrayList<>();
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

    /*
        Updates a record in the table
     */

    public void updateRecord(SQLiteDatabase database, String date, Integer price, Integer liter, Integer trip) {
        ContentValues values = new ContentValues();
//        HistoryModel historyModel = new HistoryModel();

        values.put(COLUMN_DATE, date);
        values.put(COLUMN_HISTORY_PRICE, price);
        values.put(COLUMN_HISTORY_LITER, liter);
        values.put(COLUMN_HISTORY_TRIP, trip);

        database.update(TABLE_NAME, values, "history_price = ?", new String[]{Integer.toString(price)});
    }


    /*
        Deletes the record in the table
     */

    public void deleteRecord(Integer id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, "date = ?", new String[]{Integer.toString(id)});

    }

}






















