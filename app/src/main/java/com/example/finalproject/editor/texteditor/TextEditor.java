package com.example.finalproject.editor.texteditor;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.finalproject.R;
import com.example.finalproject.util.FilePicker;
import com.example.finalproject.util.RealPathUtil;

import org.aviran.cookiebar2.CookieBar;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import jp.wasabeef.richeditor.RichEditor;

public class TextEditor extends Fragment {

    private final static Pattern HTML_IMAGE_PATTERN = Pattern.compile("(?<=<img\\ssrc=\")(.*?)(?=\"\\salt=\"\">)");

    private FilePicker filePicker;

    private RichEditor richEditor;
    private final EditorData editorData;

    private final Map<String, List<String>> insertedFiles = new HashMap<>();

    private int currentTab;
    private Button[] tabButtons;
    private final List<View[]> toolViews = new ArrayList<>();

    public TextEditor(EditorData editorData) {
        super();
        this.editorData = editorData;
    }

    public RichEditor getRichEditor() {
        return richEditor;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_text_editor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        richEditor = view.findViewById(R.id.re_text);

        initEditorData(view);
        initFakeRichEditor(view);
    }

    void initEditorData(View view) {

        richEditor.getSettings().setAllowFileAccess(true);
        richEditor.getSettings().setAllowFileAccessFromFileURLs(true);
        richEditor.getSettings().setAllowUniversalAccessFromFileURLs(true);

        richEditor.setPlaceholder(editorData.placeholderTxt);
        richEditor.setEditorFontColor(Color.WHITE);

        int tabLength = editorData.tabs.length;
        if (tabLength <= 0)
            return;

        filePicker = new FilePicker(requireActivity().getActivityResultRegistry(), this::onFinishPickFile);
        getLifecycle().addObserver(filePicker);

        LinearLayout layoutTabs = view.findViewById(R.id.layout_tabs);
        LinearLayout layoutTools = view.findViewById(R.id.layout_tools);

        tabButtons = new Button[tabLength];
        for (int i = 0; i < tabLength; i++) {
            Button tabButton = new Button(requireContext(), null, 0, R.style.TE_Tab_Button);
            tabButton.setText(editorData.tabs[i].name);
            int finalI = i;
            tabButton.setOnClickListener(v -> onClickTab(finalI));
            tabButtons[i] = tabButton;
            layoutTabs.addView(tabButton);

            int toolLength = editorData.tabs[i].toolFactories.length;

            toolViews.add(new View[toolLength]);
            for (int j = 0; j < toolLength; j++) {
                View toolView = editorData.tabs[i].toolFactories[j].createToolView(this);
                toolViews.get(i)[j] = toolView;
                layoutTools.addView(toolView);
            }
        }

        currentTab = 1;
        onClickTab(0);
    }

    public void onClickTab(int index) {
        if (currentTab == index)
            return;

        currentTab = index;
        int tabLength = tabButtons.length;
        for (int i = 0; i < tabLength; i++) {
            if (i == currentTab) {
                tabButtons[i].setBackgroundColor(getResources().getColor(COLOR_ID_SELECTED_TAB));
                for (View toolView : toolViews.get(i)) {
                    toolView.setVisibility(View.VISIBLE);
                }
            } else {
                tabButtons[i].setBackgroundColor(getResources().getColor(COLOR_ID_UNSELECTED_TAB));
                for (View toolView : toolViews.get(i)) {
                    toolView.setVisibility(View.GONE);
                }
            }
        }
    }

    public String getFixedHtmlText() {
        Map<String, String> fixedFilesPath = getFixedFilesPath();
        String fixedHtml = richEditor.getHtml();
        if (fixedFilesPath.size() > 0) {
            //renaming all source to file key name
            for (Map.Entry<String, String> fileData : fixedFilesPath.entrySet()) {
                //future use pattern to avoid detect non html tag
                fixedHtml.replaceAll(fileData.getValue(), fileData.getKey());
            }
        }
        return fixedHtml;
    }

    public Map<String, String> getFixedFilesPath() {
        Map<String, String> fixedFilesPath = new HashMap<>();
        //validate duplicate only in same key
        if (insertedFiles.size() > 0) {
            for (Map.Entry<String, List<String>> entry : insertedFiles.entrySet()) {
                String fileName = entry.getKey();
                int currentIndex = 0;
                for (String filePath : entry.getValue()) {
                    if (!fixedFilesPath.containsValue(filePath)) {
                        fixedFilesPath.put(fileName + "_" + currentIndex, filePath);
                        currentIndex++;
                    }
                }
            }
        }
        return fixedFilesPath;
    }

    int tempFileType;
    String tempKey;
    boolean tempIsSingleFile;

    public void openFile(@FileType int fileType, String key, boolean isSingleFile) {
        tempFileType = fileType;
        tempKey = key;
        tempIsSingleFile = isSingleFile;
        switch (fileType) {
            case TYPE_FILE:
                filePicker.selectFile();
                break;
            case TYPE_IMAGE:
                filePicker.selectImage();
                break;
            case TYPE_AUDIO:
                filePicker.selectAudio();
                break;
        }

    }

    public void onFinishPickFile(Uri uri) {
        String filePath = RealPathUtil.getRealPath(requireContext(), uri);
        if (filePath == null) {
            CookieBar.build(requireActivity())
                    .setTitle("Gagal mengambil file!")
                    .setMessage("Harap izinkan akses penyimpanan file")
                    .setBackgroundColor(R.color.alizarin)
                    .show();
            return;
        }

        if (tempFileType == TYPE_IMAGE) {
            richEditor.insertImage("file://" + filePath, "");
        }

        if (!insertedFiles.containsKey(tempKey)) {
            insertedFiles.put(tempKey, new ArrayList<>());
        }
        if (tempIsSingleFile && Objects.requireNonNull(insertedFiles.get(tempKey)).size() > 0) {
            Objects.requireNonNull(insertedFiles.get(tempKey)).remove(0);//always keep contain 1 file
        }
        Objects.requireNonNull(insertedFiles.get(tempKey)).add(filePath);
    }

    private RichEditor fakeRichEditor;

    void initFakeRichEditor(View view) {
        fakeRichEditor = view.findViewById(R.id.fake_re_text);
        fakeRichEditor.setInputEnabled(false);

        richEditor.setOnTextChangeListener(this::onTrueRichEditorTextChange);
    }

    void onTrueRichEditorTextChange(String text) {
        fakeRichEditor.setHtml(text);
        fakeRichEditor.setVisibility(View.VISIBLE);
        int fakeREHeight = fakeRichEditor.getHeight();
        fakeRichEditor.setVisibility(View.GONE);
        if (fakeREHeight > richEditor.getHeight()) {
            richEditor.setEditorHeight(fakeREHeight);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TYPE_FILE, TYPE_IMAGE, TYPE_AUDIO})
    public @interface FileType {
    }

    public static final int TYPE_FILE = 0;
    public static final int TYPE_IMAGE = 1;
    public static final int TYPE_AUDIO = 2;

    private final static int COLOR_ID_SELECTED_TAB = R.color.cool_grey_9;
    private final static int COLOR_ID_UNSELECTED_TAB = R.color.transparent;

}