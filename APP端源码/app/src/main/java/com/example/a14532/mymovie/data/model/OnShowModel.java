package com.example.a14532.mymovie.data.model;

import com.example.a14532.mymovie.data.remote.RetrofitClient;
import com.example.a14532.mymovie.data.remote.bean.MovieBean;
import com.example.a14532.mymovie.data.remote.onDataResponseListener;
import com.example.a14532.mymovie.data.remote.MovieApi;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//热映列表的model
public class OnShowModel {
    private Call<MovieBean> call;

    //热映请求
    public void onShowRequest(final onDataResponseListener onDataResponseListener, String start, String count) {
        MovieApi request = new RetrofitClient().getMovieService();

        Map<String,String> map = new HashMap<>();
        map.put("apikey","0b2bdeda43b5688921839c8ecb20399b");
        map.put("city","%E5%8C%97%E4%BA%AC");
        map.put("start",start);
        map.put("count",count);
        map.put("client","message");//无用参数
        map.put("udid","message");//无用参数
        call = request.getOnShowCall(map);
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
}
