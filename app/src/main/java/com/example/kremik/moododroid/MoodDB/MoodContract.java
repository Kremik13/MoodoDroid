package com.example.kremik.moododroid.MoodDB;

import android.provider.BaseColumns;

public class MoodContract {

    public static int MOOD_VERY_SAD = 0;
    public static int MOOD_SAD = 1;
    public static int MOOD_NEUTRAL = 2;
    public static int MOOD_HAPPY = 3;
    public static int MOOD_VERY_HAPPY = 4;
    public static int MOOD_INVALID = 5;

    private MoodContract(){}

    public static class MoodsTable implements BaseColumns {
        public static final String TABLE_NAME = "moods";
        public static final String DATE = "date";
        public static final String MOOD = "mood";
        public static final String TAGS = "tags";
        public static final String UUID = "uuid";
    }
}
