package com.example.finalproject.editor.texteditor;

public class EditorData {

    public final String placeholderTxt;
    public final Tab[] tabs;

    public EditorData(String placeholderTxt, Tab[] tabs) {
        this.placeholderTxt = placeholderTxt;
        this.tabs = tabs;
    }

    public static class Tab {

        public final String name;
        public final ToolFactory[] toolFactories;

        public Tab(String name, ToolFactory[] toolFactories) {
            this.name = name;
            this.toolFactories = toolFactories;
        }

    }
}