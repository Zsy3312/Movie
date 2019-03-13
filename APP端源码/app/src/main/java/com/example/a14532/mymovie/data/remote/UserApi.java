package com.example.a14532.mymovie.data.remote;

import com.example.a14532.mymovie.data.remote.bean.CollectMovieBean;
import com.example.a14532.mymovie.data.remote.bean.DataCountBean;
import com.example.a14532.mymovie.data.remote.bean.RecommendMovieBean;
import com.example.a14532.mymovie.data.remote.bean.UserCountBean;
import com.example.a14532.mymovie.data.remote.bean.ValueTextBean;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
//访问后端的API
public interface UserApi {
    @GET("login")
    Call<ValueTextBean> getLoginCall(
            @QueryMap Map<String, String> map
    );

    @GET("register")
    Call<ValueTextBean> getRegisterCall(
            @QueryMap Map<String, String> map
    );

    @GET("collect")
    Call<ValueTextBean> getCollectCall(
            @QueryMap Map<String, String> map
    );

    @GET("cancel_collect")
    Call<ValueTextBean> getCancelCollectCall(
            @QueryMap Map<String, String> map
    );

    @GET("collect_or_not")
    Call<ValueTextBean> getCollectOrNotCall(
            @QueryMap Map<String, String> map
    );

    @GET("collect_display")
    Call<CollectMovieBean>getCollectListCall(
            @QueryMap Map<String, String> map
    );

    @GET("history_display")
    Call<CollectMovieBean>getHistoryListCall(
            @QueryMap Map<String, String> map
    );

    @GET("add_history")
    Call<ValueTextBean>addHistoryCall(
            @QueryMap Map<String, String> map
    );

    @GET("delete_history")
    Call<ValueTextBean>deleteHistoryCall(
            @QueryMap Map<String, String> map
    );

    @GET("update_photo")
    Call<ValueTextBean>updatePhotoCall(
            @QueryMap Map<String, String> map
    );

    @GET("guess_you_like")
    Call<RecommendMovieBean>getRecommendCall(
            @QueryMap Map<String, String> map
    );

    @GET("get_movie_count")
    Call<DataCountBean>getMovieCountCall();

    @GET("get_user_count")
    Call<UserCountBean>getUserCountCall();
}
