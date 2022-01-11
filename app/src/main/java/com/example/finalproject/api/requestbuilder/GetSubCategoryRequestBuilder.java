package com.example.finalproject.api.requestbuilder;

public class GetSubCategoryRequestBuilder extends ApiRequestBuilder {

    public GetSubCategoryRequestBuilder(int id) {
        super();
        completeUrl = BASE_API_URL + "subcategory-get/" + id;

        //headers
        addDefaultHeaders();
    }

}
