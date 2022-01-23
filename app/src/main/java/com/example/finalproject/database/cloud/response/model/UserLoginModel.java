package com.example.finalproject.database.cloud.response.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserLoginModel {
    @SerializedName("user_data")
    @Expose
    public UserModel userData;
    @SerializedName("token")
    @Expose
    public String token;
}
