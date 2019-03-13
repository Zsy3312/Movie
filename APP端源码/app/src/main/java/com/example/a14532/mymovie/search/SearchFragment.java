package com.example.a14532.mymovie.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.a14532.mymovie.R;
import com.example.a14532.mymovie.movielist.MovieListActivity;

import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchView;
import scut.carson_ho.searchview.bCallBack;

public class SearchFragment extends Fragment implements SearchContract.View {
    private SearchContract.Presenter mPresenter;
    private SearchView searchView;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter){
        mPresenter=presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.search_fra, container,false);
        searchView = (SearchView) root.findViewById(R.id.movie_search_view);
        // 设置点击键盘上的搜索按键后的操作（通过回调接口）
        // 参数 = 搜索框输入的内容
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                mPresenter.toMovieListAct(string);
            }
        });

        // 5. 设置点击返回按键后的操作（通过回调接口）
        searchView.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                getActivity().finish();
            }
        });
        return root;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMovieListAct(String query,String path,String type ){
        Intent intent = new Intent(getContext(), MovieListActivity.class);
        intent.putExtra("path",path);//activity之间传递参数
        intent.putExtra("type",type);
        intent.putExtra("query",query);
        startActivity(intent);
    }
}
