package com.example.test.database;

import android.content.Context;
import android.database.Cursor;
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
    // 预测收入表
    public static final String TABLE_PREDICTED_INCOME = "predicted_income";
    public static final String COLUMN_PREDICTED_INCOME_ID = "_id";
    public static final String COLUMN_PREDICTED_INCOME_PROJECT = "project";
    public static final String COLUMN_PREDICTED_INCOME_AMOUNT = "amount";

    // 预测支出表
    public static final String TABLE_PREDICTED_EXPENSE = "predicted_expense";
    public static final String COLUMN_PREDICTED_EXPENSE_ID = "_id";
    public static final String COLUMN_PREDICTED_EXPENSE_PROJECT = "project";
    public static final String COLUMN_PREDICTED_EXPENSE_AMOUNT = "amount";

    // 收入表
    public static final String TABLE_INCOME = "income";
    public static final String COLUMN_INCOME_ID = "_id";
    public static final String COLUMN_INCOME_CATEGORY = "category";
    public static final String COLUMN_INCOME_DATE = "date";
    public static final String COLUMN_INCOME_AMOUNT = "amount";
    public static final String COLUMN_INCOME_DESCRIPTION = "description";
    public static final String COLUMN_INCOME_CASH_TYPE = "cash_type";

    // 支出表
    public static final String TABLE_EXPENSE = "expense";
    public static final String COLUMN_EXPENSE_ID = "_id";
    public static final String COLUMN_EXPENSE_CATEGORY = "category";
    public static final String COLUMN_EXPENSE_DATE = "date";
    public static final String COLUMN_EXPENSE_AMOUNT = "amount";
    public static final String COLUMN_EXPENSE_DESCRIPTION = "description";
    public static final String COLUMN_EXPENSE_CASH_TYPE = "cash_type";

    // 月度总结表
    public static final String TABLE_MONTHLY_SUMMARY = "monthly_summary";
    public static final String COLUMN_MONTHLY_SUMMARY_ID = "_id";
    public static final String COLUMN_MONTHLY_SUMMARY_DATE = "date";
    public static final String COLUMN_MONTHLY_INCOME_AMOUNT = "income_amount";
    public static final String COLUMN_MONTHLY_EXPENSE_AMOUNT = "expense_amount";
    public static final String COLUMN_MONTHLY_SAVINGS = "savings";

    // 年度总结表
    public static final String TABLE_YEARLY_SUMMARY = "yearly_summary";
    public static final String COLUMN_YEARLY_SUMMARY_ID = "_id";
    public static final String COLUMN_YEARLY_SUMMARY_DATE = "date";
    public static final String COLUMN_YEARLY_INCOME_AMOUNT = "income_amount";
    public static final String COLUMN_YEARLY_EXPENSE_AMOUNT = "expense_amount";
    public static final String COLUMN_YEARLY_SAVINGS = "savings";
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
        if (oldVersion < 3) {
            upgradeToVersion3(db);
        }
        // 如果有更多的升级可以添加更多的条件
    }

    private void createTables(SQLiteDatabase db) {
        // 创建用户表
        String createUserTable = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + " (" +
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
        String createPreIncome = "CREATE TABLE IF NOT EXISTS " + TABLE_PREDICTED_INCOME + " (" +
                COLUMN_PREDICTED_INCOME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PREDICTED_INCOME_PROJECT + " TEXT(20), " +
                COLUMN_PREDICTED_INCOME_AMOUNT + "INTEGER, " +
                "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " +
                TABLE_USER + "(" + COLUMN_USER_ID + ")" +
                ")";
        db.execSQL(createPreIncome);
        String createPreExpense = "CREATE TABLE IF NOT EXISTS " + TABLE_PREDICTED_EXPENSE + " (" +
                COLUMN_PREDICTED_EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PREDICTED_EXPENSE_PROJECT + " TEXT(20), " +
                COLUMN_PREDICTED_EXPENSE_AMOUNT + " INTEGER, " +
                "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " +
                TABLE_USER + "(" + COLUMN_USER_ID + ")" +
                ")";
        db.execSQL(createPreExpense);
        String createIncome = "CREATE TABLE IF NOT EXISTS " + TABLE_INCOME + " (" +
                COLUMN_INCOME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_INCOME_CATEGORY + " TEXT(10), " +
                COLUMN_INCOME_DATE + " DATE, " +
                COLUMN_INCOME_AMOUNT + " INTEGER, " +
                COLUMN_INCOME_DESCRIPTION + " TEXT(20), " +
                COLUMN_INCOME_CASH_TYPE + " INTEGER, " +
                "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " +
                TABLE_USER + "(" + COLUMN_USER_ID + ")" +
                ")";
        db.execSQL(createIncome);
        String createExpense = "CREATE TABLE IF NOT EXISTS " + TABLE_EXPENSE + " (" +
                COLUMN_EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EXPENSE_CATEGORY + " TEXT(10), " +
                COLUMN_EXPENSE_DATE + " DATE, " +
                COLUMN_EXPENSE_AMOUNT + " INTEGER, " +
                COLUMN_EXPENSE_DESCRIPTION + " TEXT(20), " +
                COLUMN_EXPENSE_CASH_TYPE + " INTEGER, " +
                "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " +
                TABLE_USER + "(" + COLUMN_USER_ID + ")" +
                ")";
        db.execSQL(createExpense);
        String createMonthSummary = "CREATE TABLE IF NOT EXISTS " + TABLE_MONTHLY_SUMMARY + " (" +
                COLUMN_MONTHLY_SUMMARY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MONTHLY_SUMMARY_DATE + " DATE, " +
                COLUMN_MONTHLY_INCOME_AMOUNT + " INTEGER, " +
                COLUMN_MONTHLY_EXPENSE_AMOUNT + " INTEGER, " +
                COLUMN_MONTHLY_SAVINGS + " INTEGER, " +
                "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " +
                TABLE_USER + "(" + COLUMN_USER_ID + ")" +
                ")";
        db.execSQL(createMonthSummary);
        String createYearSummary = "CREATE TABLE IF NOT EXISTS " + TABLE_YEARLY_SUMMARY + " (" +
                COLUMN_YEARLY_SUMMARY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_YEARLY_SUMMARY_DATE + " DATE, " +
                COLUMN_YEARLY_INCOME_AMOUNT + " INTEGER, " +
                COLUMN_YEARLY_EXPENSE_AMOUNT + " INTEGER, " +
                COLUMN_YEARLY_SAVINGS + " INTEGER, " +
                "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " +
                TABLE_USER + "(" + COLUMN_USER_ID + ")" +
                ")";
        db.execSQL(createYearSummary);
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
    private void upgradeToVersion3(SQLiteDatabase db) {
        // 创建新表
        createTables(db);
    }

    private void dropTables(SQLiteDatabase db) {
        // 删除所有表
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PREDICTED_EXPENSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PREDICTED_INCOME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MONTHLY_SUMMARY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_YEARLY_SUMMARY);
    }
    public Cursor getAllTransactions() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_INCOME + " UNION ALL SELECT * FROM " + TABLE_EXPENSE + " ORDER BY date DESC";
        return db.rawQuery(query, null);
    }
}
