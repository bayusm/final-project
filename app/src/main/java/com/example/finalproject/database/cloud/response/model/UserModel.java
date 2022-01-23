package com.example.finalproject.database.cloud.response.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("role")
    @Expose
    public String role;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("email_verified_at")
    @Expose
    public Object emailVerifiedAt;
    @SerializedName("violation")
    @Expose
    public Integer violation;
    @SerializedName("end_suspend_at")
    @Expose
    public Object endSuspendAt;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
}
