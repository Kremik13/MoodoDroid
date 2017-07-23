package com.example.kremik.moododroid;

import java.util.Date;
import java.util.UUID;

public class MoodLog {

    private UUID mID;
    private int mMood;
    private Date mDate;
    private String mTags;

    public MoodLog() {
        this(UUID.randomUUID());
    }

    public MoodLog(UUID uuid) {
        mID = uuid;
        mDate = new Date();
    }

    public int getMood() {
        return mMood;
    }

    public void setMood(int mood) {
        mMood = mood;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getTags() {
        return mTags;
    }

    public void setTags(String tags) {
        mTags = tags;
    }

    public void addTag(String tag) {
        mTags += tag + ",";
    }

    public UUID getID() {
        return mID;
    }
}
