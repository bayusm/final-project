package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.finalproject.api.ApiManager;
import com.example.finalproject.database.cloud.response.model.BaseResponseModel;
import com.example.finalproject.database.cloud.response.model.PostDetailModel;
import com.example.finalproject.helper.progressdialog.ProgressDialogHelper;

import org.aviran.cookiebar2.CookieBar;

import jp.wasabeef.richeditor.RichEditor;

public class PostDetailActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ApiManager apiManager;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RichEditor richEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        apiManager = new ApiManager(this);

        initUI();
        initData();
    }

    void initUI() {
        findViewById(R.id.action_btn_back).setOnClickListener(v -> startActivity(new Intent(PostDetailActivity.this, PostListActivity.class)));

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout_post);
        swipeRefreshLayout.setOnRefreshListener(this);

        richEditor = findViewById(R.id.re_text);
        richEditor.setInputEnabled(false);
        richEditor.setEditorBackgroundColor(R.color.transparent);
        richEditor.setEditorFontColor(Color.WHITE);
    }

    void initData() {
        ProgressDialogHelper.show(this);

        int postId = getIntent().getIntExtra(ActivityExtraParameters.POST_DETAIL_ID, 0);

        apiManager.getPostDetail(this::onFinishGetPostDetailData, postId);
    }

    public void onFinishGetPostDetailData(BaseResponseModel<PostDetailModel> baseResponseModel) {
        ProgressDialogHelper.hide(this);

        if (baseResponseModel.success) {
            PostDetailModel data = baseResponseModel.data;
            TextView tvPageTitle = findViewById(R.id.action_tv_title);
            tvPageTitle.setText(data.title);
            richEditor.setHtml(data.content);
        } else {
            CookieBar.build(this)
                    .setTitle("Gagal memuat data")
                    .setMessage(baseResponseModel.message)
                    .setBackgroundColor(R.color.alizarin)
                    .show();
        }
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        initData();
    }
}