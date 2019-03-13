package com.example.a14532.mymovie.root.onshow;

import com.example.a14532.mymovie.BasePresenter;
import com.example.a14532.mymovie.BaseView;
import com.example.a14532.mymovie.data.remote.bean.MovieBean;
//热映TAB的contract
public interface OnShowContract {
    interface View extends BaseView<OnShowContract.Presenter> {
        void showMessage(String message);//展示信息

        void setAdapter(MovieBean dataBean);//设置Adapter

        void setLoadState(int state);//设置加载状态
    }

    interface Presenter extends BasePresenter {
        void getOnShowMovie();//获取热映电影列表信息

        void getMoreMovie(int size);//加载更多电影
    }
}