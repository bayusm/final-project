package com.example.finalproject.editor.texteditor.tools.base;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.DrawableRes;

import com.example.finalproject.R;
import com.example.finalproject.editor.texteditor.ToolFactory;

public abstract class TFBaseImageButton implements ToolFactory {

    protected ImageButton createBaseImageButton(Context context, @DrawableRes int drawableId) {
        ImageButton imageButton = new ImageButton(context, null, 0);
        final float scale = context.getResources().getDisplayMetrics().density;
        int pixel = (int) (50 * scale + 0.5f);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(pixel, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;
        imageButton.setLayoutParams(layoutParams);
        int paddingPxl = pixel / 5;
        imageButton.setPadding(paddingPxl, paddingPxl, paddingPxl, paddingPxl);
        imageButton.setBackgroundColor(context.getResources().getColor(R.color.transparent));
        imageButton.setImageResource(drawableId);
        imageButton.requestLayout();
        return imageButton;
    }
}
