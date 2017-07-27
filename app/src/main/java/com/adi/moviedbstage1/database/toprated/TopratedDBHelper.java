package com.adi.moviedbstage1.database.toprated;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 7/26/17.
 */

public class TopratedDBHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "toprate.db";

    static final int DATABASE_VERSION = 1;

    public TopratedDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TOP_RATED_TABLE = " CREATE TABLE "+
                TopratedContract.TopRatedEntry.TABLE_NAME +" ("+
                TopratedContract.TopRatedEntry.COLUMN_ID_MOVIE + " INTEGER NOT NULL, "+
                TopratedContract.TopRatedEntry.COLUMN_POSTER_PATH  + " TEXT NOT NULL, "+
                TopratedContract.TopRatedEntry.COLUMN_ORIGINAL_TITLE  + " TEXT NOT NULL, "+
                TopratedContract.TopRatedEntry.COLUMN_OVERVIEW  + " TEXT NOT NULL, "+
                TopratedContract.TopRatedEntry.COLUMN_BACKDROP_PATH  + " TEXT NOT NULL, "+
                TopratedContract.TopRatedEntry.COLUMN_VOTE_AVERAGE  + " TEXT NOT NULL, "+
                TopratedContract.TopRatedEntry.COLUMN_RELEASE_DATE  + " TEXT NOT NULL, "+
                TopratedContract.TopRatedEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"+
                "); ";
        db.execSQL(SQL_CREATE_TOP_RATED_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXITS "+ TopratedContract.TopRatedEntry.TABLE_NAME);
        onCreate(db);
    }
}
