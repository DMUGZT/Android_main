package com.example.test;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.test.database.DatabaseHelper;
import com.example.test.database.UserDAO;
import com.example.test.utils.ImageUtils;
import com.example.test.utils.UserSessionManager;
import com.google.android.material.textview.MaterialTextView;

public class MyFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private UserDAO userDAO;
    private ImageView profileImage;
    private TextView nickname;
    private UserSessionManager sessionManager;

    public MyFragment() {
        // Required empty public constructor
    }

    public static MyFragment newInstance(String param1, String param2) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        userDAO = new UserDAO(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);

        LinearLayout userProfileSection = view.findViewById(R.id.user_profile_section);
        profileImage = view.findViewById(R.id.profile_image);
        nickname = view.findViewById(R.id.nickname);

        MaterialTextView myAccount = view.findViewById(R.id.my_account);
        MaterialTextView settings = view.findViewById(R.id.settings);
        MaterialTextView help = view.findViewById(R.id.help);
        MaterialTextView about = view.findViewById(R.id.about);
        Button logoutButton = view.findViewById(R.id.logout_button);
        sessionManager = new UserSessionManager(getContext());

        // 处理点击事件
        userProfileSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUserProfileClick(v);
            }
        });

        myAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMyAccountClick(v);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSettingsClick(v);
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHelpClick(v);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAboutClick(v);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogoutClick(v);
            }
        });

        loadUserInfo();

        return view;
    }

    private void loadUserInfo() {
        // 获取用户信息，例如用户ID为1
//        sessionManager.getUserId();
        String userId =  sessionManager.getUserId();; // 根据实际情况获取用户ID

        Cursor cursor = userDAO.getUserInfo(userId);
        if (cursor != null && cursor.moveToFirst()) {
            String nickname = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NICKNAME));
            String profileImageBase64 = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PROFILE_IMAGE));
            String usernameText = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USERNAME));

            if (nickname != null && !nickname.isEmpty()) {
               this.nickname.setText(nickname);
            } else {
                this.nickname.setText(usernameText);
            }

            if (profileImageBase64 != null && !profileImageBase64.isEmpty()) {
                Bitmap bitmap = ImageUtils.base64ToBitmap(profileImageBase64);
                profileImage.setImageBitmap(bitmap);
            }

            cursor.close();
        }
    }

    public void onUserProfileClick(View view) {
        Intent intent = new Intent(getActivity(), UserDetailsActivity.class);
        startActivity(intent);
    }

    public void onMyAccountClick(View view) {
        // 跳转到我的账户页面
         Intent intent = new Intent(getActivity(), MyAccountActivity.class);
         startActivity(intent);
    }

    public void onSettingsClick(View view) {
        // 跳转到设置页面
        // Intent intent = new Intent(getActivity(), SettingsActivity.class);
        // startActivity(intent);
    }

    public void onHelpClick(View view) {
        // 跳转到帮助页面
        // Intent intent = new Intent(getActivity(), HelpActivity.class);
        // startActivity(intent);
    }

    public void onAboutClick(View view) {
        // 跳转到关于页面
        // Intent intent = new Intent(getActivity(), AboutActivity.class);
        // startActivity(intent);
    }

    public void onLogoutClick(View view) {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
