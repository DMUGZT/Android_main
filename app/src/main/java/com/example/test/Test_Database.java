package com.example.test;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.test.database.DatabaseHelper;

public class Test_Database {
    private DatabaseHelper dbHelper;

    public Test_Database(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void insertTestData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 插入用户表测试数据
        ContentValues userValues = new ContentValues();
        userValues.put("username", "testuser");
        userValues.put("password", "password123");
        userValues.put("nickname", "Tester");
        userValues.put("profile_image", "profile_image_path");
        userValues.put("gender", "M");
        userValues.put("phone", "1234567890");
        userValues.put("email", "testuser@example.com");
        userValues.put("permission", "user");
        db.insert("user", null, userValues);

        // 插入预测收入表测试数据
        ContentValues predictedIncomeValues = new ContentValues();
        predictedIncomeValues.put("project", "Project A");
        predictedIncomeValues.put("amount", 1000);
        db.insert("predicted_income", null, predictedIncomeValues);

        // 插入预测支出表测试数据
        ContentValues predictedExpenseValues = new ContentValues();
        predictedExpenseValues.put("project", "Project B");
        predictedExpenseValues.put("amount", 500);
        db.insert("predicted_expense", null, predictedExpenseValues);

        // 插入收入表测试数据
        ContentValues incomeValues = new ContentValues();
        incomeValues.put("category", "Salary");
        incomeValues.put("date", "2024-07-20");
        incomeValues.put("amount", 3000);
        incomeValues.put("description", "Monthly salary");
        incomeValues.put("cash_type", "Bank Transfer");
        db.insert("income", null, incomeValues);

        // 插入支出表测试数据
        ContentValues expenseValues = new ContentValues();
        expenseValues.put("category", "Groceries");
        expenseValues.put("date", "2024-07-20");
        expenseValues.put("amount", 150);
        expenseValues.put("description", "Grocery shopping");
        expenseValues.put("cash_type", "Credit Card");
        db.insert("expense", null, expenseValues);

        // 插入月度总结表测试数据
        ContentValues monthlySummaryValues = new ContentValues();
        monthlySummaryValues.put("date", "2024-07");
        monthlySummaryValues.put("income_amount", 3000);
        monthlySummaryValues.put("expense_amount", 1500);
        monthlySummaryValues.put("savings", 1500);
        db.insert("monthly_summary", null, monthlySummaryValues);

        // 插入年度总结表测试数据
        ContentValues yearlySummaryValues = new ContentValues();
        yearlySummaryValues.put("date", "2024");
        yearlySummaryValues.put("income_amount", 36000);
        yearlySummaryValues.put("expense_amount", 18000);
        yearlySummaryValues.put("savings", 18000);
        db.insert("yearly_summary", null, yearlySummaryValues);

        db.close();
    }
}
