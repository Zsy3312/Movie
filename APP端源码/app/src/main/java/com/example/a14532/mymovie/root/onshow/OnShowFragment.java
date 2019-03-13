package com.example.a14532.mymovie.root.onshow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.a14532.mymovie.R;
import com.example.a14532.mymovie.data.remote.bean.MovieBean;
import com.example.a14532.mymovie.utils.EndlessRecyclerOnScrollListener;
import com.example.a14532.mymovie.utils.MovieAdapter;


public class OnShowFragment extends Fragment implements OnShowContract.View{
    private OnShowContract.Presenter mPresenter;
    RecyclerView mRecyclerView;
    MovieAdapter mMovieAdapter;

    public static OnShowFragment newInstance() {
        return new OnShowFragment();
    }

    public OnShowFragment() {
        // Required empty public constructor
    }

    @Override
    public void setPresenter(OnShowContract.Presenter presenter){
        mPresenter=presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.on_show_fra, container,false);
        // 得到控件
        mRecyclerView = (RecyclerView) root.findViewById(R.id.on_show_recycler_view);
        // 设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
                @Override
                public void onLoadMore() {
                    mPresenter.getMoreMovie(mMovieAdapter.getItemCount());
                }
        });
        mPresenter.getOnShowMovie();

        return root;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setAdapter(MovieBean dataBean){
        mMovieAdapter = new MovieAdapter(this.getContext(),dataBean);
        mRecyclerView.setAdapter(mMovieAdapter);
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

}
