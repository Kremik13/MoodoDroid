package com.example.kremik.moododroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.example.kremik.moododroid.MoodDB.MoodContract;
import com.example.kremik.moododroid.MoodDB.MoodDbHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MoodLab {

    private static MoodLab sMoodLab = null;
    private static String TAG = "MoodLab";
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private MoodLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new MoodDbHelper(mContext)
                .getWritableDatabase();
    }

    public static MoodLab getInstance(Context context) {
        if (sMoodLab == null) {
            sMoodLab = new MoodLab(context);
            Log.i(TAG, "Created singleton.");
        }
        return sMoodLab;
    }

    public List<MoodLog> getMoodLogs() {
        List<MoodLog> logs = new ArrayList<>();
        MoodCursorWrapper cursor = queryMoods(null,null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                logs.add(cursor.getMoodLog());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return logs;
    }

    public MoodLog getMoodLog(UUID id) {
        MoodCursorWrapper cursor = queryMoods(
                MoodContract.MoodsTable.UUID + " = ?",
                new String[] { id.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getMoodLog();
        } finally {
            cursor.close();
        }
    }
    public int size() {
        int size = 0;
        MoodCursorWrapper cursor = queryMoods(null,null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                size++;
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return size;
    }

    public void addMoodLog(MoodLog moodLog) {
        ContentValues values = getContentValues(moodLog);
        mDatabase.insert(MoodContract.MoodsTable.TABLE_NAME, null, values);
    }

    public int getAllTimeMood() {
        MoodCursorWrapper cursor = queryMoods(null,null);

        int moodSum;
        int moodCounter;
            try {
                if (cursor.getCount() == 0) {
                    return MoodContract.MOOD_INVALID;
                }
                    moodSum = 0;
                    moodCounter = 0;
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        moodSum += cursor.getMoodLog().getMood();
                        moodCounter++;
                        cursor.moveToNext();
                    }
            } finally {
                cursor.close();
            }
            return moodSum / moodCounter;
    }

    public int getWeekMood() {
        long weekBefore = new Date().getTime() - 604800000L;

        MoodCursorWrapper cursor = queryMoods(
                MoodContract.MoodsTable.DATE + " > ?",
                new String[] { String.valueOf(weekBefore) }
        );

        int moodSum;
        int moodCounter;
        try {
            if (cursor.getCount() == 0) {
                return MoodContract.MOOD_INVALID;
            }
            moodSum = 0;
            moodCounter = 0;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                moodSum += cursor.getMoodLog().getMood();
                moodCounter++;
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return moodSum / moodCounter;

    }

    public int getMoodEmojiId(int mood) {
        switch ( mood ) {
            case 0:
                return R.drawable.ic_mood_very_bad;
            case 1:
                return R.drawable.ic_mood_bad;
            case 2:
                return R.drawable.ic_mood_neutral;
            case 3:
                return R.drawable.ic_mood_happy;
            case 4:
                return R.drawable.ic_mood_very_happy;
            default:
                return R.drawable.ic_mood_incorrect;
        }
    }

    private static ContentValues getContentValues(MoodLog moodLog) {
        ContentValues values = new ContentValues();
        values.put(MoodContract.MoodsTable.UUID, moodLog.getID().toString());
        values.put(MoodContract.MoodsTable.DATE, moodLog.getDate().getTime());
        values.put(MoodContract.MoodsTable.MOOD, moodLog.getMood());
        values.put(MoodContract.MoodsTable.TAGS, moodLog.getTags());
        return values;
    }

    private MoodCursorWrapper queryMoods(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                MoodContract.MoodsTable.TABLE_NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new MoodCursorWrapper(cursor);
    }
}
