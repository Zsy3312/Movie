package com.example.a14532.mymovie.root.findmovie;

import android.app.Application;

import com.example.a14532.mymovie.data.local.GlobalData;
import com.example.a14532.mymovie.data.model.FindMovieModel;

public class FindMoviePresenter implements FindMovieContract.Presenter {
    private FindMovieContract.View mFindMovieView;
    private FindMovieModel mFindMovieModel;
    private GlobalData globalData;

    @Override
    public void start(){

    }

    public FindMoviePresenter(FindMovieContract.View findMovieView , FindMovieModel findMovieModel , Application data) {
        mFindMovieView=findMovieView;
        mFindMovieModel=findMovieModel;

        globalData=(GlobalData)data;
        findMovieView.setPresenter(this);
    }

    @Override
    public void toSearchAct(){
        mFindMovieView.showSearchAct();
    }
    @Override
    public void toMovieListAct(String path){
        mFindMovieView.showMovieListAct(path);
    }
    @Override
    public void toGuessYouLikeAct(){
        String username = globalData.getUserName();
        if(username.length()==0) {
            mFindMovieView.showLoginAct();
        }
        else{
        mFindMovieView.showMovieListAct("recommend");
        }
    }
    @Override
    public void toTypeSearchAct(){
        mFindMovieView.showTypeSearchAct();
    }
}
