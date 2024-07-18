package com.example.test.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "app.db";
    private static final int DATABASE_VERSION = 1;

    // 用户表
    public static final String TABLE_USER = "user";
    public static final String COLUMN_USER_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String TABLE_TRANSACTION = "transaction";
    public static final String COLUMN_TRANSACTION_DATE = "date";
    public static final String COLUMN_TRANSACTION_DESCRIPTION = "description";
    public static final String COLUMN_TRANSACTION_AMOUNT = "amount";
    // 其他表可以在这里定义
    // public static final String TABLE_OTHER = "other";
    // public static final String COLUMN_OTHER_ID = "_id";
    // public static final String COLUMN_OTHER_NAME = "name";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTables(db);
        onCreate(db);
    }

    private void createTables(SQLiteDatabase db) {
        // 创建用户表
        String createUserTable = "CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT" +
                ")";
        db.execSQL(createUserTable);

        // 创建其他表
        // String createOtherTable = "CREATE TABLE " + TABLE_OTHER + " (" +
        //         COLUMN_OTHER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        //         COLUMN_OTHER_NAME + " TEXT" +
        //         ")";
        // db.execSQL(createOtherTable);
    }

    private void dropTables(SQLiteDatabase db) {
        // 删除用户表
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // 删除其他表
        // db.execSQL("DROP TABLE IF EXISTS " + TABLE_OTHER);
    }
}