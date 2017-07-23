package com.example.kremik.moododroid;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.kremik.moododroid.MoodDB.MoodContract;

import java.util.Date;
import java.util.UUID;

public class MoodCursorWrapper extends CursorWrapper {
    public MoodCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public MoodLog getMoodLog() {
        String uuidString = getString(getColumnIndex(MoodContract.MoodsTable.UUID));
        int mood = getInt(getColumnIndex(MoodContract.MoodsTable.MOOD));
        String tags = getString(getColumnIndex(MoodContract.MoodsTable.TAGS));
        long date = getLong(getColumnIndex(MoodContract.MoodsTable.DATE));

        MoodLog moodLog = new MoodLog(UUID.fromString(uuidString));
        moodLog.setMood(mood);
        moodLog.setDate(new Date(date));
        moodLog.setTags(tags);
        return moodLog;
    }
}
