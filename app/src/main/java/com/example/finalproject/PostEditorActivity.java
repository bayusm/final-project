package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.finalproject.api.ApiManager;
import com.example.finalproject.database.cloud.response.model.BaseResponseCM;
import com.example.finalproject.database.cloud.response.model.DummyCM;
import com.example.finalproject.database.cloud.response.model.SubCategoryListCM;
import com.example.finalproject.editor.texteditor.TextEditor;
import com.example.finalproject.helper.progressdialog.ProgressDialogHelper;
import com.example.finalproject.helper.validatetor.ValidateTor;
import com.example.finalproject.post.data.Post;
import com.example.finalproject.post.data.PostCategory;
import com.example.finalproject.post.data.PostFile;
import com.example.finalproject.post.factory.PostFactory;
import com.example.finalproject.post.factory.PostFactoryServices;

import org.aviran.cookiebar2.CookieBar;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        //create EmptyPostData
        post = postFactory.createEmptyPost();

        //create Categories
        initTags();

        //create TextEditor
        textEditor = postFactory.createTextEditor();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.layout_text_editor, textEditor);
        ft.commit();
    }

    void initTags() {
        ProgressDialogHelper.show(this);

        apiManager.getSubCategories(this::onFinishGetSubCategoriesData, post.getMainCategory().id);
    }

    public void onFinishGetSubCategoriesData(BaseResponseCM<SubCategoryListCM> baseResponseCM) {
        ProgressDialogHelper.hide(this);

        if (baseResponseCM.success) {
            LinearLayout layoutTags = findViewById(R.id.layout_post_tags);
            int tagLength = baseResponseCM.data.subCategories.size();
            for (int i = 0; i < tagLength; i++) {
                PostCategory postCategory = new PostCategory(baseResponseCM.data.subCategories.get(i));
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
                    .setMessage(baseResponseCM.message)
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
        //add files from inserted file in text editor
        Map<String, String> postFilesPath = textEditor.getFixedFilesPath();
        int filesLength = postFilesPath.size();
        if (filesLength > 0) {
            String[] namePostFilesPath = postFilesPath.keySet().toArray(new String[0]);
            PostFile[] postFiles = new PostFile[filesLength];
            for (int i = 0; i < filesLength; i++) {
                //create byte
                File file = new File(Objects.requireNonNull(postFilesPath.get(namePostFilesPath[i])));
                int size = (int) file.length();
                byte[] fileBytes = new byte[size];
                try {
                    BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
                    buf.read(fileBytes, 0, fileBytes.length);
                    buf.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return;
                }
                String fileType = null;
                String extension = MimeTypeMap.getFileExtensionFromUrl(postFilesPath.get(namePostFilesPath[i]));
                ;
                if (extension != null) {
                    fileType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                }
                postFiles[i] = new PostFile(namePostFilesPath[i], fileType, fileBytes);
            }
            post.addFiles(postFiles);
        }
        apiManager.postUpload(this::onFinishPublish, post);
    }

    public void onFinishPublish(BaseResponseCM<DummyCM> baseResponseCM) {
        ProgressDialogHelper.hide(this);

        if (baseResponseCM.success) {
            CookieBar.build(this)
                    .setTitle("Sukses publish!")
                    .setMessage("Berhasil membuat post")
                    .setBackgroundColor(R.color.emerald)
                    .setEnableAutoDismiss(false)
                    .show();
        } else {
            CookieBar.build(this)
                    .setTitle("Gagal publish!")
                    .setMessage(baseResponseCM.message)
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