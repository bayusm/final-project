package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.finalproject.helper.sharedpref.SharedPrefHelper;
import com.example.finalproject.helper.sharedpref.SharedPrefKeyName;
import com.example.finalproject.user.ActiveUser;

import org.aviran.cookiebar2.CookieBar;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initUI();
    }

    void initUI() {
        if (ActiveUser.getActiveUser() == null) {
            Button btnLoginPage = findViewById(R.id.btn_login_page);
            Button btnRegisterPage = findViewById(R.id.btn_register_page);
            btnLoginPage.setVisibility(View.VISIBLE);
            btnRegisterPage.setVisibility(View.VISIBLE);
            btnLoginPage.setOnClickListener(this::onClickBtnLoginPage);
            btnRegisterPage.setOnClickListener(this::onClickBtnRegisterPage);
        } else {
            Button btnProfilePage = findViewById(R.id.btn_profile_page);
            Button btnLogout = findViewById(R.id.btn_logout);
            btnProfilePage.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.VISIBLE);
            btnProfilePage.setOnClickListener(this::onClickBtnProfilePage);
            btnLogout.setOnClickListener(this::onClickBtnLogout);

            TextView tvWelcome = findViewById(R.id.tv_welcome);
            String welcomeStr = "Selamat datang " + ActiveUser.getActiveUser().getUsername();
            tvWelcome.setText(welcomeStr);
        }

        findViewById(R.id.btn_setting_page).setOnClickListener(this::onClickBtnSettingPage);

        findViewById(R.id.btn_post_list_page).setOnClickListener(this::onClickBtnForumPage);
        findViewById(R.id.btn_create_post_page).setOnClickListener(this::onClickBtnCreatePostPage);
        findViewById(R.id.btn_my_post_page).setOnClickListener(this::onClickBtnMyPost);
        findViewById(R.id.btn_draft_page).setOnClickListener(this::onClickBtnDraft);
        findViewById(R.id.btn_team_page).setOnClickListener(this::onClickBtnTeamPage);
    }

    public void onClickBtnLoginPage(View view) {
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
    }

    public void onClickBtnRegisterPage(View view) {
        startActivity(new Intent(HomeActivity.this, RegisterActivity.class));
    }

    public void onClickBtnProfilePage(View view) {
//        startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
        CookieBar.build(this)
                .setMessage("Under Maintenance!")
                .setBackgroundColor(R.color.alizarin)
                .show();
    }

    public void onClickBtnLogout(View view) {
        SharedPrefHelper.deleteValue(this, SharedPrefKeyName.OBJECT_CURRENT_USER_CLASS);
        ActiveUser.setActiveUser(null);
        recreate();
    }

    public void onClickBtnSettingPage(View view) {
//        startActivity(new Intent(HomeActivity.this, SettingActivity.class));
        CookieBar.build(this)
                .setMessage("Under Maintenance!")
                .setBackgroundColor(R.color.alizarin)
                .show();
    }

    public void onClickBtnForumPage(View view) {
        startActivity(new Intent(HomeActivity.this, PostListActivity.class));
    }

    public void onClickBtnCreatePostPage(View view) {
        if (ActiveUser.getActiveUser() == null) {
            CookieBar.build(this)
                    .setMessage("Silahkan Login dahulu!")
                    .setBackgroundColor(R.color.alizarin)
                    .show();
        } else {
            startActivity(new Intent(HomeActivity.this, CreatePostActivity.class));
        }
    }

    public void onClickBtnMyPost(View view) {
        CookieBar.build(this)
                .setMessage("Under Maintenance!")
                .setBackgroundColor(R.color.alizarin)
                .show();
    }

    public void onClickBtnDraft(View view) {
        CookieBar.build(this)
                .setMessage("Under Maintenance!")
                .setBackgroundColor(R.color.alizarin)
                .show();
    }

    public void onClickBtnTeamPage(View view) {
        CookieBar.build(this)
                .setMessage("Under Maintenance!")
                .setBackgroundColor(R.color.alizarin)
                .show();
    }
}