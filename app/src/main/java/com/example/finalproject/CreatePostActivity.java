package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.finalproject.post.PostCategoryID;

import org.aviran.cookiebar2.CookieBar;

public class CreatePostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        initUI();
    }

    void initUI() {
        findViewById(R.id.action_btn_back).setOnClickListener(v -> startActivity(new Intent(CreatePostActivity.this, HomeActivity.class)));
        TextView tvTitle = findViewById(R.id.action_tv_title);
        tvTitle.setText("Pilih Kategori");

        findViewById(R.id.btn_post_blog).setOnClickListener(this::onClickBtnBlog);
        findViewById(R.id.btn_post_ask).setOnClickListener(this::onClickBtnAsk);
        findViewById(R.id.btn_post_share_song).setOnClickListener(this::onClickBtnShareSong);
    }

    public void onClickBtnBlog(View view) {
        Intent intent = new Intent(CreatePostActivity.this, PostEditorActivity.class);
        intent.putExtra(ActivityExtraParameters.POST_FACTORY_TYPE, PostCategoryID.BLOG);
        startActivity(intent);
    }

    public void onClickBtnAsk(View view) {
        Intent intent = new Intent(CreatePostActivity.this, PostEditorActivity.class);
        intent.putExtra(ActivityExtraParameters.POST_FACTORY_TYPE, PostCategoryID.ASK);
        startActivity(intent);
    }

    public void onClickBtnShareSong(View view) {
        CookieBar.build(this)
                .setMessage("Under Maintenance!")
                .setBackgroundColor(R.color.alizarin)
                .show();
    }

}