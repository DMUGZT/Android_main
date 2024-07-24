package com.example.test;

/**
 * 点击事件的回调接口
 */



public interface OnItemActionListener {
    void OnItemClick(int position);

    void OnItemDetail(int position);

    void OnItemDelete(int position);
}
