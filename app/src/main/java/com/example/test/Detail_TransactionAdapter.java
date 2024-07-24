package com.example.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.test.database.IncomeDAO;

import java.util.ArrayList;

public class Detail_TransactionAdapter extends RecyclerView.Adapter<Detail_TransactionAdapter.TransactionViewHolder> {

    private ArrayList<Detail_Transaction> transactionList;
    private IncomeDAO incomeDAO;
    private OnItemDeletedListener onItemDeletedListener;
    public interface OnItemDeletedListener {
        void onItemDeleted();
    }
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

    public Detail_TransactionAdapter(ArrayList<Detail_Transaction> transactionList, IncomeDAO incomeDAO,OnItemDeletedListener listener) {
        this.transactionList = transactionList;
        this.incomeDAO = incomeDAO; // 初始化成员变量
        this.onItemDeletedListener = listener;
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
    public void deleteItem(int position) {
        Detail_Transaction transaction = transactionList.get(position);
        int id = transaction.getId();
        transactionList.remove(position);
        notifyItemRemoved(position);
        incomeDAO.DeleteById(id); // 从数据库中删除
        if (onItemDeletedListener != null) {
            onItemDeletedListener.onItemDeleted(); // 通知删除操作已完成
        }
//        DetailsFragment.refreshData();
    }
}