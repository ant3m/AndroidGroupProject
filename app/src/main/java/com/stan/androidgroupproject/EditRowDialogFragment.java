package com.stan.androidgroupproject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by stan on 02/01/2018.
 * Dialog fragment which helps us to edit a transaction.
 */

public class EditRowDialogFragment extends AppCompatDialogFragment {
    private EditText editDateEditText;
    private TextInputEditText editPriceInput, editLitreInput, editKmInput;
    private Button doneButton, cancelButton;
    private Calendar refuelEditDate = Calendar.getInstance();
    private EditRowDialogListener mEditRowDialogListener;
    AutomobileBackgroundTask mAutomobileBackgroundTask;

    static EditRowDialogFragment newInstance(){
        return new EditRowDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.edit_row_dialog_fragment, null);

        editDateEditText = view.findViewById(R.id.editDate_editText);
        editPriceInput = view.findViewById(R.id.EditPrice_textInputEditText);
        editLitreInput = view.findViewById(R.id.EditLiter_textInputEditText);
        editKmInput = view.findViewById(R.id.EditTrip_textInputEditText);

        doneButton = view.findViewById(R.id.done_button);
        cancelButton = view.findViewById(R.id.cancel_button);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        final DatePickerDialog.OnDateSetListener dateSetListener = (view1, year, month, dayOfMonth) -> {
            refuelEditDate.set(Calendar.YEAR, year);
            refuelEditDate.set(Calendar.MONTH, month);
            refuelEditDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            getRefuelEditDate();
        };

        editDateEditText.setOnClickListener(v -> new DatePickerDialog(getContext(), dateSetListener,
                refuelEditDate.get(Calendar.YEAR),
                refuelEditDate.get(Calendar.MONTH),
                refuelEditDate.get(Calendar.DAY_OF_MONTH)).show());

        cancelButton.setOnClickListener(v -> dismiss());

        doneButton.setOnClickListener(v -> {

            String date;
            int price;
            int liter;
            int kilometer;

            if (TextUtils.isEmpty(editDateEditText.getText().toString())){
                Toast.makeText(getContext(),"Please add a date of refuel!", Toast.LENGTH_SHORT).show();
            }
            else if (editPriceInput.getText().toString().isEmpty()){
                Toast.makeText(getContext(),"Please add a price of refuel!", Toast.LENGTH_SHORT).show();
            }
            else if (editLitreInput.getText().toString().isEmpty()){
                Toast.makeText(getContext(),"Please add liter of refuel!", Toast.LENGTH_SHORT).show();
            }
            else if (editKmInput.getText().toString().isEmpty()){
                Toast.makeText(getContext(),"Please add km of refuel!", Toast.LENGTH_SHORT).show();
            }
            else {
                date = editDateEditText.getText().toString();
                price = Integer.parseInt(editPriceInput.getText().toString());
                liter = Integer.parseInt(editLitreInput.getText().toString());
                kilometer = Integer.parseInt(editKmInput.getText().toString());
                mAutomobileBackgroundTask = new AutomobileBackgroundTask(getContext());
                mAutomobileBackgroundTask.execute("update_refuel", date, String.valueOf(price), String.valueOf(liter), String.valueOf(kilometer));
            }
            dismiss();
        });

        alertDialogBuilder.setView(view);

        return alertDialogBuilder.create();
    }

    private void getRefuelEditDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.CANADA);

        editDateEditText.setText(simpleDateFormat.format(refuelEditDate.getTime()));
    }

    public interface EditRowDialogListener{
        void applyEditValues(String date, Integer price, Integer liter, Integer km);
    }
}























