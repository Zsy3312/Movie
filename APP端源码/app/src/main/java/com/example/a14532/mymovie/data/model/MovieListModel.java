package com.example.a14532.mymovie.data.model;

import com.example.a14532.mymovie.data.remote.MovieApi;
import com.example.a14532.mymovie.data.remote.RetrofitClient;
import com.example.a14532.mymovie.data.remote.UserApi;
import com.example.a14532.mymovie.data.remote.bean.CollectMovieBean;
import com.example.a14532.mymovie.data.remote.bean.MovieBean;
import com.example.a14532.mymovie.data.remote.bean.RecommendMovieBean;
import com.example.a14532.mymovie.data.remote.bean.ValueTextBean;
import com.example.a14532.mymovie.data.remote.onDataResponseListener;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//电影列表的model
public class MovieListModel {
    private Call<MovieBean> call;
    private Call<CollectMovieBean> collectCall;
    private Call<RecommendMovieBean> recommendCall;

    //电影列表请求
    public void movieListRequest(final onDataResponseListener onDataResponseListener,String path,Map map) {
        MovieApi request = new RetrofitClient().getMovieService();

        call = request.getMovieListCall(path,map);
        call.enqueue(new Callback<MovieBean>() {
            @Override
            public void onResponse(Call<MovieBean> call, Response<MovieBean> response) {
                MovieBean movieBean = response.body();
                if (movieBean != null){
                    onDataResponseListener.onSuccess(movieBean);
                }
            }
            @Override
            public void onFailure(Call<MovieBean> call, Throwable t) {
                onDataResponseListener.onFailed(t.getMessage());
            }
        });
    }

    //收藏列表
    public void collectListRequest(final onDataResponseListener onDataResponseListener, String username) {
        UserApi request = new RetrofitClient().getUserService();

        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        collectCall = request.getCollectListCall(map);
        collectCall.enqueue(new Callback<CollectMovieBean>() {
            @Override
            public void onResponse(Call<CollectMovieBean> call, Response<CollectMovieBean> response) {
                CollectMovieBean collectMovieBean = response.body();
                if (collectMovieBean != null) {
                    onDataResponseListener.onSuccess(collectMovieBean);
                }
            }

            @Override
            public void onFailure(Call<CollectMovieBean> call, Throwable t) {
                onDataResponseListener.onFailed(t.getMessage());
            }
        });
    }
    //历史记录列表
    public void historyListRequest(final onDataResponseListener onDataResponseListener, String username) {
        UserApi request = new RetrofitClient().getUserService();

        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        collectCall = request.getHistoryListCall(map);
        collectCall.enqueue(new Callback<CollectMovieBean>() {
            @Override
            public void onResponse(Call<CollectMovieBean> call, Response<CollectMovieBean> response) {
                CollectMovieBean collectMovieBean = response.body();
                if (collectMovieBean != null) {
                    onDataResponseListener.onSuccess(collectMovieBean);
                }
            }
            @Override
            public void onFailure(Call<CollectMovieBean> call, Throwable t) {
                onDataResponseListener.onFailed(t.getMessage());
            }
        });
    }

    //推荐电影列表
    public void recommendListRequest(final onDataResponseListener onDataResponseListener, String username) {
        UserApi request = new RetrofitClient().getUserService();

        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        recommendCall = request.getRecommendCall(map);
        recommendCall.enqueue(new Callback<RecommendMovieBean>() {
            @Override
            public void onResponse(Call<RecommendMovieBean> call, Response<RecommendMovieBean> response) {
                RecommendMovieBean recommendMovieBean = response.body();
                if (recommendMovieBean != null) {
                    onDataResponseListener.onSuccess(recommendMovieBean);
                }
            }
            @Override
            public void onFailure(Call<RecommendMovieBean> call, Throwable t) {
                onDataResponseListener.onFailed(t.getMessage());
            }
        });
    }

}
