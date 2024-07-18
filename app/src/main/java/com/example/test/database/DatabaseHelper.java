package com.example.test.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "app.db";
    private static final int DATABASE_VERSION = 2; // 增加数据库版本

    // 用户表
    public static final String TABLE_USER = "user";
    public static final String COLUMN_USER_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_NICKNAME = "nickname";
    public static final String COLUMN_PROFILE_IMAGE = "profile_image";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PERMISSION = "permission";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            upgradeToVersion2(db);
        }
        // 如果有更多的升级可以添加更多的条件
    }

    private void createTables(SQLiteDatabase db) {
        // 创建用户表
        String createUserTable = "CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_NICKNAME + " TEXT, " +
                COLUMN_PROFILE_IMAGE + " TEXT, " +
                COLUMN_GENDER + " TEXT, " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PERMISSION + " INTEGER" +
                ")";
        db.execSQL(createUserTable);
    }

    private void upgradeToVersion2(SQLiteDatabase db) {
        // 添加新列到现有的用户表
        db.execSQL("ALTER TABLE " + TABLE_USER + " ADD COLUMN " + COLUMN_NICKNAME + " TEXT");
        db.execSQL("ALTER TABLE " + TABLE_USER + " ADD COLUMN " + COLUMN_PROFILE_IMAGE + " TEXT");
        db.execSQL("ALTER TABLE " + TABLE_USER + " ADD COLUMN " + COLUMN_GENDER + " TEXT");
        db.execSQL("ALTER TABLE " + TABLE_USER + " ADD COLUMN " + COLUMN_PHONE + " TEXT");
        db.execSQL("ALTER TABLE " + TABLE_USER + " ADD COLUMN " + COLUMN_EMAIL + " TEXT");
        db.execSQL("ALTER TABLE " + TABLE_USER + " ADD COLUMN " + COLUMN_PERMISSION + " INTEGER");
    }

    private void dropTables(SQLiteDatabase db) {
        // 删除用户表
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
    }
}
