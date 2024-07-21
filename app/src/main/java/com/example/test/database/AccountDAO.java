package com.example.test.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.test.Account;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AccountDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;


    public AccountDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public Cursor getUserAccounts(String userID) {
        return   database.query(DatabaseHelper.TABLE_ACCOUNT, null,
                DatabaseHelper.COLUMN_ACCOUNT_USER_ID + "=?",
                new String[]{userID}, null, null, null);
    }

    //初始化用户的账号
    public void initUserAccount(String userID){
        // 自定义账户类型列表
        List<String> customAccountTypes = Arrays.asList(
                "现金", "微信钱包", "支付宝", "银行卡", "基金"
        );

        // 遍历自定义账户类型并将其添加到数据库中
        for (String accountType : customAccountTypes) {
            addAccount(accountType, userID);
        }
    }

    // 获取用户的所有账户
    public List<Account> getUserAccountList(String userID) {
        List<Account> accounts = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_ACCOUNT, null,
                DatabaseHelper.COLUMN_ACCOUNT_USER_ID + "=?",
                new String[]{userID}, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ACCOUNT_ID));
            String type = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ACCOUNT_TYPE));
            double balance = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_ACCOUNT_BALANCE));
            accounts.add(new Account(id, type, balance));
        }
        cursor.close();
        return accounts;
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
