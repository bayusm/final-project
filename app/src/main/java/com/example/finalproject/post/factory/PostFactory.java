package com.example.finalproject.post.factory;

import androidx.annotation.NonNull;

import com.example.finalproject.editor.texteditor.TextEditor;
import com.example.finalproject.post.data.Post;
import com.example.finalproject.post.data.PostCategory;

public abstract class PostFactory {

    @NonNull
    public abstract PostCategory[] arrPostCategories();

    public Post createEmptyPost() {
        return new Post(null, null, null, null, null, null, arrPostCategories(), null, null, null);
    }

    @NonNull
    public abstract TextEditor createTextEditor();
}
