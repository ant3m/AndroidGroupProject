package com.stan.androidgroupproject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by jerma on 2017-12-27.
 */

public class FoodFragment extends Fragment {

//public MessageFragment(ChatWindow cw){

    // }

    private Activity parent;
    private String passedFood;
    private int passedCals;
    private int passedFat;
    private int passedCarbs;
    private int passedPro;
    private TextView foodText;
    private TextView foodID;
    private TextView foodCals;
    private TextView foodCarbs;
    private TextView foodFat;
    private TextView foodPro;
    private Long id;
    private int convert;

    private Button deleteFood;
    private Button goBack;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("ONCREATE?", "FRAGMENT ON CREATE CALLED");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
       //Transition is the activity
        parent = activity;
        Log.i("WHICH ACTIVITY?", "THE ACTIVITY IS: " + parent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //opens up the fragment layout and attatch reference to layout you want to load with this fragment
        //this currently inflates the layout with the 2 text views and the button
        View view = inflater.inflate(R.layout.fragment_view, container, false);

        if(getArguments() != null){
            passedCals = getArguments().getInt("Calories");
            passedFood = getArguments().getString("Food");
            passedCarbs = getArguments().getInt("Carbs");
            passedFat = getArguments().getInt("Fat");
            passedPro = getArguments().getInt("Protein");
            id = getArguments().getLong("Key");
            convert = id.intValue();

        }

        foodText = view.findViewById(R.id.foodFragName);
        foodText.setText(passedFood);

        foodID = view.findViewById(R.id.foodFragID);
        foodID.setText("ID: " + (id+1));

        foodCals = view.findViewById(R.id.foodFragCals);
        foodCals.setText("Calories: " + passedCals);

        foodCarbs = view.findViewById(R.id.foodFragCarbs);
        foodCarbs.setText("Carbohydrates: " + passedCarbs);

        foodPro = view.findViewById(R.id.foodFragPro);
        foodPro.setText("Protein: " + passedPro);

        foodFat = view.findViewById(R.id.foodFragFat);
        foodFat.setText("Total Fat: " + passedFat);


        //also include buttonOnClickEvent
        goBack = view.findViewById(R.id.goBack);
        deleteFood = view.findViewById(R.id.deleteEntry);

        deleteFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(parent, FoodChat.class);
                    intent.putExtra("deleteMe", convert);
                    intent.putExtra("deleteString",passedFood);


                    parent.setResult(0, intent);
                    parent.finishActivity(0);
                    parent.finish();
                    parent.getFragmentManager().beginTransaction().remove(FoodFragment.this).commit();
        }
    });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent, FoodChat.class);
                parent.startActivity(intent);
                parent.finish();
            }
        });
        return view;
}

}
