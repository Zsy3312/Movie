package com.example.a14532.mymovie.moviecount;


import android.content.Context;

import com.example.a14532.mymovie.R;
import com.example.a14532.mymovie.data.model.MovieCountModel;
import com.example.a14532.mymovie.data.remote.bean.DataCountBean;
import com.example.a14532.mymovie.data.remote.onDataResponseListener;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

public class MovieCountPresenter implements MovieCountContract.Presenter{
    private MovieCountContract.View mMovieCountView;
    private MovieCountModel mMovieCountModel;
    private DataCountBean data;

    @Override
    public void start(){

    }


    public MovieCountPresenter(MovieCountContract.View movieCountView , MovieCountModel movieCountModel) {
        mMovieCountView=movieCountView;
        mMovieCountModel=movieCountModel;

        movieCountView.setPresenter(this);
    }

    @Override
    public void getMovieData(){
        mMovieCountModel.movieCountRequest(new onDataResponseListener<DataCountBean>() {
            public void onSuccess(DataCountBean dataBean) {
                data=dataBean;
                mMovieCountView.showMessage("请选择要查看的图表");
                mMovieCountView.setListener();
            }
            @Override public void onFailed(String string) {
                mMovieCountView.showMessage(string);
            }

        });
    }


    @Override
    public void drawChart(String target, Context context){
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(context.getResources().getColor(R.color.colorDarkGoldenRod));
        colors.add(context.getResources().getColor(R.color.colorDarkKhaki));
        colors.add(context.getResources().getColor(R.color.colorDarkSlateGray));
        colors.add(context.getResources().getColor(R.color.colorDarkMagenta));
        colors.add(context.getResources().getColor(R.color.colorIndianRed));
        colors.add(context.getResources().getColor(R.color.colorDarkRed));
        colors.add(context.getResources().getColor(R.color.colorDarkOrange));
        colors.add(context.getResources().getColor(R.color.colorForestGreen));

        if(target.equals("类型")){
            List<PieEntry> entries = new ArrayList<>();
            for(int i=0;i<data.getTypeCount().size();i++)
            {
                entries.add(new PieEntry(data.getTypeCount().get(i).getCount(), data.getTypeCount().get(i).getName()));
            }

            PieDataSet set = new PieDataSet(entries, "电影类型");
            set.setColors(colors);

            Description description = new Description();
            description.setText("各类型电影收藏占比图");
            description.setTextSize(12f);

            PieData data = new PieData(set);
            data.setDrawValues(true);
            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(12f);
            mMovieCountView.setChart(data,description);
        }else if(target.equals("年代")){
            List<PieEntry> entries = new ArrayList<>();
            for(int i=0;i<data.getYearCount().size();i++)
            {
                entries.add(new PieEntry(data.getYearCount().get(i).getCount(), data.getYearCount().get(i).getName()));
            }

            PieDataSet set = new PieDataSet(entries, "电影年代");
            set.setColors(colors);

            Description description = new Description();
            description.setText("各年代电影收藏占比图");
            description.setTextSize(12f);

            PieData data = new PieData(set);
            data.setDrawValues(true);
            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(12f);
            mMovieCountView.setChart(data,description);

        }else if(target.equals("语言")){
            List<PieEntry> entries = new ArrayList<>();
            for(int i=0;i<data.getLanguageCount().size();i++)
            {
                entries.add(new PieEntry(data.getLanguageCount().get(i).getCount(), data.getLanguageCount().get(i).getName()));
            }

            PieDataSet set = new PieDataSet(entries, "电影语言");
            set.setColors(colors);

            Description description = new Description();
            description.setText("各语种电影收藏占比图");
            description.setTextSize(12f);

            PieData data = new PieData(set);
            data.setDrawValues(true);
            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(12f);
            mMovieCountView.setChart(data,description);
        }
    }
}
