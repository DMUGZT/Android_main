package com.example.test;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test.database.DatabaseHelper;
import com.example.test.database.UserDAO;
import com.example.test.utils.ImageUtils;
import com.example.test.utils.UserSessionManager;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class UserDetailsActivity extends AppCompatActivity {

    private LinearLayout profileImageView, nicknameTextView, genderTextView, phoneTextView, emailTextView;
    private TextView accountIdTextView, username, nickname, gender, phone, email;
    private ImageView profileImage;
    private UserDAO userDAO;
    private UserSessionManager sessionManager;

    private static final int SELECT_PICTURE = 1;
    private static final int TAKE_PICTURE = 2;

    private Bitmap selectedImageBitmap;

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
        sessionManager = new UserSessionManager(this);

        MaterialButton saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 保存用户数据
                String profileImageBase64 = selectedImageBitmap != null ? ImageUtils.bitmapToBase64(selectedImageBitmap) : null;
                userDAO.updateUserProfile(
                        sessionManager.getUserId(),
                        nickname.getText().toString(),
                        profileImageBase64,
                        gender.getText().toString(),
                        phone.getText().toString(),
                        email.getText().toString()
                );
                Toast.makeText(UserDetailsActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
            }
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

        nicknameTextView = findViewById(R.id.account_nickname);
        nicknameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog("修改昵称", nickname);
            }
        });

        genderTextView = findViewById(R.id.account_gender);
        genderTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGenderDialog();
            }
        });

        phoneTextView = findViewById(R.id.account_phone);
        phoneTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog("修改手机号", phone);
            }
        });

        emailTextView = findViewById(R.id.account_email);
        emailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog("修改邮箱", email);
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
        String userId = sessionManager.getUserId(); // 你需要根据实际情况获取用户ID

        Cursor cursor = userDAO.getUserInfo(userId);
        if (cursor != null && cursor.moveToFirst()) {
            String nicknameStr = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NICKNAME));
            String profileImageBase64 = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PROFILE_IMAGE));
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

            if (profileImageBase64 != null && !profileImageBase64.isEmpty()) {
                Bitmap bitmap = ImageUtils.base64ToBitmap(profileImageBase64);
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

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("选择图片来源");
        String[] pictureDialogItems = {
                "从图库选择",
                "拍照"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallery();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, SELECT_PICTURE);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                if (data != null) {
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(data.getData());
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        profileImage.setImageBitmap(bitmap);
                        selectedImageBitmap = bitmap;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } else if (requestCode == TAKE_PICTURE) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                profileImage.setImageBitmap(thumbnail);
                selectedImageBitmap = thumbnail;
            }
        }
    }

    private void showEditDialog(String title, final TextView textView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);

        final EditText input = new EditText(this);
        if(textView.getText().toString().equals("未绑定") ){
            input.setText("");
        }else{
            input.setText(textView.getText().toString());}
        builder.setView(input);

        builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(input.getText().toString().isEmpty()){
                    dialog.cancel();
                }else{
                    textView.setText(input.getText().toString());
                }

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void showGenderDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择性别");

        final String[] genderOptions = {"男", "女", "保密"};
        int checkedItem = -1;
        String currentGender = gender.getText().toString();
        for (int i = 0; i < genderOptions.length; i++) {
            if (genderOptions[i].equals(currentGender)) {
                checkedItem = i;
                break;
            }
        }

        builder.setSingleChoiceItems(genderOptions, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gender.setText(genderOptions[which]);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }
}
