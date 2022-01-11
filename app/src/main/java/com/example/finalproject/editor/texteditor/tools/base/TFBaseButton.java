package com.example.finalproject.editor.texteditor.tools.base;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.finalproject.R;
import com.example.finalproject.editor.texteditor.ToolFactory;

public abstract class TFBaseButton implements ToolFactory {

    protected Button createBaseButton(Context context, String text) {
        Button button = new Button(context, null, 0);
        final float scale = context.getResources().getDisplayMetrics().density;
        int heightPxl = (int) (50 * scale + 0.5f);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, heightPxl);
        layoutParams.gravity = Gravity.CENTER;
        button.setLayoutParams(layoutParams);
        int paddingPxl = heightPxl / 6;
        button.setPadding(paddingPxl, paddingPxl, paddingPxl, paddingPxl);
        button.setBackgroundResource(R.drawable.btn_primary);
        button.setText(text);
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        button.setGravity(Gravity.CENTER);
        button.requestLayout();
        return button;
    }

}
