package com.example.test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class Detail_TransactionAdapter extends RecyclerView.Adapter<Detail_TransactionAdapter.TransactionViewHolder> {

    private ArrayList<Detail_Transaction> transactionList;

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewDate, textViewDescription, textViewAmount,textViewMoneyType;

        public TransactionViewHolder(View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewMoneyType = itemView.findViewById(R.id.textViewMoneyType);
            textViewAmount = itemView.findViewById(R.id.textViewAmount);
        }
    }

    public Detail_TransactionAdapter(ArrayList<Detail_Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {
        Detail_Transaction transaction = transactionList.get(position);
        holder.textViewDate.setText(transaction.getDate());
        holder.textViewMoneyType.setText(transaction.getCategory());
        holder.textViewDescription.setText(transaction.getDescription());
        holder.textViewAmount.setText(String.valueOf(transaction.getAmount()));
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

}