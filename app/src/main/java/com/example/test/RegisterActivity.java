package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test.database.UserDAO;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText usernameEditText, passwordEditText, confirmPasswordEditText;
    private TextInputLayout usernameTextInputLayout, passwordTextInputLayout, confirmPasswordTextInputLayout;
    private MaterialButton registerButton;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 绑定视图组件
        usernameTextInputLayout = findViewById(R.id.usernameTextInputLayout);
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout);
        confirmPasswordTextInputLayout = findViewById(R.id.confirmPasswordTextInputLayout);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        registerButton = findViewById(R.id.registerButton);
        TextView loginTextView = findViewById(R.id.loginTextView);
        userDAO = new UserDAO(this);

        usernameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateUsername();
                }
            }
        });

        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validatePassword();
                }
            }
        });

        confirmPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateConfirmPassword();
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateUsername() && validatePassword() && validateConfirmPassword()) {
                    String username = usernameEditText.getText().toString();
                    String password = passwordEditText.getText().toString();
                    if (userDAO.checkUserExists(username)) {
                        Toast.makeText(RegisterActivity.this, "用户已存在", Toast.LENGTH_SHORT).show();
                    } else {
                        userDAO.addUser(username, password);
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateUsername() {
        String username = usernameEditText.getText().toString();
        if (username.isEmpty() || username.length() < 4 || !Pattern.matches("^[a-zA-Z0-9]+$", username)) {
            usernameTextInputLayout.setError("账号必须是4位及以上且只能包含数字和字母");
            return false;
        } else {
            usernameTextInputLayout.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String password = passwordEditText.getText().toString();
        if (password.isEmpty() || password.length() < 8 || !Pattern.matches("^[a-zA-Z0-9]+$", password)) {
            passwordTextInputLayout.setError("密码必须是8位及以上且只能包含数字和字母");
            return false;
        } else {
            passwordTextInputLayout.setError(null);
            return true;
        }
    }

    private boolean validateConfirmPassword() {
        String confirmPassword = confirmPasswordEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (!confirmPassword.equals(password)) {
            confirmPasswordTextInputLayout.setError("两次输入的密码不匹配");
            return false;
        } else {
            confirmPasswordTextInputLayout.setError(null);
            return true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDAO.close();
    }
}
