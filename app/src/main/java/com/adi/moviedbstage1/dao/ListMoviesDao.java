package com.adi.moviedbstage1.dao;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adi on 6/14/2017.
 */

public class ListMoviesDao implements Parcelable {
    public int page ;
    public List<MovieDao> results = new ArrayList<>();
    public DatesDao dates ;
    public int total_pages ;
    public int total_results ;

    public ListMoviesDao() {
    }

    protected ListMoviesDao(Parcel in) {
        page = in.readInt();
        if (in.readByte() == 0x01) {
            results = new ArrayList<MovieDao>();
            in.readList(results, MovieDao.class.getClassLoader());
        } else {
            results = null;
        }
        dates = (DatesDao) in.readValue(DatesDao.class.getClassLoader());
        total_pages = in.readInt();
        total_results = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        if (results == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(results);
        }
        dest.writeValue(dates);
        dest.writeInt(total_pages);
        dest.writeInt(total_results);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ListMoviesDao> CREATOR = new Parcelable.Creator<ListMoviesDao>() {
        @Override
        public ListMoviesDao createFromParcel(Parcel in) {
            return new ListMoviesDao(in);
        }

        @Override
        public ListMoviesDao[] newArray(int size) {
            return new ListMoviesDao[size];
        }
    };
}