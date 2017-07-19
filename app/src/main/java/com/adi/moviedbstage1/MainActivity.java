package com.adi.moviedbstage1;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.adi.moviedbstage1.adapter.MainViewPagerAdapter;
import com.adi.moviedbstage1.api.core.MovieAPI;
import com.adi.moviedbstage1.dao.ListMoviesDao;
import com.adi.moviedbstage1.dao.MovieDao;
import com.adi.moviedbstage1.dao.MovieDetailDao;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MoviePassInterface {

    String TAG = MainActivity.class.getSimpleName();
    ViewPager viewPager;
    CoordinatorLayout mainLayout;
    ProgressBar pb;
    ListMoviesDao nowPlaying = new ListMoviesDao();
    ListMoviesDao listUpComing = new ListMoviesDao();
    ListMoviesDao listPopular = new ListMoviesDao();
    ListMoviesDao listTopRated = new ListMoviesDao();
    ProgressDialog progressDialog;

    private static final String NOW_PLAYING = "nowPlaying";
    private static final String LIST_UP_COMING = "listUpComing";
    private static final String LIST_POPULAR = "listPopular";
    private static final String LIST_TOP_RATED = "listTopRated";

    int flag = 0;
    int getPositionTab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        mainLayout = (CoordinatorLayout) findViewById(R.id.htab_main_content);
        pb = (ProgressBar) findViewById(R.id.loading);
        viewPager = (ViewPager) findViewById(R.id.htab_viewpager);

        if(savedInstanceState != null){
            if(savedInstanceState.containsKey(NOW_PLAYING)){
                nowPlaying = savedInstanceState.getParcelable(NOW_PLAYING);
            }
            if(savedInstanceState.containsKey(LIST_UP_COMING)){
                listUpComing =savedInstanceState.getParcelable(LIST_UP_COMING);
            }
            if(savedInstanceState.containsKey(LIST_POPULAR)){
                listPopular = savedInstanceState.getParcelable(LIST_POPULAR);
            }
            if(savedInstanceState.containsKey(LIST_TOP_RATED)){
                listTopRated = savedInstanceState.getParcelable(LIST_TOP_RATED);
            }
            initView();
        }else {
            fetchDataFromAPI();
        }

    }

    public void fetchDataFromAPI(){
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait Fetching Movie Progress...");
        progressDialog.show();
        getNowPlaying();
    }

    public void getNowPlaying(){
        MovieAPI.requestNowPlaying(movieListener);
    }

    public void getUpComing(){
        MovieAPI.requestUpcoming(movieListener);
    }

    public void getPopular(){
        MovieAPI.requestPopular(movieListener);
    }

    public void getTopRated(){
        MovieAPI.requestTopRated(movieListener);
    }

    Callback<ListMoviesDao> movieListener = new Callback<ListMoviesDao>() {
        @Override
        public void onResponse(Call<ListMoviesDao> call, Response<ListMoviesDao> response) {
            if(flag == 0){
                nowPlaying = response.body();
                flag = 1;
                getUpComing();
            }else if(flag == 1){
                listUpComing = response.body();
                flag = 2;
                getPopular();
            }else if(flag == 2){
                listPopular = response.body();
                flag = 3;
                getTopRated();
            }else if(flag == 3){
                listTopRated = response.body();
                flag = 0;
                initView();
            }
        }

        @Override
        public void onFailure(Call<ListMoviesDao> call, Throwable t) {
            Log.e(TAG,t.getMessage());
        }
    };



    public void initView(){
        final Toolbar toolbar = (Toolbar) findViewById(R.id.htab_toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(getBaseContext(),R.color.white_70));
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) getSupportActionBar().setTitle("Movie DB Stage 1");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        final ImageView imgHeader = (ImageView) findViewById(R.id.htab_header);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.htab_tabs);
        tabLayout.setupWithViewPager(viewPager);

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.htab_collapse_toolbar);

        try{
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.now_playing);
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {

                    int vibrantColor = palette.getVibrantColor(ContextCompat.getColor(getBaseContext(),R.color.primary_500));
                    int vibrantDarkColor = palette.getDarkVibrantColor(ContextCompat.getColor(getBaseContext(),R.color.primary_700));
                    collapsingToolbarLayout.setContentScrimColor(vibrantColor);
                    collapsingToolbarLayout.setStatusBarScrimColor(vibrantDarkColor);
                }
            });
        }catch (Exception e){
            Log.e(TAG,"onCreate : failed to create bitmap from background",e.fillInStackTrace());
            collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(this,R.color.primary_500));
            collapsingToolbarLayout.setStatusBarScrimColor(ContextCompat.getColor(this,R.color.primary_700));
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                getPositionTab = tab.getPosition();
                int pos = tab.getPosition();
                if(pos == 0){
                    imgHeader.setImageResource(R.drawable.now_playing);
                }else if(pos == 1){
                    imgHeader.setImageResource(R.drawable.up_coming);
                }else if(pos == 2){
                    imgHeader.setImageResource(R.drawable.popular);
                }else if(pos == 3){
                    imgHeader.setImageResource(R.drawable.top_rated);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        progressDialog.dismiss();
    }
    private void setupViewPager(ViewPager pager){
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new MovieFragment(nowPlaying),"Now Playing");
        adapter.addFrag(new MovieFragment(listUpComing),"Up Coming");
        adapter.addFrag(new MovieFragment(listPopular),"Popular");
        adapter.addFrag(new MovieFragment(listTopRated),"Top Rated");

        pager.setAdapter(adapter);
        int a = adapter.getCount();
        adapter.notifyDataSetChanged();
        progressDialog.dismiss();
    }

    @Override
    public void passMoviewData(MovieDao movieDao) {
        MovieDetailActivity.startThisActivity(MainActivity.this,movieDao);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(NOW_PLAYING,nowPlaying);
        outState.putParcelable(LIST_POPULAR,listPopular);
        outState.putParcelable(LIST_TOP_RATED,listTopRated);
        outState.putParcelable(LIST_UP_COMING,listUpComing);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        nowPlaying = savedInstanceState.getParcelable(NOW_PLAYING);
        listPopular = savedInstanceState.getParcelable(LIST_POPULAR);
        listTopRated = savedInstanceState.getParcelable(LIST_TOP_RATED);
        listUpComing = savedInstanceState.getParcelable(LIST_UP_COMING);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_refresh:
                fetchDataFromAPI();
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Refresh data movie db...");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
