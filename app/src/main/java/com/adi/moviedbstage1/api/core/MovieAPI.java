package com.adi.moviedbstage1.api.core;


import android.support.v7.util.DiffUtil;

import com.adi.moviedbstage1.Constants;
import com.adi.moviedbstage1.dao.ListMoviesDao;
import com.adi.moviedbstage1.dao.ListReviewsDao;
import com.adi.moviedbstage1.dao.ListVideo;
import com.adi.moviedbstage1.dao.MovieDetailDao;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Adi on 6/14/2017.
 */

public class MovieAPI {
    public static void requestNowPlaying(Callback<ListMoviesDao> callback){
        Call<ListMoviesDao> call = RetrofitClient.getApiInstance().getApiService().getNowPlaying(Constants.api_key);
        call.enqueue(callback);
    }

    public static void requestUpcoming(Callback<ListMoviesDao> callback){
        Call<ListMoviesDao> call= RetrofitClient.getApiInstance().getApiService().getUpComing(Constants.api_key);
        call.enqueue(callback);
    }

    public static void requestPopular(Callback<ListMoviesDao> callback){
        Call<ListMoviesDao> call = RetrofitClient.getApiInstance().getApiService().getPopular(Constants.api_key);
        call.enqueue(callback);
    }

    public static void requestTopRated(Callback<ListMoviesDao> callback){
        Call<ListMoviesDao> call = RetrofitClient.getApiInstance().getApiService().getTopRated(Constants.api_key);
        call.enqueue(callback);
    }

    public static void requestMovieDetail(int movieId, Callback<MovieDetailDao> callback){
        Call<MovieDetailDao> call = RetrofitClient.getApiInstance().getApiService().getDetailMovie(movieId,Constants.api_key);
        call.enqueue(callback);
    }

    public static void requestListTrailer(int movieId, Callback<ListVideo> callback){
        Call<ListVideo> call = RetrofitClient.getApiInstance().getApiService().getListVideo(movieId,Constants.api_key);
        call.enqueue(callback);
    }

    public static void requestListReviews(int movieId, Callback<ListReviewsDao> callback){
        Call<ListReviewsDao> call = RetrofitClient.getApiInstance().getApiService().getListReview(movieId,Constants.api_key);
        call.enqueue(callback);
    }
}
