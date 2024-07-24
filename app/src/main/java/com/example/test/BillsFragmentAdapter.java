package com.example.test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class BillsFragmentAdapter extends RecyclerView.Adapter<BillsFragmentAdapter.TransactionViewHolder> {

    private ArrayList<BillsMonth> BillsMonthList;

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewMonth, textViewMonthIncome, textViewMonthExpense,textViewMonthBalance;

        public TransactionViewHolder(View itemView) {
            super(itemView);
            textViewMonth = itemView.findViewById(R.id.textViewMonth);
            textViewMonthIncome = itemView.findViewById(R.id.textViewMonthIncome);
            textViewMonthExpense = itemView.findViewById(R.id.textViewMonthExpense);
            textViewMonthBalance = itemView.findViewById(R.id.textViewMonthBalance);
        }
    }

    public BillsFragmentAdapter(ArrayList<BillsMonth> BillsMonthList) {
        this.BillsMonthList = BillsMonthList;
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {
        BillsMonth bill = BillsMonthList.get(position);
        holder.textViewMonth.setText(bill.getMonth());
        holder.textViewMonthIncome.setText(String.valueOf(bill.getMonthIncome()));
        holder.textViewMonthExpense.setText(String.valueOf(bill.getMonthExpense()));
        holder.textViewMonthBalance.setText(String.valueOf(bill.getMonthBalance()));
    }

    @Override
    public int getItemCount() {
        return BillsMonthList.size();
    }

}