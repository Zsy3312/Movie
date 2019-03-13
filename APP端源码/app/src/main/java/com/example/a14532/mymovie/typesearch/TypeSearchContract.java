package com.example.a14532.mymovie.typesearch;

import com.example.a14532.mymovie.BasePresenter;
import com.example.a14532.mymovie.BaseView;
import com.example.a14532.mymovie.data.remote.bean.MovieBean;//
//分类搜索契约类
public interface TypeSearchContract {
    interface View extends BaseView<Presenter> {
        void showMessage(String message);//展示信息

        void setAdapter(MovieBean dataBean);//设置Adapter

        void setLoadState(int state);//设置加载状态
    }

    interface Presenter extends BasePresenter {
        void getTypeMovie(String type);//获取分类电影信息

        void getMoreMovie(int size,String type);//上拉加载是调用，获取更多电影信息
    }
}
