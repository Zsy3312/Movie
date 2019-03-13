package com.example.a14532.mymovie.movielist;

import android.app.Application;
import android.content.Intent;

import com.example.a14532.mymovie.data.model.MovieListModel;
import com.example.a14532.mymovie.data.remote.bean.CollectMovieBean;
import com.example.a14532.mymovie.data.remote.bean.MovieBean;
import com.example.a14532.mymovie.data.remote.bean.RecommendMovieBean;
import com.example.a14532.mymovie.data.remote.onDataResponseListener;
import com.example.a14532.mymovie.data.local.GlobalData;

import java.util.HashMap;
import java.util.Map;


public class MovieListPresenter implements MovieListContract.Presenter {
    private MovieListContract.View mMovieListView;
    private MovieListModel mMovieListModel;
    private GlobalData globalData;
    private String path="";
    private int totalCount;
    private int start = 0;
    private int count = 20;
    private MovieBean movieData;
    private int loading=0;

    @Override
    public void start(){

    }

    public MovieListPresenter(MovieListContract.View movieListView , MovieListModel movieListModel, Application data) {
        mMovieListView=movieListView;
        mMovieListModel=movieListModel;
        globalData=(GlobalData) data;
        movieListView.setPresenter(this);
    }

    @Override
    public void getListManage(Intent intent){
        path=intent.getStringExtra("path");
        switch (path) {
            case "collect":
                this.getCollectList();
                break;
            case "history":
                this.getHistoryList();
                break;
            case "recommend":
                this.getRecommendList();
                break;
            default:
                this.getMovieList(intent);
                break;
        }
    }

    @Override
    public void getMovieList(Intent intent){
        Map<String,String> map = new HashMap<>();
        if(path.equals("collect")||path.equals("history")||path.equals("recommend")){
            return;
        }
        if(path.equals("search")){
            //搜索的参数
            String type=intent.getStringExtra("type");
            if(type.equals("query")){
                map.put("q",intent.getStringExtra("query"));
            }
            else if(type.equals("tag")){
                map.put("tag",intent.getStringExtra("tag"));
            }
        }
        map.put("apikey","0b2bdeda43b5688921839c8ecb20399b");
        map.put("start",String.valueOf(start));
        map.put("count",String.valueOf(count));
        map.put("client","message");//无用参数
        map.put("udid","message");//无用参数
        mMovieListModel.movieListRequest(new onDataResponseListener<MovieBean>() {
            @Override
            public void onSuccess(MovieBean dataBean) {
                if(start==0){//第一次
                    movieData =dataBean;
                    totalCount=dataBean.getTotal();
                    mMovieListView.setAdapter(movieData);
                    start=start+count;
                }
                else {
                    for(int i=0;i<dataBean.getSubjects().size();i++)
                    {
                        movieData.getSubjects().add(dataBean.getSubjects().get(i));
                    }
                    start=start+count;
                    mMovieListView.setLoadState(2);
                    loading =0;
                }
            }
            @Override public void onFailed(String string) {
                mMovieListView.showMessage(string);
                mMovieListView.setLoadState(2);
                loading =0;
            }
        },path,map);
    }

    @Override
    public void getCollectList(){
        String username = globalData.getUserName();
        mMovieListModel.collectListRequest(new onDataResponseListener<CollectMovieBean>() {
            @Override
            public void onSuccess(CollectMovieBean dataBean) {
                mMovieListView.setAdapter(dataBean);
            }
            @Override public void onFailed(String string) {
                mMovieListView.showMessage(string);
            }
        },username);
    }

    @Override
    public void getHistoryList(){
        String username = globalData.getUserName();
        mMovieListModel.historyListRequest(new onDataResponseListener<CollectMovieBean>() {
            @Override
            public void onSuccess(CollectMovieBean dataBean) {
                mMovieListView.setAdapter(dataBean);
            }
            @Override public void onFailed(String string) {
                mMovieListView.showMessage(string);
            }
        },username);
    }

    @Override
    public void getRecommendList(){
        String username = globalData.getUserName();
        mMovieListModel.recommendListRequest(new onDataResponseListener<RecommendMovieBean>() {
            @Override
            public void onSuccess(RecommendMovieBean dataBean) {
                mMovieListView.setAdapter(dataBean);
            }
            @Override public void onFailed(String string) {
                mMovieListView.showMessage(string);
            }
        },username);
    }

    @Override
    public void getMoreMovie(int size,Intent intent){
        if(loading ==0){//未在加载
            loading=1;
        }
        else if(loading ==1 ){//加载中
            return;
        }
        mMovieListView.setLoadState(1);

        if (size<totalCount) {
            // 获取网络数据
            getMovieList(intent);
        } else {
            // 显示加载到底的提示
            loading =0;
            mMovieListView.setLoadState(3);
        }
    }

    @Override
    public void afterBackRefresh(){
        if(path.equals("collect")){
            getCollectList();
        }
        else if(path.equals("history")){
            getHistoryList();
        }
    }
}