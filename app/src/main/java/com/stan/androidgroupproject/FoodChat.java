package com.stan.androidgroupproject;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


import static com.stan.androidgroupproject.FoodDatabaseHelper.KEY_CALORIES;
import static com.stan.androidgroupproject.FoodDatabaseHelper.KEY_CARBS;
import static com.stan.androidgroupproject.FoodDatabaseHelper.KEY_FAT;
import static com.stan.androidgroupproject.FoodDatabaseHelper.KEY_ID;
import static com.stan.androidgroupproject.FoodDatabaseHelper.KEY_NAME;
import static com.stan.androidgroupproject.FoodDatabaseHelper.KEY_PROTEIN;
import static com.stan.androidgroupproject.FoodDatabaseHelper.TABLE_NAME;

public class FoodChat extends Activity {

    private ListView lv;
    private FoodDatabaseHelper foodHelper;
    private SQLiteDatabase db;
    private Cursor curse;
    private ArrayList<String> nameList;
    private ArrayList<String> timeList;
    private ArrayList<Integer> calList;
    private ArrayList<Integer> proList;
    private ArrayList<Integer> carbList;
    private ArrayList<Integer> fatList;
    private FoodAdapter adapter;
    private TextView food;
    private Bundle info;
    private Button back;
    private Button edit;
    private Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_chat);

        back = findViewById(R.id.back2Main);
        info = new Bundle();
        adapter = new FoodAdapter(this, 0);
        nameList = new ArrayList<>();
        calList = new ArrayList<>();
        proList = new ArrayList<>();
        carbList = new ArrayList<>();
        fatList = new ArrayList<>();
        lv = findViewById(R.id.foodList);
        foodHelper = new FoodDatabaseHelper(this);
        db = foodHelper.getWritableDatabase(); //opens database
        curse = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME, KEY_CALORIES, KEY_CARBS, KEY_FAT, KEY_PROTEIN}, null, null, null, null, null);

        getList();
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,  int position,  long id) {

                    info.putString("Food", lv.getItemAtPosition(position).toString());
                    info.putLong("Key", id);
                    info.putInt("Calories", calList.get(position));
                    info.putInt("Carbs", carbList.get(position));
                    info.putInt("Protein", proList.get(position));
                    info.putInt("Fat", fatList.get(position));

                    Intent intent = new Intent(FoodChat.this, Transition.class);
                    intent.putExtras(info);
                    startActivityForResult(intent, 0);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodChat.this, NutriMain.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bundle extras = data.getExtras();
        int deleteMe;
        String deleteString;
        if (resultCode == 10) {
            String newFood = extras.getString("newFood");
            int newCal = extras.getInt("newCal");
            int newPro = extras.getInt("newPro");
            int newCarb = extras.getInt("newCarb");
            int newFat = extras.getInt("newFat");
            deleteString = extras.getString("deleteString");
            Log.i("FoodChat", "THE NAME YOU WANT TO CHANGE IS " + newFood);
            ContentValues cv = new ContentValues();
            cv.put(KEY_NAME, newFood);
            cv.put(KEY_CALORIES, newCal);
            cv.put(KEY_PROTEIN, newPro);
            cv.put(KEY_CARBS, newCarb);
            cv.put(KEY_FAT, newFat);
            Log.i("allvalues", "THE VALUES ARE " + requestCode + ", " + resultCode + ", " + data);
            db.update(TABLE_NAME, cv, KEY_NAME + " = ?", new String[]{deleteString});
            finish();
            startActivity(getIntent());
            adapter.notifyDataSetChanged();
            Log.i("allvalues", "THE VALUES ARE " + requestCode + ", " + resultCode + ", " + data);
            Log.i("LISTSIZE", "THE SIZE OF THE LIST IS: " + nameList.size());
            Log.i("FoodChat", "THIS HAS BEEN CALLED");
        } else {
            deleteMe = extras.getInt("deleteMe");
            deleteString = extras.getString("deleteString");
            Log.i("value", "THE VALUE OF THE ID THAT YOU WANT TO DELETE IS " + deleteMe);
            Log.i("string", "THE STRING OF THE ID THAT YOU WANT TO DELETE IS " + deleteString);

            nameList.remove(deleteMe);
            calList.remove(deleteMe);
            carbList.remove(deleteMe);
            fatList.remove(deleteMe);
            proList.remove(deleteMe);
            db.delete(TABLE_NAME, KEY_NAME + " = ?", new String[]{deleteString});
            adapter.notifyDataSetChanged();
            Log.i("LISTSIZE", "THE SIZE OF THE LIST IS: " + nameList.size());
        }
    }

        public void getList(){
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

        public View getView(final int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = FoodChat.this.getLayoutInflater();
            View result;

            result = inflater.inflate(R.layout.food_item, null);
            food = result.findViewById(R.id.foodText);
           edit = result.findViewById(R.id.edit);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    info.putString("Food", lv.getItemAtPosition(position).toString());
                    info.putInt("Calories", calList.get(position));
                    info.putInt("Carbs", carbList.get(position));
                    info.putInt("Protein", proList.get(position));
                    info.putInt("Fat", fatList.get(position));

                    //start new activity for the phone
                    //opens EmptyFragment which opens MessageFragment which loads the layout it wants
                    Intent intent = new Intent(FoodChat.this, EditFood.class);
                    //stores the bundle object
                    intent.putExtras(info);
                    startActivityForResult(intent, 0);

                }
            });

            food.setText(getItem(position));//THIS IS BREAKING IT
            return result;
        }

    }

    protected void onDestroy(){
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

