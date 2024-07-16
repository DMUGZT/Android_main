package com.example.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.database.UserDAO;

public class ForgotPasswordActivity extends Activity {

    private EditText usernameEditText, newPasswordEditText;
    private Button resetPasswordButton;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        usernameEditText = findViewById(R.id.usernameEditText);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);
        userDAO = new UserDAO(this);

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String newPassword = newPasswordEditText.getText().toString();

                if (username.isEmpty() || newPassword.isEmpty()) {
                    Toast.makeText(ForgotPasswordActivity.this, "请填写用户名和新密码", Toast.LENGTH_SHORT).show();
                } else if (newPassword.length() < 8 || !newPassword.matches("^[a-zA-Z0-9]+$")) {
                    Toast.makeText(ForgotPasswordActivity.this, "密码必须8位以上且只能包含数字和字母", Toast.LENGTH_SHORT).show();
                } else if (!userDAO.checkUserExists(username)) {
                    Toast.makeText(ForgotPasswordActivity.this, "用户名不存在", Toast.LENGTH_SHORT).show();
                } else {
                    userDAO.updatePassword(username, newPassword);
                    Toast.makeText(ForgotPasswordActivity.this, "密码重置成功", Toast.LENGTH_SHORT).show();
                    finish(); // 重置密码成功后关闭当前活动
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDAO.close();
    }
}
