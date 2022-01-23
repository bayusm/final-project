package com.example.finalproject.database.cloud.response.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryModel {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("id_parent")
    @Expose
    public Integer idParent;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("multiple_child_selected")
    @Expose
    public Boolean multipleChildSelected;
    @SerializedName("deleted_at")
    @Expose
    public String deletedAt;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
}
