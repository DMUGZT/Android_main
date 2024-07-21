package com.example.test.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// TODO 重写ExpenseDAO和IncomeDAO

public class Rebuild_ExpenseDAO {
    private SQLiteDatabase database;
    private SQLiteOpenHelper dbHelper;

    public Rebuild_ExpenseDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // 插入一条记录
    public long addExpense(String category, String date, double amount, String description, String cashType) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_INCOME_CATEGORY, category);
        values.put(DatabaseHelper.COLUMN_INCOME_DATE, date);
        values.put(DatabaseHelper.COLUMN_INCOME_AMOUNT, amount);
        values.put(DatabaseHelper.COLUMN_INCOME_DESCRIPTION, description);
        values.put(DatabaseHelper.COLUMN_INCOME_CASH_TYPE, cashType);

        return database.insert(DatabaseHelper.TABLE_INCOME, null, values);
    }

    // 更新一条记录
    public int updateExpense(String id, String category, String date, double amount, String description, String cashType) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_INCOME_CATEGORY, category);
        values.put(DatabaseHelper.COLUMN_INCOME_DATE, date);
        values.put(DatabaseHelper.COLUMN_INCOME_AMOUNT, amount);
        values.put(DatabaseHelper.COLUMN_INCOME_DESCRIPTION, description);
        values.put(DatabaseHelper.COLUMN_INCOME_CASH_TYPE, cashType);

        return database.update(DatabaseHelper.TABLE_INCOME, values, DatabaseHelper.COLUMN_INCOME_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // 删除一条记录
    public int deleteExpense(String id) {
        return database.delete(DatabaseHelper.TABLE_INCOME, DatabaseHelper.COLUMN_INCOME_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // 查询所有记录
    public Cursor getAllExpenses() {
//        return database.query(DatabaseHelper.TABLE_INCOME, null, null, null, null, null, DatabaseHelper.COLUMN_INCOME_DATE + " DESC");
        return database.query(DatabaseHelper.TABLE_INCOME, null, null, null, null, null, null);
    }

    // 根据ID查询单条记录
    public Cursor getExpenseById(String id) {
        return database.query(DatabaseHelper.TABLE_INCOME, null, DatabaseHelper.COLUMN_INCOME_ID + " = ?", new String[]{id}, null, null, null);
    }
}
