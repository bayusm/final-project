package com.example.finalproject.post.factory;

import androidx.annotation.NonNull;

import com.example.finalproject.editor.texteditor.EditorData;
import com.example.finalproject.editor.texteditor.EditorDataBuilder;
import com.example.finalproject.editor.texteditor.tools.TFCustomButtonInsertFile;
import com.example.finalproject.editor.texteditor.tools.TFRedo;
import com.example.finalproject.editor.texteditor.tools.TFUndo;
import com.example.finalproject.post.PostCategoryID;
import com.example.finalproject.post.PostCategoryName;
import com.example.finalproject.post.data.PostCategory;
import com.example.finalproject.editor.texteditor.TextEditor;

public class ShareSongPostFactory extends PostFactory {
    @NonNull
    @Override
    public PostCategory[] arrPostCategories() {
        return new PostCategory[]{new PostCategory(PostCategoryID.SHARE_SONG, PostCategoryName.SHARE_SONG)};
    }

    @NonNull
    @Override
    public TextEditor createTextEditor() {
        EditorData editorData = new EditorDataBuilder("Masukkan isi konten disini...")
                .addToolFactory("Edit", new TFUndo())
                .addToolFactory("Edit", new TFRedo())
                .addToolFactory("Masukkan musik!", new TFCustomButtonInsertFile("Masukkan musik disini", TextEditor.TYPE_AUDIO, true))
                .build();

        return new TextEditor(editorData);
    }

}