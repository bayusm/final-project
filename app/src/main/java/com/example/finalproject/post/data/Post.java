package com.example.finalproject.post.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.finalproject.database.cloud.response.model.PostPageCM;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Post {

    private Integer id;
    private String title;
    private String content;
    private Integer idPublisher;
    private String usernamePublisher;
    private String createdAt;

    private final List<PostCategory> listPostCategories = new ArrayList<>();
    private final List<PostFile> listPostFiles = new ArrayList<>();

    private final List<PostComment> listPostComments = new ArrayList<>();
    private final List<PostLike> listPostLikes = new ArrayList<>();

    public Post(@Nullable Integer id, @Nullable String title, @Nullable String content, @Nullable Integer idPublisher, @Nullable String usernamePublisher, @Nullable String createdAt, @NonNull PostCategory[] arrPostCategories, @Nullable PostFile[] arrPostFiles, @Nullable PostComment[] arrPostComments, @Nullable PostLike[] arrPostLikes) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.idPublisher = idPublisher;
        this.usernamePublisher = usernamePublisher;
        this.createdAt = createdAt;
        Collections.addAll(this.listPostCategories, arrPostCategories);
        if (arrPostFiles != null)
            Collections.addAll(this.listPostFiles, arrPostFiles);
        if (arrPostComments != null)
            Collections.addAll(this.listPostComments, arrPostComments);
        if (arrPostLikes != null)
            Collections.addAll(this.listPostLikes, arrPostLikes);
    }

    public Post(PostPageCM.PostCM cloudModel) {
        this.id = cloudModel.id;
        this.title = cloudModel.title;
        this.content = cloudModel.content;
        this.idPublisher = cloudModel.idUser;
        this.usernamePublisher = cloudModel.user.username;
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            this.createdAt = Objects.requireNonNull(dateFormat.parse(cloudModel.createdAt)).toString();
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        int postCategoryLength = cloudModel.categories.size();
        PostCategory[] postCategories = new PostCategory[postCategoryLength];
        for (int i = 0; i < postCategoryLength; i++) {
            postCategories[i] = new PostCategory(cloudModel.categories.get(i));
        }
        Collections.addAll(this.listPostCategories, postCategories);

        //listPostFile -> null

        if (cloudModel.postCommentsCount > 0) {
            Collections.addAll(this.listPostComments, new PostComment[cloudModel.postCommentsCount]);
        }

        if (cloudModel.postLikesCount > 0) {
            Collections.addAll(this.listPostLikes, new PostLike[cloudModel.postLikesCount]);
        }
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsernamePublisher() {
        return usernamePublisher;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Integer[] getCategoriesId() {
        int ln = listPostCategories.size();
        Integer[] cts = new Integer[ln];
        for (int i = 0; i < ln; i++) {
            cts[i] = listPostCategories.get(i).id;
        }
        return cts;
    }

    public void addCategories(PostCategory[] postCategories) {
        listPostCategories.addAll(Arrays.asList(postCategories));
    }

    public void addFiles(PostFile[] postFiles) {
        listPostFiles.addAll(Arrays.asList(postFiles));
    }

    public PostCategory getMainCategory() {
        return listPostCategories.get(0);
    }

    public PostCategory[] getSubCategories() {
        int subCategoriesLength = listPostCategories.size();
        if (subCategoriesLength < 1) {
            return null;
        }
        PostCategory[] postSubCategories = new PostCategory[subCategoriesLength - 1];
        for (int i = 0; i < subCategoriesLength - 1; i++) {
            postSubCategories[i] = listPostCategories.get(i + 1);
        }
        return postSubCategories;
    }

    public PostFile[] getFiles() {
        if (listPostFiles.size() > 0)
            return this.listPostFiles.toArray(new PostFile[0]);
        return null;
    }

    public int getCommentAmount() {
        return listPostComments.size();
    }

    public int getLikeAmount() {
        return listPostLikes.size();
    }

}
