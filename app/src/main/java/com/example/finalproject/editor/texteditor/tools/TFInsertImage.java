package com.example.finalproject.editor.texteditor.tools;

import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;

import com.example.finalproject.R;
import com.example.finalproject.editor.texteditor.TextEditor;
import com.example.finalproject.editor.texteditor.tools.base.TFBaseImageButton;

public class TFInsertImage extends TFBaseImageButton {

    @NonNull
    @Override
    public View createToolView(TextEditor textEditor) {
        ImageButton toolView = createBaseImageButton(textEditor.requireContext(), R.drawable.ic_tool_insert_image);
        toolView.setOnClickListener(v -> textEditor.openFile(TextEditor.TYPE_IMAGE, "global_image", false));
        return toolView;
    }
}
