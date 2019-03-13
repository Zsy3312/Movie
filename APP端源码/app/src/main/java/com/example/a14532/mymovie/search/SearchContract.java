package com.example.a14532.mymovie.search;

import com.example.a14532.mymovie.BasePresenter;
import com.example.a14532.mymovie.BaseView;
//搜索电影Contract
public interface SearchContract {
    interface View extends BaseView<SearchContract.Presenter> {
        void showMessage(String message);//展示信息

        void showMovieListAct(String query,String path,String type);//展示电影列表界面
    }

    interface Presenter extends BasePresenter {
        void toMovieListAct(String query);//前往电影列表界面
    }
}
