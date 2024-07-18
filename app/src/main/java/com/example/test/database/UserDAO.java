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

    public void updatePassword(String username, String newPassword) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_PASSWORD, newPassword);

        database.update(DatabaseHelper.TABLE_USER, values, DatabaseHelper.COLUMN_USERNAME + " = ?", new String[]{username});
    }

    public Cursor getUserInfo(String userId) {
        return database.query(
                DatabaseHelper.TABLE_USER,
                null,
                DatabaseHelper.COLUMN_USER_ID + " = ?",
                new String[]{userId},
                null, null, null);
    }


    public void updateUserProfile(String userId, String nickname, String profileImage, String gender, String phone, String email) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NICKNAME, nickname);
        values.put(DatabaseHelper.COLUMN_PROFILE_IMAGE, profileImage);
        values.put(DatabaseHelper.COLUMN_GENDER, gender);
        values.put(DatabaseHelper.COLUMN_PHONE, phone);
        values.put(DatabaseHelper.COLUMN_EMAIL, email);

        database.update(DatabaseHelper.TABLE_USER, values, DatabaseHelper.COLUMN_USER_ID + " = ?", new String[]{userId});
    }

}