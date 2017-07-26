package com.adi.moviedbstage1.database;

import android.provider.BaseColumns;

/**
 * Created by user on 7/25/17.
 */

public class FavoriteMovieContract {

    public static final class FavoriteEntry implements BaseColumns{
        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_ID_MOVIE = "id";
        public static final String COLUMN_ORIGINAL_TITLE = "original_title";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
