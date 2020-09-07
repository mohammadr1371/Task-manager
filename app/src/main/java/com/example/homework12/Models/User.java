package com.example.homework12.Models;

import java.io.Serializable;

public class User implements Serializable {

    private String mUsername;
    private int mPassword;

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public int getPassword() {
        return mPassword;
    }

    public void setPassword(int password) {
        mPassword = password;
    }

    public User(String username, int password) {
        mUsername = username;
        mPassword = password;
    }
}
