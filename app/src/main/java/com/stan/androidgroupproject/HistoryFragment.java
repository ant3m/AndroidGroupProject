package com.stan.androidgroupproject;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Stan on 28/12/2017.
 * Fragment for viewing the refuel History
 */


public class HistoryFragment extends Fragment {

    HistoryAdapter historyAdapter;
    HistoryModel mHistoryModel;
    View rootView;

    public HistoryFragment() {
        // Required empty public constructor
    }

    public HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("HistoryFragment", "In onCreate");

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_history, container, false);

        final RecyclerView recyclerView = rootView.findViewById(R.id.history_recyclerView);
        recyclerView.setHasFixedSize(true);
        Log.i("HistoryFragment", "In onCreateView");

        mHistoryModel = new HistoryModel();

        AutomobileDatabaseHelper automobileDatabaseHelper = AutomobileDatabaseHelper.newInstance(getContext());
        historyAdapter = new HistoryAdapter(automobileDatabaseHelper.getHistoryList(), getActivity().getApplicationContext(), recyclerView);

        historyAdapter.notifyDataSetChanged();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(historyAdapter);
        recyclerView.post(() -> recyclerView.smoothScrollToPosition(historyAdapter.getItemCount()));

        return rootView;

    }

    class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

        private List<HistoryModel> mHistoryModelList;
        private Context mContext;
        private RecyclerView mRecyclerView;
        private AutomobileDatabaseHelper mAutomobileDatabaseHelper;


        class MyViewHolder extends RecyclerView.ViewHolder implements EditRowDialogFragment.EditRowDialogListener {

            CardView mCardView;
            ImageView gasImage;
            TextView dateTextView, priceTextView, kmTextView, qtyTextView;

            MyViewHolder(View itemView) {
                super(itemView);
                mCardView = itemView.findViewById(R.id.history_cardView);
                gasImage = itemView.findViewById(R.id.gas_imageView);
                dateTextView = itemView.findViewById(R.id.date_textView);
                priceTextView = itemView.findViewById(R.id.history_price_textView);
                kmTextView = itemView.findViewById(R.id.history_trip_textView);
                qtyTextView = itemView.findViewById(R.id.history_qty_textView);
            }

            @Override
            public void applyEditValues(String date, Integer price, Integer liter, Integer km) {
                dateTextView.setText(date);
                priceTextView.setText(price);
                kmTextView.setText(km);
                qtyTextView.setText(liter);
            }

        }

        public void add(int position, HistoryModel historyModel) {
            mHistoryModelList.add(position, historyModel);
            notifyItemInserted(position);
        }

        void remove(int position) {
            mHistoryModelList.remove(position);
            notifyItemRemoved(position);
        }

        HistoryAdapter(List<HistoryModel> myDataSet, Context context, RecyclerView recyclerView) {
            this.mHistoryModelList = myDataSet;
            this.mContext = context;
            this.mRecyclerView = recyclerView;
        }

        @Override
        public HistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_layout, parent, false); // Create a new View

            MyViewHolder myViewHolder = new MyViewHolder(view);  // Sets the Views size, padding and other layout parameters
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(HistoryAdapter.MyViewHolder holder, int position) {

            final HistoryModel historyModel = mHistoryModelList.get(position);
            holder.dateTextView.setText(historyModel.getDate());
            holder.priceTextView.setText("$ " + historyModel.getPrice());
            holder.qtyTextView.setText(historyModel.getLiter() + " Liter");
            holder.kmTextView.setText(historyModel.getDistance() + " KM");

            holder.mCardView.setOnClickListener(v -> {
                final CharSequence[] options = {"Edit", "Delete"};
//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.select_dialog_item, options);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setTitle("Choose an action")
                        .setItems(options, (dialog, which) -> {
                            switch (which) {
//                                    case 0 : Toast.makeText(mContext, "Pressed on edit", Toast.LENGTH_SHORT).show();
                                case 0:
                                    openEditorDialog();
                                    break;
                                case 1:
                                    removeRow(position);
                                    break;
                            }
                        });
                AlertDialog dialog = alertDialogBuilder.create();
                dialog.show();

            });

        }

        private void addRow(int position, HistoryModel models) {
            add(position, models);
        }

        private void removeRow(int position) {
            historyAdapter.remove(position);
            Snackbar.make(rootView, "Removed an entry", Snackbar.LENGTH_SHORT).show();
        }

        private void openEditorDialog() {

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            EditRowDialogFragment editRowDialogFragment = EditRowDialogFragment.newInstance();
            editRowDialogFragment.setTargetFragment(getParentFragment(), 0);
            editRowDialogFragment.show(fragmentManager, "edit_row_dialog");
        }

        @Override
        public int getItemCount() {

            return mHistoryModelList.size();

        }
    }

}
