package com.app.blog.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by darknoe on 15/4/15.
 */
public class DatabaseHelper  extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "tecnomarqueting.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createPostTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DataContract.PostEntry.TABLE_NAME);
        onCreate(db);
    }

    private String createPostTable(){
        final String sql = "CREATE TABLE " + DataContract.PostEntry.TABLE_NAME + " (" +
                DataContract.PostEntry._ID + " INTEGER PRIMARY KEY," +
                DataContract.PostEntry.COLUMN_ID + " INTEGER, " +
                DataContract.PostEntry.COLUMN_SLUG + " TEXT, " +
                DataContract.PostEntry.COLUMN_TYPE + " TEXT, " +
                DataContract.PostEntry.COLUMN_URL + " TEXT NOT NULL, " +
                DataContract.PostEntry.COLUMN_STATUS + " TEXT, " +
                DataContract.PostEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                DataContract.PostEntry.COLUMN_TITLE_PLAIN + " TEXT NOT NULL, " +
                DataContract.PostEntry.COLUMN_CONTENT + " TEXT NOT NULL, " +
                DataContract.PostEntry.COLUMN_EXCERPT + " TEXT NOT NULL, " +
                DataContract.PostEntry.COLUMN_DATE + " TEXT, " +
                DataContract.PostEntry.COLUMN_ICON + " TEXT NOT NULL, " +
                DataContract.PostEntry.COLUMN_HEADER + " TEXT NOT NULL, " +
                " UNIQUE ("+DataContract.PostEntry.COLUMN_ID+")"+
                " ON CONFLICT REPLACE "+
                " );";
        return sql;
    }

}
