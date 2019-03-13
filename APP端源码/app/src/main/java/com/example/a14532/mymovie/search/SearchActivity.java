package com.example.a14532.mymovie.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.a14532.mymovie.R;
import com.example.a14532.mymovie.data.model.SearchModel;
import com.example.a14532.mymovie.utils.ActivityUtils;

public class SearchActivity extends AppCompatActivity {
    SearchPresenter mSearchPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_act);

        SearchFragment searchFragment = (SearchFragment) getSupportFragmentManager()
                .findFragmentById(R.id.searchContentFrame);

        if (searchFragment == null) {
            searchFragment = SearchFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    searchFragment, R.id.searchContentFrame);
        }
        SearchModel searchModel=new SearchModel();

        // Create the presenter
        mSearchPresenter = new SearchPresenter(searchFragment,searchModel);
    }
}
