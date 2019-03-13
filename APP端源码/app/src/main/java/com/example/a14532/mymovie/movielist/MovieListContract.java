package com.example.a14532.mymovie.movielist;

import android.content.Intent;

import com.example.a14532.mymovie.BasePresenter;
import com.example.a14532.mymovie.BaseView;
import com.example.a14532.mymovie.data.remote.bean.CollectMovieBean;
import com.example.a14532.mymovie.data.remote.bean.MovieBean;
import com.example.a14532.mymovie.data.remote.bean.RecommendMovieBean;
//电影列表契约类
public interface MovieListContract {
    interface View extends BaseView<MovieListContract.Presenter> {
        void showMessage(String message);//展示信息

        void setAdapter(MovieBean dataBean);//为电影列表设置MovieBean格式的Adapt

        void setAdapter(CollectMovieBean dataBean);//为收藏列表设置CollectMovieBean格式的Adapt

        void setAdapter(RecommendMovieBean dataBean);//为推荐列表设置RecommendMovieBean格式的Adapt

        void setLoadState(int state);//设置上拉加载的加载状态
    }

    interface Presenter extends BasePresenter {
        void getMovieList(Intent intent);//获取电影列表数据

        void getListManage(Intent intent);//电影列表管理器，判断该生产什么类型的列表

        void getCollectList();//获取收藏列表数据

        void getHistoryList();//获取历史记录列表数据

        void getRecommendList();//获取推荐列表数据

        void getMoreMovie(int size,Intent intent);//加载更多电影

        void afterBackRefresh();//返回时刷新

    }
}
