package com.example.a14532.mymovie.search;

import com.example.a14532.mymovie.data.model.SearchModel;

public class SearchPresenter implements SearchContract.Presenter {
    private SearchContract.View mSearchView;
    private SearchModel mSearchModel;

    @Override
    public void start(){

    }

    public SearchPresenter(SearchContract.View searchView , SearchModel searchModel) {
        mSearchView=searchView;
        mSearchModel=searchModel;

        mSearchView.setPresenter(this);
    }
    @Override
    public void toMovieListAct(String query){
        mSearchView.showMovieListAct(query,"search","query");
    }
}