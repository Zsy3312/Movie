package com.example.a14532.mymovie.moviecount;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a14532.mymovie.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

public class MovieCountFragment extends Fragment implements MovieCountContract.View {

    private MovieCountContract.Presenter mPresenter;

    private TextView typeText;
    private TextView languageText;
    private TextView yearText;
    private TextView point;
    private PieChart movieCountPieChart;

    public static MovieCountFragment newInstance() {
        return new MovieCountFragment();
    }

    public MovieCountFragment() {
        // Required empty public constructor
    }

    @Override
    public void setPresenter(MovieCountContract.Presenter presenter){
        mPresenter=presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.count_movie_fra, container,false);
        typeText=root.findViewById(R.id.movieTypeCount);

        languageText=root.findViewById(R.id.movieLanguageCount);
        yearText=root.findViewById(R.id.movieYearCount);
        point = typeText;
        movieCountPieChart= root.findViewById(R.id.moviePieChart);

        mPresenter.getMovieData();

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
            mPresenter.drawChart(that.getText().toString(),context);
        }
    }

    @Override
    public void setChart(PieData data,Description description){
        movieCountPieChart.setDescription(description);
        movieCountPieChart.setData(data);
        movieCountPieChart.invalidate();
    }

    @Override
    public void setListener(){
        typeText.setOnClickListener(new textListener());
        languageText.setOnClickListener(new textListener());
        yearText.setOnClickListener(new textListener());
    }
}