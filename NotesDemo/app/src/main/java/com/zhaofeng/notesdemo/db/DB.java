package com.zhaofeng.notesdemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zhaofeng on 17-2-15.
 */

public class DB extends SQLiteOpenHelper {

    public final static String TABLE_NOTE = "note";
    public final static String TABLE_NOTE_MEDIA = "note_media";
    public final static String COLUMN_ID = "_id";
    public final static String COLUMN_NOTE_NAME = "name";
    public final static String COLUMN_NOTE_CONTENT = "content";
    public final static String COLUMN_NOTE_DATE = "date";
    public final static String COLUMN_NOTE_MEDIA_NOTE_ID = "note_id";
    public final static String COLUMN_NOTE_MEDIA_PATH = "path";

    public DB(Context context) {
        super(context, "note", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NOTE+" ( "+COLUMN_ID+" integer primary key autoincrement," +
                COLUMN_NOTE_NAME+" text default \"\", " +
                COLUMN_NOTE_CONTENT+" text default \"\", " +
                COLUMN_NOTE_DATE+" text default \"\" )");

        db.execSQL("create table "+TABLE_NOTE_MEDIA+" ( "+COLUMN_ID+" integer primary key autoincrement," +
                COLUMN_NOTE_MEDIA_NOTE_ID+" text default \"\", " +
                COLUMN_NOTE_MEDIA_PATH+" text default \"\" )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
