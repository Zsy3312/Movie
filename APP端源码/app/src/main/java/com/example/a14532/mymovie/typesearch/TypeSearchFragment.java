package com.example.a14532.mymovie.typesearch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a14532.mymovie.R;
import com.example.a14532.mymovie.data.remote.bean.MovieBean;
import com.example.a14532.mymovie.utils.EndlessRecyclerOnScrollListener;
import com.example.a14532.mymovie.utils.MovieSmallAdapter;

public class TypeSearchFragment extends Fragment implements TypeSearchContract.View {
    private TypeSearchContract.Presenter mPresenter;
    private RecyclerView movieRecyclerView;
    private MovieSmallAdapter movieSmallAdapter;
    private TextView text_happy;
    private TextView text_afraid;
    private TextView text_love;
    private TextView text_suspense;
    private TextView text_plot;
    private TextView text_act;
    private TextView text_adventure;
    private TextView text_science;
    private TextView text_chinese;
    private TextView text_korean;
    private TextView text_western;
    private TextView text_japan;
    private TextView point;


    public static TypeSearchFragment newInstance() {
        return new TypeSearchFragment();
    }

    public TypeSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void setPresenter(TypeSearchContract.Presenter presenter){
        mPresenter=presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.type_fra, container,false);
        text_happy=root.findViewById(R.id.text_happy);
        text_afraid=root.findViewById(R.id.text_afraid);
        text_love=root.findViewById(R.id.text_love);
        text_suspense=root.findViewById(R.id.text_suspense);
        text_plot=root.findViewById(R.id.text_plot);
        text_act=root.findViewById(R.id.text_act);
        text_adventure=root.findViewById(R.id.text_adventure);
        text_science=root.findViewById(R.id.text_science);
        text_chinese=root.findViewById(R.id.text_chinese);
        text_korean=root.findViewById(R.id.text_korean);
        text_western=root.findViewById(R.id.text_western);
        text_japan=root.findViewById(R.id.text_japan);
        text_happy.setOnClickListener(new textListener());
        text_afraid.setOnClickListener(new textListener());
        text_love.setOnClickListener(new textListener());
        text_suspense.setOnClickListener(new textListener());
        text_plot.setOnClickListener(new textListener());
        text_act.setOnClickListener(new textListener());
        text_adventure.setOnClickListener(new textListener());
        text_science.setOnClickListener(new textListener());
        text_chinese.setOnClickListener(new textListener());
        text_korean.setOnClickListener(new textListener());
        text_western.setOnClickListener(new textListener());
        text_japan.setOnClickListener(new textListener());

        point = text_happy;

        movieRecyclerView = (RecyclerView) root.findViewById(R.id.type_search_recycler_view);
        // 设置布局管理器
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        movieRecyclerView.setLayoutManager(layoutManager);
        movieRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                if(movieSmallAdapter!=null)
                    mPresenter.getMoreMovie(movieSmallAdapter.getItemCount(),point.getText().toString());
            }
        });

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
            mPresenter.getTypeMovie(that.getText().toString());
        }
    }

    @Override
    public void setAdapter(MovieBean dataBean){
        movieSmallAdapter=new MovieSmallAdapter(this.getContext(),dataBean);
        movieRecyclerView.setAdapter(movieSmallAdapter);
    }

    @Override
    public void setLoadState(int state){
        switch (state) {
            case 1:
                movieSmallAdapter.setLoadState(movieSmallAdapter.LOADING);
                break;
            case 2:
                movieSmallAdapter.setLoadState(movieSmallAdapter.LOADING_COMPLETE);
                break;
            case 3:
                movieSmallAdapter.setLoadState(movieSmallAdapter.LOADING_END);
                break;
        }
    }
}
