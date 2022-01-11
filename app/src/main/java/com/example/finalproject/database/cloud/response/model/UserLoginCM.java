package com.example.finalproject.database.cloud.response.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserLoginCM {
    @SerializedName("user_data")
    @Expose
    public UserCM userData;
    @SerializedName("token")
    @Expose
    public String token;
}
