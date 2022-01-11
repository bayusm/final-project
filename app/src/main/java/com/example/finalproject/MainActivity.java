package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;

import com.example.finalproject.helper.sharedpref.SharedPrefKeyName;
import com.example.finalproject.helper.sharedpref.SharedPrefHelper;
import com.example.finalproject.user.ActiveUser;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initAllData();
    }

    void initAllData() {
        //init UserActive
        ActiveUser activeUser = SharedPrefHelper.getSavedObject(this, SharedPrefKeyName.OBJECT_CURRENT_USER_CLASS, ActiveUser.class);
        if (activeUser != null) {
            ActiveUser.setActiveUser(activeUser);
        }

        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}