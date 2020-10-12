package com.example.homework12.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.homework12.utils.DateUtils;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

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
    @ColumnInfo(name = "_id")
    private UUID mUUID;
    @ColumnInfo(name = "date")
    private Date mDate;
    @ColumnInfo(name = "username")
    private String mUsername;

    public int getId() {
        return mId;
    }

    public String getUsername() {
        return mUsername;
    }

    public Date getDate() {
        return mDate;
    }

    public UUID getUUID() {
        return mUUID;
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
        this.mUUID = UUID.randomUUID();
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
     // calculator field
    public String getImgFileName (){
        return "IMG" + mUUID + ".jpg";
    }
}
