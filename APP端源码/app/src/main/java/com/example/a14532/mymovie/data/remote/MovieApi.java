package com.example.a14532.mymovie.data.remote;

import com.example.a14532.mymovie.data.remote.bean.MovieBean;
import com.example.a14532.mymovie.data.remote.bean.SubjectInfoBean;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
//豆瓣电影API
public interface MovieApi {
    @GET("in_theaters")
    Call<MovieBean> getOnShowCall(
            @QueryMap Map<String, String> map
    );

    @GET("subject/{movieId}")
    Call<SubjectInfoBean> getSubjectInfoCall(
            @Path("movieId") String movieId,
            @QueryMap Map<String, String> map
    );

    @GET("{path}")
    Call<MovieBean> getMovieListCall(
            @Path("path") String path,
            @QueryMap Map<String, String> map
    );
}
