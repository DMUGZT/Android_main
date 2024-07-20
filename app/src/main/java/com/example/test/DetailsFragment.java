package com.example.test;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.database.DatabaseHelper;

import java.util.ArrayList;


public class DetailsFragment extends Fragment {

    private RecyclerView recyclerView;
    private Detail_TransactionAdapter adapter;
    private ArrayList<Detail_Transaction> transactionList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize DatabaseHelper and load data
        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
        Test_Database test = new Test_Database(dbHelper);
        test.insertTestData();
        transactionList = new ArrayList<>();
        Cursor cursor = dbHelper.getAllTransactions();

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

        // Set up adapter
        adapter = new Detail_TransactionAdapter(transactionList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}