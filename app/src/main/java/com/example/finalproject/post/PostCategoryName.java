package com.example.finalproject.post;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@StringDef({PostCategoryName.BLOG, PostCategoryName.ASK,PostCategoryName.SHARE_SONG})
public @interface PostCategoryName {
    //Main Category
    public static final String BLOG = "Blog";
    public static final String ASK = "Tanya";
    public static final String SHARE_SONG = "Share Musik";
    //Tags/Sub Categories get from server
}