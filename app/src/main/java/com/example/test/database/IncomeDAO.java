package com.example.test.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class IncomeDAO {
    private SQLiteDatabase database;
    private SQLiteOpenHelper dbHelper;

    public IncomeDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // 插入一条收入记录
    public long addIncome(String category, String date, int amount, String description, int cashType,int user_id) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_INCOME_CATEGORY, category);
        values.put(DatabaseHelper.COLUMN_INCOME_DATE, date);
        values.put(DatabaseHelper.COLUMN_INCOME_AMOUNT, amount);
        values.put(DatabaseHelper.COLUMN_INCOME_DESCRIPTION, description);
        values.put(DatabaseHelper.COLUMN_INCOME_CASH_TYPE, cashType);
        values.put(DatabaseHelper.COLUMN_USER_ID, user_id);
//        DatabaseHelper dbHelper = new DatabaseHelper(context);
//        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long newRowId = database.insert(DatabaseHelper.TABLE_INCOME, null, values);


        return newRowId;
    }

    // 更新一条收入记录
//    public int updateIncome(long id, String category, String date, double amount, String description, String cashType) {
//        ContentValues values = new ContentValues();
//        values.put(DatabaseHelper.COLUMN_INCOME_CATEGORY, category);
//        values.put(DatabaseHelper.COLUMN_INCOME_DATE, date);
//        values.put(DatabaseHelper.COLUMN_INCOME_AMOUNT, amount);
//        values.put(DatabaseHelper.COLUMN_INCOME_DESCRIPTION, description);
//        values.put(DatabaseHelper.COLUMN_INCOME_CASH_TYPE, cashType);
//
//        return database.update(DatabaseHelper.TABLE_INCOME, values, DatabaseHelper.COLUMN_INCOME_ID + " = ?", new String[]{String.valueOf(id)});
//    }
//
//    // 删除一条收入记录
//    public int deleteIncome(long id) {
//        return database.delete(DatabaseHelper.TABLE_INCOME, DatabaseHelper.COLUMN_INCOME_ID + " = ?", new String[]{String.valueOf(id)});
//    }
//
//    // 查询所有收入记录
//    public Cursor getAllIncomes() {
//        return database.query(DatabaseHelper.TABLE_INCOME, null, null, null, null, null, DatabaseHelper.COLUMN_INCOME_DATE + " DESC");
//    }

    public boolean DeleteById(int id){
        String whereClause = "_id = ?";
        String[] whereArgs = new String[] { String.valueOf(id) };
        int rowsDeleted = database.delete("income", whereClause, whereArgs);
        return rowsDeleted > 0;
    }
    // 根据用户ID查询收入记录
    public Cursor getIncomeById(long id) {
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_INCOME + " WHERE " + DatabaseHelper.COLUMN_USER_ID + " = " + id +
                " UNION ALL " +
                "SELECT * FROM " + DatabaseHelper.TABLE_EXPENSE + " WHERE " + DatabaseHelper.COLUMN_USER_ID + " = " + id +
                " ORDER BY " + DatabaseHelper.COLUMN_INCOME_DATE + " DESC";
        Cursor cursor = database.rawQuery(query,null);


        return cursor;
    }

    public Cursor getIncomeByYear(String userId, String year) {
        String query = "SELECT * FROM income WHERE user_id = ? AND date LIKE ?";
        Cursor cursor = database.rawQuery(query, new String[]{userId, year + '%'});
        return cursor;
    }

    public Cursor getIncomeByYearAndMonth(String userId, String year, String month) {
        // 构建查询字符串，格式为 "YYYY-M%"
        String queryDate = year + "-" + month + "%";

        String query = "SELECT * FROM income WHERE user_id = ? AND date LIKE ?";
        Cursor cursor = database.rawQuery(query, new String[]{userId, queryDate});
        return cursor;
    }


}