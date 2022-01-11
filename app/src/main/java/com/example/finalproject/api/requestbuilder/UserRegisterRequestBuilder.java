package com.example.finalproject.api.requestbuilder;

import com.example.finalproject.api.ParameterNames;

public class UserRegisterRequestBuilder extends ApiRequestBuilder {

    public UserRegisterRequestBuilder(String username, String password, String email) {
        super();
        method = METHOD_POST;
        completeUrl = BASE_API_URL + "user-register";

        //headers
        addDefaultHeaders();
        //params
        params.put(ParameterNames.USERNAME, username);
        params.put(ParameterNames.PASSWORD, password);
        params.put(ParameterNames.EMAIL, email);
    }

}
