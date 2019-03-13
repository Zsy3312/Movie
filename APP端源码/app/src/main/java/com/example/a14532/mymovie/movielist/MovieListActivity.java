package com.example.a14532.mymovie.movielist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.a14532.mymovie.R;
import com.example.a14532.mymovie.data.model.MovieListModel;
import com.example.a14532.mymovie.utils.ActivityUtils;

public class MovieListActivity extends AppCompatActivity {
    MovieListPresenter mMovieListPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_list_act);

        MovieListFragment movieListFragment = (MovieListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.movieListContentFrame);

        if (movieListFragment == null) {
            movieListFragment = MovieListFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    movieListFragment, R.id.movieListContentFrame);
        }
        MovieListModel movieListModel=new MovieListModel();

        // Create the presenter
        mMovieListPresenter = new MovieListPresenter(movieListFragment,movieListModel,getApplication());
    }
}
