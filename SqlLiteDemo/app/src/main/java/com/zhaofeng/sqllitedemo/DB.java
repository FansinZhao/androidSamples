package com.zhaofeng.sqllitedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zhaofeng on 17-2-13.
 */

public class DB extends SQLiteOpenHelper {

    public DB(Context context) {
        super(context, "db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user(/*_id INT PRIMARY KEY AUTOINCREMENT,*/name TEXT default \"\", age INT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
