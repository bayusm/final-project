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
import com.example.finalproject.database.cloud.response.model.EmptyModel;
import com.example.finalproject.database.cloud.response.model.PostDetailModel;
import com.example.finalproject.database.cloud.response.model.PostPageModel;
import com.example.finalproject.database.cloud.response.model.SubCategoryListModel;
import com.example.finalproject.database.cloud.response.model.UserLoginModel;
import com.example.finalproject.database.cloud.response.model.BaseResponseModel;
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
            BaseResponseModel<T> baseResponseModel = new BaseResponseModel<T>();
            String json = null;

            try {
                json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                Gson gson = new Gson();
                baseResponseModel = gson.fromJson(json, type);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                baseResponseModel.success = false;
                baseResponseModel.message = "Error parsing!";
            }

            finishRequestAction.onResponse(baseResponseModel);
        });
        apiRequestBuilder.setResponseErrorAction(error -> {
            BaseResponseModel<T> baseResponseModel = new BaseResponseModel<T>();
            String json = null;
            try {
                json = new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers));
                ValidateTor validateTor = new ValidateTor();
                if (validateTor.isJSON(json)) {
                    Gson gson = new Gson();
                    baseResponseModel = gson.fromJson(json, type);
                    if (baseResponseModel.message == null) {
                        baseResponseModel.success = false;
                        baseResponseModel.message = "Error! status code " + error.networkResponse.statusCode;
                    } else if (baseResponseModel.message.equals("Unauthenticated.")) {
                        //
                    }
                } else {
                    baseResponseModel.success = false;
                    baseResponseModel.message = "Error! status code " + error.networkResponse.statusCode;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                baseResponseModel.success = false;
                baseResponseModel.message = "Error parsing!";
            }

            finishRequestAction.onResponse(baseResponseModel);
        });

        queue.add(apiRequestBuilder.build());
    }

    public void getSubCategories(@NonNull FinishRequestAction<SubCategoryListModel> finishRequestAction, @NonNull Integer id) {
        GetSubCategoryRequestBuilder getSubCategoryRequestBuilder = new GetSubCategoryRequestBuilder(id);
        Type type = new TypeToken<BaseResponseModel<SubCategoryListModel>>() {
        }.getType();
        applyApiResponse(getSubCategoryRequestBuilder, finishRequestAction, type);
    }

    public void userRegister(@NonNull FinishRequestAction<EmptyModel> finishRequestAction, @NonNull String username, @NonNull String password, @NonNull String email) {
        UserRegisterRequestBuilder userRegisterRequestBuilder = new UserRegisterRequestBuilder(username, password, email);
        Type type = new TypeToken<BaseResponseModel<EmptyModel>>() {
        }.getType();
        applyApiResponse(userRegisterRequestBuilder, finishRequestAction, type);
    }

    public void userLogin(@NonNull FinishRequestAction<UserLoginModel> finishRequestAction, @NonNull String username, @NonNull String password) {
        UserLoginRequestBuilder userLoginRequestBuilder = new
                UserLoginRequestBuilder(username, password);
        Type type = new TypeToken<BaseResponseModel<UserLoginModel>>() {
        }.getType();
        applyApiResponse(userLoginRequestBuilder, finishRequestAction, type);
    }

    public void getPostPage(@NonNull FinishRequestAction<PostPageModel> finishRequestAction, @Nullable String cursor, @Nullable List<GetPostPageRequestBuilder.CategoryParam> categories, @Nullable String search) {
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
        Type type = new TypeToken<BaseResponseModel<PostPageModel>>() {
        }.getType();
        applyApiResponse(getPostPageRequestBuilder, finishRequestAction, type);
    }

    public void getPostDetail(@NonNull FinishRequestAction<PostDetailModel> finishRequestAction, @NonNull Integer postId) {
        GetPostDetailRequestBuilder getPostDetailRequestBuilder = new GetPostDetailRequestBuilder(postId);
        Type type = new TypeToken<BaseResponseModel<PostDetailModel>>() {
        }.getType();
        applyApiResponse(getPostDetailRequestBuilder, finishRequestAction, type);
    }

    public void postUpload(@NonNull FinishRequestAction<EmptyModel> finishRequestAction, Post post) {
        PostUploadRequestBuilder postUploadRequestBuilder = new PostUploadRequestBuilder(ActiveUser.getActiveUser().getToken(), post.getTitle(), post.getContent(), post.getCategoriesId());
        PostFile[] postFiles = post.getFiles();
        if (postFiles != null && postFiles.length > 0) {
            VolleyMultipartRequest.DataPart[] filesDataPart = new VolleyMultipartRequest.DataPart[postFiles.length];
            for (int i = 0; i < postFiles.length; i++) {
                filesDataPart[i] = new VolleyMultipartRequest.DataPart(postFiles[i].name, postFiles[i].bytes, postFiles[i].type);
            }
            postUploadRequestBuilder.setFiles(filesDataPart);
        }
        Type type = new TypeToken<BaseResponseModel<EmptyModel>>() {
        }.getType();
        applyApiResponse(postUploadRequestBuilder, finishRequestAction, type);
    }

    public void postUpdate(@NonNull FinishRequestAction<EmptyModel> finishRequestAction) {

    }

    public void downloadFile() {

    }

    public interface FinishRequestAction<T> {
        public void onResponse(BaseResponseModel<T> baseResponseModel);
    }

}
