package com.example.finalproject.api.requestbuilder;

import com.example.finalproject.api.ParameterNames;

public class UserLoginRequestBuilder extends ApiRequestBuilder{

    public UserLoginRequestBuilder(String username, String password) {
        super();
        method = METHOD_POST;
        completeUrl = BASE_API_URL + "user-login";

        //headers
        addDefaultHeaders();
        //params
        params.put(ParameterNames.USERNAME, username);
        params.put(ParameterNames.PASSWORD, password);
    }
}
