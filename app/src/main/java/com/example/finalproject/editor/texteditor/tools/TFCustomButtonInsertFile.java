package com.example.finalproject.editor.texteditor.tools;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.finalproject.editor.texteditor.TextEditor;
import com.example.finalproject.editor.texteditor.tools.base.TFBaseButton;

public class TFCustomButtonInsertFile extends TFBaseButton {

    private final String name;//Don't contain "_" (for divider), prefix global (for other non custom insert factory) NEED ANNOTATION!
    private final int fileType;
    private final boolean isSingleFile;//either array

    public TFCustomButtonInsertFile(String name, @TextEditor.FileType int fileType, boolean isSingleFile) {
        this.name = name;
        this.fileType = fileType;
        this.isSingleFile = isSingleFile;
    }

    @NonNull
    @Override
    public View createToolView(TextEditor textEditor) {
        Button toolView = createBaseButton(textEditor.requireContext(), name);
        toolView.setOnClickListener(v -> textEditor.openFile(fileType, name, isSingleFile));
        return toolView;
    }
}
