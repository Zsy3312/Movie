package com.example.a14532.mymovie.data.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//抽象出来的Retrofit类
public class RetrofitClient {

    public MovieApi getMovieService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.douban.com/v2/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(MovieApi.class);
    }

    public UserApi getUserService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.3.6:3500/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(UserApi.class);
    }
}