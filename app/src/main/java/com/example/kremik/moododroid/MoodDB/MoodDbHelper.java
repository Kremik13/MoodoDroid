package com.example.kremik.moododroid.MoodDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.kremik.moododroid.MoodDB.MoodContract.MoodsTable;

public class MoodDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Mood.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MoodsTable.TABLE_NAME + " ( " +
                    MoodsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MoodsTable.UUID + " TEXT, " +
                    MoodsTable.DATE + " INTEGER, " +
                    MoodsTable.MOOD + " INTEGER, " +
                    MoodsTable.TAGS + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MoodsTable.TABLE_NAME;

    public MoodDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
