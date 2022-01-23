package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.finalproject.api.ApiManager;
import com.example.finalproject.database.cloud.response.model.BaseResponseModel;
import com.example.finalproject.database.cloud.response.model.EmptyModel;
import com.example.finalproject.database.cloud.response.model.SubCategoryListModel;
import com.example.finalproject.editor.texteditor.TextEditor;
import com.example.finalproject.helper.progressdialog.ProgressDialogHelper;
import com.example.finalproject.helper.validatetor.ValidateTor;
import com.example.finalproject.post.data.Post;
import com.example.finalproject.post.data.PostCategory;
import com.example.finalproject.post.factory.PostFactory;
import com.example.finalproject.post.factory.PostFactoryServices;

import org.aviran.cookiebar2.CookieBar;

import java.util.HashMap;
import java.util.Map;

public class PostEditorActivity extends AppCompatActivity {

    private ApiManager apiManager;
    private final ValidateTor validateTor = new ValidateTor();

    private PostFactory postFactory;
    private Post post;

    private final Map<Integer, PostCategory> postTags = new HashMap<>();

    private EditText etTitle;
    private TextEditor textEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_editor);

        apiManager = new ApiManager(this);

        initUI();
        initData();
    }


    void initUI() {
        findViewById(R.id.action_btn_back).setOnClickListener(v -> startActivity(new Intent(PostEditorActivity.this, CreatePostActivity.class)));
        TextView tvPageTitle = findViewById(R.id.action_tv_title);
        tvPageTitle.setText("Buat Post");

        etTitle = findViewById(R.id.et_post_title);
        findViewById(R.id.btn_post_publish).setOnClickListener(this::onClickBtnPublish);

    }

    void initData() {
        int type = getIntent().getIntExtra(ActivityExtraParameters.POST_FACTORY_TYPE, 0);
        postFactory = PostFactoryServices.GetInstancePostFactory(type);
        assert postFactory != null;
        post = postFactory.createEmptyPost();

        initTags();

        textEditor = postFactory.createTextEditor();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.layout_text_editor, textEditor);
        ft.commit();
    }

    void initTags() {
        ProgressDialogHelper.show(this);

        apiManager.getSubCategories(this::onFinishGetSubCategoriesData, post.getMainCategory().id);
    }

    public void onFinishGetSubCategoriesData(BaseResponseModel<SubCategoryListModel> baseResponseModel) {
        ProgressDialogHelper.hide(this);

        if (baseResponseModel.success) {
            LinearLayout layoutTags = findViewById(R.id.layout_post_tags);
            int tagLength = baseResponseModel.data.subCategories.size();
            for (int i = 0; i < tagLength; i++) {
                PostCategory postCategory = new PostCategory(baseResponseModel.data.subCategories.get(i));
                Button tagButton = onCreateTagButton();
                tagButton.setText(postCategory.name);
                int finalI = i;
                tagButton.setOnClickListener(v -> {
                    if (postTags.containsKey(finalI)) {
                        postTags.remove(finalI);
                        v.setBackgroundColor(getResources().getColor(R.color.pumpkin));
                    } else {
                        postTags.put(finalI, postCategory);
                        v.setBackgroundColor(getResources().getColor(R.color.carrot));
                    }
                });

                layoutTags.addView(tagButton);
            }
        } else {
            CookieBar.build(this)
                    .setTitle("Gagal memuat data tags!")
                    .setMessage(baseResponseModel.message)
                    .setBackgroundColor(R.color.alizarin)
                    .show();
        }
    }

    public static final int minimumTitleLength = 6;
    public static final int minimumContentLength = 10;

    public void onClickBtnPublish(View view) {
        String titleStr = etTitle.getText().toString();
        String contentStr = textEditor.getFixedHtmlText();

        if (!validateTor.isAtleastLength(titleStr, minimumTitleLength)
                || !validateTor.isAtleastLength(contentStr, 10)) {

            CookieBar.build(this)
                    .setTitle("Gagal publish!")
                    .setMessage("judul harus memiliki panjang karakter minimal " + minimumTitleLength + " karakter, isi konten harus alphanumeric dan memiliki panjang minimal " + minimumContentLength + " karakter")
                    .setBackgroundColor(R.color.alizarin)
                    .setEnableAutoDismiss(false)
                    .show();
            return;
        }

        ProgressDialogHelper.show(this);

        post.setTitle(titleStr);
        post.setContent(contentStr);
        PostCategory[] tags = postTags.values().toArray(new PostCategory[0]);
        post.addCategories(tags);
        apiManager.postUpload(this::onFinishPublish, post);
    }

    public void onFinishPublish(BaseResponseModel<EmptyModel> baseResponseModel) {
        ProgressDialogHelper.hide(this);

        if (baseResponseModel.success) {
            CookieBar.build(this)
                    .setTitle("Sukses publish!")
                    .setMessage("Berhasil membuat post")
                    .setBackgroundColor(R.color.emerald)
                    .setEnableAutoDismiss(false)
                    .show();
        } else {
            CookieBar.build(this)
                    .setTitle("Gagal publish!")
                    .setMessage(baseResponseModel.message)
                    .setBackgroundColor(R.color.alizarin)
                    .setEnableAutoDismiss(false)
                    .show();
        }
        post = postFactory.createEmptyPost();
    }

    public Button onCreateTagButton() {
        Button button = new Button(this, null, 0);
        final float scale = getResources().getDisplayMetrics().density;
        int heightPxl = (int) (40 * scale + 0.5f);
        int marginPxl = heightPxl / 6;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, heightPxl);
        layoutParams.topMargin = marginPxl;
        layoutParams.bottomMargin = marginPxl;
        layoutParams.rightMargin = marginPxl;
        layoutParams.leftMargin = marginPxl;
        button.setLayoutParams(layoutParams);
        button.setPadding(marginPxl, marginPxl, marginPxl, marginPxl);
        button.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.shape_rounded_rect, null));
        button.setBackgroundColor(getResources().getColor(R.color.pumpkin));
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
        button.setTextColor(getResources().getColor(R.color.dark_white_1));
        button.setGravity(Gravity.CENTER);
        button.requestLayout();
        return button;
    }

}