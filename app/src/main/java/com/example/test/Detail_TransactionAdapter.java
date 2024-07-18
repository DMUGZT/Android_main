package com.example.test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class Detail_TransactionAdapter extends RecyclerView.Adapter<Detail_TransactionAdapter.ViewHolder> {

    private ArrayList<Detail_Transaction> detailTransactions;

    public Detail_TransactionAdapter(ArrayList<Detail_Transaction> detailTransactions) {
        this.detailTransactions = detailTransactions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Detail_Transaction detailTransaction = detailTransactions.get(position);
        holder.textViewDate.setText(detailTransaction.getDate());
        holder.textViewDescription.setText(detailTransaction.getDescription());
        holder.textViewAmount.setText(detailTransaction.getAmount());
    }

    @Override
    public int getItemCount() {
        return detailTransactions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewDate, textViewDescription, textViewAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewAmount = itemView.findViewById(R.id.textViewAmount);
        }
    }
}