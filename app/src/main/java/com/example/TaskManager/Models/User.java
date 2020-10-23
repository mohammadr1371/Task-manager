package com.example.TaskManager.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int mId;
    @ColumnInfo(name = "username")
    private String mUsername;
    @ColumnInfo(name = "password")
    private String mPassword;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public User(String username, String password) {
        mUsername = username;
        mPassword = password;
    }
}
