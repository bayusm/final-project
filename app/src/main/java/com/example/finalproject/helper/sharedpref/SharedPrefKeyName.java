package com.example.finalproject.helper.sharedpref;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@StringDef({SharedPrefKeyName.STRING_DEFAULT_DATA_SAVED,SharedPrefKeyName.OBJECT_CURRENT_USER_CLASS,SharedPrefKeyName.SET_OBJECT_LOCAL_POST_CATEGORY_LIST})
public @interface SharedPrefKeyName {
    public static final String STRING_DEFAULT_DATA_SAVED = "defaultdataset";
    public static final String OBJECT_CURRENT_USER_CLASS = "currentuserclass";

    //Post
    public static final String SET_OBJECT_LOCAL_POST_CATEGORY_LIST = "localpostcategorylist";
}
