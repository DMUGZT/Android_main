package com.example.test.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AccountDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public AccountDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public Cursor getAllAccounts() {
        return database.query(DatabaseHelper.TABLE_ACCOUNT, null, null, null, null, null, null);
    }

    public void addAccount(String accountType,String userID) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ACCOUNT_TYPE, accountType);
        values.put(DatabaseHelper.COLUMN_ACCOUNT_USER_ID, userID);
        values.put(DatabaseHelper.COLUMN_ACCOUNT_BALANCE, 0.0);
        database.insert(DatabaseHelper.TABLE_ACCOUNT, null, values);
    }

    public void updateAccount(int accountId, double newBalance) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ACCOUNT_BALANCE, newBalance);
        database.update(DatabaseHelper.TABLE_ACCOUNT, values, DatabaseHelper.COLUMN_ACCOUNT_ID + "=?", new String[]{String.valueOf(accountId)});
    }

    public void deleteAccount(int accountId) {
        database.delete(DatabaseHelper.TABLE_ACCOUNT, DatabaseHelper.COLUMN_ACCOUNT_ID + "=?", new String[]{String.valueOf(accountId)});
    }
}
