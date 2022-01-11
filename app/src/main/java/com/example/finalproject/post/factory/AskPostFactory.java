package com.example.finalproject.post.factory;

import androidx.annotation.NonNull;

import com.example.finalproject.editor.texteditor.EditorData;
import com.example.finalproject.editor.texteditor.EditorDataBuilder;
import com.example.finalproject.editor.texteditor.tools.TFRedo;
import com.example.finalproject.editor.texteditor.tools.TFUndo;
import com.example.finalproject.post.PostCategoryID;
import com.example.finalproject.post.PostCategoryName;
import com.example.finalproject.post.data.PostCategory;
import com.example.finalproject.editor.texteditor.TextEditor;

import java.io.Serializable;

public class AskPostFactory extends PostFactory implements Serializable {

    @NonNull
    @Override
    public PostCategory[] arrPostCategories() {
        return new PostCategory[]{new PostCategory(PostCategoryID.ASK, PostCategoryName.ASK)};
    }

    @NonNull
    @Override
    public TextEditor createTextEditor() {
        EditorData editorData = new EditorDataBuilder("Masukkan isi konten disini...")
                .addToolFactory("Edit", new TFUndo())
                .addToolFactory("Edit", new TFRedo())
                .build();

        return new TextEditor(editorData);
    }
}
