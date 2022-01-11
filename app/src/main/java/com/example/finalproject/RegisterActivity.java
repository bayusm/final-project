package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.finalproject.api.ApiManager;
import com.example.finalproject.database.cloud.response.model.BaseResponseCM;
import com.example.finalproject.database.cloud.response.model.DummyCM;
import com.example.finalproject.database.cloud.response.model.UserLoginCM;
import com.example.finalproject.helper.progressdialog.ProgressDialogHelper;
import com.example.finalproject.helper.sharedpref.SharedPrefHelper;
import com.example.finalproject.helper.sharedpref.SharedPrefKeyName;
import com.example.finalproject.helper.validatetor.ValidateTor;
import com.example.finalproject.user.ActiveUser;

import org.aviran.cookiebar2.CookieBar;
import org.aviran.cookiebar2.OnActionClickListener;

public class RegisterActivity extends AppCompatActivity {

    private static final int minimumUsernameAndPasswordLength = 4;

    private ApiManager apiManager;
    private final ValidateTor validateTor = new ValidateTor();

    private EditText etUsername, etPassword, etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        apiManager = new ApiManager(this);

        initUI();
    }

    void initUI() {
        findViewById(R.id.action_btn_back).setOnClickListener(v -> startActivity(new Intent(RegisterActivity.this, HomeActivity.class)));
        TextView tvPageTitle = findViewById(R.id.action_tv_title);
        tvPageTitle.setText("Register");

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etEmail = findViewById(R.id.et_email);

        findViewById(R.id.btn_register).setOnClickListener(this::onClickBtnRegister);
    }

    public void onClickBtnRegister(View view) {

        String usernameStr = etUsername.getText().toString();
        String passwordStr = etPassword.getText().toString();
        String emailStr = etEmail.getText().toString();

        //validate str
        if (validateTor.isAtleastLength(usernameStr, minimumUsernameAndPasswordLength)
                && validateTor.isAlphanumeric(usernameStr)
                && validateTor.isAtleastLength(passwordStr, minimumUsernameAndPasswordLength)
                && validateTor.isAlphanumeric(passwordStr)
                && validateTor.isEmail(emailStr)) {

            ProgressDialogHelper.show(this);
            apiManager.userRegister(this::onFinishUserRegister, usernameStr, passwordStr, emailStr);
        } else {
            CookieBar.build(this)
                    .setTitle("Gagal register!")
                    .setMessage("Username dan password harus alphanumeric dan memiliki panjang minimal " + minimumUsernameAndPasswordLength + " karakter, Email juga harus benar")
                    .setBackgroundColor(R.color.alizarin)
                    .setEnableAutoDismiss(false)
                    .show();
        }
    }

    public void onFinishUserRegister(BaseResponseCM<DummyCM> baseResponseCM) {
        ProgressDialogHelper.hide(this);

        if (baseResponseCM.success) {
            CookieBar.build(this)
                    .setTitle("Sukses register!")
                    .setMessage("Sekarang kamu bisa login")
                    .setBackgroundColor(R.color.emerald)
                    .setAction("Login Sekarang", () -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)))
                    .setEnableAutoDismiss(false)
                    .show();
        } else {
            CookieBar.build(this)
                    .setTitle("Gagal register!")
                    .setMessage(baseResponseCM.message)
                    .setBackgroundColor(R.color.alizarin)
                    .setEnableAutoDismiss(false)
                    .show();
        }
    }
}