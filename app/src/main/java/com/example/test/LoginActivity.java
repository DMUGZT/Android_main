package com.example.test;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test.database.UserDAO;
import com.example.test.utils.UserSessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText usernameEditText, passwordEditText;
    private TextView forgotPasswordTextView, registerTextView;
    private MaterialButton loginButton;
    private UserDAO userDAO;
    private UserSessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 初始化组件
        registerTextView = findViewById(R.id.registerTextView);
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        usernameEditText = findViewById(R.id.usernameEditText);

        userDAO = new UserDAO(this);
        sessionManager = new UserSessionManager(this);

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                // 登录逻辑
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "请输入账号密码", Toast.LENGTH_SHORT).show();
                } else {
                    if(userDAO.checkUserIsDelete(username)){
                        Toast.makeText(LoginActivity.this, "用户已被管理员拉黑或则注销", Toast.LENGTH_SHORT).show();
                    }else{
                        if (userDAO.validateUser(username, password)) {
                        //对于用户登录的会话的id记录下来
                        String userId = userDAO.getUserIdByUsername(username);

                        // 保存用户ID到SharedPreferences
                        sessionManager.loginUser(userId);
                            boolean isAdmin = userDAO.checkIsAdmin(username);
                            if (isAdmin) {
                                // 显示一个对话框，提示是否进入管理员界面
                                new AlertDialog.Builder(LoginActivity.this)
                                        .setTitle("管理员登录")
                                        .setMessage("您是管理员，是否进入管理员界面？")
                                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // 跳转到管理员界面
                                                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                                startActivity(intent);
                                                finish(); // 关闭当前活动
                                                Toast.makeText(LoginActivity.this, "欢迎来到管理员界面", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // 跳转到主界面
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finish(); // 关闭当前活动
                                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .setCancelable(false) // 用户不能通过按返回键取消对话框
                                        .show();
                            } else {
                                // 普通用户直接跳转到主界面
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                finish(); // 关闭当前活动
                            }

                    } else {

                            Toast.makeText(LoginActivity.this, "用户密码错误", Toast.LENGTH_SHORT).show();


                    }}
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
