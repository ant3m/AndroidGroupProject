package com.stan.androidgroupproject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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


public class HistoryFragment extends Fragment {

    HistoryAdapter historyAdapter;
    HistoryModel mHistoryModel;

    public HistoryFragment() {
        // Required empty public constructor
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

        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        final RecyclerView recyclerView = rootView.findViewById(R.id.history_recyclerView);
        recyclerView.setHasFixedSize(true);
        Log.i("HistoryFragment", "In onCreateView");

        mHistoryModel = new HistoryModel();

        AutomobileDatabaseHelper automobileDatabaseHelper = new AutomobileDatabaseHelper(getContext());
        historyAdapter = new HistoryAdapter(automobileDatabaseHelper.getHistoryList(), getActivity().getApplicationContext(), recyclerView);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(historyAdapter);
        recyclerView.post(() -> recyclerView.smoothScrollToPosition(historyAdapter.getItemCount()));

        return rootView;

    }

    static class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

        private List<HistoryModel> mHistoryModelList;
        private Context mContext;
        private RecyclerView mRecyclerView;

//        private String[] date, price, liter, trip;

        class MyViewHolder extends RecyclerView.ViewHolder {

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
        }

        public void add(int position, HistoryModel historyModel) {
            mHistoryModelList.add(position, historyModel);
            notifyItemInserted(position);
        }

        public void remove(int position) {
            mHistoryModelList.remove(position);
            notifyItemRemoved(position);
        }

        HistoryAdapter(List<HistoryModel> myDataSet, Context context, RecyclerView recyclerView) {
            this.mHistoryModelList = myDataSet;
            this.mContext = context;
            this.mRecyclerView = recyclerView;
//            notifyDataSetChanged();
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
            if (historyModel.getLiter() <= 1) {
                holder.qtyTextView.setText(historyModel.getLiter() + " Liter");
            } else {
                holder.qtyTextView.setText(historyModel.getLiter() + " Liters");
            }
            holder.kmTextView.setText(historyModel.getDistance() + " KM");

        }

        @Override
        public int getItemCount() {

            return mHistoryModelList.size();

        }

    }
}
