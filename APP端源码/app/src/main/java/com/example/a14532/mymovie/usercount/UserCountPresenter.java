package com.example.a14532.mymovie.usercount;

import android.content.Context;

import com.example.a14532.mymovie.R;
import com.example.a14532.mymovie.data.model.UserCountModel;
import com.example.a14532.mymovie.data.remote.bean.UserCountBean;
import com.example.a14532.mymovie.data.remote.onDataResponseListener;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;

public class UserCountPresenter implements UserCountContract.Presenter{
    private UserCountContract.View mUserCountView;
    private UserCountModel mUserCountModel;
    private UserCountBean data;

    @Override
    public void start(){

    }


    public UserCountPresenter(UserCountContract.View userCountView , UserCountModel userCountModel) {
        mUserCountView=userCountView;
        mUserCountModel=userCountModel;

        userCountView.setPresenter(this);
    }

    @Override
    public void getUserCountData(){
        mUserCountModel.userCountRequest(new onDataResponseListener<UserCountBean>() {
            public void onSuccess(UserCountBean dataBean) {
                data=dataBean;
                mUserCountView.showMessage("请选择要查看的图表");
                mUserCountView.setListener();
            }
            @Override public void onFailed(String string) {
                mUserCountView.showMessage(string);
            }

        });
    }

    @Override
    public void drawChart(String target, Context context,XAxis xAxis){

        if(target.equals("年龄")){
            ArrayList<BarEntry> manYearValues = new ArrayList<>();
            ArrayList<BarEntry> womanYearValues= new ArrayList<>();

            for (int i = 0; i < data.getManYear().size(); i++) {
                manYearValues.add(new BarEntry(i, (int)data.getManYear().get(i).getCount()));
                womanYearValues.add(new BarEntry(i, (int)data.getWomanYear().get(i).getCount()));
            }
            // y 轴数据集
            BarDataSet barDataSet = new BarDataSet(manYearValues, "男");
            barDataSet.setColor(context.getResources().getColor(R.color.colorDarkSlateGray));
            barDataSet.setValueFormatter(new myValueFormatter());

            BarDataSet barDataSetTwo = new BarDataSet(womanYearValues, "女");
            barDataSetTwo.setColor(context.getResources().getColor(R.color.colorIndianRed));
            barDataSetTwo.setValueFormatter(new myValueFormatter());
            BarData mBarData = new BarData(barDataSet,barDataSetTwo);

            int barAmount = 2;
            float groupSpace = 0.2f; //柱状图组之间的间距
            float barWidth = (1f - groupSpace) / barAmount;
            float barSpace = 0f;

            mBarData.setBarWidth(barWidth);
            mBarData.groupBars(0f, groupSpace, barSpace);
            mBarData.setValueTextSize(12f);


            // 设置 x轴 的刻度数量
            xAxis.setLabelCount(data.getManYear().size());

            xAxis.setAxisMaximum(data.getManYear().size());

            xAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    int pos=(int) Math.abs(value) %data.getManYear().size();
                    return String.valueOf(data.getManYear().get(pos).getName());
                }
            });
            Description description = new Description();
            description.setText("各年龄段用户条形图");
            description.setTextSize(14f);
            description.setPosition(780,40);
            mUserCountView.setChart(mBarData,description);
        }else if(target.equals("学历")){
            ArrayList<BarEntry> manStudyValues = new ArrayList<>();
            ArrayList<BarEntry> womanStudyValues= new ArrayList<>();

            for (int i = 0; i < data.getManStudy().size(); i++) {
                manStudyValues.add(new BarEntry(i, (int)data.getManStudy().get(i).getCount()));
                womanStudyValues.add(new BarEntry(i, (int)data.getWomanStudy().get(i).getCount()));
            }
            // y 轴数据集
            BarDataSet barDataSet = new BarDataSet(manStudyValues, "男");
            barDataSet.setColor(context.getResources().getColor(R.color.colorDarkSlateGray));
            barDataSet.setValueFormatter(new myValueFormatter());

            BarDataSet barDataSetTwo = new BarDataSet(womanStudyValues, "女");
            barDataSetTwo.setColor(context.getResources().getColor(R.color.colorIndianRed));
            barDataSetTwo.setValueFormatter(new myValueFormatter());
            BarData mBarData = new BarData(barDataSet,barDataSetTwo);

            int barAmount = 2;
            float groupSpace = 0.2f; //柱状图组之间的间距
            float barWidth = (1f - groupSpace) / barAmount;
            float barSpace = 0f;

            mBarData.setBarWidth(barWidth);
            mBarData.groupBars(0f, groupSpace, barSpace);
            mBarData.setValueTextSize(12f);


            // 设置 x轴 的刻度数量
            xAxis.setLabelCount(data.getManStudy().size());

            xAxis.setAxisMaximum(data.getManStudy().size());

            xAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    int pos=(int) Math.abs(value) %data.getManStudy().size();
                    return String.valueOf(data.getManStudy().get(pos).getName());
                }
            });
            Description description = new Description();
            description.setText("各学历用户条形图");
            description.setTextSize(14f);
            description.setPosition(780,40);
            mUserCountView.setChart(mBarData,description);
        }
    }

    public class myValueFormatter implements IValueFormatter {
        @Override
        public String getFormattedValue(float value,
        Entry entry,
        int dataSetIndex, ViewPortHandler viewPortHandler) {
            int n = (int) value;
            return n+"";
        }
    }
}
