package com.example.test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {

    private List<Account> accounts;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onEditClick(Account account);
        void onDeleteClick(Account account);
    }

    public AccountAdapter(List<Account> accounts, OnItemClickListener onItemClickListener) {
        this.accounts = accounts;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account, parent, false);
        return new AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        Account account = accounts.get(position);
        holder.bind(account, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    public static class AccountViewHolder extends RecyclerView.ViewHolder {
        private TextView accountTypeTextView;
        private TextView balanceTextView;

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            accountTypeTextView = itemView.findViewById(R.id.account_type);
            balanceTextView = itemView.findViewById(R.id.balance);
        }

        public void bind(final Account account, final OnItemClickListener listener) {
            accountTypeTextView.setText(account.getType());
            balanceTextView.setText(String.format("$%.2f", account.getBalance()));

            itemView.findViewById(R.id.edit_button).setOnClickListener(v -> listener.onEditClick(account));
            itemView.findViewById(R.id.delete_button).setOnClickListener(v -> listener.onDeleteClick(account));
        }
    }
}