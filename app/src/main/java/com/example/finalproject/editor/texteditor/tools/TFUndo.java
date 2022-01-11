package com.example.finalproject.editor.texteditor.tools;

import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;

import com.example.finalproject.R;
import com.example.finalproject.editor.texteditor.TextEditor;
import com.example.finalproject.editor.texteditor.tools.base.TFBaseImageButton;

public class TFUndo extends TFBaseImageButton {
    @NonNull
    @Override
    public View createToolView(TextEditor textEditor) {
        ImageButton imageButton = createBaseImageButton(textEditor.requireContext(), R.drawable.ic_tool_undo);
        imageButton.setOnClickListener(v -> textEditor.getRichEditor().undo());
        return imageButton;
    }
}
