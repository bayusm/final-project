package com.example.finalproject.editor.texteditor;

import android.view.View;

import androidx.annotation.NonNull;

public interface ToolFactory {
    @NonNull
    public View createToolView(TextEditor textEditor);
}
