package com.example.finalproject.api;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.api.requestbuilder.ApiRequestBuilder;
import com.example.finalproject.api.requestbuilder.GetPostDetailRequestBuilder;
import com.example.finalproject.api.requestbuilder.GetPostPageRequestBuilder;
import com.example.finalproject.api.requestbuilder.GetSubCategoryRequestBuilder;
import com.example.finalproject.api.requestbuilder.PostUploadRequestBuilder;
import com.example.finalproject.api.requestbuilder.UserLoginRequestBuilder;
import com.example.finalproject.api.requestbuilder.UserRegisterRequestBuilder;
import com.example.finalproject.database.cloud.response.model.DummyCM;
import com.example.finalproject.database.cloud.response.model.PostDetailCM;
import com.example.finalproject.database.cloud.response.model.PostPageCM;
import com.example.finalproject.database.cloud.response.model.SubCategoryListCM;
import com.example.finalproject.database.cloud.response.model.UserLoginCM;
import com.example.finalproject.database.cloud.response.model.BaseResponseCM;
import com.example.finalproject.helper.validatetor.ValidateTor;
import com.example.finalproject.post.data.Post;
import com.example.finalproject.post.data.PostFile;
import com.example.finalproject.user.ActiveUser;
import com.example.finalproject.util.VolleyMultipartRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;

public class ApiManager {

    private final RequestQueue queue;

    public ApiManager(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public <T> void applyApiResponse(ApiRequestBuilder apiRequestBuilder, FinishRequestAction<T> finishRequestAction, Type type) {

        apiRequestBuilder.setResponseAction(response -> {
            BaseResponseCM<T> baseResponseCM = new BaseResponseCM<T>();
            String json = null;

            try {
                json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                Gson gson = new Gson();
                baseResponseCM = gson.fromJson(json, type);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                baseResponseCM.success = false;
                baseResponseCM.message = "Error parsing!";
            }

            finishRequestAction.onResponse(baseResponseCM);
        });
        apiRequestBuilder.setResponseErrorAction(error -> {
            BaseResponseCM<T> baseResponseCM = new BaseResponseCM<T>();
            String json = null;
            try {
                json = new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers));
                ValidateTor validateTor = new ValidateTor();
                if (validateTor.isJSON(json)) {
                    Gson gson = new Gson();
                    baseResponseCM = gson.fromJson(json, type);
                    if (baseResponseCM.message == null) {
                        baseResponseCM.success = false;
                        baseResponseCM.message = "Error! status code " + error.networkResponse.statusCode;
                    } else if (baseResponseCM.message.equals("Unauthenticated.")) {
                        //
                    }
                } else {
                    baseResponseCM.success = false;
                    baseResponseCM.message = "Error! status code " + error.networkResponse.statusCode;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                baseResponseCM.success = false;
                baseResponseCM.message = "Error parsing!";
            }

            finishRequestAction.onResponse(baseResponseCM);
        });

        queue.add(apiRequestBuilder.build());
    }

    public void getSubCategories(@NonNull FinishRequestAction<SubCategoryListCM> finishRequestAction, @NonNull Integer id) {
        GetSubCategoryRequestBuilder getSubCategoryRequestBuilder = new GetSubCategoryRequestBuilder(id);
        Type type = new TypeToken<BaseResponseCM<SubCategoryListCM>>() {
        }.getType();
        applyApiResponse(getSubCategoryRequestBuilder, finishRequestAction, type);
    }

    public void userRegister(@NonNull FinishRequestAction<DummyCM> finishRequestAction, @NonNull String username, @NonNull String password, @NonNull String email) {
        UserRegisterRequestBuilder userRegisterRequestBuilder = new UserRegisterRequestBuilder(username, password, email);
        Type type = new TypeToken<BaseResponseCM<DummyCM>>() {
        }.getType();
        applyApiResponse(userRegisterRequestBuilder, finishRequestAction, type);
    }

    public void userLogin(@NonNull FinishRequestAction<UserLoginCM> finishRequestAction, @NonNull String username, @NonNull String password) {
        UserLoginRequestBuilder userLoginRequestBuilder = new
                UserLoginRequestBuilder(username, password);
        Type type = new TypeToken<BaseResponseCM<UserLoginCM>>() {
        }.getType();
        applyApiResponse(userLoginRequestBuilder, finishRequestAction, type);
    }

    public void getPostPage(@NonNull FinishRequestAction<PostPageCM> finishRequestAction, @Nullable String cursor, @Nullable List<GetPostPageRequestBuilder.CategoryParam> categories, @Nullable String search) {
        GetPostPageRequestBuilder getPostPageRequestBuilder = new GetPostPageRequestBuilder();
        if (cursor != null) {
            getPostPageRequestBuilder.setCursor(cursor);
        }
        if (categories != null && categories.size() > 0) {
            getPostPageRequestBuilder.setCategories((GetPostPageRequestBuilder.CategoryParam[]) categories.toArray());
        }
        if (search != null) {
            getPostPageRequestBuilder.setSearch(search);
        }
        Type type = new TypeToken<BaseResponseCM<PostPageCM>>() {
        }.getType();
        applyApiResponse(getPostPageRequestBuilder, finishRequestAction, type);
    }

    public void getPostDetail(@NonNull FinishRequestAction<PostDetailCM> finishRequestAction, @NonNull Integer postId) {
        GetPostDetailRequestBuilder getPostDetailRequestBuilder = new GetPostDetailRequestBuilder(postId);
        Type type = new TypeToken<BaseResponseCM<PostDetailCM>>() {
        }.getType();
        applyApiResponse(getPostDetailRequestBuilder, finishRequestAction, type);
    }

    public void postUpload(@NonNull FinishRequestAction<DummyCM> finishRequestAction, Post post) {
        PostUploadRequestBuilder postUploadRequestBuilder = new PostUploadRequestBuilder(ActiveUser.getActiveUser().getToken(), post.getTitle(), post.getContent(), post.getCategoriesId());
        PostFile[] postFiles = post.getFiles();
        if (postFiles != null && postFiles.length > 0) {
            VolleyMultipartRequest.DataPart[] filesDataPart = new VolleyMultipartRequest.DataPart[postFiles.length];
            for (int i = 0; i < postFiles.length; i++) {
                filesDataPart[i] = new VolleyMultipartRequest.DataPart(postFiles[i].name, postFiles[i].bytes, postFiles[i].type);
            }
            postUploadRequestBuilder.setFiles(filesDataPart);
        }
        Type type = new TypeToken<BaseResponseCM<DummyCM>>() {
        }.getType();
        applyApiResponse(postUploadRequestBuilder, finishRequestAction, type);
    }

    public void postUpdate(@NonNull FinishRequestAction<DummyCM> finishRequestAction) {

    }

    public void downloadFile() {

    }

    public interface FinishRequestAction<T> {
        public void onResponse(BaseResponseCM<T> baseResponseCM);
    }

}
