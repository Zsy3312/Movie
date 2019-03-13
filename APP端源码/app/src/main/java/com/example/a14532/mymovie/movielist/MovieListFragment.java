package com.example.a14532.mymovie.movielist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.a14532.mymovie.R;
import com.example.a14532.mymovie.data.remote.bean.CollectMovieBean;
import com.example.a14532.mymovie.data.remote.bean.MovieBean;
import com.example.a14532.mymovie.data.remote.bean.RecommendMovieBean;
import com.example.a14532.mymovie.utils.CollectListAdapter;
import com.example.a14532.mymovie.utils.EndlessRecyclerOnScrollListener;
import com.example.a14532.mymovie.utils.MovieAdapter;
import com.example.a14532.mymovie.utils.RecommendListAdapter;


public class MovieListFragment extends Fragment implements MovieListContract.View{
    private MovieListContract.Presenter mPresenter;
    RecyclerView mRecyclerView;
    MovieAdapter mMovieAdapter;


    public static MovieListFragment newInstance() {
        return new MovieListFragment();
    }

    public MovieListFragment() {
        // Required empty public constructor
    }

    @Override
    public void setPresenter(MovieListContract.Presenter presenter){
        mPresenter=presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.movie_list_fra, container,false);

        final Intent intent=getActivity().getIntent();//获取上个activity参数

        // 得到控件
        mRecyclerView = (RecyclerView) root.findViewById(R.id.movie_list_recycler_view);
        // 设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                if(mMovieAdapter!=null)
                    mPresenter.getMoreMovie(mMovieAdapter.getItemCount(),intent);
            }
        });
        mPresenter.getListManage(intent);

        return root;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setAdapter(MovieBean dataBean){
        mMovieAdapter=new MovieAdapter(this.getContext(),dataBean);
        mRecyclerView.setAdapter(mMovieAdapter);
    }

    @Override
    public void setAdapter(CollectMovieBean dataBean){
        mRecyclerView.setAdapter(new CollectListAdapter(this.getContext(),dataBean));
    }

    @Override
    public void setAdapter(RecommendMovieBean dataBean){
        mRecyclerView.setAdapter(new RecommendListAdapter(this.getContext(),dataBean));
    }

    @Override
    public void setLoadState(int state){
        switch (state) {
            case 1:
                mMovieAdapter.setLoadState(mMovieAdapter.LOADING);
                break;
            case 2:
                mMovieAdapter.setLoadState(mMovieAdapter.LOADING_COMPLETE);
                break;
            case 3:
                mMovieAdapter.setLoadState(mMovieAdapter.LOADING_END);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.afterBackRefresh();
    }
}
