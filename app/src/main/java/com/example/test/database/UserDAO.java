package com.example.test.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserDAO {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public UserDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long addUser(String username, String password) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USERNAME, username);
        values.put(DatabaseHelper.COLUMN_PASSWORD, password);
        return database.insert(DatabaseHelper.TABLE_USER, null, values);
    }

    public boolean checkUserExists(String username) {
        Cursor cursor = database.query(
                DatabaseHelper.TABLE_USER,
                new String[]{DatabaseHelper.COLUMN_USER_ID},
                DatabaseHelper.COLUMN_USERNAME + " = ?",
                new String[]{username},
                null, null, null);

        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }


    public boolean validateUser(String username, String password) {
        Cursor cursor = database.query(
                DatabaseHelper.TABLE_USER,
                new String[]{DatabaseHelper.COLUMN_USER_ID},
                DatabaseHelper.COLUMN_USERNAME + " = ? AND " + DatabaseHelper.COLUMN_PASSWORD + " = ?",
                new String[]{username, password},
                null, null, null);

        boolean isValid = (cursor.getCount() > 0);
        cursor.close();
        return isValid;
    }
}