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
        values.put(DatabaseHelper.COLUMN_NICKNAME, username);
        values.put(DatabaseHelper.COLUMN_GENDER,"保密");
        values.put(DatabaseHelper.COLUMN_PERMISSION,0);
        values.put(DatabaseHelper.COLUMN_IS_DELETE,0);
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

    public boolean checkUserIsDelete(String username) {
        boolean isDeleted = false;

        // 定义要查询的列
        String[] columns = {DatabaseHelper.COLUMN_IS_DELETE};

        // 定义查询条件
        String selection = DatabaseHelper.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};

        // 查询数据库
        Cursor cursor = database.query(
                DatabaseHelper.TABLE_USER, // 表名
                columns,                   // 要查询的列
                selection,                 // WHERE 子句
                selectionArgs,             // WHERE 子句的参数
                null,                      // GROUP BY 子句
                null,                      // HAVING 子句
                null                       // ORDER BY 子句
        );

        // 检查结果
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int isDeletedColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_IS_DELETE);
                isDeleted = cursor.getInt(isDeletedColumnIndex) > 0; // 1 表示 true, 0 表示 false
            }
            cursor.close();
        }

        return isDeleted;
    }
    public void updateUserDeleteStatus(int userId, int isDeleted) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_IS_DELETE, isDeleted);
        database.update(DatabaseHelper.TABLE_USER, values, DatabaseHelper.COLUMN_USER_ID + "=?", new String[]{String.valueOf(userId)});
    }


    public boolean checkIsAdmin(String username){
        Cursor cursor = database.query(
                DatabaseHelper.TABLE_USER,
                new String[]{DatabaseHelper.COLUMN_PERMISSION},
                DatabaseHelper.COLUMN_USERNAME + " = ?",
                new String[]{username},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String userPower = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PERMISSION));
            cursor.close();
            if(userPower.equals("1")){
                return true;
            }else return false;
        } else {
            return false;
        }
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
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null ! Check userId");
        }
        return database.query(
                DatabaseHelper.TABLE_USER,
                null,
                DatabaseHelper.COLUMN_USER_ID + " = ?",
                new String[]{userId},
                null, null, null);
    }

    public Cursor getUserAllInfo(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null! Check userId.");
        }
            // 查询所有用户的信息
            return database.query(
                    DatabaseHelper.TABLE_USER, // 表名
                    null,                      // 列（null 表示所有列）
                    null,                      // 选择条件（null 表示没有条件，选择所有行）
                    null,                      // 选择参数（null 因为没有选择条件）
                    null,                      // 分组
                    null,                      // 分组后筛选
                    null                       // 排序
            );
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

    public String getUserIdByUsername(String username) {
        Cursor cursor = database.query(
                DatabaseHelper.TABLE_USER,
                new String[]{DatabaseHelper.COLUMN_USER_ID},
                DatabaseHelper.COLUMN_USERNAME + " = ?",
                new String[]{username},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String userId = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_ID));
            cursor.close();
            return userId;
        } else {
            return null;
        }
    }

}