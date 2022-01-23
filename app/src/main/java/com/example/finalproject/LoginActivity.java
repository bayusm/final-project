package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.finalproject.api.ApiManager;
import com.example.finalproject.database.cloud.response.model.BaseResponseModel;
import com.example.finalproject.database.cloud.response.model.UserLoginModel;
import com.example.finalproject.helper.progressdialog.ProgressDialogHelper;
import com.example.finalproject.helper.sharedpref.SharedPrefHelper;
import com.example.finalproject.helper.sharedpref.SharedPrefKeyName;
import com.example.finalproject.helper.validatetor.ValidateTor;
import com.example.finalproject.user.ActiveUser;

import org.aviran.cookiebar2.CookieBar;

public class LoginActivity extends AppCompatActivity {

    private static final int minimumUsernameAndPasswordLength = 4;

    private ApiManager apiManager;
    private final ValidateTor validateTor = new ValidateTor();

    private EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        apiManager = new ApiManager(this);

        initUI();
    }

    void initUI() {
        findViewById(R.id.action_btn_back).setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, HomeActivity.class)));
        TextView tvPageTitle = findViewById(R.id.action_tv_title);
        tvPageTitle.setText("Login");

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);

        findViewById(R.id.btn_login).setOnClickListener(this::onClickBtnLogin);
    }


    public void onClickBtnLogin(View view) {

        String usernameStr = etUsername.getText().toString();
        String passwordStr = etPassword.getText().toString();

        //validate str
        if (validateTor.isAtleastLength(usernameStr, minimumUsernameAndPasswordLength)
                && validateTor.isAlphanumeric(usernameStr)
                && validateTor.isAtleastLength(passwordStr, minimumUsernameAndPasswordLength)
                && validateTor.isAlphanumeric(passwordStr)) {

            ProgressDialogHelper.show(this);
            apiManager.userLogin(this::onFinishUserLogin, usernameStr, passwordStr);
        } else {
            CookieBar.build(this)
                    .setTitle("Gagal login!")
                    .setMessage("Username dan password harus alphanumeric dan memiliki panjang minimal " + minimumUsernameAndPasswordLength + " karakter")
                    .setBackgroundColor(R.color.alizarin)
                    .setEnableAutoDismiss(false)
                    .show();
        }
    }

    public void onFinishUserLogin(BaseResponseModel<UserLoginModel> baseResponseModel) {
        ProgressDialogHelper.hide(this);

        if (baseResponseModel.success) {
            ActiveUser activeUser = new ActiveUser(baseResponseModel.data);
            SharedPrefHelper.saveObject(this, SharedPrefKeyName.OBJECT_CURRENT_USER_CLASS, activeUser);
            ActiveUser.setActiveUser(activeUser);

            finishAffinity();
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        } else {
            CookieBar.build(this)
                    .setTitle("Gagal Login!")
                    .setMessage(baseResponseModel.message)
                    .setBackgroundColor(R.color.alizarin)
                    .setEnableAutoDismiss(false)
                    .show();
        }
    }
}