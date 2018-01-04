package com.stan.androidgroupproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by stan on 31/12/2017.
 * Async class to execute SQL commands in the background thread.
 */

public class AutomobileBackgroundTask extends AsyncTask<String, Void, String> {

    private Context mContext;
    private SQLiteDatabase sqLiteDatabase;

    AutomobileBackgroundTask(Context context) {
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        AutomobileDatabaseHelper automobileDatabaseHelper = AutomobileDatabaseHelper.newInstance(mContext);
        sqLiteDatabase = automobileDatabaseHelper.getWritableDatabase();

        String date;
        int price, liter, kilometer;

        String method = params[0];

        if (method.equals("add_refuel")) {
            date = params[1];
            price = Integer.parseInt(params[2]);
            liter = Integer.parseInt(params[3]);
            kilometer = Integer.parseInt(params[4]);

            automobileDatabaseHelper.saveNewRefuel(sqLiteDatabase, date, price, liter, kilometer);
            return "Added new entry!";
        }

        if (method.equals("update_refuel")) {
            date = params[1];
            price = Integer.parseInt(params[2]);
            liter = Integer.parseInt(params[3]);
            kilometer = Integer.parseInt(params[4]);

            automobileDatabaseHelper.updateRecord(sqLiteDatabase, date, price, liter, kilometer);
            return "Updated entry!";
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {

        Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
    }
}
