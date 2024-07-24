package com.example.test;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test.database.DatabaseHelper;
import com.example.test.database.UserDAO;
import com.example.test.utils.UserSessionManager;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminActivity extends AppCompatActivity implements OnItemActionListener {

    private UserListView mUserListView;
    private List<User> mUserList;
    private List<User> mFilteredUserList;
    private UserListAdapter mAdapter;
    private UserSessionManager userSessionManager;
    private UserDAO userDAO;

    private EditText mSearchUserNickName;
    private EditText mSearchUsername;
    private Button mSearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mUserListView = findViewById(R.id.userListView);
        mUserListView.setActionListener(this);
        userDAO = new UserDAO(this);

        mSearchUserNickName = findViewById(R.id.searchUserNickName);
        mSearchUsername = findViewById(R.id.searchUsername);
        mSearchButton = findViewById(R.id.searchButton);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = mSearchUserNickName.getText().toString().trim();
                String username = mSearchUsername.getText().toString().trim();
                filterUsers(nickname, username);
            }
        });

        userSessionManager = new UserSessionManager(this);
        String userId = userSessionManager.getUserId();

        // 初始化用户列表
        mUserList = new ArrayList<>();

        // 获取数据库中的用户信息
        Cursor cursor = userDAO.getUserAllInfo(userId);


        // 检查是否有数据
        if (cursor != null) {
            while (cursor.moveToNext()) {
                // 从Cursor中获取用户信息
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_ID));
                String username = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USERNAME));
                String password = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PASSWORD));
                String nickname = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NICKNAME));
                String profileImage = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PROFILE_IMAGE));
                String gender = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_GENDER));
                String phone = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PHONE));
                String email = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL));
                int permission = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_PERMISSION));
                int isdelete = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_IS_DELETE));
                // 创建 User 对象并添加到列表
                mUserList.add(new User(id, username, password, nickname, profileImage, gender, phone, email, permission,isdelete));
                Log.d("AdminActivity", "Found user: " + username);
            }
            cursor.close(); // 记得关闭Cursor
        }else {
            Log.d("AdminActivity", "Cursor is null or empty");
        }


//        // 模拟数据
//        mUserList = new ArrayList<>();
//        mUserList.add(new User(1, "user1", "password1", "Nickname1", "", "Male", "123456789", "user1@example.com", 0));
//        mUserList.add(new User(2, "user2", "password2", "Nickname2", "", "Female", "987654321", "user2@example.com", 1));
//        mUserList.add(new User(3, "user3", "password3", "Nickname3", "", "Male", "111111111", "user3@example.com", 2));

        mFilteredUserList = new ArrayList<>(mUserList);

        // 设置数据到UserListView中
        mAdapter = new UserListAdapter(this, mFilteredUserList, this);
        mUserListView.setAdapter(mAdapter);
        mUserListView.setData(mFilteredUserList);

        // 设置toolbar
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        setSupportActionBar(topAppBar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            // 处理退出按钮点击事件
//            Toast.makeText(this, "退出", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            this.finish();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnItemClick(int position) {
        // 检查position是否有效
        if (position >= 0 && position < mFilteredUserList.size()) {
//            Toast.makeText(this, "Item clicked: " + mFilteredUserList.get(position).getUsername(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void OnItemDetail(int position) {
        // 检查position是否有效
        if (position >= 0 && position < mFilteredUserList.size()) {
            User user = mFilteredUserList.get(position);
            showUserDetailDialog(user);
        }
    }

    @Override
    public void OnItemDelete(int position) {
        // 检查position是否有效
        if (position >= 0 && position < mFilteredUserList.size()) {
            User user = mFilteredUserList.get(position);
            // 切换 isDeleted 状态
            int newStatus = user.getIsDeleted() == 1 ? 0 : 1;
            userDAO.updateUserDeleteStatus(user.getId(), newStatus);

            // 更新用户列表中的状态
            user.setIsDeleted(newStatus);
            mAdapter.notifyDataSetChanged();

            String action = newStatus == 1 ? "删除" : "恢复";
            Toast.makeText(this, action + ": " + user.getUsername(), Toast.LENGTH_SHORT).show();
        }
    }

    private void filterUsers(String nickname, String username) {
        mFilteredUserList = mUserList.stream()
                .filter(user -> (nickname.isEmpty() || user.getNickname().contains(nickname)) &&
                        (username.isEmpty() || user.getUsername().contains(username)))
                .collect(Collectors.toList());
        mAdapter.updateData(mFilteredUserList);
    }

    private void showUserDetailDialog(User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("用户详细信息");
        String power=user.getPermission()==0?"普通用户":"管理员";

        String message = "ID: " + user.getId() + "\n" +
                "用户账号: " + user.getUsername() + "\n" +
                "昵称: " + user.getNickname() + "\n" +
                "密码: " + user.getPassword() + "\n" +
                "性别: " + user.getGender() + "\n" +
                "电话: " + user.getPhone() + "\n" +
                "邮箱: " + user.getEmail() + "\n" +
                "权限: " + power;

        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.show();
    }
}
