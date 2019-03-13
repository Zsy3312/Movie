package com.example.a14532.mymovie.typesearch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.a14532.mymovie.R;
import com.example.a14532.mymovie.data.model.MovieListModel;
import com.example.a14532.mymovie.utils.ActivityUtils;

public class TypeSearchActivity extends AppCompatActivity {
    TypeSearchPresenter mTypeSearchPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.type_act);

        TypeSearchFragment typeSearchFragment = (TypeSearchFragment) getSupportFragmentManager()
                .findFragmentById(R.id.typeContentFrame);

        if (typeSearchFragment == null) {
            typeSearchFragment = TypeSearchFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    typeSearchFragment, R.id.typeContentFrame);
        }
        MovieListModel typeSearchModel=new MovieListModel();

        // Create the presenter
        mTypeSearchPresenter = new TypeSearchPresenter(typeSearchFragment,typeSearchModel);
    }
}