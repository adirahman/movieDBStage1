package com.adi.moviedbstage1;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.adi.moviedbstage1.adapter.ReviewAdapter;
import com.adi.moviedbstage1.adapter.TrailerAdapter;
import com.adi.moviedbstage1.api.core.MovieAPI;
import com.adi.moviedbstage1.dao.ListReviewsDao;
import com.adi.moviedbstage1.dao.ListVideo;
import com.adi.moviedbstage1.dao.MovieDao;
import com.adi.moviedbstage1.dao.ReviewDao;
import com.adi.moviedbstage1.dao.VideoData;
import com.adi.moviedbstage1.database.favorite.FavoriteDBHelper;
import com.adi.moviedbstage1.database.favorite.FavoriteMovieContract;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity implements TrailerAdapter.TrailerAdapterListener,ReviewAdapter.ReviewAdapaterListener{

    String TAG = MovieDetailActivity.class.getSimpleName();
    MovieDao dataMovie;
    ImageView ivPoster;
    TextView tvTitle,tvDesc,tvVote,tvRelease,tvNoReviews;
    Button btnAddFavorite;
    RecyclerView trailerRecyclerView,reviewRecyclerView;
    TrailerAdapter trailerAdapter;
    ReviewAdapter reviewAdapter;

    ProgressDialog progressDialog;

    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        progressDialog = new ProgressDialog(this);
        ivPoster = (ImageView) findViewById(R.id.iv_poster);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvDesc = (TextView) findViewById(R.id.tv_desc);
        tvVote = (TextView) findViewById(R.id.tv_vote);
        tvRelease = (TextView) findViewById(R.id.tv_release_date);
        tvNoReviews = (TextView) findViewById(R.id.tv_no_reviews);
        btnAddFavorite = (Button) findViewById(R.id.btn_favorite);
        trailerRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        reviewRecyclerView = (RecyclerView) findViewById(R.id.recycler_reviews);
        LinearLayoutManager llm = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        LinearLayoutManager llm2 = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        reviewRecyclerView.setLayoutManager(llm2);
        trailerRecyclerView.setLayoutManager(llm);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait Initializing Detail Movie Progress...");
        progressDialog.show();
        getData();
        initDB();
    }

    public static void startThisActivity(Context context,MovieDao movieDao){
        Intent i =new Intent(context,MovieDetailActivity.class);
        i.putExtra("MovieData",movieDao);
        context.startActivity(i);
    }

    public void getData(){
        dataMovie = getIntent().getParcelableExtra("MovieData");

        Picasso.with(MovieDetailActivity.this)
                .load(Constants.BASE_IMAGE_URL_2+dataMovie.backdrop_path)
                .into(ivPoster);
        tvTitle.setText(dataMovie.original_title);
        tvDesc.setText(dataMovie.overview);
        tvVote.setText("Vote Average : "+String.valueOf(dataMovie.vote_average));
        tvRelease.setText("Release Date : "+dataMovie.release_date);

        getTrailer();
        getReviews();
    }

    public void getTrailer(){
        MovieAPI.requestListTrailer(dataMovie.id,callBackTrailer);
    }

    Callback<ListVideo> callBackTrailer = new Callback<ListVideo>() {
        @Override
        public void onResponse(Call<ListVideo> call, Response<ListVideo> response) {
            if(response.isSuccessful()){
                trailerAdapter = new TrailerAdapter(response.body().results,MovieDetailActivity.this,MovieDetailActivity.this);
                trailerRecyclerView.setAdapter(trailerAdapter);
                trailerAdapter.notifyDataSetChanged();

                Log.d(TAG,"response success");
            }else {
                Log.d(TAG,"response failed");
            }
            progressDialog.dismiss();
        }

        @Override
        public void onFailure(Call<ListVideo> call, Throwable t) {
            Log.d(TAG,t.getMessage());
            progressDialog.dismiss();
        }
    };

    private void getReviews(){
        MovieAPI.requestListReviews(dataMovie.id,callbackReviews);
    }

    Callback<ListReviewsDao> callbackReviews = new Callback<ListReviewsDao>() {
        @Override
        public void onResponse(Call<ListReviewsDao> call, Response<ListReviewsDao> response) {
            if(response.isSuccessful()){
                Log.d("Reviews","success");
                if(response.body().results.size() > 0){
                    reviewAdapter = new ReviewAdapter(response.body().results,MovieDetailActivity.this,MovieDetailActivity.this);
                    reviewRecyclerView.setAdapter(reviewAdapter);
                    reviewAdapter.notifyDataSetChanged();
                }else{
                    tvNoReviews.setVisibility(View.VISIBLE);
                    reviewRecyclerView.setVisibility(View.GONE);
                }
            }else{
                Log.d("Reviews","failed on response");
            }
        }

        @Override
        public void onFailure(Call<ListReviewsDao> call, Throwable t) {
            Log.d("Reviews","on failure : "+t.getMessage());
        }
    };

    // trailer on Click
    @Override
    public void onClick(VideoData videoData) {
        //Toast.makeText(MovieDetailActivity.this,videoData.key,Toast.LENGTH_LONG).show();
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.WATCH_TRAILER_URL+videoData.key)));
    }

    private void initDB(){
        FavoriteDBHelper dbHelper = new FavoriteDBHelper(this);
        mDb = dbHelper.getWritableDatabase();
    }

    public void addFavoriteMovie(View view){
        ContentValues cv = new ContentValues();
        cv.put(FavoriteMovieContract.FavoriteEntry.COLUMN_ID_MOVIE, dataMovie.id);
        cv.put(FavoriteMovieContract.FavoriteEntry.COLUMN_ORIGINAL_TITLE,dataMovie.original_title);
        cv.put(FavoriteMovieContract.FavoriteEntry.COLUMN_VOTE_AVERAGE,dataMovie.vote_average);

        mDb.insert(FavoriteMovieContract.FavoriteEntry.TABLE_NAME,null,cv);
    }

    // review onClick
    @Override
    public void onClick(ReviewDao reviewData) {

    }
}
