package com.example.finalproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.finalproject.adapter.ListPostAdapter;
import com.example.finalproject.api.ApiManager;
import com.example.finalproject.api.ParameterNames;
import com.example.finalproject.database.cloud.response.model.BaseResponseModel;
import com.example.finalproject.database.cloud.response.model.PostPageModel;
import com.example.finalproject.helper.progressdialog.ProgressDialogHelper;
import com.example.finalproject.post.data.Post;

import org.aviran.cookiebar2.CookieBar;

import java.util.ArrayList;
import java.util.List;

public class PostListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ApiManager apiManager;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListPostAdapter listPostAdapter;

    private RecyclerView rvPosts;
    private EditText etSearchTitle;

    private final List<Post> listPost = new ArrayList<>();

    private String prevPageCursor;
    private String nextPageCursor;

    private String currentPageCursor = null;
    private String currentSearchStr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        initUI();
        initData();
    }

    void initUI() {
        findViewById(R.id.action_btn_back).setOnClickListener(v -> startActivity(new Intent(PostListActivity.this, HomeActivity.class)));

        swipeRefreshLayout = findViewById(R.id.refresh_layout_post);
        swipeRefreshLayout.setOnRefreshListener(this);

        etSearchTitle = findViewById(R.id.et_search_title);
        findViewById(R.id.btn_search).setOnClickListener(this::onClickBtnSearch);
        findViewById(R.id.btn_filter).setOnClickListener(this::onClickBtnFilter);
        findViewById(R.id.btn_previous_page).setOnClickListener(this::onClickBtnPreviousPage);
        findViewById(R.id.btn_next_page).setOnClickListener(this::onClickBtnNextPage);

        rvPosts = findViewById(R.id.rv_post);
    }

    void initData() {
        apiManager = new ApiManager(this);

        listPostAdapter = new ListPostAdapter(listPost);

        rvPosts.setHasFixedSize(true);
        rvPosts.setAdapter(listPostAdapter);

        setPostPage(null);
    }

    void setPostPage(@Nullable String cursor) {
        ProgressDialogHelper.show(this);

        currentPageCursor = cursor;

        int listPostSize = listPost.size();
        if (listPostSize > 0) {
            listPost.clear();
            listPostAdapter.notifyItemRangeRemoved(0, listPostSize);
        }

        apiManager.getPostPage(this::onFinishGetPostPage, cursor, null, currentSearchStr);
    }

    public void onFinishGetPostPage(BaseResponseModel<PostPageModel> baseResponseModel) {
        ProgressDialogHelper.hide(this);

        if (baseResponseModel.success) {
            PostPageModel model = baseResponseModel.data;

            for (PostPageModel.PostCM postCM : model.data) {
                listPost.add(0, new Post(postCM));
                listPostAdapter.notifyItemInserted(0);
            }

            prevPageCursor = getCursorValue(model.prevPageUrl);
            nextPageCursor = getCursorValue(model.nextPageUrl);
            findViewById(R.id.btn_previous_page).setEnabled(prevPageCursor != null);
            findViewById(R.id.btn_next_page).setEnabled(nextPageCursor != null);

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
        setPostPage(currentPageCursor);
    }

    public void onClickBtnSearch(View view) {
        currentSearchStr = etSearchTitle.getText().toString();
        setPostPage(null);
    }

    public void onClickBtnFilter(View view) {
        CookieBar.build(this)
                .setMessage("Under Maintenance!")
                .setBackgroundColor(R.color.alizarin)
                .show();
    }

    public void onClickBtnPreviousPage(View view) {
        setPostPage(prevPageCursor);
    }

    public void onClickBtnNextPage(View view) {
        setPostPage(nextPageCursor);
    }

    private static final String cursorPrefix = ParameterNames.CURSOR + "=";

    String getCursorValue(String urlPage) {
        if (urlPage == null)
            return null;

        StringBuilder stringBuilder = new StringBuilder();

        int urlPageLength = urlPage.length();
        int cursorPrefixLength = cursorPrefix.length();

        boolean prefixFlag = false;
        int currentDetectPrefixIndex = 0;

        for (int i = 0; i < urlPageLength; i++) {
            if (prefixFlag) {
                stringBuilder.append(urlPage.charAt(i));
            } else {
                if (urlPage.charAt(i) == cursorPrefix.charAt(currentDetectPrefixIndex)) {
                    currentDetectPrefixIndex++;
                    if (currentDetectPrefixIndex >= cursorPrefixLength) {
                        prefixFlag = true;
                    }
                } else {
                    currentDetectPrefixIndex = 0;
                }
            }
        }
        return stringBuilder.toString();
    }
}