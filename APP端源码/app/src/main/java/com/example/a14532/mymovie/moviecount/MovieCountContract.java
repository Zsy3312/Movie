package com.example.a14532.mymovie.moviecount;

import android.content.Context;

import com.example.a14532.mymovie.BasePresenter;
import com.example.a14532.mymovie.BaseView;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
//电影统计契约类
public interface MovieCountContract {
    interface View extends BaseView<Presenter> {
        void showMessage(String message);//展示信息

        void setChart(PieData data,Description description);//绘制图表

        void setListener();//设置按键监听
    }

    interface Presenter extends BasePresenter {
        void drawChart(String target, Context context);//绘制图表

        void getMovieData();//获取电影统计数据
    }
}
