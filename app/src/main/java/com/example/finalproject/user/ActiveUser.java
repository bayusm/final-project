package com.example.finalproject.user;

import com.example.finalproject.database.cloud.response.model.UserLoginCM;

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

    public ActiveUser(UserLoginCM userLoginCM)
    {
        this.id = userLoginCM.userData.id;
        this.username = userLoginCM.userData.username;
        this.email = userLoginCM.userData.email;
        this.token = userLoginCM.token;
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
