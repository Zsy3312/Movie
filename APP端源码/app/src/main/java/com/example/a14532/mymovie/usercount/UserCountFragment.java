package com.example.a14532.mymovie.usercount;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import com.example.a14532.mymovie.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.components.AxisBase;

public class UserCountFragment extends Fragment implements UserCountContract.View {

    private UserCountContract.Presenter mPresenter;

    private TextView userYearCount;
    private TextView userStudyCount;
    private TextView point;
    private BarChart userBarChart;

    public static UserCountFragment newInstance() {
        return new UserCountFragment();
    }

    public UserCountFragment() {
        // Required empty public constructor
    }

    @Override
    public void setPresenter(UserCountContract.Presenter presenter){
        mPresenter=presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.count_user_fra, container,false);
        userYearCount=root.findViewById(R.id.userYearCount);
        userStudyCount=root.findViewById(R.id.userStudyCount);
        userBarChart=root.findViewById(R.id.userBarChart);
        point=userYearCount;

        XAxis xAxis = userBarChart.getXAxis();
        // 设置 x 轴显示位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // 取消 垂直 网格线
        xAxis.setDrawGridLines(false);
        // 设置 x 轴 坐标字体大小
        xAxis.setTextSize(10f);
        // 设置 x 坐标轴 宽度
        xAxis.setAxisLineWidth(2f);

        // 设置 x 轴 从0开始 默认不是从 0
        xAxis.setAxisMinimum(0);
        // 设置 x 轴 坐标居中显示
        xAxis.setCenterAxisLabels(true);

        // 获取 右边 y 轴
        YAxis mRAxis = userBarChart.getAxisRight();
        // 隐藏 右边 Y 轴
        mRAxis.setEnabled(false);
        // 获取 左边 Y轴
        YAxis mLAxis = userBarChart.getAxisLeft();
        // 取消 左边 Y轴 坐标线
        mLAxis.setDrawAxisLine(false);
        // 取消 横向 网格线
        mLAxis.setDrawGridLines(false);
        // 设置 Y轴 的刻度数量
        mLAxis.setLabelCount(5);
        // 设置 Y轴 从零开始
        mLAxis.setAxisMinimum(0f);

        mPresenter.getUserCountData();
        return root;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    class textListener  implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            TextView that =(TextView) v;
            point.setBackgroundColor(getResources().getColor(R.color.colorSnow));
            point=that;
            point.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            Context context =getContext();
            XAxis xAxis = userBarChart.getXAxis();
            mPresenter.drawChart(that.getText().toString(),context,xAxis);
        }
    }

    @Override
    public void setListener(){
        userYearCount.setOnClickListener(new textListener());
        userStudyCount.setOnClickListener(new textListener());
    }

    @Override
    public void setChart(BarData barData,Description description){
        userBarChart.setData(barData);
        userBarChart.setDescription(description);
        userBarChart.animateXY(1000, 2000);//设置动画
        userBarChart.invalidate();
    }
}
