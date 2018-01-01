package com.stan.androidgroupproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RefuelActivity extends AppCompatActivity {

    FloatingActionButton entryFab;
    EditText dateEditText;
    TextInputEditText priceInput, litreInput, kmInput;
    Calendar refuelDate = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refuel);
        dateEditText = findViewById(R.id.date_editText);
        priceInput = findViewById(R.id.price_textInputEditText);
        litreInput = findViewById(R.id.liter_textInputEditText);
        kmInput = findViewById(R.id.trip_textInputEditText);

        Toolbar refuelToolbar = findViewById(R.id.refuel_toolbar);
        setSupportActionBar(refuelToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Log.i("RefuelActivity", "In onCreate");

        final DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            refuelDate.set(Calendar.YEAR, year);
            refuelDate.set(Calendar.MONTH, month);
            refuelDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            getRefuelDate();
        };

        dateEditText.setOnClickListener(view -> {
            new DatePickerDialog(RefuelActivity.this, dateSetListener,
                    refuelDate.get(Calendar.YEAR),
                    refuelDate.get(Calendar.MONTH),
                    refuelDate.get(Calendar.DAY_OF_MONTH)).show();
        });

        entryFab = findViewById(R.id.insert_fab);
        entryFab.setOnClickListener((View view) -> {
            saveEntry();
            onBackPressed();
        });

    }

    private void saveEntry() {

        String date = dateEditText.getText().toString();
        int price = Integer.parseInt(priceInput.getText().toString());
        int liter = Integer.parseInt(litreInput.getText().toString());
        int kilometer = Integer.parseInt(kmInput.getText().toString());

        AutomobileBackgroundTask backgroundTask = new AutomobileBackgroundTask(this);
        backgroundTask.execute("add_refuel", date, String.valueOf(price), String.valueOf(liter), String.valueOf(kilometer));

    }

    private void getRefuelDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.CANADA);

        dateEditText.setText(simpleDateFormat.format(refuelDate.getTime()));
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.stay, R.anim.slide_down);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
