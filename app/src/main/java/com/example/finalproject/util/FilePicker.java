package com.example.finalproject.util;

import android.net.Uri;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

public class FilePicker implements DefaultLifecycleObserver {
    private final ActivityResultRegistry mRegistry;
    private ActivityResultLauncher<String> mGetContent;
    private final FinishOpenFile mFinishOpenFile;

    public FilePicker(@NonNull ActivityResultRegistry registry, FinishOpenFile finishOpenFile) {
        mRegistry = registry;
        mFinishOpenFile = finishOpenFile;
    }

    public void onCreate(@NonNull LifecycleOwner owner) {
        // ...

        mGetContent = mRegistry.register("key", owner, new ActivityResultContracts.GetContent(),
                mFinishOpenFile::onFinish);
    }

    public void selectFile() {
        mGetContent.launch("application/*");
    }

    public void selectImage() {
        mGetContent.launch("image/*");
    }

    public void selectAudio() {
        mGetContent.launch("audio/*");
    }

    public interface FinishOpenFile {
        public void onFinish(Uri filePath);
    }
}
