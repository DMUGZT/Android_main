package com.example.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.utils.ImageUtils;

import java.util.List;

public class UserListAdapter extends ArrayAdapter<User> {

    private Context mContext;
    private List<User> mUsers;
    private OnItemActionListener mListener;

    public UserListAdapter(Context context, List<User> users, OnItemActionListener listener) {
        super(context, 0, users);
        mContext = context;
        mUsers = users;
        mListener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_user_listview, parent, false);
            holder = new ViewHolder();
            holder.ivProfile = convertView.findViewById(R.id.iv_profile);
            holder.tvUsername = convertView.findViewById(R.id.tv_username);
            holder.tvNickname = convertView.findViewById(R.id.tv_nickname);
            holder.btnDetail = convertView.findViewById(R.id.btn_detail);
            holder.btnDelete = convertView.findViewById(R.id.btn_delete);
            holder.itemView = convertView.findViewById(R.id.linear_show); // 新增：获取列表项的根视图
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final User user = mUsers.get(position);

        holder.tvUsername.setText(user.getUsername());
        holder.tvNickname.setText(user.getNickname());
        if (user.getProfileImage() != null && !user.getProfileImage().isEmpty()) {
            Bitmap bitmap = ImageUtils.base64ToBitmap(user.getProfileImage());
            holder.ivProfile.setImageBitmap(bitmap);
        } else {
            holder.ivProfile.setImageResource(R.mipmap.ic_launcher_round);
        }


        // 根据 isDeleted 状态设置按钮的文本和背景颜色
        if (user.getIsDeleted() == 1) {
            holder.btnDelete.setText("解冻");
            holder.itemView.setBackgroundColor(Color.RED); // 设置红色背景
        } else {
            holder.btnDelete.setText("删除");
            holder.itemView.setBackgroundColor(Color.WHITE); // 设置白色背景
        }

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.OnItemDetail(position);
                }
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.OnItemDelete(position);
                }
            }
        });

        return convertView;
    }

    public void updateData(List<User> users) {
        mUsers.clear();
        mUsers.addAll(users);
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        ImageView ivProfile;
        TextView tvUsername;
        TextView tvNickname;
        Button btnDetail;
        Button btnDelete;
        View itemView; // 新增：存储列表项的根视图
    }
}
