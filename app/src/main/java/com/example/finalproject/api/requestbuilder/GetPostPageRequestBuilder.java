package com.example.finalproject.api.requestbuilder;

import com.example.finalproject.api.ParameterNames;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;

public class GetPostPageRequestBuilder extends ApiRequestBuilder {

    public GetPostPageRequestBuilder() {
        super();
        method = METHOD_POST;
        completeUrl = BASE_API_URL + "post-get";

        //headers
        addDefaultHeaders();
    }

    public GetPostPageRequestBuilder setCursor(String cursor) {
        params.put(ParameterNames.CURSOR, cursor);
        return this;
    }

    public GetPostPageRequestBuilder setCategories(CategoryParam[] categories) {
        Gson gson = new Gson();
        int categoryLength = categories.length;
        String[] arrCategory = new String[categoryLength];
        for (int i = 0; i < categoryLength; i++) {
            arrCategory[i] = gson.toJson(categories[i], CategoryParam.class);
        }
        params.put(ParameterNames.CATEGORY, gson.toJson(arrCategory, Array.class));
        return this;
    }

    public GetPostPageRequestBuilder setSearch(String search) {
        params.put(ParameterNames.SEARCH, search);
        return this;
    }

    public static class CategoryParam {

        //null value => -1
        @SerializedName(ParameterNames.ID_CATEGORY_SELECTED)
        public final int idCategorySelected;
        @SerializedName(ParameterNames.ID_PARENT_CATEGORY)
        public final int idParentCategory;

        public CategoryParam(int idCategorySelected, int idParentCategory) {
            this.idCategorySelected = idCategorySelected;
            this.idParentCategory = idParentCategory;
        }

    }
}
