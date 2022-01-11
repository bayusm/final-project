package com.example.finalproject.post.data;

import com.example.finalproject.database.cloud.response.model.CategoryCM;

public class PostCategory {

    public final int id;
    public final Integer idParent;
    public final String name;

    public PostCategory(int id, String name) {
        this.id = id;
        this.idParent = null;
        this.name = name;
    }

    public PostCategory(int id, int parentId, String name) {
        this.id = id;
        this.idParent = parentId;
        this.name = name;
    }

    public PostCategory(CategoryCM cloudModel) {
        this.id = cloudModel.id;
        this.idParent = cloudModel.idParent;
        this.name = cloudModel.name;
    }
}