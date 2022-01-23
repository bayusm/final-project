package com.example.finalproject.database.cloud.response.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostPageModel {

    @SerializedName("data")
    @Expose
    public List<PostCM> data = null;
    @SerializedName("path")
    @Expose
    public String path;
    @SerializedName("per_page")
    @Expose
    public Integer perPage;
    @SerializedName("next_page_url")
    @Expose
    public String nextPageUrl;
    @SerializedName("prev_page_url")
    @Expose
    public String prevPageUrl;

    public static class PostCM {
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("id_user")
        @Expose
        public Integer idUser;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("content")
        @Expose
        public String content;
        @SerializedName("commentable")
        @Expose
        public Boolean commentable;
        @SerializedName("active")
        @Expose
        public Boolean active;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("post_comments_count")
        @Expose
        public Integer postCommentsCount;
        @SerializedName("post_likes_count")
        @Expose
        public Integer postLikesCount;
        @SerializedName("categories")
        @Expose
        public List<CategoryModel> categories = null;
        @SerializedName("user")
        @Expose
        public UserModel user;
    }
}
