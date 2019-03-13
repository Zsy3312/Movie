package com.example.a14532.mymovie.moviecount;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.a14532.mymovie.R;
import com.example.a14532.mymovie.data.model.MovieCountModel;
import com.example.a14532.mymovie.utils.ActivityUtils;

public class MovieCountActivity extends AppCompatActivity {

    MovieCountPresenter mMovieCountPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.count_movie_act);

        MovieCountFragment movieCountFragment = (MovieCountFragment) getSupportFragmentManager()
                .findFragmentById(R.id.movieCountContentFrame);

        if (movieCountFragment == null) {
            movieCountFragment = MovieCountFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    movieCountFragment, R.id.movieCountContentFrame);
        }
        MovieCountModel movieCountModel=new MovieCountModel();

        // Create the presenter
        mMovieCountPresenter = new MovieCountPresenter(movieCountFragment,movieCountModel);
    }
}