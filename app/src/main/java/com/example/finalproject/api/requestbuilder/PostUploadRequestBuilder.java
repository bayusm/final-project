package com.example.finalproject.api.requestbuilder;

import com.example.finalproject.api.ParameterNames;
import com.example.finalproject.util.VolleyMultipartRequest;
import com.google.gson.Gson;

public class PostUploadRequestBuilder extends ApiRequestBuilder {

    public PostUploadRequestBuilder(String token, String title, String content, Integer[] idCategories) {
        super();
        method = METHOD_POST;
        completeUrl = BASE_API_URL + "post-store";

        //headers
        addDefaultHeaders();
//        headers.put(ParameterNames.AUTHORIZATION, "Bearer " + token);
        //params
        params.put(ParameterNames.TOKEN, token);
        params.put(ParameterNames.TITLE, title);
        params.put(ParameterNames.CONTENT, content);
        for (int i = 0; i < idCategories.length; i++) {
            params.put(ParameterNames.ID_CATEGORIES + "[" + i + "]", Integer.toString(idCategories[i]));
        }
    }

    public PostUploadRequestBuilder setFiles(VolleyMultipartRequest.DataPart[] files) {
        int filesLength = files.length;
        for (int i = 0; i < filesLength; i++) {
            dataParams.put(ParameterNames.FLS + "[" + i + "]", files[i]);
        }
        return this;
    }

}
