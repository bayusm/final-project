package com.example.finalproject.database.cloud.response.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostCategoryListModel {

    @SerializedName("categoryList")
    @Expose
    public List<CategoryModel> categoryList = null;
}
