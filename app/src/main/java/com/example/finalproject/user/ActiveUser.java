package com.example.finalproject.user;

import com.example.finalproject.database.cloud.response.model.UserLoginModel;

public class ActiveUser {

    private static ActiveUser activeUser;

    public static ActiveUser getActiveUser() {
        return activeUser;
    }

    public static void setActiveUser(ActiveUser activeUser) {
        ActiveUser.activeUser = activeUser;
    }

    private Integer id;
    private String username;
    private String email;
    private String token;

    public ActiveUser(Integer id, String username, String email, String token) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.token = token;
    }

    public ActiveUser(UserLoginModel userLoginModel)
    {
        this.id = userLoginModel.userData.id;
        this.username = userLoginModel.userData.username;
        this.email = userLoginModel.userData.email;
        this.token = userLoginModel.token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
