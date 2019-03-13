package com.example.a14532.mymovie.root.findmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a14532.mymovie.R;
import com.example.a14532.mymovie.login.LoginActivity;
import com.example.a14532.mymovie.movielist.MovieListActivity;
import com.example.a14532.mymovie.search.SearchActivity;
import com.example.a14532.mymovie.typesearch.TypeSearchActivity;

public class FindMovieFragment extends Fragment implements FindMovieContract.View {

    private FindMovieContract.Presenter mPresenter;
    public SearchView mSearchView;
    public TextView guessYouLike;
    public TextView typeSearch;
    public TextView top250;
    public TextView newMovie;

    public static FindMovieFragment newInstance() {
        return new FindMovieFragment();
    }

    public FindMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void setPresenter(FindMovieContract.Presenter presenter){
        mPresenter=presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.find_movie_fra, container,false);
        mSearchView=root.findViewById(R.id.searchView);
        mSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.toSearchAct();
            }
        });
        guessYouLike=root.findViewById(R.id.guessYouLike);
        guessYouLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.toGuessYouLikeAct();
            }
        });
        typeSearch=root.findViewById(R.id.typeSearch);
        typeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.toTypeSearchAct();
            }
        });
        top250=root.findViewById(R.id.top250);
        top250.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.toMovieListAct("top250");
            }
        });
        newMovie=root.findViewById(R.id.newMovie);
        newMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.toMovieListAct("new_movies");
            }
        });
        return root;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSearchAct(){
        Intent intent = new Intent(getContext(), SearchActivity.class);
        startActivity(intent);
    }
    @Override
    public void showMovieListAct(String path){
        Intent intent = new Intent(getContext(), MovieListActivity.class);
        intent.putExtra("path",path);//activity之间传递参数
        startActivity(intent);
    }

    @Override
    public void showTypeSearchAct(){
        Intent intent = new Intent(getContext(), TypeSearchActivity.class);
        startActivity(intent);
    }
    @Override
    public void showLoginAct(){
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }
}