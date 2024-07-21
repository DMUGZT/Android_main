package com.example.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.database.DatabaseHelper;
import com.example.test.database.IncomeDAO;
import com.example.test.database.UserDAO;
import com.example.test.utils.UserSessionManager;

import java.util.ArrayList;


public class DetailsFragment extends Fragment {

    private RecyclerView recyclerView;
    private Detail_TransactionAdapter adapter;
    private ArrayList<Detail_Transaction> transactionList;
    private UserSessionManager usersessionmanager;
    private IncomeDAO incomeDAO;
    private UserDAO userDAO;
    private UserSessionManager sessionManager;
    private double incomeTotal;
    private double paymentTotal;
    private TextView incomeTextView;
    private TextView paymentTextView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        View view = inflater.inflate(R.layout.fragment_details, container, false);
        incomeDAO = new IncomeDAO(getContext());
        sessionManager = new UserSessionManager(getContext());
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        incomeTextView = view.findViewById(R.id.income_month);
        paymentTextView = view.findViewById(R.id.payment);

        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());

//        Test_Database test = new Test_Database(dbHelper);
//        test.insertTestData();
        transactionList = new ArrayList<>();
        String userId = sessionManager.getUserId();
        Cursor cursor = incomeDAO.getIncomeById(Integer.parseInt(userId));
//        Cursor cursor = dbHelper.getAllTransactions();

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("_id"));
                @SuppressLint("Range") String category = cursor.getString(cursor.getColumnIndex("category"));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("date"));
                @SuppressLint("Range") double amount = cursor.getDouble(cursor.getColumnIndex("amount"));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
                @SuppressLint("Range") String cashType = cursor.getString(cursor.getColumnIndex("cash_type"));

                Detail_Transaction transaction = new Detail_Transaction(id, category, date, amount, description, cashType);
                transactionList.add(transaction);
            } while (cursor.moveToNext());
        }
        cursor.close();
        for (Detail_Transaction transaction : transactionList) {
            if ("入账".equals(transaction.getCategory())) {
                incomeTotal += transaction.getAmount();
            } else if ("支出".equals(transaction.getCategory())) {
                paymentTotal += transaction.getAmount();
            }
        }

        incomeTextView.setText(String.format("%.2f", incomeTotal));
        paymentTextView.setText(String.format("%.2f", paymentTotal));
        // Set up adapter
        adapter = new Detail_TransactionAdapter(transactionList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}