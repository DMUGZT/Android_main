package com.example.test;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test.database.DatabaseHelper;
import com.example.test.database.UserDAO;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

public class UserDetailsActivity extends AppCompatActivity {

    private LinearLayout profileImageView, nicknameTextView, genderTextView, phoneTextView, emailTextView;
    private TextView accountIdTextView, username, nickname, gender, phone, email;
    private ImageView profileImage;
    private UserDAO userDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        // 初始化视图
        accountIdTextView = findViewById(R.id.account_id);
        username = findViewById(R.id.username);
        nickname = findViewById(R.id.nickname);
        gender = findViewById(R.id.gender);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        profileImageView = findViewById(R.id.account_profile_image);
        profileImage = findViewById(R.id.profile_image);

        userDAO = new UserDAO(this);

        MaterialButton saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserDetailsActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                // 处理保存逻辑
            }
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理头像点击事件
                Toast.makeText(UserDetailsActivity.this, "修改头像", Toast.LENGTH_SHORT).show();
            }
        });

        nicknameTextView = findViewById(R.id.account_nickname);
        nicknameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理昵称点击事件
                Toast.makeText(UserDetailsActivity.this, "修改昵称", Toast.LENGTH_SHORT).show();
                // 可以打开一个对话框让用户修改昵称
            }
        });

        genderTextView = findViewById(R.id.account_gender);
        genderTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理性别点击事件
                Toast.makeText(UserDetailsActivity.this, "修改性别", Toast.LENGTH_SHORT).show();
                // 可以打开一个对话框让用户选择性别
            }
        });

        phoneTextView = findViewById(R.id.account_phone);
        phoneTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理手机号点击事件
                Toast.makeText(UserDetailsActivity.this, "修改手机号", Toast.LENGTH_SHORT).show();
                // 可以打开一个对话框让用户修改手机号
            }
        });

        emailTextView = findViewById(R.id.account_email);
        emailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理邮箱点击事件
                Toast.makeText(UserDetailsActivity.this, "修改邮箱", Toast.LENGTH_SHORT).show();
                // 可以打开一个对话框让用户修改邮箱
            }
        });

        // 设置返回按钮点击事件
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // 加载用户信息并更新视图
        loadUserInfo();
    }

    private void loadUserInfo() {
        // 获取用户信息，例如用户ID为1
        String userId = "1"; // 你需要根据实际情况获取用户ID

        Cursor cursor = userDAO.getUserInfo(userId);
        if (cursor != null && cursor.moveToFirst()) {
            String nicknameStr = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NICKNAME));
            String profileImagePath = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PROFILE_IMAGE));
            String usernameStr = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USERNAME));
            String genderStr = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_GENDER));
            String phoneStr = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PHONE));
            String emailStr = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL));

            accountIdTextView.setText(userId);
            if (nicknameStr != null && !nicknameStr.isEmpty()) {
                nickname.setText(nicknameStr);
            } else {
                nickname.setText(usernameStr);
            }
            username.setText(usernameStr);

            if (profileImagePath != null && !profileImagePath.isEmpty()) {
                Bitmap bitmap = BitmapFactory.decodeFile(profileImagePath);
                profileImage.setImageBitmap(bitmap);
            }

            if (genderStr != null && !genderStr.isEmpty()) {
                gender.setText(genderStr);
            }

            if (phoneStr != null && !phoneStr.isEmpty()) {
                phone.setText(phoneStr);
            }

            if (emailStr != null && !emailStr.isEmpty()) {
                email.setText(emailStr);
            }

            cursor.close();
        }
    }
}
