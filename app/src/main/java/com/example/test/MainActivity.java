package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    BillsFragment billsFragment = new BillsFragment();
    DetailsFragment detailsFragment = new DetailsFragment();
    MyFragment myFragment = new MyFragment();
    CartFragment cartFragment = new CartFragment();

    private ImageView detailsIcon, billsIcon, cartIcon, myIcon;
    private TextView detailsText, billsText, cartText, myText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化 ImageView 和 TextView
        detailsIcon = findViewById(R.id.details_icon);
        billsIcon = findViewById(R.id.bills_icon);
        cartIcon = findViewById(R.id.cart_icon);
        myIcon = findViewById(R.id.my_icon);

        detailsText = findViewById(R.id.details_text);
        billsText = findViewById(R.id.bills_text);
        cartText = findViewById(R.id.cart_text);
        myText = findViewById(R.id.my_text);

        // 添加碎片
        getSupportFragmentManager().beginTransaction().add(R.id.layout, billsFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.layout, detailsFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.layout, myFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.layout, cartFragment).commit();

        getSupportFragmentManager().beginTransaction().show(detailsFragment).hide(myFragment).hide(cartFragment).hide(billsFragment).commit();

        findViewById(R.id.details_section).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetIcons();
                detailsIcon.setColorFilter(getResources().getColor(R.color.selected_color)); // 更改为选中的颜色
                detailsText.setTextColor(getResources().getColor(R.color.selected_color)); // 更改为选中的颜色
                getSupportFragmentManager().beginTransaction().show(detailsFragment).hide(myFragment).hide(cartFragment).hide(billsFragment).commit();
            }
        });
        findViewById(R.id.bills_section).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetIcons();
                billsIcon.setColorFilter(getResources().getColor(R.color.selected_color)); // 更改为选中的颜色
                billsText.setTextColor(getResources().getColor(R.color.selected_color)); // 更改为选中的颜色
                getSupportFragmentManager().beginTransaction().hide(cartFragment).hide(myFragment).show(billsFragment).hide(detailsFragment).commit();
            }
        });
        findViewById(R.id.cart_section).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetIcons();
                cartIcon.setColorFilter(getResources().getColor(R.color.selected_color)); // 更改为选中的颜色
                cartText.setTextColor(getResources().getColor(R.color.selected_color)); // 更改为选中的颜色
                getSupportFragmentManager().beginTransaction().show(cartFragment).hide(myFragment).hide(billsFragment).hide(detailsFragment).commit();
            }
        });
        findViewById(R.id.my_section).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetIcons();
                myIcon.setColorFilter(getResources().getColor(R.color.selected_color)); // 更改为选中的颜色
                myText.setTextColor(getResources().getColor(R.color.selected_color)); // 更改为选中的颜色
                getSupportFragmentManager().beginTransaction().hide(cartFragment).hide(billsFragment).show(myFragment).hide(detailsFragment).commit();
            }
        });
        findViewById(R.id.record_section).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void resetIcons() {
        detailsIcon.setColorFilter(getResources().getColor(R.color.default_color)); // 更改为默认颜色
        billsIcon.setColorFilter(getResources().getColor(R.color.default_color)); // 更改为默认颜色
        cartIcon.setColorFilter(getResources().getColor(R.color.default_color)); // 更改为默认颜色
        myIcon.setColorFilter(getResources().getColor(R.color.default_color)); // 更改为默认颜色

        detailsText.setTextColor(getResources().getColor(R.color.black)); // 更改为默认颜色
        billsText.setTextColor(getResources().getColor(R.color.black)); // 更改为默认颜色
        cartText.setTextColor(getResources().getColor(R.color.black)); // 更改为默认颜色
        myText.setTextColor(getResources().getColor(R.color.black)); // 更改为默认颜色
    }
}
