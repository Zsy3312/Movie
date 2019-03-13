package com.example.a14532.mymovie.data.model;

import com.example.a14532.mymovie.data.remote.RetrofitClient;
import com.example.a14532.mymovie.data.remote.UserApi;
import com.example.a14532.mymovie.data.remote.bean.DataCountBean;
import com.example.a14532.mymovie.data.remote.onDataResponseListener;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//电影统计界面的model
public class MovieCountModel {
    private Call<DataCountBean> movieCountCall;

    public void movieCountRequest(final onDataResponseListener onDataResponseListener) {
        UserApi request = new RetrofitClient().getUserService();

        movieCountCall = request.getMovieCountCall();
        movieCountCall.enqueue(new Callback<DataCountBean>() {
            @Override
            public void onResponse(Call<DataCountBean> call, Response<DataCountBean> response) {
                DataCountBean dataCountBean = response.body();
                if (dataCountBean != null) {
                    onDataResponseListener.onSuccess(dataCountBean);
                }
            }
            @Override
            public void onFailure(Call<DataCountBean> call, Throwable t) {
                onDataResponseListener.onFailed(t.getMessage());
            }
        });
    }
}
