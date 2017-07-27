package com.adi.moviedbstage1.database.nowplaying;

import android.provider.BaseColumns;

/**
 * Created by user on 7/26/17.
 * poster_path
 * original title
 * overview
 * backdrop_path
 * vote_average
 * release_date
 */

public class NowPlayingContract {

    public static final class NowPlayingEntry implements BaseColumns{
        public static final String TABLE_NAME = "nowplaying";
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
