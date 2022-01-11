package com.example.finalproject.api.requestbuilder;

public class GetPostDetailRequestBuilder extends ApiRequestBuilder {

    public GetPostDetailRequestBuilder(int postId) {
        super();

        completeUrl = BASE_API_URL + "post-get-detail/" + postId;

        //headers
        addDefaultHeaders();
    }
}
