package com.example.finalproject.database.cloud.response.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubCategoryListCM {

    @SerializedName("subcategories")
    @Expose
    public List<CategoryCM> subCategories = null;
}
