package com.example.finalproject.post;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({PostCategoryID.BLOG, PostCategoryID.ASK,PostCategoryID.SHARE_SONG})
public @interface PostCategoryID {
    //parent id == -1 => no parent
    //Main Category
    public static final int BLOG = 1;
    public static final int ASK = 2;
    public static final int SHARE_SONG = 3;
    //Tags/Sub Categories get from server
}
