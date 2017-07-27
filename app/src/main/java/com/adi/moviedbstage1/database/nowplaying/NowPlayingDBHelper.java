package com.adi.moviedbstage1.database.nowplaying;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.adi.moviedbstage1.database.nowplaying.NowPlayingContract;

/**
 * Created by user on 7/26/17.
 */

public class NowPlayingDBHelper extends SQLiteOpenHelper{
    static final String DATABASE_NAME = "nowplaying.db";

    static final int DATABASE_VERSION = 1   ;

    public NowPlayingDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_NOW_PLAYING_TABLE = "CREATE TABLE "+
                NowPlayingContract.NowPlayingEntry.TABLE_NAME + " ("+
                NowPlayingContract.NowPlayingEntry.COLUMN_ID_MOVIE + " INTEGER NOT NULL, "+
                NowPlayingContract.NowPlayingEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, "+
                NowPlayingContract.NowPlayingEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, "+
                NowPlayingContract.NowPlayingEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, "+
                NowPlayingContract.NowPlayingEntry.COLUMN_BACKDROP_PATH + " TEXT NOT NULL, "+
                NowPlayingContract.NowPlayingEntry.COLUMN_VOTE_AVERAGE + " TEXT NOT NULL, "+
                NowPlayingContract.NowPlayingEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, "+
                NowPlayingContract.NowPlayingEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"+
                "); ";
        db.execSQL(SQL_CREATE_NOW_PLAYING_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXITS "+ NowPlayingContract.NowPlayingEntry.TABLE_NAME);
        onCreate(db);
    }
}
