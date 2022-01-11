package com.example.finalproject.post.factory;

import androidx.annotation.NonNull;

import com.example.finalproject.editor.texteditor.EditorData;
import com.example.finalproject.editor.texteditor.EditorDataBuilder;
import com.example.finalproject.editor.texteditor.tools.TFAlignCenter;
import com.example.finalproject.editor.texteditor.tools.TFAlignLeft;
import com.example.finalproject.editor.texteditor.tools.TFAlignRight;
import com.example.finalproject.editor.texteditor.tools.TFFontBold;
import com.example.finalproject.editor.texteditor.tools.TFFontItalic;
import com.example.finalproject.editor.texteditor.tools.TFFontUnderline;
import com.example.finalproject.editor.texteditor.tools.TFIndent;
import com.example.finalproject.editor.texteditor.tools.TFInsertImage;
import com.example.finalproject.editor.texteditor.tools.TFListBullet;
import com.example.finalproject.editor.texteditor.tools.TFListNumber;
import com.example.finalproject.editor.texteditor.tools.TFOutdent;
import com.example.finalproject.editor.texteditor.tools.TFRedo;
import com.example.finalproject.editor.texteditor.tools.TFUndo;
import com.example.finalproject.post.PostCategoryID;
import com.example.finalproject.editor.texteditor.TextEditor;
import com.example.finalproject.post.PostCategoryName;
import com.example.finalproject.post.data.PostCategory;

public class BlogPostFactory extends PostFactory {

    @NonNull
    @Override
    public PostCategory[] arrPostCategories() {
        return new PostCategory[]{new PostCategory(PostCategoryID.BLOG, PostCategoryName.BLOG)};
    }

    @NonNull
    @Override
    public TextEditor createTextEditor() {
        EditorData editorData = new EditorDataBuilder("Masukkan isi konten disini...")
                .addToolFactory("Edit", new TFUndo())
                .addToolFactory("Edit", new TFRedo())
                .addToolFactory("Font", new TFFontBold())
                .addToolFactory("Font", new TFFontItalic())
                .addToolFactory("Font", new TFFontUnderline())
                .addToolFactory("Paragraph", new TFAlignLeft())
                .addToolFactory("Paragraph", new TFAlignCenter())
                .addToolFactory("Paragraph", new TFAlignRight())
                .addToolFactory("Paragraph", new TFIndent())
                .addToolFactory("Paragraph", new TFOutdent())
                .addToolFactory("Paragraph", new TFListBullet())
                .addToolFactory("Paragraph", new TFListNumber())
                .addToolFactory("Insert", new TFInsertImage())
                .build();

        return new TextEditor(editorData);
    }

}
