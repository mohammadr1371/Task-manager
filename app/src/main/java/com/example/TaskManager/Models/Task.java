package com.example.TaskManager.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "tasks")
public class Task implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int mId;
    @ColumnInfo(name = "name")
    private String mName;
    @ColumnInfo(name = "state")
    private String mState;
    @ColumnInfo(name = "description")
    private String mDescription;
    @ColumnInfo(name = "date")
    private Date mDate;
    @ColumnInfo(name = "username")
    private String mUsername;

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

    public Date getDate() {
        return mDate;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Task(String name, int stateInt, String description, String username) {
        this.mUsername = username;
        this.mName = name;
        this.mDate = new Date();
        this.mDescription = description;
        if (stateInt == 0) {
            this.mState = "Todo";
        } else if (stateInt == 1) {
            this.mState = "Doing";
        } else if(stateInt == 2) {
            this.mState = "Done";
        }
    }

    public Task(String name, String state, String description, String username) {
        this.mUsername = username;
        this.mName = name;
        this.mDate = new Date();
        this.mDescription = description;
        this.mState = state;
    }
     // calculator field
    public String getImgFileName (){
        return "IMG" + mId + ".jpg";
    }
}
