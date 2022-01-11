package com.example.finalproject.editor.texteditor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EditorDataBuilder {

    private final String placeholderTxt;
    private final Map<String, List<ToolFactory>> tabs = new HashMap<>();
    private final List<String> tabOrdering = new ArrayList<>();

    public EditorDataBuilder(String placeholderTxt) {
        this.placeholderTxt = placeholderTxt;
    }

    public EditorDataBuilder addToolFactory(String tabName, ToolFactory toolFactory) {
        if (!tabs.containsKey(tabName)) {
            tabs.put(tabName, new ArrayList<>());
            tabOrdering.add(tabName);
        }
        Objects.requireNonNull(tabs.get(tabName)).add(toolFactory);
        return this;
    }

    public EditorData build() {
        List<EditorData.Tab> tabList = new ArrayList<>();
        for (String tabName : tabOrdering) {
            EditorData.Tab tb = new EditorData.Tab(tabName, Objects.requireNonNull(tabs.get(tabName)).toArray(new ToolFactory[0]));
            tabList.add(tb);
        }
        return new EditorData(this.placeholderTxt, tabList.toArray(new EditorData.Tab[0]));
    }
}
