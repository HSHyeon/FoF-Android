package com.example.FoF_Android.my;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.example.FoF_Android.Category.CategoryActivity;
import com.example.FoF_Android.R;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout categoryLayout;
    LinearLayout infoLayout;
    LinearLayout pwLayout;
    LinearLayout logoutLayout;
    LinearLayout withdrawalLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        categoryLayout = findViewById(R.id.select_category_layout);
        infoLayout = findViewById(R.id.select_info_layout);
        pwLayout = findViewById(R.id.select_pw_layout);
        logoutLayout = findViewById(R.id.select_logout_layout);
        withdrawalLayout = findViewById(R.id.select_withdrawal_layout);

        categoryLayout.setOnClickListener(this);
        infoLayout.setOnClickListener(this);
        pwLayout.setOnClickListener(this);
        logoutLayout.setOnClickListener(this);
        withdrawalLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.select_category_layout:
                Intent intent = new Intent(this, CategoryActivity.class);
                startActivity(intent);
                break;
            case R.id.select_info_layout:
                break;
            case R.id.select_pw_layout:
                break;
            case R.id.select_logout_layout:
                break;
            case R.id.select_withdrawal_layout:
                break;
        }
    }
}