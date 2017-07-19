package com.adi.moviedbstage1.dao;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Adi on 6/14/2017.
 */

public class DatesDao implements Parcelable {
    public String maximum;
    public String minimum;

    protected DatesDao(Parcel in) {
        maximum = in.readString();
        minimum = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(maximum);
        dest.writeString(minimum);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<DatesDao> CREATOR = new Parcelable.Creator<DatesDao>() {
        @Override
        public DatesDao createFromParcel(Parcel in) {
            return new DatesDao(in);
        }

        @Override
        public DatesDao[] newArray(int size) {
            return new DatesDao[size];
        }
    };
}
