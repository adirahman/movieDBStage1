package com.adi.moviedbstage1.database.popular;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 7/26/17.
 */

public class PopularDBHelper extends SQLiteOpenHelper{

    static final String DATABASE_NAME = "popular.db";

    static final int DATABASE_VERSION = 1;

    public PopularDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_POPULAR_TABLE = " CREATE TABLE "+
                PopularContract.PopularEntry.TABLE_NAME + " ("+
                PopularContract.PopularEntry.COLUMN_ID_MOVIE+ " INTEGER NOT NULL, "+
                PopularContract.PopularEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, "+
                PopularContract.PopularEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, "+
                PopularContract.PopularEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, "+
                PopularContract.PopularEntry.COLUMN_BACKDROP_PATH + " TEXT NOT NULL, "+
                PopularContract.PopularEntry.COLUMN_VOTE_AVERAGE + " TEXT NOT NULL, "+
                PopularContract.PopularEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, "+
                PopularContract.PopularEntry.COLUMN_TIMESTAMP+ " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"+
                "); ";
            db.execSQL(SQL_CREATE_POPULAR_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXITS "+ PopularContract.PopularEntry.TABLE_NAME);
        onCreate(db);
    }
}
