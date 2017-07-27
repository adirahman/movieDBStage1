package com.adi.moviedbstage1.database;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.adi.moviedbstage1.database.favorite.FavoriteMovieContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 7/25/17.
 */

public class TestUtils {

    public static void insertFakeData(SQLiteDatabase db){
        if(db == null){
            return;
        }

        List<ContentValues> list = new ArrayList<ContentValues>();

        ContentValues cv = new ContentValues();
        cv.put(FavoriteMovieContract.FavoriteEntry.COLUMN_ID_MOVIE, 324852);
        cv.put(FavoriteMovieContract.FavoriteEntry.COLUMN_ORIGINAL_TITLE, "Despicable Me 3");
        cv.put(FavoriteMovieContract.FavoriteEntry.COLUMN_VOTE_AVERAGE, 6);
        list.add(cv);

        cv = new ContentValues();
        cv.put(FavoriteMovieContract.FavoriteEntry.COLUMN_ID_MOVIE, 315635);
        cv.put(FavoriteMovieContract.FavoriteEntry.COLUMN_ORIGINAL_TITLE, "Spider-Man: Homecoming");
        cv.put(FavoriteMovieContract.FavoriteEntry.COLUMN_VOTE_AVERAGE, 7);
        list.add(cv);

        try{
            db.beginTransaction();
            db.delete(FavoriteMovieContract.FavoriteEntry.TABLE_NAME,null,null);
            for(ContentValues c:list){
                db.insert(FavoriteMovieContract.FavoriteEntry.TABLE_NAME,null,c);
            }
            db.setTransactionSuccessful();
        }catch (SQLException e){}
        finally {
            db.endTransaction();
        }
    }
}
