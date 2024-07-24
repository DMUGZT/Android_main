// BillsFragment.java
package com.example.test;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.database.DatabaseHelper;
import com.example.test.database.IncomeDAO;
import com.example.test.utils.UserSessionManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BillsFragment extends Fragment {

    private Button btnMonthlyReport, btnYearlyReport;
    private TextView tvBalance, tvYearEndBalance, tvYearlyIncome, tvYearlyIncomeM, tvYearlyExpense, tvYearlyExpenseM,tvMonth,tvMonthIncome,tvMonthExpense,tvMonthEndBalance;
    private RecyclerView recyclerView;
    private MonthlySummaryAdapter monthlyAdapter;
    private YearlySummaryAdapter yearlyAdapter;
    private List<MonthlySummary> monthlySummaries;
    private List<YearlySummary> yearlySummaries;
    private Spinner yearSpinner;
    private String selectedYear;
    private UserSessionManager sessionManager;
    private IncomeDAO incomeDAO;
    private double Income;
    private double Expense;
    private double Balance ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bills, container, false);

        btnMonthlyReport = view.findViewById(R.id.btnMonthlyReport);
        btnYearlyReport = view.findViewById(R.id.btnYearlyReport);
        tvYearEndBalance = view.findViewById(R.id.tvYearEndBalance);
        tvYearlyIncome = view.findViewById(R.id.tvYearlyIncome);
        tvYearlyExpense = view.findViewById(R.id.tvYearlyExpense);
        recyclerView = view.findViewById(R.id.recyclerView);
        tvBalance = view.findViewById(R.id.tvBalance);
        yearSpinner = view.findViewById(R.id.year_spinner);
        tvMonth = view.findViewById(R.id.tvMonth);
        tvYearlyExpenseM = view.findViewById(R.id.tvYearlyExpenseM);
        tvYearlyIncomeM = view.findViewById(R.id.tvYearlyIncomeM);
        tvYearEndBalance = view.findViewById(R.id.tvYearEndBalance);
        tvMonthEndBalance = view.findViewById(R.id.tvMonthEndBalance);
        tvMonthExpense = view.findViewById(R.id.tvMonthExpense);
        tvMonthIncome = view.findViewById(R.id.tvMonthIncome);
        sessionManager = new UserSessionManager(getContext());
        incomeDAO = new IncomeDAO(getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 初始化数据
        monthlySummaries = new ArrayList<>();
        yearlySummaries = new ArrayList<>();

        // 设置月账单适配器
        monthlyAdapter = new MonthlySummaryAdapter(monthlySummaries);
        yearlyAdapter = new YearlySummaryAdapter(yearlySummaries);
        recyclerView.setAdapter(monthlyAdapter);

        btnMonthlyReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateReportView(true);
            }
        });

        btnYearlyReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateReportView(false);
            }
        });

        setupYearSpinner();
        return view;
    }
    public void onResume() {
        super.onResume();
        loadData(true);

    }
    private void updateReportView(boolean isMonthly) {
        if (isMonthly) {
            recyclerView.setAdapter(monthlyAdapter);
            // 更新上方的总收入、总支出、总结余
            tvBalance.setText("年结余");
            tvYearEndBalance.setText(String.format("%.2f", Balance));
            tvYearlyIncome.setText("年收入");
            tvYearlyIncomeM.setText(String.format("%.2f",Income));
            tvYearlyExpense.setText("年支出");
            tvYearlyExpenseM.setText(String.format("%.2f",Income - Balance));
            tvMonth.setText("月份");
            tvMonthIncome.setText("月收入");
            tvMonthExpense.setText("月支出");
            tvMonthEndBalance.setText("月结余");
            // 设置按钮背景色
            btnMonthlyReport.setBackgroundTintList(getResources().getColorStateList(R.color.pink));
            btnYearlyReport.setBackgroundTintList(getResources().getColorStateList(R.color.white));

            // 显示 Spinner
            yearSpinner.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setAdapter(yearlyAdapter);
            // 更新上方的总收入、总支出、总结余
            tvBalance.setText("总结余");
            tvYearEndBalance.setText(String.format("%.2f", Balance));
            tvYearlyIncome.setText("总收入");
            tvYearlyIncomeM.setText(String.format("%.2f",Income));
            tvYearlyExpense.setText("总支出");
            tvYearlyExpenseM.setText(String.format("%.2f",Income - Balance));
            tvMonth.setText("年份");
            tvMonthIncome.setText("年收入");
            tvMonthExpense.setText("年支出");
            tvMonthEndBalance.setText("年结余");

            btnMonthlyReport.setBackgroundTintList(getResources().getColorStateList(R.color.white));
            btnYearlyReport.setBackgroundTintList(getResources().getColorStateList(R.color.pink));

            // 隐藏 Spinner
            yearSpinner.setVisibility(View.INVISIBLE);
        }

        // 加载数据
        loadData(isMonthly);
    }

    @SuppressLint("DefaultLocale")
    private void setupYearSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.years_array, R.layout.spinner_item);
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
                loadData(true); // 加载月账单数据
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }


    private void loadData(boolean isMonthly) {
        // 获取用户ID
        String userId = sessionManager.getUserId();
        Income = 0.0;
        Expense = 0.0;
        Balance = 0.0;

        if (isMonthly) {
            // 获取每月的收入和支出数据
            monthlySummaries.clear();
            for (int i = 1; i <= 12; i++) {
                String month = String.format("%d", i); // 确保月份格式为两位数
                Cursor cursor = incomeDAO.getIncomeByYearAndMonth(userId, selectedYear, month);
                double monthIncome = 0.0, monthExpense = 0.0;

                while (cursor.moveToNext()) {
                    double amount = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_INCOME_AMOUNT));
                    String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_INCOME_DESCRIPTION));

                    if (description.equals("入账")) {
                        monthIncome += amount;
                    } else if (description.equals("支出")) {
                        monthExpense += amount;
                    }
                }
                cursor.close();

                monthlySummaries.add(new MonthlySummary(month + "月", monthIncome, monthExpense, monthIncome - monthExpense));

                // 更新总收入和总支出
                Income += monthIncome;
                Expense += monthExpense;
            }
            Balance = Income - Expense;
            monthlyAdapter.notifyDataSetChanged();
        } else {
            // 获取每年的收入和支出数据
            yearlySummaries.clear();
            for (int year = 2020; year <= Calendar.getInstance().get(Calendar.YEAR); year++) {
                Cursor cursor = incomeDAO.getIncomeByYear(userId, String.valueOf(year));
                double yearIncome = 0.0, yearExpense = 0.0;

                while (cursor.moveToNext()) {
                    double amount = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_INCOME_AMOUNT));
                    String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_INCOME_DESCRIPTION));

                    if (description.equals("入账")) {
                        yearIncome += amount;
                    } else if (description.equals("支出")) {
                        yearExpense += amount;
                    }
                }
                cursor.close();

                yearlySummaries.add(new YearlySummary(year + "年", yearIncome, yearExpense, yearIncome - yearExpense));

                // 更新总收入和总支出
                Income += yearIncome;
                Expense += yearExpense;
            }
            Balance = Income - Expense;
            yearlyAdapter.notifyDataSetChanged();
        }

        // 更新顶部的总收入、总支出和总结余
        tvYearEndBalance.setText(String.format("%.2f", Balance));
        tvYearlyIncomeM.setText(String.format("%.2f", Income));
        tvYearlyExpenseM.setText(String.format("%.2f", Expense));
    }


    public void refreshData() {
        loadData(true);
    }

}
