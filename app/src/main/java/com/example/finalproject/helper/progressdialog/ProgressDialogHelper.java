package com.example.finalproject.helper.progressdialog;

import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class ProgressDialogHelper {

    private static final String PROGRESS_DIALOG_TAG = "PROGRESS_TAG";

    public static void show(AppCompatActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        ProgressDialogFragment progressDialogFragment = new ProgressDialogFragment();
        progressDialogFragment.show(fragmentManager, PROGRESS_DIALOG_TAG);
    }

    private static final Handler handler = new Handler();

    public static void hide(AppCompatActivity activity) {
        handler.postDelayed(() -> {
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            ProgressDialogFragment dialog = (ProgressDialogFragment) fragmentManager.findFragmentByTag(PROGRESS_DIALOG_TAG);
            if (dialog != null) {
                dialog.dismiss();
            }
        }, 100);
    }

}
