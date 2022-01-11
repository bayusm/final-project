package com.example.finalproject.database.cloud.response.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostDetailCM {
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
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("categories")
    @Expose
    public List<CategoryCM> categories = null;
    @SerializedName("comments")
    @Expose
    public List<PostCommentCM> comments = null;
    @SerializedName("files")
    @Expose
    public List<FileCM> files = null;
}
