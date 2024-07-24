package com.example.test;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.database.DatabaseHelper;
import com.example.test.database.IncomeDAO;
import com.example.test.utils.UserSessionManager;

import java.util.ArrayList;
import java.util.Calendar;

public class DetailsFragment extends Fragment implements Detail_TransactionAdapter.OnItemDeletedListener {

    private RecyclerView recyclerView;
    private Detail_TransactionAdapter adapter;
    private ArrayList<Detail_Transaction> transactionList;
    private UserSessionManager sessionManager;
    private IncomeDAO incomeDAO;
    private double incomeTotal;
    private double paymentTotal;
    private TextView incomeTextView;
    private TextView paymentTextView;
    private Spinner monthSpinner;
    private Spinner yearSpinner;

    private String selectedMonth ;
    private String selectedYear ;
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
        monthSpinner = view.findViewById(R.id.month_spinner);
        yearSpinner = view.findViewById(R.id.year_spinner);

        transactionList = new ArrayList<>();
        adapter = new Detail_TransactionAdapter(transactionList,incomeDAO,this);
        recyclerView.setAdapter(adapter);

        setupMonthSpinner();
        setupYearSpinner();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        return view;
    }
    public void onResume() {
        super.onResume();
        loadData();
    }

    @SuppressLint("DefaultLocale")
    private void setupMonthSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.months_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        monthSpinner.setAdapter(adapter);
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH); // 0-indexed
        selectedMonth = String.format("%d", currentMonth + 1); // 1-indexed

        monthSpinner.setSelection(currentMonth);

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMonth = String.format("%d", position + 1); // 1-indexed
                loadData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }
    @SuppressLint("DefaultLocale")
    private void setupYearSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.years_array,  R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int startYear = 2018; // years_array数组的起始年份
        yearSpinner.setAdapter(adapter);
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentYearIndex = currentYear - startYear;
        if (currentYearIndex >= 0 && currentYearIndex < adapter.getCount()) {
            yearSpinner.setSelection(currentYearIndex);
        } else {
            yearSpinner.setSelection(0); // 如果当前年份不在范围内，默认选择第一个元素
        }
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedYear = String.format("%d", position + startYear);
                loadData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }
    private void loadData() {
        String userId = sessionManager.getUserId();
        Cursor cursor = incomeDAO.getIncomeByYearAndMonth(userId, selectedYear,selectedMonth);

        transactionList.clear();
        incomeTotal = 0.0;
        paymentTotal = 0.0;

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
            if ("入账".equals(transaction.getDescription())) {
                incomeTotal += transaction.getAmount();
            } else if ("支出".equals(transaction.getDescription())) {
                paymentTotal += transaction.getAmount();
            }
        }

        incomeTextView.setText(String.format("%.2f", incomeTotal));
        paymentTextView.setText(String.format("%.2f", paymentTotal));
        adapter.notifyDataSetChanged();
    }

    public void refreshData() {
        loadData();
    }

    @Override
    public void onItemDeleted() {
        loadData();
    }
}
