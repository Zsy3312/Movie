package com.example.a14532.mymovie.usercount;

import android.content.Context;

import com.example.a14532.mymovie.BasePresenter;
import com.example.a14532.mymovie.BaseView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
//用户统计契约类
public interface UserCountContract {
    interface View extends BaseView<Presenter> {
        void showMessage(String message);//展示信息

        void setListener();//设置按钮监听器

        void setChart(BarData barData,Description description);//绘制图表
    }

    interface Presenter extends BasePresenter {
        void getUserCountData();//获取用户统计信息

        void drawChart(String target, Context context,XAxis xAxis);//绘制图表
    }
}
