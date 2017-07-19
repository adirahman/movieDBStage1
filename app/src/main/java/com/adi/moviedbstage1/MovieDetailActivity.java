package com.adi.moviedbstage1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adi.moviedbstage1.adapter.TrailerAdapter;
import com.adi.moviedbstage1.api.core.MovieAPI;
import com.adi.moviedbstage1.dao.ListVideo;
import com.adi.moviedbstage1.dao.MovieDao;
import com.adi.moviedbstage1.dao.MovieDetailDao;
import com.adi.moviedbstage1.dao.VideoData;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity implements TrailerAdapter.TrailerAdapterListener{

    String TAG = MovieDetailActivity.class.getSimpleName();
    MovieDao dataMovie;
    ImageView ivPoster;
    TextView tvTitle,tvDesc,tvVote,tvRelease;
    RecyclerView recyclerView;
    TrailerAdapter adapter;

    ProgressDialog progressDialog;

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
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager llm = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(llm);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait Initializing Detail Movie Progress...");
        progressDialog.show();
        getData();
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
    }

    public void getTrailer(){
        MovieAPI.requestListTrailer(dataMovie.id,callBackTrailer);
    }

    Callback<ListVideo> callBackTrailer = new Callback<ListVideo>() {
        @Override
        public void onResponse(Call<ListVideo> call, Response<ListVideo> response) {
            if(response.isSuccessful()){
                adapter = new TrailerAdapter(response.body().results,MovieDetailActivity.this,MovieDetailActivity.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

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

    @Override
    public void onClick(VideoData videoData) {
        //Toast.makeText(MovieDetailActivity.this,videoData.key,Toast.LENGTH_LONG).show();
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.WATCH_TRAILER_URL+videoData.key)));
    }
}
