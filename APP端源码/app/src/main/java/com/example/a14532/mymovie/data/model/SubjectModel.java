package com.example.a14532.mymovie.data.model;

import com.example.a14532.mymovie.data.remote.MovieApi;
import com.example.a14532.mymovie.data.remote.RetrofitClient;
import com.example.a14532.mymovie.data.remote.UserApi;
import com.example.a14532.mymovie.data.remote.bean.SubjectInfoBean;
import com.example.a14532.mymovie.data.remote.bean.ValueTextBean;
import com.example.a14532.mymovie.data.remote.onDataResponseListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//电影详情界面的的model
public class SubjectModel {
    private Call<SubjectInfoBean> call;
    private Call<ValueTextBean> userCall;

    //电影详情请求
    public void subjectRequest(final onDataResponseListener onDataResponseListener, String movieId) {
        MovieApi request = new RetrofitClient().getMovieService();

        Map<String,String> map = new HashMap<>();
        map.put("apikey","0b2bdeda43b5688921839c8ecb20399b");
        map.put("city","%E5%8C%97%E4%BA%AC");
        map.put("client","message");//无用参数
        map.put("udid","message");//无用参数
        call = request.getSubjectInfoCall(movieId,map);
        call.enqueue(new Callback<SubjectInfoBean>() {
            @Override
            public void onResponse(Call<SubjectInfoBean> call, Response<SubjectInfoBean> response) {
                SubjectInfoBean subjectInfoBean = response.body();
                if (subjectInfoBean != null){
                    onDataResponseListener.onSuccess(subjectInfoBean);
                }
            }
            @Override
            public void onFailure(Call<SubjectInfoBean> call, Throwable t) {
                onDataResponseListener.onFailed(t.getMessage());
            }
        });
    }

    //收藏电影
    public void onCollectRequest(final onDataResponseListener onDataResponseListener, String movieId,String movieTitle,String username,String year,String type,String language,String director) {
        UserApi request = new RetrofitClient().getUserService();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        String collectDate = simpleDateFormat.format(date);

        Map<String,String> map = new HashMap<>();
        map.put("username",username);
        map.put("movieId",movieId);
        map.put("movieTitle",movieTitle);
        map.put("time",collectDate);
        map.put("movieType",type);
        map.put("movieYear",year);
        map.put("movieLanguage",language);
        map.put("movieDirector",director);

        userCall = request.getCollectCall(map);
        userCall.enqueue(new Callback<ValueTextBean>() {
            @Override
            public void onResponse(Call<ValueTextBean> call, Response<ValueTextBean> response) {
                ValueTextBean ValueTextBean = response.body();
                if (ValueTextBean != null){
                    onDataResponseListener.onSuccess(ValueTextBean);
                }
            }
            @Override
            public void onFailure(Call<ValueTextBean> call, Throwable t) {
                onDataResponseListener.onFailed(t.getMessage());
            }
        });
    }

    //取消收藏
    public void onCancelCollectRequest(final onDataResponseListener onDataResponseListener, String movieId,String username) {
        UserApi request = new RetrofitClient().getUserService();

        Map<String,String> map = new HashMap<>();
        map.put("username",username);
        map.put("movieId",movieId);
        userCall = request.getCancelCollectCall(map);
        userCall.enqueue(new Callback<ValueTextBean>() {
            @Override
            public void onResponse(Call<ValueTextBean> call, Response<ValueTextBean> response) {
                ValueTextBean ValueTextBean = response.body();
                if (ValueTextBean != null){
                    onDataResponseListener.onSuccess(ValueTextBean);
                }
            }
            @Override
            public void onFailure(Call<ValueTextBean> call, Throwable t) {
                onDataResponseListener.onFailed(t.getMessage());
            }
        });
    }

    //收藏与否
    public void onCollectOrNotRequest(final onDataResponseListener onDataResponseListener, String movieId,String username) {
        UserApi request = new RetrofitClient().getUserService();

        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("movieId", movieId);
        userCall = request.getCollectOrNotCall(map);
        userCall.enqueue(new Callback<ValueTextBean>() {
            @Override
            public void onResponse(Call<ValueTextBean> call, Response<ValueTextBean> response) {
                ValueTextBean ValueTextBean = response.body();
                if (ValueTextBean != null) {
                    onDataResponseListener.onSuccess(ValueTextBean);
                }
            }

            @Override
            public void onFailure(Call<ValueTextBean> call, Throwable t) {
                onDataResponseListener.onFailed(t.getMessage());
            }
        });
    }

    //添加浏览记录
    public void onAddHistoryRequest(final onDataResponseListener onDataResponseListener, String movieId,String movieTitle,String username) {
        UserApi request = new RetrofitClient().getUserService();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        String collectDate = simpleDateFormat.format(date);

        Map<String,String> map = new HashMap<>();
        map.put("username",username);
        map.put("movieId",movieId);
        map.put("movieTitle",movieTitle);
        map.put("time",collectDate);

        userCall = request.addHistoryCall(map);
        userCall.enqueue(new Callback<ValueTextBean>() {
            @Override
            public void onResponse(Call<ValueTextBean> call, Response<ValueTextBean> response) {
                ValueTextBean ValueTextBean = response.body();
                if (ValueTextBean != null){
                    onDataResponseListener.onSuccess(ValueTextBean);
                }
            }
            @Override
            public void onFailure(Call<ValueTextBean> call, Throwable t) {
                onDataResponseListener.onFailed(t.getMessage());
            }
        });
    }

    //删除浏览记录
    public void onDeleteHistoryReuest(final onDataResponseListener onDataResponseListener, String movieId,String username) {
        UserApi request = new RetrofitClient().getUserService();

        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("movieId", movieId);
        userCall = request.deleteHistoryCall(map);
        userCall.enqueue(new Callback<ValueTextBean>() {
            @Override
            public void onResponse(Call<ValueTextBean> call, Response<ValueTextBean> response) {
                ValueTextBean ValueTextBean = response.body();
                if (ValueTextBean != null) {
                    onDataResponseListener.onSuccess(ValueTextBean);
                }
            }
            @Override
            public void onFailure(Call<ValueTextBean> call, Throwable t) {
                onDataResponseListener.onFailed(t.getMessage());
            }
        });
    }
}
