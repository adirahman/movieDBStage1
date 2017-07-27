package com.adi.moviedbstage1.database.toprated;

import android.provider.BaseColumns;

/**
 * Created by user on 7/26/17.
 */

public class TopratedContract {

    public static final class  TopRatedEntry implements BaseColumns{
        public static final String TABLE_NAME = "toprated";
        public static final String COLUMN_ID_MOVIE = "id";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_ORIGINAL_TITLE = "origina_ltitle";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
