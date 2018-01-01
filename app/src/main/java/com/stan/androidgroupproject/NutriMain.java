package com.stan.androidgroupproject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.stan.androidgroupproject.CalculateCalories;
import com.stan.androidgroupproject.FoodChat;
import com.stan.androidgroupproject.FoodInfoForm;
import com.stan.androidgroupproject.R;


import static com.stan.androidgroupproject.FoodDatabaseHelper.VERSION_NUM;

public class NutriMain extends AppCompatActivity {
    private Button newFoodButton;
    private Button foodListButton;
    private Button avgCals;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutri_main);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        newFoodButton = findViewById(R.id.newFoodButton);
        foodListButton = findViewById(R.id.foodListButton);
        avgCals = findViewById(R.id.calcCals);

        newFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NutriMain.this, FoodInfoForm.class);
                startActivityForResult(intent, 0);
            }
        });

        foodListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NutriMain.this, FoodChat.class);
                startActivity(intent);
            }
        });

        avgCals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NutriMain.this,CalculateCalories.class);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu m){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, m);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_one:
                //Toast toast1 = Toast.makeText(this, "Action One ", Toast.LENGTH_LONG);
                //toast1.show();
                // Snackbar.make(this.findViewById(R.id.myToolbar), text.getText().toString(), Snackbar.LENGTH_LONG) .setAction("Action", null).show();
                Snackbar.make(this.findViewById(R.id.my_toolbar), getString(R.string.nutriMainActNum) + " " + VERSION_NUM, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                break;
            case R.id.action_two:
                // Toast toast2 = Toast.makeText(this, "Action Two ", Toast.LENGTH_LONG);
                //toast2.show();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.author) + " Jermal Longley");
                builder.setMessage(R.string.instructions);
                // Add the buttons
                builder.setNegativeButton(R.string.goBack, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.dismiss();
                    }
                });
                // Create the AlertDialog
                AlertDialog dialog1 = builder.create();
                dialog1.show();

                break;
        }
        return true;
    }



    public void onActivityResult(int requestCode, int responseCode, Intent data){
        String messagePassed = data.getStringExtra("Success");
        Toast.makeText(this, messagePassed, Toast.LENGTH_SHORT).show();
    }
}
