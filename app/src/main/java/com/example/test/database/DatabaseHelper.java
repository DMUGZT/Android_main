package com.example.test.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.test.AppConstants;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "app.db";
    private static final int DATABASE_VERSION = 4; // 增加数据库版本

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
    public static final String COLUMN_PREDICTED_INCOME_USER_ID = "user_id";
    public static final String COLUMN_PREDICTED_INCOME_ITEM = "item";
    public static final String COLUMN_PREDICTED_INCOME_AMOUNT = "amount";

    // 预测支出表
    public static final String TABLE_PREDICTED_EXPENSE = "predicted_expense";
    public static final String COLUMN_PREDICTED_EXPENSE_ID = "_id";
    public static final String COLUMN_PREDICTED_EXPENSE_USER_ID = "user_id";
    public static final String COLUMN_PREDICTED_EXPENSE_ITEM = "item";
    public static final String COLUMN_PREDICTED_EXPENSE_AMOUNT = "amount";

    // 收入表
    public static final String TABLE_INCOME = "income";
    public static final String COLUMN_INCOME_ID = "_id";
    public static final String COLUMN_INCOME_USER_ID = "user_id";
    public static final String COLUMN_INCOME_CATEGORY = "category";
    public static final String COLUMN_INCOME_DATE = "date";
    public static final String COLUMN_INCOME_AMOUNT = "amount";
    public static final String COLUMN_INCOME_DESCRIPTION = "description";
    public static final String COLUMN_INCOME_FUND_TYPE = "fund_type";

    // 支出表
    public static final String TABLE_EXPENSE = "expense";
    public static final String COLUMN_EXPENSE_ID = "_id";
    public static final String COLUMN_EXPENSE_USER_ID = "user_id";
    public static final String COLUMN_EXPENSE_CATEGORY = "category";
    public static final String COLUMN_EXPENSE_DATE = "date";
    public static final String COLUMN_EXPENSE_AMOUNT = "amount";
    public static final String COLUMN_EXPENSE_DESCRIPTION = "description";
    public static final String COLUMN_EXPENSE_FUND_TYPE = "fund_type";

    // 月度总结表
    public static final String TABLE_MONTHLY_SUMMARY = "monthly_summary";
    public static final String COLUMN_MONTHLY_SUMMARY_ID = "_id";
    public static final String COLUMN_MONTHLY_SUMMARY_USER_ID = "user_id";
    public static final String COLUMN_MONTHLY_SUMMARY_DATE = "date";
    public static final String COLUMN_MONTHLY_SUMMARY_INCOME_AMOUNT = "income_amount";
    public static final String COLUMN_MONTHLY_SUMMARY_EXPENSE_AMOUNT = "expense_amount";
    public static final String COLUMN_MONTHLY_SUMMARY_SAVINGS = "savings";

    // 年度总结表
    public static final String TABLE_YEARLY_SUMMARY = "yearly_summary";
    public static final String COLUMN_YEARLY_SUMMARY_ID = "_id";
    public static final String COLUMN_YEARLY_SUMMARY_USER_ID = "user_id";
    public static final String COLUMN_YEARLY_SUMMARY_DATE = "date";
    public static final String COLUMN_YEARLY_SUMMARY_INCOME_AMOUNT = "income_amount";
    public static final String COLUMN_YEARLY_SUMMARY_EXPENSE_AMOUNT = "expense_amount";
    public static final String COLUMN_YEARLY_SUMMARY_SAVINGS = "savings";

    // 账户表
    public static final String TABLE_ACCOUNT = "account";
    public static final String COLUMN_ACCOUNT_ID = "_id";
    public static final String COLUMN_ACCOUNT_USER_ID = "user_id";
    public static final String COLUMN_ACCOUNT_TYPE = "type";
    public static final String COLUMN_ACCOUNT_BALANCE = "balance";


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
        if (oldVersion < 4) {
            upgradeToVersion4(db);
        }
        // 如果有更多的升级可以添加更多的条件

    }

    private void createTables(SQLiteDatabase db) {
        // 创建用户表
        String createUserTable = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT " +
                ") ";
        db.execSQL(createUserTable);

        // 创建预测收入表
        String createPredictedIncomeTable = "CREATE TABLE IF NOT EXISTS " + TABLE_PREDICTED_INCOME + " (" +
                COLUMN_PREDICTED_INCOME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PREDICTED_INCOME_USER_ID + " INTEGER, " +
                COLUMN_PREDICTED_INCOME_ITEM + " TEXT, " +
                COLUMN_PREDICTED_INCOME_AMOUNT + " REAL, " +
                "FOREIGN KEY(" + COLUMN_PREDICTED_INCOME_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + ")" +
                ")";
        db.execSQL(createPredictedIncomeTable);

        // 创建预测支出表
        String createPredictedExpenseTable = "CREATE TABLE IF NOT EXISTS " + TABLE_PREDICTED_EXPENSE + " (" +
                COLUMN_PREDICTED_EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PREDICTED_EXPENSE_USER_ID + " INTEGER, " +
                COLUMN_PREDICTED_EXPENSE_ITEM + " TEXT, " +
                COLUMN_PREDICTED_EXPENSE_AMOUNT + " REAL, " +
                "FOREIGN KEY(" + COLUMN_PREDICTED_EXPENSE_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + ")" +
                ")";
        db.execSQL(createPredictedExpenseTable);

        // 创建收入表
        String createIncomeTable = "CREATE TABLE IF NOT EXISTS " + TABLE_INCOME + " (" +
                COLUMN_INCOME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_INCOME_USER_ID + " INTEGER, " +
                COLUMN_INCOME_CATEGORY + " TEXT, " +
                COLUMN_INCOME_DATE + " TEXT, " +
                COLUMN_INCOME_AMOUNT + " REAL, " +
                COLUMN_INCOME_DESCRIPTION + " TEXT, " +
                COLUMN_INCOME_FUND_TYPE + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_INCOME_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + ")" +
                ")";
        db.execSQL(createIncomeTable);

        // 创建支出表
        String createExpenseTable = "CREATE TABLE IF NOT EXISTS " + TABLE_EXPENSE + " (" +
                COLUMN_EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EXPENSE_USER_ID + " INTEGER, " +
                COLUMN_EXPENSE_CATEGORY + " TEXT, " +
                COLUMN_EXPENSE_DATE + " TEXT, " +
                COLUMN_EXPENSE_AMOUNT + " REAL, " +
                COLUMN_EXPENSE_DESCRIPTION + " TEXT, " +
                COLUMN_EXPENSE_FUND_TYPE + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_EXPENSE_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + ")" +
                ")";
        db.execSQL(createExpenseTable);

        // 创建月度总结表
        String createMonthlySummaryTable = "CREATE TABLE IF NOT EXISTS " + TABLE_MONTHLY_SUMMARY + " (" +
                COLUMN_MONTHLY_SUMMARY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MONTHLY_SUMMARY_USER_ID + " INTEGER, " +
                COLUMN_MONTHLY_SUMMARY_DATE + " TEXT, " +
                COLUMN_MONTHLY_SUMMARY_INCOME_AMOUNT + " REAL, " +
                COLUMN_MONTHLY_SUMMARY_EXPENSE_AMOUNT + " REAL, " +
                COLUMN_MONTHLY_SUMMARY_SAVINGS + " REAL, " +
                "FOREIGN KEY(" + COLUMN_MONTHLY_SUMMARY_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + ")" +
                ")";
        db.execSQL(createMonthlySummaryTable);

        // 创建年度总结表
        String createYearlySummaryTable = "CREATE TABLE IF NOT EXISTS " + TABLE_YEARLY_SUMMARY + " (" +
                COLUMN_YEARLY_SUMMARY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_YEARLY_SUMMARY_USER_ID + " INTEGER, " +
                COLUMN_YEARLY_SUMMARY_DATE + " TEXT, " +
                COLUMN_YEARLY_SUMMARY_INCOME_AMOUNT + " REAL, " +
                COLUMN_YEARLY_SUMMARY_EXPENSE_AMOUNT + " REAL, " +
                COLUMN_YEARLY_SUMMARY_SAVINGS + " REAL, " +
                "FOREIGN KEY(" + COLUMN_YEARLY_SUMMARY_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + ")" +
                ")";
        db.execSQL(createYearlySummaryTable);

        // 创建账户表
        String createAccountTable = "CREATE TABLE IF NOT EXISTS " + TABLE_ACCOUNT + " (" +
                COLUMN_ACCOUNT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ACCOUNT_USER_ID + " INTEGER, " +
                COLUMN_ACCOUNT_TYPE + " TEXT, " +
                COLUMN_ACCOUNT_BALANCE + " REAL, " +
                "FOREIGN KEY(" + COLUMN_ACCOUNT_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + ")" +
                ")";
        db.execSQL(createAccountTable);


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
    private void upgradeToVersion4(SQLiteDatabase db) {
        // 创建新表
        createTables(db);

    }

    private void dropTables(SQLiteDatabase db) {
        // 删除所有表
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PREDICTED_INCOME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PREDICTED_EXPENSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MONTHLY_SUMMARY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_YEARLY_SUMMARY);
    }
}
