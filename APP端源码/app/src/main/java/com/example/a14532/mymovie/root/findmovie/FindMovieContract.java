package com.example.a14532.mymovie.root.findmovie;

import com.example.a14532.mymovie.BasePresenter;
import com.example.a14532.mymovie.BaseView;
//找片契约类
public interface FindMovieContract {
    interface View extends BaseView<FindMovieContract.Presenter> {
        void showMessage(String message);//展示信息

        void showSearchAct();//展示查找电影界面

        void showMovieListAct(String path);//展示电影列表界面

        void showTypeSearchAct();//展示分类查找界面

        void showLoginAct();//展示登录界面
    }

    interface Presenter extends BasePresenter {
        void toSearchAct();//前往查找界面

        void toMovieListAct(String path);//前往电影列表界面

        void toGuessYouLikeAct();//前往电影推荐界面

        void toTypeSearchAct();//前往分类查找界面
    }

}
