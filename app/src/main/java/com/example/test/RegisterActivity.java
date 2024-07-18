package com.example.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test.database.UserDAO;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    // 定义视图组件
    private EditText usernameEditText, passwordEditText, confirmPasswordEditText;
    private Button registerButton;
    private ImageButton togglePasswordVisibilityButton1, togglePasswordVisibilityButton2;
    private boolean isPasswordVisible1 = false;
    private boolean isPasswordVisible2 = false;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // 绑定视图组件
        TextView textView = findViewById(R.id.loginTextView);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        registerButton = findViewById(R.id.registerButton);
        togglePasswordVisibilityButton1 = findViewById(R.id.togglePasswordVisibilityButton1);
        togglePasswordVisibilityButton2 = findViewById(R.id.togglePasswordVisibilityButton2);
        userDAO = new UserDAO(this);

        // 定义通用的点击事件监听器
        View.OnClickListener togglePasswordVisibilityListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 根据点击的按钮ID执行相应的逻辑
                if (v.getId() == R.id.togglePasswordVisibilityButton1) {
                    togglePasswordVisibility(passwordEditText, togglePasswordVisibilityButton1, isPasswordVisible1);
                    isPasswordVisible1 = !isPasswordVisible1;
                } else if (v.getId() == R.id.togglePasswordVisibilityButton2) {
                    togglePasswordVisibility(confirmPasswordEditText, togglePasswordVisibilityButton2, isPasswordVisible2);
                    isPasswordVisible2 = !isPasswordVisible2;
                }
            }
        };

        // 设置点击事件监听器
        togglePasswordVisibilityButton1.setOnClickListener(togglePasswordVisibilityListener);
        togglePasswordVisibilityButton2.setOnClickListener(togglePasswordVisibilityListener);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                if(isValidUsername(username) && isValidPassword(password)){
                    if(!password.equals(confirmPassword)){
                        Toast.makeText(RegisterActivity.this, "密码不匹配", Toast.LENGTH_SHORT).show();
                    }else if(userDAO.checkUserExists(username)){
                        Toast.makeText(RegisterActivity.this, "用户已存在，可直接登录", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        // 处理注册逻辑，例如将用户信息存储在数据库中
                        userDAO.addUser(username, password);
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                } else {
                    if (!isValidUsername(username)) {
                        Toast.makeText(RegisterActivity.this, "账号必须是4位且只能包含数字和字母", Toast.LENGTH_SHORT).show();
                    return;
                    }
                    if (!isValidPassword(password)) {
                        Toast.makeText(RegisterActivity.this, "密码必须是8位且只能包含数字和字母", Toast.LENGTH_SHORT).show();
                    return;
                    }
                }
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    // 切换密码可见性的方法
    private void togglePasswordVisibility(EditText editText, ImageButton button, boolean isVisible) {
        if (isVisible) {
            // 如果当前是可见状态，则切换为不可见
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            button.setImageResource(R.drawable.ic_visibility_off);
        } else {
            // 如果当前是不可见状态，则切换为可见
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
            button.setImageResource(R.drawable.ic_visibility);
        }
        // 将光标移动到文本的末尾
        editText.setSelection(editText.length());
    }

    private boolean isValidUsername(String username) {
        // 检查账号是否为4位且只能包含数字和字母
        return username.length() >= 4 && Pattern.matches("^[a-zA-Z0-9]+$", username);
    }

    private boolean isValidPassword(String password) {
        // 检查密码是否为8位且只能包含数字和字母
        return password.length() >= 8 && Pattern.matches("^[a-zA-Z0-9]+$", password);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDAO.close();
    }
}
