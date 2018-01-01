package com.stan.androidgroupproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by stan on 31/12/2017.
 */

public class AutomobileBackgroundTask extends AsyncTask<String, Void, String> {

    private Context mContext;
    SQLiteDatabase sqLiteDatabase;


    AutomobileBackgroundTask(Context context) {
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        String method = params[0];
        AutomobileDatabaseHelper automobileDatabaseHelper = new AutomobileDatabaseHelper(mContext);
        if (method.equals("add_refuel")) {

            String date = params[1];
            int price = Integer.parseInt(params[2]);
            int liter = Integer.parseInt(params[3]);
            int kilometer = Integer.parseInt(params[4]);

            sqLiteDatabase = automobileDatabaseHelper.getWritableDatabase();
            automobileDatabaseHelper.saveNewRefuel(sqLiteDatabase, date, price, liter, kilometer);

            return "Added new entry!";

        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {

        Toast.makeText(mContext,result,Toast.LENGTH_SHORT).show();
    }
}
