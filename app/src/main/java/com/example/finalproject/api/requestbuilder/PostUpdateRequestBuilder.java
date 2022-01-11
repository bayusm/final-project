package com.example.finalproject.api.requestbuilder;

import com.example.finalproject.api.ParameterNames;
import com.example.finalproject.util.VolleyMultipartRequest;
import com.google.gson.Gson;

import java.lang.reflect.Array;

public class PostUpdateRequestBuilder extends ApiRequestBuilder{

    public PostUpdateRequestBuilder(String token, int postId) {
        super();
        method = METHOD_POST;
        completeUrl = BASE_API_URL + "post-update";

        //headers
        addDefaultHeaders();
        headers.put(ParameterNames.AUTHORIZATION, "Bearer " + token);
        //params
        params.put(ParameterNames.ID, Integer.toString(postId));
    }

    public PostUpdateRequestBuilder setTitle(String title) {
        params.put(ParameterNames.TITLE, title);
        return this;
    }

    public PostUpdateRequestBuilder setContent(String content) {
        params.put(ParameterNames.CONTENT, content);
        return this;
    }

    public PostUpdateRequestBuilder setIdSelectedCategories(int[] idSelectedCategories) {
        Gson gson = new Gson();
        String jsonValue = gson.toJson(idSelectedCategories, Array.class);
        params.put(ParameterNames.ID_INSERTED_CATEGORIES, jsonValue);
        return this;
    }

    public PostUpdateRequestBuilder setIdDeletedCategories(int[] idDeletedCategories) {
        Gson gson = new Gson();
        String jsonValue = gson.toJson(idDeletedCategories, Array.class);
        params.put(ParameterNames.ID_DELETED_CATEGORIES, jsonValue);
        return this;
    }

    public PostUpdateRequestBuilder setInsertedFiles(VolleyMultipartRequest.DataPart[] insertedFiles) {

        return this;
    }


}
