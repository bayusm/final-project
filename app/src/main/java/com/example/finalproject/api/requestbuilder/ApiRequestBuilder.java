package com.example.finalproject.api.requestbuilder;

import androidx.annotation.IntDef;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.example.finalproject.api.ParameterNames;
import com.example.finalproject.util.VolleyMultipartRequest;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

public class ApiRequestBuilder {

    public static final String BASE_API_URL = "https://final-project-app-music.herokuapp.com/api/";

    @Method
    protected int method = METHOD_GET;
    protected String completeUrl;
    protected Map<String, String> headers = new HashMap<>();
    protected Map<String, String> params = new HashMap<>();
    protected Map<String, VolleyMultipartRequest.DataPart> dataParams = new HashMap<>();
    protected Response.Listener<NetworkResponse> responseAction;
    protected Response.ErrorListener responseErrorAction;

    protected void addDefaultHeaders() {
        headers.put(ParameterNames.ACCEPT, "application/json");
    }

    public void setResponseAction(Response.Listener<NetworkResponse> responseAction) {
        this.responseAction = responseAction;
    }

    public void setResponseErrorAction(Response.ErrorListener responseErrorAction) {
        this.responseErrorAction = responseErrorAction;
    }

    public VolleyMultipartRequest build() {
        return new VolleyMultipartRequest(method, completeUrl, responseAction, responseErrorAction) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                return headers;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                return dataParams;
            }
        };
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({METHOD_GET, METHOD_POST})
    public @interface Method {
    }

    public static final int METHOD_GET = 0;
    public static final int METHOD_POST = 1;
}
