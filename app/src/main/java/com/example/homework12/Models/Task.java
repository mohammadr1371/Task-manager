package com.example.homework12.Models;

import com.example.homework12.utils.DateUtils;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class Task implements Serializable {

    private String mName;
    private String mState;
    private String mDescription;
    private UUID mUUID;
    private Date mDate;

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

    public Task(String name, int stateInt, String description) {
        this.mUUID = UUID.randomUUID();
        this.mName = name;
        this.mDate = DateUtils.getRandomDate(2000, 2020);
        this.mDescription = description;
        if (stateInt == 0) {
            this.mState = "Todo";
        } else if (stateInt == 1) {
            this.mState = "Doing";
        } else if(stateInt == 2) {
            this.mState = "Done";
        }
    }
}
