package com.example.test;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.database.AccountDAO;
import com.example.test.database.DatabaseHelper;
import com.example.test.utils.UserSessionManager;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class MyAccountActivity extends AppCompatActivity {

    private AccountDAO accountDAO;
    private AccountAdapter accountAdapter;
    private List<Account> accountList = new ArrayList<>();
    private UserSessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        // 初始化数据库操作对象
        accountDAO = new AccountDAO(this);

        sessionManager = new UserSessionManager(this);

        // 设置 RecyclerView
        RecyclerView recyclerView = findViewById(R.id.accounts_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        accountAdapter = new AccountAdapter(accountList, new AccountAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(Account account) {
                showEditDialog(account);
            }

            @Override
            public void onDeleteClick(Account account) {
                showDeleteConfirmationDialog(account);
            }
        });
        recyclerView.setAdapter(accountAdapter);

        // 设置添加账户按钮点击事件
        MaterialButton addAccountButton = findViewById(R.id.add_account_button);
        addAccountButton.setOnClickListener(v -> showAddAccountDialog());

        // 设置返回按钮点击事件
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // 加载账户数据
        loadAccounts();
    }

    //初始化数据渲染
    private void loadAccounts() {
        accountList.clear();
        Cursor cursor = accountDAO.getAllAccounts();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ACCOUNT_ID));
            String type = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ACCOUNT_TYPE));
            double balance = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_ACCOUNT_BALANCE));
            accountList.add(new Account(id, type, balance));
        }
        cursor.close();
        accountAdapter.notifyDataSetChanged();
    }

    //添加账户
    private void showAddAccountDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("添加账户");

        final EditText input = new EditText(this);
        input.setHint("账户类型");
        builder.setView(input);

        builder.setPositiveButton("添加", (dialog, which) -> {
            String accountType = input.getText().toString();
            if (!accountType.isEmpty()) {
                accountDAO.addAccount(accountType,sessionManager.getUserId());
                AppConstants.addAccountType(accountType);
                loadAccounts();
            } else {
                Toast.makeText(MyAccountActivity.this, "账户类型不能为空", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("取消", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    //修改账户余额
    private void showEditDialog(Account account) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("修改余额");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input.setText(String.valueOf(account.getBalance()));
        builder.setView(input);

        builder.setPositiveButton("保存", (dialog, which) -> {
            try {
                double newBalance = Double.parseDouble(input.getText().toString());
                accountDAO.updateAccount(account.getId(), newBalance);
                loadAccounts();
            } catch (NumberFormatException e) {
                Toast.makeText(MyAccountActivity.this, "无效的金额", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("取消", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    //删除账户
    private void showDeleteConfirmationDialog(final Account account) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("删除账户");
        builder.setMessage("确定要删除这个账户吗？");

        builder.setPositiveButton("删除", (dialog, which) -> {
            accountDAO.deleteAccount(account.getId());
            AppConstants.removeAccountType(account.getType());
            loadAccounts();
        });

        builder.setNegativeButton("取消", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}